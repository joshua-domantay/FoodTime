package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class WelcomeActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome1);

        setNextButton();
    }

    private void setNextButton() {
        Button btn = findViewById(R.id.welcomeNext1);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, WelcomeActivity2.class));
        });
    }
}