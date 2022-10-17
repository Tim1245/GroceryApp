package com.example.groceryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

    @Override
    public void onStop() {
        // Testig code
        Log.i("DEBUG", "Main activity had onstop called");
        startService(new Intent(this, NotificationHandler.class));
        // End of testing code
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("DEBUG", "Main activity had onstart called");
        stopService(new Intent(this, NotificationHandler.class));
    }
}
