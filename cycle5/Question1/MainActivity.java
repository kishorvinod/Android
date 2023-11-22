package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText editTextName, editTextAge, editTextMark;
    private TextView textViewData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextMark = findViewById(R.id.editTextMark);
        textViewData = findViewById(R.id.textViewData);
        Button buttonInsert = findViewById(R.id.buttonInsert);
        Button buttonSelect = findViewById(R.id.buttonSelect);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());
                int mark = Integer.parseInt(editTextMark.getText().toString());
                boolean insertData = db.insertUser(name, age, mark); // insert data
                if (insertData) {
                    Toast.makeText(MainActivity.this, "User Inserted Successfully", Toast.LENGTH_SHORT).show();
                    displayData();
                } else {Toast.makeText(MainActivity.this, "Failed to Insert User", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayData();
            }
        });
    }
    private void displayData() {
        Cursor cursor = db.getAllUsers();
        if (cursor.getCount() == 0) {
            textViewData.setText("No users found");
        } else {
            StringBuilder data = new StringBuilder();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                int mark = cursor.getInt(3);
                data.append("ID: ").append(id)
                        .append(", Name: ").append(name)
                        .append(", Age: ").append(age)
                        .append(", Mark: ").append(mark)
                        .append("\n");
            }
            textViewData.setText(data.toString());
        }
    }
}