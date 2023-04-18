package com.marvinjoshayush.foodtime;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChangeAddressActivity extends AppCompatActivity {
    private EditText addressEditText;
    private Button saveButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);

        Toolbar toolbar = findViewById(R.id.toolbar);

        addressEditText = findViewById(R.id.address_edit_text);
        View titleText = findViewById(R.id.titleTextView);
        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = addressEditText.getText().toString().trim();
                if (address.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your address", Toast.LENGTH_SHORT).show();
                } else {
                    // save address
                    Toast.makeText(getApplicationContext(), "Address saved", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
        Intent intent = new Intent(ChangeAddressActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
