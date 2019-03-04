package com.example.xdev.perkid.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xdev.perkid.R;
import com.example.xdev.perkid.adapters.SocialMediaAdapter;
import com.example.xdev.perkid.models.History;
import com.example.xdev.perkid.models.SocialMedia;
import com.example.xdev.perkid.models.SocialMediaAdapterModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class KidsFragment extends Fragment implements SocialMediaAdapter.ListItemClickListener {

    RecyclerView rv_socialMedia;
    List<SocialMediaAdapterModel> socialMediaList;
    SocialMediaAdapter socialMediaAdapter;
    Realm realm;
    String currentUserName;

    public KidsFragment() {
        // Required empty public constructor
    }

    public static KidsFragment newInstance(String username) {
        KidsFragment fragment = new KidsFragment();
        Bundle kidsBundle = new Bundle();
        kidsBundle.putString("KIDS_USER_NAME", username);
        fragment.setArguments(kidsBundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_kids, container, false);

        realm = Realm.getDefaultInstance();

        currentUserName = getArguments().getString("KIDS_USER_NAME");

        rv_socialMedia = rootView.findViewById(R.id.rv_socialMedia);
        socialMediaList = new RealmList<>();

        getSocialMediaView();
        //List get when user name;
        socialMediaAdapter = new SocialMediaAdapter(getContext(), socialMediaList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rv_socialMedia.setLayoutManager(gridLayoutManager);
        rv_socialMedia.setHasFixedSize(false);
        rv_socialMedia.setAdapter(socialMediaAdapter);

        return rootView;
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {
        sumUpNumberOFClicks(socialMediaList.get(clickedItemIndex).getName());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateFormat.format(c.getTime());

        addToHistory(socialMediaList.get(clickedItemIndex).getName(), datetime);
    }

    void sumUpNumberOFClicks(final String socialMediaName) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                SocialMedia socialMedia = realm.where(SocialMedia.class)
                        .equalTo("username", currentUserName)
                        .equalTo("name", socialMediaName)
                        .findFirst();

                int visitTimes = socialMedia.getVisitTimes();
                visitTimes++;
                socialMedia.setVisitTimes(visitTimes);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                openBrowser(socialMediaName);
                getSocialMediaView();
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

    void getSocialMediaView() {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                socialMediaList.clear();
                RealmResults<SocialMedia> realmResults = realm.where(SocialMedia.class)
                        .equalTo("username", currentUserName)
                        .findAll();

                for (int i = 0; i < realmResults.size(); i++) {
                    SocialMediaAdapterModel socialMediaAdapterModel =
                            new SocialMediaAdapterModel(realmResults.get(i).getUsername(),
                                    realmResults.get(i).getVisitTimes(),
                                    realmResults.get(i).getName());
                    socialMediaList.add(socialMediaAdapterModel);
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                socialMediaAdapter.notifyDataSetChanged();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void addToHistory(final String socialMediaName, final String socialMediaTimeAndDate) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                History history = realm.createObject(History.class);
                history.setKidUsername(currentUserName);
                history.setSocialMediaName(socialMediaName);
                history.setSocialMediaTimeAndData(socialMediaTimeAndDate);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });

    }

    void openBrowser(String socialMediaName) {
        String URL = "";
        if (socialMediaName.equals("youtube"))
            URL = "https://www.youtube.com/";

        if (socialMediaName.equals("facebook"))
            URL = "https://www.facebook.com/";

        if (socialMediaName.equals("instagram"))
            URL = "https://www.instagram.com/";

        if (socialMediaName.equals("pinterest"))
            URL = "https://www.pinterest.com/";

        if (socialMediaName.equals("snapchat"))
            URL = "https://www.snapchat.com/";

        if (socialMediaName.equals("twitter"))
            URL = "https://twitter.com/";

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
    }
}


