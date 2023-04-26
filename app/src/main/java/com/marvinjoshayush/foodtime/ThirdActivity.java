package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ToggleButton;



import com.google.firebase.auth.FirebaseAuth;

public class ThirdActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private boolean ismile_radiusSelected;
    private boolean isFast_foodSelected;
    private boolean isRestaurantsSelected;
    private boolean isDeliverySelected;
    private boolean isShow_timesSelected;
    private boolean isCondimentsSelected;
    private View backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        backButton = findViewById(R.id.backButton);
        Toolbar toolbar = findViewById(R.id.toolbar);

        sharedPreferences = getSharedPreferences("ButtonPreferences", Context.MODE_PRIVATE);

        // Set extra for profile fragment
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fromProfileFragment")) {
            intent.putExtra("startedFromProfileFragment", true);
        }

        ToggleButton button1 = findViewById(R.id.mile_radius);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ismile_radiusSelected = button1.isChecked();
            }
        });

        ToggleButton button2 = findViewById(R.id.Fast_food);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFast_foodSelected = button2.isChecked();
            }
        });

        ToggleButton button3 = findViewById(R.id.Restaurants);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRestaurantsSelected = button3.isChecked();
            }
        });

        ToggleButton button4 = findViewById(R.id.Delivery);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeliverySelected = button4.isChecked();
            }
        });

        ToggleButton button5 = findViewById(R.id.Show_times);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow_timesSelected = button5.isChecked();
            }
        });

        ToggleButton button6 = findViewById(R.id.Condiments);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCondimentsSelected = button6.isChecked();
            }
        });


        ismile_radiusSelected = sharedPreferences.getBoolean("mile_radius", false);
        isFast_foodSelected = sharedPreferences.getBoolean("Fast_food", false);
        isRestaurantsSelected = sharedPreferences.getBoolean("Restaurants", false);
        isDeliverySelected = sharedPreferences.getBoolean("Delivery", false);
        isShow_timesSelected = sharedPreferences.getBoolean("Show_times", false);
        isCondimentsSelected = sharedPreferences.getBoolean("Condiments", false);

// Set the states of the toggle buttons
        button1.setChecked(ismile_radiusSelected);

        button2.setChecked(isFast_foodSelected);

        button3.setChecked(isRestaurantsSelected);

        button4.setChecked(isDeliverySelected);

        button5.setChecked(isShow_timesSelected);

        button6.setChecked(isCondimentsSelected);


        Button saveButton = findViewById(R.id.Save2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("mile_radius", ismile_radiusSelected);
                editor.putBoolean("Fast_food", isFast_foodSelected);
                editor.putBoolean("Restaurants", isRestaurantsSelected);
                editor.putBoolean("Delivery", isDeliverySelected);
                editor.putBoolean("Show_times", isShow_timesSelected);
                editor.putBoolean("Condiments", isCondimentsSelected);
                editor.apply();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
