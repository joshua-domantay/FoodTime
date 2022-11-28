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



//added here
        /*
        one = (ToggleButton) findViewById(R.id.Ovo);
        two =(ToggleButton) findViewById(R.id.Lacto);
        three = (ToggleButton)findViewById(R.id.Vegan);
        four = (ToggleButton)findViewById(R.id.pasca);
        five = (ToggleButton) findViewById(R.id.felxi);
        six = (ToggleButton) findViewById(R.id.others);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        */

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
        /*
        if(one.isChecked()) {
            list1.setVisibility(v.GONE);
            list2.setVisibility(v.GONE);
            one.setChecked(true);
            two.setChecked(false);
            three.setChecked(false);
            four.setChecked(false);
            five.setChecked(false);
            six.setChecked(false);
            // nextButton.setText(R.string.next);
        }
        if(!one.isChecked()){
            six.setChecked(false);
            // nextButton.setText(R.string.skip);
        }
        */

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

        /*
        switch (id){
            case R.id.Ovo:

                String ovo = one.getText().toString();

                if(one.isChecked()) {
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    one.setChecked(true);
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                    five.setChecked(false);
                    six.setChecked(false);
                    nextButton.setText(R.string.next);
                    */
                    /*
                   nextButton.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           mAuth = FirebaseAuth.getInstance(); //added this
                           u = mAuth.getCurrentUser();
                           userID = u.getUid();
                           myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                           myRef.child("Choices").setValue(ovo);
                           startActivity(new Intent(view.getContext(), WelcomeActivity4.class));



                       }
                   });
                   */
/*
                }

                if(!one.isChecked()){
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }

                break;
            case R.id.Lacto:
                if(two.isChecked()) {
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    one.setChecked(false);
                    two.setChecked(true);
                    three.setChecked(false);
                    four.setChecked(false);
                    five.setChecked(false);
                    six.setChecked(false);
                    nextButton.setText(R.string.next);


                }

                if(!two.isChecked()){
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }

                break;
            case R.id.Vegan:
                if(three.isChecked()) {
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    one.setChecked(false);
                    two.setChecked(false);
                    three.setChecked(true);
                    four.setChecked(false);
                    five.setChecked(false);
                    six.setChecked(false);
                    nextButton.setText(R.string.next);



                }

                if(!three.isChecked()){
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }

                //do ur code
                break;
            case R.id.pasca:
                if(four.isChecked()) {
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    one.setChecked(false);
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(true);
                    five.setChecked(false);
                    six.setChecked(false);
                    nextButton.setText(R.string.next);





                }

                if(!four.isChecked()){
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }

               //do ur code;

                break;

            case R.id.felxi:
                if(five.isChecked()) {
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    one.setChecked(false);
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                    five.setChecked(true);
                    six.setChecked(false);
                    nextButton.setText(R.string.next);




                }
                if(!five.isChecked()){
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }


                break;

            case R.id.others:
                if(six.isChecked()) {
                    int isvisible1 = list1.getVisibility();
                    int isvisible2 = list2.getVisibility();
                    one.setChecked(false);
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                    five.setChecked(false);
                    six.setChecked(true);

                        list1.setVisibility(v.VISIBLE);
                             list2.setVisibility(v.VISIBLE);



                }

                if(!six.isChecked()){
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }

            default:
                //do ur code;
        }
        */
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

    /*
    private void setPreferencesButtons() {

            prefButtons = new ToggleButton[]{findViewById(R.id.option1Pref), findViewById(R.id.option2Pref),
                    findViewById(R.id.option3Pref), findViewById(R.id.option4Pref),
                    findViewById(R.id.option5Pref), findViewById(R.id.option6Pref)};






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


                    }else {
                        nextButton.setText(R.string.skip);
                    }
                });
            }

    }
    */


    }










