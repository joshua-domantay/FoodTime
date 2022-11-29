package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

// Add allergies screen
public class WelcomeActivity4 extends AppCompatActivity {
    private Button nextButton;
    private ToggleButton[] allergyButtons;
    private int btnSelectedCount;

    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser u;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome4);

        nextButton = findViewById(R.id.welcomeFinish);
        btnSelectedCount = 0;

        setNextButton();
        setAllergiesButtons();
    }

    // This will return empty string if no allergies are pressed
    private String getAllergiesString() {
        String val = "";
        if(btnSelectedCount > 0) {
            for (ToggleButton btn : allergyButtons) {
                if (btn.isChecked()) {
                    val += (btn.getText().toString().toLowerCase() + "&");      // Allergies separated by '&'
                }
            }
            val = val.substring(0, (val.length() - 1));     // Remove last '&'
        }
        return val;
    }

    private void addAllergiesToServer() {
        String myAll = getAllergiesString();

        mAuth = FirebaseAuth.getInstance(); //added this
        u = mAuth.getCurrentUser();
        userID = u.getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        myRef.child("Allergies").setValue(myAll);

        Button btn = findViewById(R.id.welcomeFinish);
        btn.setText(R.string.finish);
    }

    private void setNextButton() {
        Button btn = findViewById(R.id.welcomeFinish);
        btn.setOnClickListener(item -> {
            addAllergiesToServer();
            startActivity(new Intent(this, HomeActivity.class));
        });
    }

    private void setAllergiesButtons() {
        allergyButtons = new ToggleButton[]{findViewById(R.id.option1Allergy), findViewById(R.id.option2Allergy),
                findViewById(R.id.option3Allergy), findViewById(R.id.option4Allergy), findViewById(R.id.option5Allergy),
                findViewById(R.id.option6Allergy), findViewById(R.id.option7Allergy), findViewById(R.id.option8Allergy),
                findViewById(R.id.option9Allergy), findViewById(R.id.option10Allergy)};

        for(ToggleButton btn : allergyButtons) {
            btn.setOnClickListener(item -> {
                if(btn.isChecked()) {
                    btnSelectedCount++;
                } else {
                    btnSelectedCount--;
                }
            });
        }
    }
}