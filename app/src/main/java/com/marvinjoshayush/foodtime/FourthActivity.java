package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class FourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Set extra for profile fragment
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fromProfileFragment")) {
            intent.putExtra("startedFromProfileFragment", true);
        }

        Button button1 = findViewById(R.id.Nuts);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourthActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.Dairy);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourthActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

        Button button3 = findViewById(R.id.Fish);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourthActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

        Button button4 = findViewById(R.id.Eggs);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourthActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

        Button button5 = findViewById(R.id.Wheat);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourthActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

        Button button6 = findViewById(R.id.Soy);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourthActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

        Button button7 = findViewById(R.id.Gluten);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourthActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

        Button myButton8 = findViewById(R.id.backButton);
        myButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(FourthActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}