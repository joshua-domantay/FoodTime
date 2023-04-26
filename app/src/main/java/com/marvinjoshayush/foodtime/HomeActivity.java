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
    public static int FOODITEM_ID;
    private FirebaseUser user;
    private DatabaseReference dbReference;
    private RestaurantManager restaurantManager;
    private IngredientsManager ingredientsManager;
    private String userID;
    private BottomNavigationView bottomNavBar;
    public ArrayList<FoodItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        restaurantManager = new RestaurantManager(this);
        ingredientsManager = new IngredientsManager(this);
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
    }

    private void setBottomNavBar() {
        bottomNavBar = findViewById(R.id.homeNavBar);
        bottomNavBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeMenuHome:
                    setFragmentFromNavBar(HomeFragments.HOME);
                    return true;
                case R.id.homeMenuCart:
                    setFragmentFromNavBar(HomeFragments.CART);
                    return true;
                case R.id.homeMenuProfile:
                    setFragmentFromNavBar(HomeFragments.PROFILE);
                    return true;
                default:
                    setFragmentFromNavBar(HomeFragments.SEARCH);
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
            case CART:
                CURRENT_FRAGMENT = new CartFragment(this);
                break;
            case PROFILE:
                CURRENT_FRAGMENT = new ProfileFragment(this);
                break;
            default:
                // SearchFragment deleted... why?
                // CURRENT_FRAGMENT = new SearchFragment(this);
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
    public IngredientsManager getIngredientsManager() { return ingredientsManager; }
}