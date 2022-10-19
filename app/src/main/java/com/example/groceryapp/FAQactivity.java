package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FAQactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.faq);

        TextView backbtn = findViewById(R.id.textView16);
        ImageView btnback = findViewById(R.id.left2);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(Settings.class);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(Settings.class);
            }
        });
    }
    public void OpenActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}

