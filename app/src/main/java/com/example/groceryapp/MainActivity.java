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
import android.widget.Toast;

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
    LocaleHelper localehelper = new LocaleHelper();
    // Get the database instance and store into object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If we want to force immediate login
        //UserManagement.RequireUserLogin(this);
        localehelper.onMake(this);
        setContentView(R.layout.newhomepage);
        ImageView btnRead = findViewById(R.id.productbtn);
        ImageView btnMap = findViewById(R.id.mapbtn);
        ImageView btnLogOut = findViewById(R.id.logoutbtn);
        ImageView btnSettings = findViewById(R.id.settingbtn);
        ImageView btnSearch = findViewById(R.id.searchbtn);


        btnMap.setOnClickListener(view -> OpenActivity(MapsActivity.class));

        btnRead.setOnClickListener(view -> OpenActivity(ButikerActivity.class));

        btnLogOut.setOnClickListener((View view) ->  {
            UserManagement.signOut();
            if (LoginActivity.logOutPressed == 1)
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            LoginActivity.logOutPressed = 0;
        });

        btnSettings.setOnClickListener(view -> OpenActivity(Settings.class));

        btnSearch.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, ReadDatabase.class);
            intent.putExtra("Butik", "ALL");
            startActivity(intent);
        });

        startService(new Intent(this, NotificationHandler.class));

    }

    public void OpenActivity(Class activity) {
        Intent intent = new Intent(this, activity);

        startActivity(intent);
    }
}
