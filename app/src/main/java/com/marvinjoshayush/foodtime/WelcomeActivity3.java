package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Add preferences screen
public class WelcomeActivity3 extends AppCompatActivity {

    private Button nextButton;
    private ToggleButton[] prefButtons;
    private int btnSelectedCount;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private TextView click1;
    private TextView click2;
    private TextView click3;
    private TextView click4;
    private TextView click5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome3);

        nextButton = findViewById(R.id.welcomeNext3);
        btnSelectedCount = 0;

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


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
        prefButtons = new ToggleButton[]{findViewById(R.id.option1Pref), findViewById(R.id.option2Pref),
                findViewById(R.id.option3Pref), findViewById(R.id.option4Pref),
                findViewById(R.id.option5Pref),};

        for (ToggleButton btn : prefButtons) {
            btn.setOnClickListener(item -> {
                if (btn.isChecked()) {
                    btnSelectedCount++;
                } else {
                    btnSelectedCount--;
                }

                // Change Next Button text
                if (btnSelectedCount > 0) {
                    nextButton.setText(R.string.next);
                } else {
                    nextButton.setText(R.string.skip);
                }
            });
        }
    }

}