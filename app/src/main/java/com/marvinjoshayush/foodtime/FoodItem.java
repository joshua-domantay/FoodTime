package com.marvinjoshayush.foodtime;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FoodItem {
    private String restaurant;
    private String itemName;
    private ArrayList<String> description;
    private float price;
    private LinearLayout view;

    public FoodItem(Context temp, String restaurant, String itemName, String itemImgStr, ArrayList<String> description, float price) {
        this.restaurant = restaurant;
        this.itemName = itemName;
        this.description = description;
        this.price = price;

        generateView(temp, itemImgStr);
    }

    private void generateView(Context temp, String itemImgStr) {
        view = ViewMaker.createFoodItemView(temp, itemImgStr, restaurant, itemName, description, price);
    }

    public LinearLayout getView() {
        return view;
    }
}
