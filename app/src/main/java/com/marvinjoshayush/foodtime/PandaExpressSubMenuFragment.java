package com.marvinjoshayush.foodtime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class PandaExpressSubMenuFragment extends Fragment {
    private HomeActivity home;
    private View view;
    private ArrayList<View> viewsToAdd;
    private HashMap<String, ImageButtonInfo> buttonInfo;
    private int[] maxSelectionForSections;
    private ArrayList<ArrayList<ImageButton>> imageButtonForSections;
    private LinearLayout parentLayout;
    private float price;

    public PandaExpressSubMenuFragment(HomeActivity home, ArrayList<View> viewsToAdd) {
        this.home = home;
        this.viewsToAdd = viewsToAdd;
    }

    public PandaExpressSubMenuFragment(HomeActivity home, ArrayList<View> viewsToAdd,
                                       HashMap<String, ImageButtonInfo> buttonInfo, int[] maxSelectionForSections, float price) {
        this.home = home;
        this.viewsToAdd = viewsToAdd;
        this.maxSelectionForSections = maxSelectionForSections;
        this.buttonInfo = buttonInfo;
        this.imageButtonForSections = new ArrayList<>();
        this.price = price;

        for(int i = 0; i < maxSelectionForSections.length; i++) { imageButtonForSections.add(new ArrayList<>()); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_panda_express_sub_menu, container, false);
        parentLayout = view.findViewById(R.id.pandaExpress_subMenu);
        addViewsToAdd();

        setButtons();

        return view;
    }

    private void addViewsToAdd() {
        for(View v : viewsToAdd) {
            parentLayout.addView(v);
        }
    }

    private void setButtons() {
        view.findViewById(R.id.pandaExpress_returnToMenu).setOnClickListener(item -> {
            home.setFragment(new PandaExpressFragment(home));
        });

        if(buttonInfo != null) {
            // Get ImageButtons for each sections
            int curr = 0;
            for (View x : viewsToAdd) {
                if (x instanceof ImageButton) {
                    imageButtonForSections.get(curr).add((ImageButton) x);
                } else if (imageButtonForSections.get(curr).size() > 0) {
                    curr++;
                }
            }

            // Set on click
            for(ArrayList<ImageButton> section : imageButtonForSections) {
                for(ImageButton x : section) {
                    x.setOnClickListener(item -> {
                    });
                }
            }
        }
    }
}