package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;

// Add preferences screen
public class WelcomeActivity3 extends AppCompatActivity {

    private Button nextButton;
    private ToggleButton[] prefButtons;
    private int btnSelectedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome3);

        nextButton = findViewById(R.id.welcomeNext3);
        btnSelectedCount = 0;

        setNextButton();
        setPreferencesButtons();
    }

    private void setNextButton() {
        Button btn = findViewById(R.id.welcomeNext3);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, WelcomeActivity4.class));
        });
    }

    private void setPreferencesButtons() {
        prefButtons = new ToggleButton[] {findViewById(R.id.option1Pref), findViewById(R.id.option2Pref),
                                          findViewById(R.id.option3Pref), findViewById(R.id.option4Pref),
                                          findViewById(R.id.option5Pref), findViewById(R.id.option6Pref),
                                          findViewById(R.id.option7Pref), findViewById(R.id.option8Pref),};

        for(ToggleButton btn : prefButtons) {
            btn.setOnClickListener(item -> {
                if(btn.isChecked()) {
                    btnSelectedCount++;
                } else {
                    btnSelectedCount--;
                }

                // Change Next Button text
                if(btnSelectedCount > 0) {
                    nextButton.setText(R.string.next);
                } else {
                    nextButton.setText(R.string.skip);
                }
            });
        }
    }
}