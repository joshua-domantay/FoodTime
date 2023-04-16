package com.marvinjoshayush.foodtime;

import android.media.Image;
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
    private ArrayList<ImageButtonInfo> imageButtonInfos;
    private LinearLayout parentLayout;

    public SubMenuFragment(HomeActivity home, ImageView logo, Fragment returnFragment, ArrayList<View> viewsToAdd, ArrayList<ImageButtonInfo> imageButtonInfos) {
        this.home = home;
        this.logo = logo;
        this.returnFragment = returnFragment;
        this.viewsToAdd = viewsToAdd;
        this.imageButtonInfos = imageButtonInfos;
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
        if(viewsToAdd == null) {
            for(ImageButtonInfo ibf : imageButtonInfos) {
                parentLayout.addView(ibf.getButton());
            }
        } else {
            for (View v : viewsToAdd) {
                parentLayout.addView(v);
            }
        }
    }

    private void setButtons() {
        view.findViewById(R.id.subMenu_returnToMenu).setOnClickListener(item -> {
            home.setFragment(returnFragment);
        });

        for(ImageButtonInfo ibf : imageButtonInfos) {
            ibf.getButton().setOnClickListener(item -> {
                LinearLayout foodItem = ViewMaker.createFoodItemView(getContext(), ("mcdonalds_" + ibf.getNameForFile()),
                        "McDonalds", ibf.getName(), new ArrayList<>(), ibf.getPrice());
                home.setFragment(new AddToCartFragment(home, foodItem, new McDonaldsFragment(home)));
            });
        }
    }
}
