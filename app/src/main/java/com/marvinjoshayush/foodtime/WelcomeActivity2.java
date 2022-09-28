package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class WelcomeActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);

        setNextButton();
    }

    private void setNextButton() {
        Button btn = findViewById(R.id.welcomeNext2);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, WelcomeActivity3.class));
        });
    }
}