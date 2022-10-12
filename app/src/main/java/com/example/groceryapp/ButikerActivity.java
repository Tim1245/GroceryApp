package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.firebase.database.FirebaseDatabase;

public class ButikerActivity extends AppCompatActivity {
    static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    static final String ICA_MAXI = "ICA-MAXI";
    static final String LIDL = "LIDL";
    static final String WILLYS = "WILLYS";

    String butik;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    Switch switchWILLYS;
    Switch switchCOOP;
    Switch switchICA;
    Switch switchLIDL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stores);
        ImageView btnWILLYS = findViewById(R.id.willysbtn);
        switchWILLYS = findViewById(R.id.switch_willys);
        ImageView btnCOOP = findViewById(R.id.coopbtn);
        switchCOOP = findViewById(R.id.switch_coop);
        ImageView btnICA = findViewById(R.id.maxibtn);
        switchICA = findViewById(R.id.switch_maxi);
        ImageView btnLIDL = findViewById(R.id.lidlbtn);
        switchLIDL = findViewById(R.id.switch_lidl);

        if (UserManagement.isUserLoggedIn()) {
            SetFavouriteSwitches();
        }

        switchWILLYS.setOnClickListener((View view) -> OnFavouriteSwitched());
        switchCOOP.setOnClickListener((View view) -> OnFavouriteSwitched());
        switchICA.setOnClickListener((View view) -> OnFavouriteSwitched());
        switchLIDL.setOnClickListener((View view) -> OnFavouriteSwitched());

        btnCOOP.setOnClickListener((View view) -> OpenStoreActivity(STORA_COOP_VALSVIKEN));
        btnICA.setOnClickListener((View view) -> OpenStoreActivity(ICA_MAXI));
        btnLIDL.setOnClickListener((View view) -> OpenStoreActivity(LIDL));
        btnWILLYS.setOnClickListener((View view) -> OpenStoreActivity(WILLYS));
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

    private void SetFavouriteSwitches() {
        Log.i("DEBUG", "Setting switches");
        AccountSettings.AddUpdateCallback(this, (UserSettingsMessage settings) -> {
            switchWILLYS.setChecked(settings.IsWillysFavourited());
            switchCOOP.setChecked(settings.IsCOOPFavourited());
            switchICA.setChecked(settings.IsICAFavourited());
            switchLIDL.setChecked(settings.IsLIDLFavourited());
        });
    }

    public void OpenStoreActivity(String Butik){
        butik = Butik;
        AccountSettings.RemoveUpdateCallback(this);
        Intent intent = new Intent(this, ReadDatabase.class);
        intent.putExtra("Butik", butik);
        startActivity(intent);
    }
}


