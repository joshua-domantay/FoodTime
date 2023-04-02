package com.marvinjoshayush.foodtime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class PandaExpressSubMenuFragment extends Fragment {
    private HomeActivity home;
    private View view;
    private ArrayList<View> viewsToAdd;
    private LinearLayout parentLayout;

    public PandaExpressSubMenuFragment(HomeActivity home, ArrayList<View> viewsToAdd) {
        this.home = home;
        this.viewsToAdd = viewsToAdd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_panda_express_sub_menu, container, false);
        parentLayout = view.findViewById(R.id.pandaExpress_subMenu);
        addViewsToAdd();
        return view;
    }

    private void addViewsToAdd() {
        for(View v : viewsToAdd) {
            parentLayout.addView(v);
        }
    }
}