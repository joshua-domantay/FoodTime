package com.marvinjoshayush.foodtime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileFragment extends Fragment {
    private HomeActivity home;
    private View view;
    private Button[] buttons;

    public ProfileFragment(HomeActivity home) { this.home = home; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        setButtons();

        return view;
    }

    private void setButtons() {
        buttons = new Button[] {view.findViewById(R.id.profileChangeName), view.findViewById(R.id.profileChangeAddress),
                                view.findViewById(R.id.profileChangePassword), view.findViewById(R.id.profileForgotPassword)};

        for(Button btn : buttons) {
            btn.getBackground().setAlpha(0);    // Remove background (transparent background)
        }
    }
}