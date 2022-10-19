package com.example.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LanguageSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView messageView;
        Context context;
        Resources resources;
        setContentView(R.layout.languagesettings);
        ImageView swebtn = findViewById(R.id.swebtn);
        ImageView engbtn = findViewById(R.id.engbtn);
        TextView backbtn = findViewById(R.id.textView13);
        ImageView btnback = findViewById(R.id.left);
        LocaleHelper localhelper = new LocaleHelper();

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

    engbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            localhelper.setLocale(LanguageSettings.this,"en");
            Intent refresh = getIntent();
            finish();
            startActivity(refresh);
        }
    });

        swebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            localhelper.setLocale(LanguageSettings.this,"sv");
            Intent refresh = getIntent();
            finish();
            startActivity(refresh);
        }

    });

}
    public void OpenActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
