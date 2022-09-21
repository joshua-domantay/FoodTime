package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setNewUserSignUpButton();
    }

    private void setNewUserSignUpButton() {
        Button btn = findViewById(R.id.newUserSignUpButton);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }
}