package com.marvinjoshayush.foodtime;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    private HomeActivity home;
    private View view;
    private Button[] buttons;
    private Button myButton;
    private FirebaseAuth mAuth;

    public ProfileFragment(HomeActivity home) {
        this.home = home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        myButton = view.findViewById(R.id.profileViewAllergies);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        Button preferencesButton = view.findViewById(R.id.profileViewPreferences);
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThirdActivity.class);
                startActivity(intent);
                getActivity().finish();
            } //the design is activity third
        });

        Button myButton3 = view.findViewById(R.id.profileChangeName);
        myButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeNameActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        Button myButton4 = view.findViewById(R.id.profileChangeAddress);
        myButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThirdActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        Button myButton5 = view.findViewById(R.id.profileChangePassword);
        myButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        Button myButton6 = view.findViewById(R.id.profileForgotPassword);
        myButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        Button myButton7 = view.findViewById(R.id.profileLogOut);
        myButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void setButtons() {
        buttons = new Button[]{view.findViewById(R.id.profileChangeName), view.findViewById(R.id.profileChangeAddress),
                view.findViewById(R.id.profileChangePassword), view.findViewById(R.id.profileForgotPassword)
        };

        /*
        buttons = new Button[] {view.findViewById(R.id.profileChangeName), view.findViewById(R.id.profileChangeAddress),
                                view.findViewById(R.id.profileChangePassword), view.findViewById(R.id.profileForgotPassword)};

        for(Button btn : buttons) {
            btn.getBackground().setAlpha(0);    // Remove background (transparent background)
        }
        */
    }
}