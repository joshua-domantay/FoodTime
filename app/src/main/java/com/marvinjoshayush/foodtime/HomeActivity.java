package com.marvinjoshayush.foodtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private static HomeFragments CURRENT_HOME_FRAGMENT;
    private static Fragment CURRENT_FRAGMENT;
    private FirebaseUser user;
    private DatabaseReference dbReference;
    private RestaurantManager restaurantManager;
    private String userID;
    private BottomNavigationView bottomNavBar;
    public ArrayList<LinearLayout> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(restaurantManager == null) {
            restaurantManager = new RestaurantManager(this);
        }
        getCurrentUserInfo();
        setBottomNavBar();
        cartItems = new ArrayList<>();

        // Default to HOME fragment
        setFragmentFromNavBar(HomeFragments.HOME);
    }

    private void getCurrentUserInfo() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        /*
        final TextView welcomename = (TextView) findViewById(R.id.welcomeName);

        dbReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    String fullname = userProfile.firstname + " " + userProfile.lastname;
                    welcomename.setText(fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        */
    }

    private void setBottomNavBar() {
        bottomNavBar = findViewById(R.id.homeNavBar);
        bottomNavBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeMenuHome:
                    setFragmentFromNavBar(HomeFragments.HOME);
                    return true;
                case R.id.homeMenuSearch:
                    setFragmentFromNavBar(HomeFragments.SEARCH);
                    return true;
                default:        // R.id.homeMenuProfile
                    setFragmentFromNavBar(HomeFragments.PROFILE);
                    return true;
            }
        });
    }

    private void setFragmentFromNavBar(HomeFragments newHomeFragment) {
        if(CURRENT_HOME_FRAGMENT == newHomeFragment) { return; }

        CURRENT_HOME_FRAGMENT = newHomeFragment;
        switch(newHomeFragment) {
            case HOME:
                CURRENT_FRAGMENT = new HomeFragment(this);
                break;
            case SEARCH:
                CURRENT_FRAGMENT = new SearchFragment(this);
                break;
            default:        // PROFILE
                CURRENT_FRAGMENT = new ProfileFragment(this);
        }
        setFragment(CURRENT_FRAGMENT);
    }

    public void setFragment(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.homeFragmentContainer, newFragment).commit();
    }

    public void setRestaurants() {
        if(CURRENT_HOME_FRAGMENT == HomeFragments.HOME) {
            ((HomeFragment) CURRENT_FRAGMENT).setRestaurants();
        }
    }

    public RestaurantManager getRestaurantManager() { return restaurantManager; }
}