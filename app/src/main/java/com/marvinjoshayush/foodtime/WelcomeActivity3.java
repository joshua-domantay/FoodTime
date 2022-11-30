package com.marvinjoshayush.foodtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
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
    private int btnSelectedCount;
    private int listSelectCount;
    private LinearLayout list1, list2;
    private ToggleButton other;
    private ToggleButton one;
   private ToggleButton two ;
   private ToggleButton three ;
  private  ToggleButton four;
  private  ToggleButton five;
   private ToggleButton six;
   private static User mychoices = new User();

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
        btnSelectedCount = 0;

        //added here
        list1 = findViewById(R.id.LinearLayout1);

        list2 = findViewById(R.id.LinearLayout2);

        other =  (ToggleButton) findViewById(R.id.others);




        prefButtons = new ToggleButton[] {(ToggleButton) findViewById(R.id.Ovo), two =(ToggleButton) findViewById(R.id.Lacto),
                three = (ToggleButton)findViewById(R.id.Vegan), four = (ToggleButton)findViewById(R.id.pasca),
                five = (ToggleButton) findViewById(R.id.felxi), six = (ToggleButton) findViewById(R.id.others)};
        for (ToggleButton x : prefButtons) {
            x.setOnClickListener(this);
        }

        setNextButton();

        // setPreferencesButtons();

    }

    private void preferenceSpecificClick(View v, int index) {


        if(prefButtons[index].isChecked()) {
            if(index != (prefButtons.length - 1)) {
                preferencesStr = prefButtons[index].getText().toString().toLowerCase();
            } else {
                preferencesStr = "";
            }
            list1.setVisibility(v.GONE);
            list2.setVisibility(v.GONE);
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


// added here
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.Ovo:
                preferenceSpecificClick(v, 0);
                break;
            case R.id.Lacto:
                preferenceSpecificClick(v, 1);
                break;
            case R.id.Vegan:
                preferenceSpecificClick(v, 2);
                break;
            case R.id.pasca:
                preferenceSpecificClick(v, 3);
                break;
            case R.id.felxi:
                preferenceSpecificClick(v, 4);
                break;
            default:
                preferenceSpecificClick(v, (prefButtons.length - 1));
                if(prefButtons[prefButtons.length - 1].isChecked()) {
                    list1.setVisibility(v.VISIBLE);
                    list2.setVisibility(v.VISIBLE);
                } else {
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
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










