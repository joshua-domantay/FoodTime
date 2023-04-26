package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

public class SecondActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private boolean isNutsSelected;
    private boolean isDairySelected;
    private boolean isFishSelected;
    private boolean isEggsSelected;
    private boolean isWheatSelected;
    private boolean isSoySelected;
    private boolean isGlutenSelected;
    private View backButton;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        backButton = findViewById(R.id.backButton);
        Toolbar toolbar = findViewById(R.id.toolbar);

        sharedPreferences = getSharedPreferences("ButtonPreferences", Context.MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Set extra for profile fragment
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fromProfileFragment")) {
            intent.putExtra("startedFromProfileFragment", true);
        }

        ToggleButton button1 = findViewById(R.id.Nuts);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNutsSelected = button1.isChecked();
            }
        });

        ToggleButton button2 = findViewById(R.id.Dairy);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDairySelected = button2.isChecked();
            }
        });

        ToggleButton button3 = findViewById(R.id.Fish);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFishSelected = button3.isChecked();
            }
        });

        ToggleButton button4 = findViewById(R.id.Eggs);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEggsSelected = button4.isChecked();
            }
        });

        ToggleButton button5 = findViewById(R.id.Wheat);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWheatSelected = button5.isChecked();
            }
        });

        ToggleButton button6 = findViewById(R.id.Soy);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSoySelected = button6.isChecked();
            }
        });

        ToggleButton button7 = findViewById(R.id.Gluten);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGlutenSelected = button7.isChecked();
            }
        });

        isNutsSelected = sharedPreferences.getBoolean("Nuts", false);

        isDairySelected = sharedPreferences.getBoolean("Dairy", false);

        isFishSelected = sharedPreferences.getBoolean("Fish", false);

        isEggsSelected = sharedPreferences.getBoolean("Eggs", false);

        isWheatSelected = sharedPreferences.getBoolean("Wheat", false);

        isSoySelected = sharedPreferences.getBoolean("Soy", false);

        isGlutenSelected = sharedPreferences.getBoolean("Gluten", false);

// Set the states of the toggle buttons
        button1.setChecked(isNutsSelected);

        button2.setChecked(isDairySelected);

        button3.setChecked(isFishSelected);

        button4.setChecked(isEggsSelected);

        button5.setChecked(isWheatSelected);

        button6.setChecked(isSoySelected);

        button7.setChecked(isGlutenSelected);

        Button saveButton = findViewById(R.id.Save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("Nuts", isNutsSelected);
                editor.putBoolean("Dairy", isDairySelected);
                editor.putBoolean("Fish", isFishSelected);
                editor.putBoolean("Eggs", isEggsSelected);
                editor.putBoolean("Wheat", isWheatSelected);
                editor.putBoolean("Soy", isSoySelected);
                editor.putBoolean("Gluten", isGlutenSelected);
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






