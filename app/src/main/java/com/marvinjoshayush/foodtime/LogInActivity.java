package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteOpenHelper openhelper;
    Button _btnlogin;
    EditText _txtEmail, _txtpass;
    Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setNewUserSignUpButton();

        openhelper = new DBlogin(this);
        db = openhelper.getReadableDatabase();
        _btnlogin = (Button) findViewById(R.id.logInButton);
        _txtEmail = (EditText) findViewById(R.id.emailLogIn);
        _txtpass = (EditText) findViewById(R.id.passwordLogIn);
        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = _txtEmail.getText().toString();
                String pass = _txtpass.getText().toString();
                cursor = db.rawQuery("SELECT * FROM "+ DBlogin.TABLE_USER+ " WHERE "+ DBlogin.COLUMN_USER_EMAIL+ "=? AND "+ DBlogin.COLUMN_USER_PASSWORD+"=?",new String[]{email,pass});


                if(cursor != null){
                    cursor.moveToNext();
                    if(cursor.getCount() >0){
                        Toast.makeText(getApplicationContext(),"login successfully",Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });



    }

    private void setNewUserSignUpButton() {
        Button btn = findViewById(R.id.newUserSignUpButton);
        btn.setOnClickListener(item -> {
            startActivity(new Intent(this, mainActivity.class));
        });
    }


}



