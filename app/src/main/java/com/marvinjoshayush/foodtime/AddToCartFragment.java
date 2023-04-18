package com.marvinjoshayush.foodtime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class AddToCartFragment extends Fragment {
    private HomeActivity home;
    private ScrollView parent;
    private FoodItem foodItem;
    private View view;
    private Fragment back;

    public AddToCartFragment(HomeActivity home, FoodItem foodItem, Fragment back) {
        this.home = home;
        this.foodItem = foodItem;
        this.back = back;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_to_cart, container, false);
        parent = view.findViewById(R.id.addToCart_scrollView);
        parent.addView(foodItem.getView());
        view.findViewById(R.id.addToCart_cancel).setOnClickListener(item -> {
            home.setFragment(back);
        });
        view.findViewById(R.id.addToCart_add).setOnClickListener(item -> {
            home.cartItems.add(foodItem);
            home.setFragment(new HomeFragment(home));
        });
        return view;
    }
}