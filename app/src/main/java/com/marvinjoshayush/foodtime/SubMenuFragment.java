package com.marvinjoshayush.foodtime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SubMenuFragment extends Fragment {
    private HomeActivity home;
    private Fragment returnFragment;
    private View view;
    private ImageView logo;
    private ArrayList<View> viewsToAdd;
    private LinearLayout parentLayout;

    public SubMenuFragment(HomeActivity home, ImageView logo, Fragment returnFragment, ArrayList<View> viewsToAdd) {
        this.home = home;
        this.returnFragment = returnFragment;
        this.logo = logo;
        this.viewsToAdd = viewsToAdd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sub_menu, container, false);
        parentLayout = view.findViewById(R.id.subMenuFragment_subMenu);
        addViewsToAdd();

        setButtons();

        return view;
    }

    private void addViewsToAdd() {
        ((LinearLayout) view.findViewById(R.id.subMenuFragment_logoParent)).addView(logo);
        for(View v : viewsToAdd) {
            parentLayout.addView(v);
        }
    }

    private void setButtons() {
        view.findViewById(R.id.subMenu_returnToMenu).setOnClickListener(item -> {
            home.setFragment(returnFragment);
        });
    }
}
