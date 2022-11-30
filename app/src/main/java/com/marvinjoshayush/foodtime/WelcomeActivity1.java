package com.marvinjoshayush.foodtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity1 extends AppCompatActivity {

    // "@+id/welcomeName
    // @+id/welcomeNext1

    private TextView fullname;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;



    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome1);

        fullname = (TextView) findViewById(R.id.welcomeName);

        User myName = new User();


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView welcomename = (TextView) findViewById(R.id.welcomeName);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String fullname = userProfile.firstname + " " + userProfile.lastname;

                    welcomename.setText(fullname);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(WelcomeActivity1.this,"Error in the page!!", Toast.LENGTH_LONG).show();

            }
        });

        setNextButton();
    }


    private void setNextButton() {
        Button btn = findViewById(R.id.welcomeNext1);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, WelcomeActivity3.class));
        });
    }



}

