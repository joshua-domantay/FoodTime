package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setHaveAccountLogInButton();
    }

    private void setHaveAccountLogInButton() {
        Button btn = findViewById(R.id.haveAccountLogInButton);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, LogInActivity.class));
        });
    }
}