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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;




import com.google.firebase.auth.FirebaseAuth;

public class ThirdActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseRef;

    private boolean isOvo_VegetarianSelected;
    private boolean isLacto_VegetarianSelected;
    private boolean isveganSelected;
    private boolean isPescatarianSelected;
    private boolean isFlexitarianSelected;
    private boolean isOthersSelected;
    private View backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        backButton = findViewById(R.id.backButton);
        Toolbar toolbar = findViewById(R.id.toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("users");

        sharedPreferences = getSharedPreferences("ButtonPreferences", Context.MODE_PRIVATE);

        // Set extra for profile fragment
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fromProfileFragment")) {
            intent.putExtra("startedFromProfileFragment", true);
        }

        ToggleButton button1 = findViewById(R.id.Ovo_Vegetarian);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOvo_VegetarianSelected = button1.isChecked();
            }
        });

        ToggleButton button2 = findViewById(R.id.Lacto_Vegetarian);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLacto_VegetarianSelected = button2.isChecked();
            }
        });

        ToggleButton button3 = findViewById(R.id.vegan);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isveganSelected = button3.isChecked();
            }
        });

        ToggleButton button4 = findViewById(R.id.Pescatarian);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPescatarianSelected = button4.isChecked();
            }
        });

        ToggleButton button5 = findViewById(R.id.Flexitarian);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFlexitarianSelected = button5.isChecked();
            }
        });

        ToggleButton button6 = findViewById(R.id.Others);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOthersSelected = button6.isChecked();
            }
        });


        isOvo_VegetarianSelected = sharedPreferences.getBoolean("Ovo_Vegetarian", false);
        isLacto_VegetarianSelected = sharedPreferences.getBoolean("Lacto_Vegetarian", false);
        isveganSelected = sharedPreferences.getBoolean("vegan", false);
        isPescatarianSelected = sharedPreferences.getBoolean("Pescatarian", false);
        isFlexitarianSelected = sharedPreferences.getBoolean("Flexitarian", false);
        isOthersSelected= sharedPreferences.getBoolean("Others", false);

// Set the states of the toggle buttons
        button1.setChecked(isOvo_VegetarianSelected);

        button2.setChecked(isLacto_VegetarianSelected);

        button3.setChecked(isveganSelected);

        button4.setChecked(isPescatarianSelected);

        button5.setChecked(isFlexitarianSelected);

        button6.setChecked(isOthersSelected);


        Button saveButton = findViewById(R.id.Save2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("Ovo_Vegetarian", isOvo_VegetarianSelected);
                editor.putBoolean("Lacto_Vegetarian", isLacto_VegetarianSelected);
                editor.putBoolean("vegan", isveganSelected);
                editor.putBoolean("Pescatarian", isPescatarianSelected);
                editor.putBoolean("Flexitarian", isFlexitarianSelected);
                editor.putBoolean("Others", isOthersSelected);
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
