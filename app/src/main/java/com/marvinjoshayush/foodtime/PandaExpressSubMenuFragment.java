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
    private String itemName;
    private String imageStr;
    private LinearLayout parentLayout;
    private float price;

    public PandaExpressSubMenuFragment(HomeActivity home, ArrayList<View> viewsToAdd,
                                       ArrayList<ImageButtonInfo> imageButtonInfos, float price) {
        this.home = home;
        this.viewsToAdd = viewsToAdd;
        this.imageButtonInfoSections = new ArrayList<>();
        imageButtonInfoSections.add(new ArrayList<>());
        for(ImageButtonInfo ibf : imageButtonInfos) {
            imageButtonInfoSections.get(0).add(ibf);
        }
        this.price = price;
    }

    public PandaExpressSubMenuFragment(HomeActivity home, ArrayList<View> viewsToAdd,
                                       ArrayList<ImageButtonInfo> imageButtonInfos, float price,
                                       int[] maxSelectionForSections, String itemName, String imageStr) {
        this.home = home;
        this.viewsToAdd = viewsToAdd;
        this.itemName = itemName;
        this.imageStr = imageStr;
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
            for(ArrayList<ImageButtonInfo> section : imageButtonInfoSections) {
                for (ImageButtonInfo button : section) {
                    button.getButton().setOnClickListener(item -> {
                        FoodItem foodItem = new FoodItem(getContext(), "Panda Express", button.getName(),
                                ("panda_express_" + button.getNameForFile()), new ArrayList<>(), (price + button.getPrice()));
                        home.setFragment(new AddToCartFragment(home, foodItem, new PandaExpressFragment(home)));
                    });
                }
            }
        }

        // Add to cart button
        view.findViewById(R.id.pandaExpress_addToCart).setOnClickListener(item -> {
            if(maxSelectionForSections != null) {
                ArrayList<String> description = new ArrayList<>();
                for(ArrayList<ImageButtonInfo> section : imageButtonInfoSections) {
                    for (ImageButtonInfo button : section) {
                        if(button.selected) {
                            description.add(button.getName());
                            price += button.getPrice();
                        }
                    }
                }
                FoodItem foodItem = new FoodItem(getContext(), "Panda Express", itemName, imageStr, description, price);
                home.setFragment(new AddToCartFragment(home, foodItem, new PandaExpressFragment(home)));
            }
        });
    }
}