package com.marvinjoshayush.foodtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView login ,signup;
    private EditText FirstName, LastName,Email, Password;


    private FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        mAuth= FirebaseAuth.getInstance();
        login = (TextView) findViewById(R.id.haveAccountLogInButton);
        login.setOnClickListener(this);
        signup = (Button) findViewById(R.id.signUpButton);
        signup.setOnClickListener(this);
        FirstName = (EditText)  findViewById(R.id.firstNameSignUp);
        LastName = (EditText)  findViewById(R.id.lastNameSignUp);
        Email = (EditText)  findViewById(R.id.emailSignUp);
        Password = (EditText)  findViewById(R.id.passwordSignUp);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.haveAccountLogInButton:
                startActivity(new Intent(this,LogInActivity.class));
                break;

            case R.id.signUpButton:
                signup();
                break;
        }

    }

    private void signup(){

        String Firstname = FirstName.getText().toString().trim();
        String Lastname = LastName.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(Firstname.isEmpty()){
            FirstName.setError("Firstname is Required!");
            FirstName.requestFocus();
            return;

        }

        if(Lastname.isEmpty()){
            LastName.setError("Lastname is Required!");
            LastName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            Email.setError("Email is Required!");
            Email.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Provide valid Email!");
            Email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            Password.setError("Password is Required!");
            Password.requestFocus();
            return;
        }

        if(password.length() < 6){
            Password.setError("Password should be more then 6 character!");
            Password.requestFocus();
            return;

        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(Firstname,Lastname,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this,"User has been registered Sucessfully!",Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(SignUpActivity.this, WelcomeActivity1.class));
                                            }

                                            else {
                                                Toast.makeText(SignUpActivity.this,"Failed to register Sucessfully! Try-Again!!",Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });
                        }
                        else{
                            Toast.makeText(SignUpActivity.this,"Failed to register Sucessfully!!",Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
}