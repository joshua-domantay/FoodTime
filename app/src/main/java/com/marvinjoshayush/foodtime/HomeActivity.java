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
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static HomeFragments CURRENT_HOME_FRAGMENT;
    private static Fragment CURRENT_FRAGMENT;
    public static int FOODITEM_ID;
    private FirebaseUser user;
    private DatabaseReference dbReference;
    private RestaurantManager restaurantManager;
    private IngredientsManager ingredientsManager;
    private boolean setAvoidPrefDone;
    private boolean setAvoidAllergyDone;
    private String userID;
    private BottomNavigationView bottomNavBar;
    public ArrayList<FoodItem> cartItems;
    public HashMap<String, Integer> userAvoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        restaurantManager = new RestaurantManager(this);
        ingredientsManager = new IngredientsManager(this);
        setAvoidPrefDone = false;
        setAvoidAllergyDone = false;
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

        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("MYTEST", snapshot.child("Choices").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void setUserAvoidPref() {
        if(userAvoid == null) {
            userAvoid = new HashMap<>();
        }

        String[] x = new String[]{"beef", "pork", "poultry", "seafood", "eggs", "dairy", "meatingredients", "grains"};
        for (String i : x) {
            ingredientsManager.getIngredientCategory(i);
        }

        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] vals = new String[]{};
                String choice = snapshot.child("Choices").getValue().toString();
                switch(choice) {
                    case "ovo-vegetarian":
                        vals = new String[]{"beef", "pork", "poultry", "seafood", "dairy", "meatingredients"};
                        break;
                    case "lacto-vegetarian":
                        vals = new String[]{"beef", "pork", "poultry", "seafood", "eggs", "meatingredients"};
                        break;
                    case "vegan":
                        vals = new String[]{"beef", "pork", "poultry", "seafood", "eggs", "dairy", "meatingredients"};
                        break;
                    case "pascatarian":
                        vals = new String[]{"beef", "pork", "poultry", "meatingredients"};
                        break;
                    case "flexitarian":
                        vals = new String[]{"beef", "pork", "poultry", "seafood", "meatingredients"};
                        break;
                    case "":
                        break;
                    default:
                        vals = choice.split("&");
                        ArrayList<String> valSplit = new ArrayList<>();
                        valSplit.add("beef");
                        valSplit.add("dairy");
                        valSplit.add("eggs");
                        valSplit.add("grains");
                        valSplit.add("meatingredients");
                        valSplit.add("pork");
                        valSplit.add("poultry");
                        valSplit.add("seafood");
                        for(String i : vals) {
                            switch(i) {
                                case "meat":
                                    valSplit.remove("beef");
                                    valSplit.remove("pork");
                                    break;
                                case "meat ingredients":
                                    valSplit.remove("meatingredients");
                                    break;
                                case "dairy products":
                                    valSplit.remove("dairy");
                                default:
                                    valSplit.remove(i);
                                    break;
                            }
                        }
                        vals = valSplit.toArray(new String[0]);
                        break;
                }
                for(String i : vals) {
                    ArrayList<String> ingredients = ingredientsManager.getIngredientCategory(i);
                    if(ingredients != null) {
                        for(String ingr : ingredients) {
                            userAvoid.put(ingr, 1);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void setUserAvoidAllergy() {
        if(userAvoid == null) {
            userAvoid = new HashMap<>();
        }

        String[] y = new String[]{"fish", "garlic", "milk", "onion", "peanuts", "sesame", "shellfish", "soy", "treenuts", "wheat"};
        for (String i : y) {
            ingredientsManager.getAllergen(i);
        }

        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] vals = snapshot.child("Allergies").getValue().toString().split("&");
                for(String i : vals) {
                    String newI = i;
                    if(i.equalsIgnoreCase("tree nuts")) {
                        newI = "treenuts";
                    }
                    ArrayList<String> allergies = ingredientsManager.getAllergen(newI);
                    if(allergies != null) {
                        for (String a : allergies) {
                            userAvoid.put(a, 1);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
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