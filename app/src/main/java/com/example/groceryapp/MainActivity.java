package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    // Get the database instance and store into object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If we want to force immediate login
        //UserManagement.RequireUserLogin(this);
        setContentView(R.layout.newhomepage);
        ImageView btnRead = findViewById(R.id.productbtn);
        ImageView btnMap = findViewById(R.id.mapbtn);
        ImageView btnLogOut = findViewById(R.id.logoutbtn);
        ImageView btnSettings = findViewById(R.id.settingbtn);
        ImageView btnSearch = findViewById(R.id.searchbtn);


        btnMap.setOnClickListener(view -> OpenActivity(MapsActivity.class));

        btnRead.setOnClickListener(view -> OpenActivity(ButikerActivity.class));

        btnLogOut.setOnClickListener((View view) -> UserManagement.signOut());

        btnSettings.setOnClickListener(view -> OpenActivity(Settings.class));

        btnSearch.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, ReadDatabase.class);
            intent.putExtra("Butik", "ALL");
            startActivity(intent);
        });



    }

    public void OpenActivity(Class activity){
        Intent intent = new Intent(this, activity);

        startActivity(intent);
    }


}
