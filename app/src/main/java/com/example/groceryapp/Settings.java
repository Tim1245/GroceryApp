package com.example.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        TextView messageView;
        Context context;
        Resources resources;
        setContentView(R.layout.settings);
        ImageView faqbtn = findViewById(R.id.FAQbtn);
        TextView faqtext = findViewById(R.id.faqtext);
        ImageView languaguebtn = findViewById(R.id.languagebtn);
        TextView backbtn = findViewById(R.id.textView13);
        ImageView btnback = findViewById(R.id.left);
        LocaleHelper localhelper = new LocaleHelper();


        faqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(FAQactivity.class);
            }
        });
        languaguebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(LanguageSettings.class);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(MainActivity.class);
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(MainActivity.class);
            }
        });
        faqtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(FAQactivity.class);
            }
        });


*/
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TEMP DEBUG", "Calling on resume");
        setContentView(R.layout.settings);
        ImageView faqbtn = findViewById(R.id.FAQbtn);
        TextView faqtext = findViewById(R.id.faqtext);
        ImageView languaguebtn = findViewById(R.id.languagebtn);
        TextView backbtn = findViewById(R.id.textView13);
        ImageView btnback = findViewById(R.id.left);
        LocaleHelper localhelper = new LocaleHelper();


        faqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(FAQactivity.class);
            }
        });
        languaguebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(LanguageSettings.class);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(MainActivity.class);
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(MainActivity.class);
            }
        });
        faqtext.setOnClickListener(new View.OnClickListener() {
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