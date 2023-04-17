package com.marvinjoshayush.foodtime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SubwayFragment extends Fragment {
    private HomeActivity home;
    private View view;

    public SubwayFragment(HomeActivity home) {
        this.home = home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subway, container, false);
        return view;
    }
}
