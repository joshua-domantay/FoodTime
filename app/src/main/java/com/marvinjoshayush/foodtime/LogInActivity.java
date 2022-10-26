package com.marvinjoshayush.foodtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText Email, Password;
    private Button logIn;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);


        register = (TextView) findViewById(R.id.newUserSignUpButton);
        register.setOnClickListener(this);

        logIn = (Button) findViewById(R.id.logInButton);
        logIn.setOnClickListener(this);

        Email = (EditText) findViewById(R.id.emailLogIn);
        Password = (EditText) findViewById(R.id.passwordLogIn);
        mAuth = FirebaseAuth.getInstance();

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.newUserSignUpButton:
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.logInButton:
                userLogin();
                break;

        }
    }

    private void userLogin() {

        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("Email is Required!");
            Email.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Provide valid Email!");
            Email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            Password.setError("Password is Required!");
            Password.requestFocus();
            return;
        }

        if (password.length() < 6) {
            Password.setError("Password should be more then 6 character!");
            Password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LogInActivity.this, "Failed to login Sucessfully! Try-Again!!", Toast.LENGTH_LONG).show();

                }
            }


        });
    }
}