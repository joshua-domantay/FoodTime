package com.marvinjoshayush.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.MenuItem;



public class ChangeNameActivity extends AppCompatActivity {

    private EditText newNameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}