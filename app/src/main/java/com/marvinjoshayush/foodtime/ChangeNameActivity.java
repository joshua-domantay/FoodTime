package com.marvinjoshayush.foodtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeNameActivity extends AppCompatActivity {

    private EditText newNameField;
    private Button backButton;
    private FirebaseAuth mAuth;
    private SharedPreferences mSharedPreferences;

    public static final int RESULT_OK = 1;
    public static final int REQUEST_CHANGE_NAME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        TextView profileNameTextView = findViewById(R.id.profileName);

        backButton = findViewById(R.id.back_button);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        newNameField = findViewById(R.id.new_name_field);

        // Initialize the save button
        Button saveButton = findViewById(R.id.save_button);

        // Set an OnClickListener to handle the button click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = newNameField.getText().toString();

                // Save the user's input to SharedPreferences
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("name", newName);
                editor.apply();

                // Display a success message
                Toast.makeText(ChangeNameActivity.this, "Name saved: " + newName, Toast.LENGTH_SHORT).show();
            }
        });

        String savedName = mSharedPreferences.getString("name", "");
        profileNameTextView.setText(savedName);

        Button changeNameButton = findViewById(R.id.change_name_button);
        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeNameActivity.this, ChangeNameActivity.class);
                startActivityForResult(intent, REQUEST_CHANGE_NAME);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHANGE_NAME && resultCode == ChangeNameActivity.RESULT_OK) {
            String newName = data.getStringExtra("newName");
            TextView profileNameTextView = findViewById(R.id.profileNameChange);
            profileNameTextView.setText(newName);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangeNameActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // clear any previous instances of HomeActivity
        startActivity(intent);
        finish();
    }
}



