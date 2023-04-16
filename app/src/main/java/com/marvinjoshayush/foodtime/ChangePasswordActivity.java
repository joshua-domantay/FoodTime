package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText mOldPasswordEditText;
    private EditText mNewPasswordEditText;
    private Button mChangePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mOldPasswordEditText = findViewById(R.id.old_password);
        mNewPasswordEditText = findViewById(R.id.new_password);
        mChangePasswordButton = findViewById(R.id.change_password_button);

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = mOldPasswordEditText.getText().toString().trim();
                String newPassword = mNewPasswordEditText.getText().toString().trim();

                // Verify that both old and new passwords are not empty
                if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Please enter both old and new passwords", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Reauthenticate the user with their old password
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // If reauthentication is successful, update the password
                                        user.updatePassword(newPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(ChangePasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        } else {
                                                            Toast.makeText(ChangePasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(ChangePasswordActivity.this, "Invalid old password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
