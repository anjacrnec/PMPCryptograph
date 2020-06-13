package com.example.pmpcryptograph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class LoadingFragment extends Fragment {


    public LoadingFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static LoadingFragment newInstance(String param1, String param2) {
        LoadingFragment fragment = new LoadingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_loading, container, false);

        return v;
    }
}
