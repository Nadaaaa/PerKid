package com.example.xdev.perkid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.xdev.perkid.R;
import com.example.xdev.perkid.adapters.HistoryAdapter;
import com.example.xdev.perkid.models.History;
import com.example.xdev.perkid.models.HistoryAdapterModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ParentsFragment extends Fragment {

    RecyclerView rv_history;
    List<HistoryAdapterModel> historyList;
    HistoryAdapter historyAdapter;
    Realm realm;
    String kidUserName;

    public ParentsFragment() {
        // Required empty public constructor
    }

    public static ParentsFragment newInstance(String username) {
        ParentsFragment fragment = new ParentsFragment();
        Bundle kidsBundle = new Bundle();
        kidsBundle.putString("KIDS_USER_NAME", username);
        fragment.setArguments(kidsBundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_parents, container, false);

        realm = Realm.getDefaultInstance();

        kidUserName = getArguments().getString("KIDS_USER_NAME");

        rv_history = rootView.findViewById(R.id.rv_history);
        historyList = new ArrayList<>();

        getHistoryView();

        historyAdapter = new HistoryAdapter(getContext(), historyList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_history.setLayoutManager(linearLayoutManager);
        rv_history.setHasFixedSize(false);
        rv_history.setAdapter(historyAdapter);

        return rootView;
    }

    void getHistoryView() {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                historyList.clear();
                RealmResults<History> realmResults = realm.where(History.class)
                        .equalTo("kidUsername", kidUserName)
                        .findAll();

                for (int i = 0; i < realmResults.size(); i++) {
                    HistoryAdapterModel historyAdapterModel = new HistoryAdapterModel
                            (realmResults.get(i).getSocialMediaName(),
                                    realmResults.get(i).getSocialMediaTimeAndData());
                    historyList.add(historyAdapterModel);
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                historyAdapter.notifyDataSetChanged();
                // Transaction was a success.
                // Toast.makeText(MainActivity.this, "EditSuccess", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                //Toast.makeText(MainActivity.this, "EditError", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
