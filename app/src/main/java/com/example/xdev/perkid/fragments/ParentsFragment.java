package com.example.xdev.perkid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xdev.perkid.R;

public class ParentsFragment extends Fragment {

    public ParentsFragment() {
        // Required empty public constructor
    }

    public static ParentsFragment newInstance(String param1, String param2) {
        ParentsFragment fragment = new ParentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parents, container, false);
    }

}
