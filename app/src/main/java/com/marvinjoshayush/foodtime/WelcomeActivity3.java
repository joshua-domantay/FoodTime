package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

// Add preferences screen
public class WelcomeActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome3);

        setNextButton();
    }

    private void setNextButton() {
        Button btn = findViewById(R.id.welcomeNext3);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, WelcomeActivity4.class));
        });
    }
}