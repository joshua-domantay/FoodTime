package com.marvinjoshayush.foodtime;

import static com.marvinjoshayush.foodtime.DBlogin.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    Button _reg;
    Button _alreadyAcc;
    EditText fname, lname, email, password;



    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_sign_up);
        openHelper = new DBlogin(this);
        _reg = (Button)findViewById(R.id.signUpButton);
        fname = (EditText) findViewById(R.id.firstNameSignUp);
        lname = (EditText) findViewById(R.id.lastNameSignUp);
        email = (EditText) findViewById(R.id.emailSignUp);
        password = (EditText) findViewById(R.id.passwordSignUp);
        _alreadyAcc = (Button) findViewById(R.id.haveAccountLogInButton);
        _reg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                db= openHelper.getWritableDatabase();
                String Fname = fname.getText().toString();
                String Lname =lname.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                insertString(Fname,Lname,Email,Password);
                Toast.makeText(getApplicationContext(),"register successfully", Toast.LENGTH_LONG).show();
                




            }
            });

        _alreadyAcc.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this,LogInActivity.class);
                startActivity(intent);


            }
        });




    }

    public void insertString(String fname, String lname, String email, String password){

        ContentValues contentValues= new ContentValues(); // to write values in the database
        contentValues.put(DBlogin.COLUMN_USER_FNAME, fname );
        contentValues.put(DBlogin.COLUMN_USER_EMAIL, lname );
        contentValues.put(DBlogin.COLUMN_USER_EMAIL, email );
        contentValues.put(DBlogin.COLUMN_USER_PASSWORD, password);
        long id = db.insert(TABLE_USER,null,contentValues);


    }
}