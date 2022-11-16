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

// Add allergies screen
public class WelcomeActivity4 extends AppCompatActivity {

    private Button nextButton;
    private ToggleButton[] prefButtons;
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

        setPreferencesButtons();


    }

    private void setNextButton() {
        Button btn = findViewById(R.id.welcomeFinish);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, HomeActivity.class));
        });
    }

    private void setPreferencesButtons() {

        prefButtons = new ToggleButton[]{findViewById(R.id.option1Allergy), findViewById(R.id.option2Allergy),
                findViewById(R.id.option3Allergy), findViewById(R.id.option5Allergy),
                findViewById(R.id.option6Allergy), findViewById(R.id.option7Allergy), findViewById(R.id.option8Allergy),
                findViewById(R.id.option9Allergy), findViewById(R.id.option10Allergy)};


        String myAll = "";
        for (ToggleButton btn : prefButtons) {
            if (btn.isChecked()) {
                btnSelectedCount++;
                }
            else {
                    btnSelectedCount--;
                }
                if (btnSelectedCount > 0) {

                    nextButton.setText(R.string.finish);
                }
                else {
                    nextButton.setText(R.string.skip);
                }
            myAll += btn.getText().toString();
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance(); //added this
                u = mAuth.getCurrentUser();
                userID = u.getUid();
                myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                myRef.child("Allergies").setValue(myAll + " ");


            }
        });

    }
}