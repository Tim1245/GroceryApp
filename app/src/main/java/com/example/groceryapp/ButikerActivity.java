package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ButikerActivity extends AppCompatActivity {
    static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    static final String ICA_MAXI = "ICA-MAXI";
    static final String LIDL = "LIDL";
    static final String WILLYS = "WILLYS";
    static final String ALL = "ALL";



    Switch switchWILLYS;
    Switch switchCOOP;
    Switch switchICA;
    Switch switchLIDL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stores);

        ImageView backbtn = findViewById(R.id.left);
        TextView btnback = findViewById(R.id.textView13);
        ImageView btnWILLYS = findViewById(R.id.willysbtn);
        switchWILLYS = findViewById(R.id.switch_willys);
        ImageView btnCOOP = findViewById(R.id.coopbtn);
        switchCOOP = findViewById(R.id.switch_coop);
        ImageView btnICA = findViewById(R.id.maxibtn);
        switchICA = findViewById(R.id.switch_maxi);
        ImageView btnLIDL = findViewById(R.id.lidlbtn);
        switchLIDL = findViewById(R.id.switch_lidl);
        ImageView btnALL = findViewById(R.id.allbtn);

        switchWILLYS.setOnClickListener((View view) -> OnFavouriteSwitched());
        switchCOOP.setOnClickListener((View view) -> OnFavouriteSwitched());
        switchICA.setOnClickListener((View view) -> OnFavouriteSwitched());
        switchLIDL.setOnClickListener((View view) -> OnFavouriteSwitched());

        btnCOOP.setOnClickListener((View view) -> OpenStoreActivity(STORA_COOP_VALSVIKEN));
        btnICA.setOnClickListener((View view) -> OpenStoreActivity(ICA_MAXI));
        btnLIDL.setOnClickListener((View view) -> OpenStoreActivity(LIDL));
        btnWILLYS.setOnClickListener((View view) -> OpenStoreActivity(WILLYS));
        btnALL.setOnClickListener((View view) -> OpenStoreActivity(ALL));

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

    }

    @Override
    public void onResume() {
        super.onResume();
        AccountSettings.AddUserSettingsUpdateCallback(this, (UserSettingsMessage settings) -> {
            Log.i("ACCOUNT SETTINGS LOG", "Updating switches, " + settings.toString());
            switchWILLYS.setChecked(settings.IsWILLYSFavoured());
            switchCOOP.setChecked(settings.IsCOOPFavoured());
            switchICA.setChecked(settings.IsICAFavoured());
            switchLIDL.setChecked(settings.IsLIDLFavoured());
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        AccountSettings.RemoveUserSettingsUpdateCallback(this);
    }

    void OnFavouriteSwitched() {
        if (UserManagement.isUserLoggedIn()) {
            AccountSettings.UpdateFavouriteStores(
                    switchWILLYS.isChecked(),
                    switchICA.isChecked(),
                    switchLIDL.isChecked(),
                    switchCOOP.isChecked()
            );
        }
        else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void OpenStoreActivity(String butik){
        AccountSettings.RemoveUserSettingsUpdateCallback(this);
        Intent intent = new Intent(this, ReadDatabase.class);
        intent.putExtra("Butik", butik);
        startActivity(intent);
    }
    public void OpenActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}


