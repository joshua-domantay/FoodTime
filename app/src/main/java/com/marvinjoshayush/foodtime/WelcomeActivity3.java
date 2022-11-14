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

        nextButton = findViewById(R.id.welcomeNext3);
        btnSelectedCount = 0;
        listSelectCount = 0;

        //added here
        list1 = findViewById(R.id.LinearLayout1);

        list2 = findViewById(R.id.LinearLayout2);

        other =  (ToggleButton) findViewById(R.id.others);



//added here

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


        setNextButton();

        setPreferencesButtons();

    }

// added here
    public void onClick(View v) {

        int id = v.getId();
        switch (id){
            case R.id.Ovo:

                String ovo = one.getText().toString();

                if(one.isChecked()) {

                     one.setChecked(true);
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                    five.setChecked(false);
                    six.setChecked(false);
                    nextButton.setText(R.string.next);
                   nextButton.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           mAuth = FirebaseAuth.getInstance(); //added this
                           u = mAuth.getCurrentUser();
                           userID = u.getUid();
                           myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                           myRef.child("Choices").setValue(ovo);


                       }
                   });
                }

                if(!six.isChecked()){
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }

                break;
            case R.id.Lacto:
                if(two.isChecked()) {
                    one.setChecked(false);
                    two.setChecked(true);
                    three.setChecked(false);
                    four.setChecked(false);
                    five.setChecked(false);
                    six.setChecked(false);





                }

                if(!six.isChecked()){
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }

                break;
            case R.id.Vegan:
                if(three.isChecked()) {
                    one.setChecked(false);
                    two.setChecked(false);
                    three.setChecked(true);
                    four.setChecked(false);
                    five.setChecked(false);
                    six.setChecked(false);



                }

                if(!six.isChecked()){
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }

                //do ur code
                break;
            case R.id.pasca:
                if(four.isChecked()) {
                    one.setChecked(false);
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(true);
                    five.setChecked(false);
                    six.setChecked(false);





                }

                if(!six.isChecked()){
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
                    six.setChecked(false);
                    nextButton.setText(R.string.skip);
                }

               //do ur code;

                break;

            case R.id.felxi:
                if(five.isChecked()) {
                    one.setChecked(false);
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                    five.setChecked(true);
                    six.setChecked(false);




                }
                if(!six.isChecked()){
                    list1.setVisibility(v.GONE);
                    list2.setVisibility(v.GONE);
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

//    public void addDataToFirebase(String ovo){
//
//        mychoices.setChoices(ovo);
//
//        reference.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                // inside the method of on Data change we are setting
//                // our object class to our database reference.
//                // data base reference will sends data to firebase.
//                reference.setValue(mychoices);
//
//                // after adding this data we are showing toast message.
//                Toast.makeText(WelcomeActivity3.this, " added", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // if the data is not added or it is cancelled then
//                // we are displaying a failure toast message.
//                Toast.makeText(WelcomeActivity3.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    }










