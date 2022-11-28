package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private static HomeFragments CURRENT_FRAGMENT;
    private BottomNavigationView bottomNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setBottomNavBar();

        // Default to HOME fragment
        setFragmentOnMenu(HomeFragments.HOME);
    }

    private void setBottomNavBar() {
        bottomNavBar = findViewById(R.id.homeNavBar);
        bottomNavBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeMenuHome:
                    setFragmentOnMenu(HomeFragments.HOME);
                    return true;
                case R.id.homeMenuSearch:
                    setFragmentOnMenu(HomeFragments.SEARCH);
                    return true;
                default:        // R.id.homeMenuProfile
                    setFragmentOnMenu(HomeFragments.PROFILE);
                    return true;
            }
        });
    }

    private void setFragmentOnMenu(HomeFragments newFragment) {
        if(CURRENT_FRAGMENT == newFragment) { return; }

        Fragment _frag;
        CURRENT_FRAGMENT = newFragment;
        switch(newFragment) {
            case HOME:
                _frag = new HomeFragment(this);
                break;
            case SEARCH:
                _frag = new SearchFragment(this);
                break;
            default:        // PROFILE
                _frag = new ProfileFragment(this);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.homeFragmentContainer, _frag).commit();
    }
}