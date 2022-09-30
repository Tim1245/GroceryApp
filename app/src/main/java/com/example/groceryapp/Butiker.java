package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Butiker extends AppCompatActivity {
    String butik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butiker);

        Button btnWILLYS = findViewById(R.id.btnWILLYS);
        Button btnCOOP = findViewById(R.id.btnCOOP);
        Button btnICA = findViewById(R.id.btnICA);
        Button btnLIDL = findViewById(R.id.btnLIDL);



        btnCOOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butik = "STORA_COOP_VALSVIKEN";
                OpenActivity();
            }
        });

        btnICA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butik = "ICA-MAXI";
                OpenActivity();
            }
        });

        btnLIDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butik = "LIDL";
                OpenActivity();
            }
        });

        btnWILLYS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butik = "WILLYS";
                OpenActivity();
            }
        });



    }
    public void OpenActivity(){
        Intent intent = new Intent(this, ReadDatabase.class);
        intent.putExtra("Butik", butik);
        startActivity(intent);
    }
}


