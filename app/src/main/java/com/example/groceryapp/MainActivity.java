package com.example.groceryapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private TextView textView;
    // Get the database instance and store into object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input=findViewById(R.id.input);
        Button btn = findViewById(R.id.btn);
        Button btnRead = findViewById(R.id.btnRead);
        textView = findViewById(R.id.textView);

        DatabaseReference rootDatabaseref = FirebaseDatabase.getInstance("https://grocery-price-tracker-fedd5-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Message");
        DatabaseReference LidlDatabaseref = FirebaseDatabase.getInstance("https://grocery-price-tracker-fedd5-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Lidl");

        btnRead.setOnClickListener(view -> LidlDatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String data=snapshot.getValue().toString();
                    textView.setText(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));


        btn.setOnClickListener(view -> {
            String data=input.getText().toString();

            rootDatabaseref.setValue(data);
        });


    }
}