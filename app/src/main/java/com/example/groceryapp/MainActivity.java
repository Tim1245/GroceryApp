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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> favs = new ArrayList<String>();
    static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    static final String ICA_MAXI = "ICA-MAXI";
    static final String LIDL = "LIDL";
    static final String WILLYS = "WILLYS";
    static final String ALL = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*localehelper.onMake(this);
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
*/
    }

    @Override
    public void onResume() {
        super.onResume();
        stopService(new Intent(this, NotificationHandler.class));
        LocaleHelper.onMake(this);
        setContentView(R.layout.newhomepage);
        ImageView btnRead = findViewById(R.id.productbtn);
        ImageView btnMap = findViewById(R.id.mapbtn);
        ImageView btnLogOut = findViewById(R.id.logoutbtn);
        ImageView btnLogIn = findViewById(R.id.loginbtn);
        ImageView btnSettings = findViewById(R.id.settingbtn);
        ImageView btnSearch = findViewById(R.id.searchbtn);
        ImageView btnFavorites = findViewById(R.id.favoritesbtn);



        btnMap.setOnClickListener(view -> OpenActivity(MapsActivity.class));

        btnRead.setOnClickListener(view -> OpenActivity(ButikerActivity.class));

        btnLogOut.setOnClickListener((View view) ->  {
            UserManagement.signOut();
            if (LoginActivity.logOutPressed == 1)
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            LoginActivity.logOutPressed = 0;
        });

        btnLogIn.setOnClickListener((View view) ->  {
            if (UserManagement.isUserLoggedIn()) {
            }
            else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        btnSettings.setOnClickListener(view -> OpenActivity(Settings.class));

        btnFavorites.setOnClickListener(view -> OpenActivity(FavoritesActivity.class));

        btnSearch.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, ReadDatabase.class);
            intent.putExtra("Butik", "ALL");
            startActivity(intent);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        startService(new Intent(this, NotificationHandler.class));
    }

    public void OpenActivity(Class activity) {
        Intent intent = new Intent(this, activity);

        startActivity(intent);
    }

    public void openFav(){

        if(UserManagement.isUserLoggedIn()){
            AccountSettings.AddUserSettingsUpdateCallback(this, (UserSettingsMessage settings) -> {
                Log.i("ACCOUNT SETTINGS LOG", "Updating switches, " + settings.toString());
                if (settings.IsWILLYSFavoured())
                    favs.add(WILLYS);
                if (settings.IsCOOPFavoured())
                    favs.add(STORA_COOP_VALSVIKEN);
                if (settings.IsLIDLFavoured())
                    favs.add(LIDL);
                if (settings.IsICAFavoured())
                    favs.add(ICA_MAXI);


            });

            Intent intent = new Intent(this, ReadDatabase.class);

            intent.putExtra("favs", favs);
            startActivity(intent);
        }else{

            Intent intent = new Intent(this, MapsActivity.class);

            startActivity(intent);}
}
}
