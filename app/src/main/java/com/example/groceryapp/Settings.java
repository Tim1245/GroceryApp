package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);
        ImageView settingsbtn = findViewById(R.id.FAQbtn);


        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(FAQactivity.class);
            }
        });

    }

    public void OpenActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
