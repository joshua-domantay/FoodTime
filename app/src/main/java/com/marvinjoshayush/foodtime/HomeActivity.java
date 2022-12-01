package com.marvinjoshayush.foodtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
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

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference dbReference;
    private String userID;

    private static HomeFragments CURRENT_FRAGMENT;
    private BottomNavigationView bottomNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getCurrentUserInfo();
        setBottomNavBar();

        // Default to HOME fragment
        setFragmentOnMenu(HomeFragments.HOME);
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
                _frag = new HomeFragment(this, user, dbReference, userID);
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