package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity1 extends AppCompatActivity implements View.OnClickListener {

    // "@+id/welcomeName
    // @+id/welcomeNext1

    private TextView fullname;



    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome1);

        fullname = (TextView) findViewById(R.id.welcomeName);
        fullname.setOnClickListener(this);

      User myname = new User();

      fullname.setText(myname.firstname);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.welcomeNext1:
                startActivity(new Intent(this,WelcomeActivity2.class));
                break;

        }





    }



}

