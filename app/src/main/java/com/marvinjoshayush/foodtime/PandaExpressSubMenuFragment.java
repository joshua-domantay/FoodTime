package com.marvinjoshayush.foodtime;

import android.graphics.Color;
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
    private ArrayList<ArrayList<ImageButtonInfo>> imageButtonInfoSections;
    private int[] maxSelectionForSections;
    private LinearLayout parentLayout;
    private float price;

    public PandaExpressSubMenuFragment(HomeActivity home, ArrayList<View> viewsToAdd) {
        this.home = home;
        this.viewsToAdd = viewsToAdd;
    }

    public PandaExpressSubMenuFragment(HomeActivity home, ArrayList<View> viewsToAdd,
                                       ArrayList<ImageButtonInfo> imageButtonInfos, int[] maxSelectionForSections, float price) {
        this.home = home;
        this.viewsToAdd = viewsToAdd;
        this.imageButtonInfoSections = new ArrayList<>();
        this.maxSelectionForSections = maxSelectionForSections;
        this.price = price;

        for(int i = 0; i < maxSelectionForSections.length; i++) { imageButtonInfoSections.add(new ArrayList<>()); }
        for(ImageButtonInfo ibf : imageButtonInfos) {
            imageButtonInfoSections.get(ibf.section).add(ibf);
        }
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

        if(maxSelectionForSections != null) {
            for(ArrayList<ImageButtonInfo> section : imageButtonInfoSections) {
                for (ImageButtonInfo button : section) {
                    button.getButton().setOnClickListener(item -> {
                        if (button.selected) {
                            maxSelectionForSections[button.section]++;
                            button.selected = false;
                            button.getButton().setColorFilter(Color.argb(0, 0, 0, 0));
                        } else if (maxSelectionForSections[button.section] > 0) {
                            maxSelectionForSections[button.section]--;
                            button.selected = true;
                            button.getButton().setColorFilter(Color.argb(50, 0, 0, 0));
                        }
                    });
                }
            }
        } else {
            for(View x : viewsToAdd) {
                if(x instanceof ImageButton) {
                    x.setOnClickListener(item -> {
                        // Add to cart
                    });
                }
            }
        }

        // Add to cart button
    }
}