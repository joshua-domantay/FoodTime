package com.marvinjoshayush.foodtime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class CartFragment extends Fragment {
    private HomeActivity home;
    private View view;
    private LinearLayout itemContainer;

    public CartFragment(HomeActivity home) {
        this.home = home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        itemContainer = view.findViewById(R.id.cart_itemContainer);
        fillCart();
        setCheckoutButton();
        return view;
    }

    private void fillCart() {
        for(FoodItem item : home.cartItems) {
            itemContainer.addView(item.getView());
        }
    }

    private void setCheckoutButton() {
        view.findViewById(R.id.cart_checkOutButton).setOnClickListener(item -> {
            home.setFragment(new CheckoutFragment(home));
        });
    }
}
