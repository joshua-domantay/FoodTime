package com.marvinjoshayush.foodtime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class CheckoutFragment extends Fragment {
    private HomeActivity home;
    private View view;

    public CheckoutFragment(HomeActivity home) {
        this.home = home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_checkout, container, false);
        view.findViewById(R.id.checkout_cancel).setOnClickListener(item -> {
            home.setFragment(new CartFragment(home));
        });
        return view;
    }
}
