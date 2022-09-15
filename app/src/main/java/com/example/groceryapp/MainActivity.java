package com.example.groceryapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText input;

    // Get the database instance and store into object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input=findViewById(R.id.input);
        Button btn = findViewById(R.id.btn);

        DatabaseReference rootDatabaseref = FirebaseDatabase.getInstance("https://grocery-price-tracker-fedd5-default-rtdb.europe-west1.firebasedatabase.app/").getReference("message");

        btn.setOnClickListener(view -> {
            String data=input.getText().toString();

            rootDatabaseref.setValue(data);
        });


    }
}