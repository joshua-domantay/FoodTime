package com.marvinjoshayush.foodtime;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FoodItem {
    private Context context;
    private String itemImgStr;
    private String restaurant;
    private String itemName;
    private ArrayList<String> description;
    private float price;

    public FoodItem(Context context, String restaurant, String itemName, String itemImgStr, ArrayList<String> description, float price) {
        this.restaurant = restaurant;
        this.itemName = itemName;
        this.description = description;
        this.price = price;

        this.context = context;
        this.itemImgStr = itemImgStr;
    }

    public LinearLayout getView() {
        return ViewMaker.createFoodItemView(context, itemImgStr, restaurant, itemName, description, price);
    }
}
