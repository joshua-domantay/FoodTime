package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeNameActivity extends AppCompatActivity {

    private EditText newNameField;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        backButton = findViewById(R.id.back_button);
        Toolbar toolbar = findViewById(R.id.toolbar);
       // enable back button

        newNameField = findViewById(R.id.new_name_field);

        Button changeNameButton = findViewById(R.id.change_name_button);
        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = newNameField.getText().toString();

                // Here, you would typically save the new name to a database or shared preferences
                // so that it persists across app launches.
                // For the sake of simplicity, we will just display a toast message with the new name.

                Toast.makeText(ChangeNameActivity.this, "Name changed to " + newName, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
