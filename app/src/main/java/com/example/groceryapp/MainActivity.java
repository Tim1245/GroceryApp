package com.example.groceryapp;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.widget.Toast;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    // Get the database instance and store into object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseReference= FirebaseDatabase.getInstance("https://grocery-price-tracker-fedd5-default-rtdb.europe-west1.firebasedatabase.app/").getReference("message");
        databaseReference.setValue("Hello,Test 2").addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_LONG).show()).addOnCompleteListener(task -> {
            //
        });




    }
}