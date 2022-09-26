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

    Button _alreadyAcc;
    EditText fName, lName, email, password;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        openHelper = new DBlogin(this);
        fName = (EditText) findViewById(R.id.firstNameSignUp);
        lName = (EditText) findViewById(R.id.lastNameSignUp);
        email = (EditText) findViewById(R.id.emailSignUp);
        password = (EditText) findViewById(R.id.passwordSignUp);
        _alreadyAcc = (Button) findViewById(R.id.haveAccountLogInButton);

        setSignUpButton();
        setHaveAccButton();
    }

    private void setSignUpButton() {
        Button btn = findViewById(R.id.signUpButton);
        btn.setOnClickListener(item -> {
            db= openHelper.getWritableDatabase();
            insertString();
            // Toast.makeText(getApplicationContext(),"register successfully", Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, WelcomeActivity1.class));
        });
    }

    private void setHaveAccButton() {
        Button btn = findViewById(R.id.haveAccountLogInButton);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, LogInActivity.class));
        });
    }

    private void insertString() {
        ContentValues contentValues= new ContentValues(); // to write values in the database
        contentValues.put(DBlogin.COLUMN_USER_FNAME, fName.getText().toString());
        contentValues.put(DBlogin.COLUMN_USER_EMAIL, lName.getText().toString());
        contentValues.put(DBlogin.COLUMN_USER_EMAIL, email.getText().toString());
        contentValues.put(DBlogin.COLUMN_USER_PASSWORD, password.getText().toString());
        long id = db.insert(TABLE_USER,null,contentValues);
    }
}