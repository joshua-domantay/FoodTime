package com.marvinjoshayush.foodtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

// Add preferences screen
public class WelcomeActivity3 extends AppCompatActivity implements View.OnClickListener {
    private String preferencesStr;
    private Button nextButton;
    private ToggleButton[] prefButtons;
    private LinearLayout othersLayout;

    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser u;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome3);

        preferencesStr = "";
        nextButton = findViewById(R.id.welcomeNext3);

        othersLayout = findViewById(R.id.othersLayout);

        prefButtons = new ToggleButton[] {(ToggleButton) findViewById(R.id.ovoVegetarian), (ToggleButton) findViewById(R.id.lactoVegetarian),
                (ToggleButton)findViewById(R.id.vegan), (ToggleButton)findViewById(R.id.pascatarian),
                (ToggleButton) findViewById(R.id.flexitarian), (ToggleButton) findViewById(R.id.others)};
        for (ToggleButton x : prefButtons) {
            x.setOnClickListener(this);
        }

        setNextButton();
    }

    private void preferenceSpecificClick(View v, int index) {
        if(prefButtons[index].isChecked()) {
            if(index != (prefButtons.length - 1)) {
                preferencesStr = prefButtons[index].getText().toString().toLowerCase();
            } else {
                preferencesStr = "";
            }
            othersLayout.setVisibility(v.GONE);
            prefButtons[index].setChecked(true);
            for(int i = 0; i < prefButtons.length; i++) {
                if(index != i) {
                    prefButtons[i].setChecked(false);
                }
            }
        }

        int count = 0;
        for(int i = 0; i < prefButtons.length; i++) {
            if(prefButtons[i].isChecked()) {
                count++;
            }
        }
        if(count == 0) {
            preferencesStr = "";
            nextButton.setText(R.string.skip);
        } else {
            nextButton.setText(R.string.next);
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.ovoVegetarian:
                preferenceSpecificClick(v, 0);
                break;
            case R.id.lactoVegetarian:
                preferenceSpecificClick(v, 1);
                break;
            case R.id.vegan:
                preferenceSpecificClick(v, 2);
                break;
            case R.id.pascatarian:
                preferenceSpecificClick(v, 3);
                break;
            case R.id.flexitarian:
                preferenceSpecificClick(v, 4);
                break;
            default:
                preferenceSpecificClick(v, (prefButtons.length - 1));
                if(prefButtons[prefButtons.length - 1].isChecked()) {
                    othersLayout.setVisibility(v.VISIBLE);
                } else {
                    othersLayout.setVisibility(v.GONE);
                }
        }
    }

    private void addPreferenceToFirebase() {
        mAuth = FirebaseAuth.getInstance(); //added this
        u = mAuth.getCurrentUser();
        userID = u.getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        myRef.child("Choices").setValue(preferencesStr);
    }

    private void setNextButton() {
        Button btn = findViewById(R.id.welcomeNext3);
        btn.setOnClickListener(item -> {
            addPreferenceToFirebase();
            startActivity(new Intent(this, WelcomeActivity4.class));
        });
    }
}










