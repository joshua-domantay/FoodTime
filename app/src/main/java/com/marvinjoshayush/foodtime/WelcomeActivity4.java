package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

// Add allergies screen
public class WelcomeActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome4);

        setNextButton();
    }

    private void setNextButton() {
        Button btn = findViewById(R.id.welcomeFinish);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, WelcomeActivity4.class));
        });
    }
}