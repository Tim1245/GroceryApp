package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class ButikerActivity extends AppCompatActivity {
    static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    static final String ICA_MAXI = "ICA-MAXI";
    static final String LIDL = "LIDL";
    static final String WILLYS = "WILLYS";

    // Request code was randomly generated
    static final String TEST_TAG = "FAVOURITES_TEST";

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

        switchWILLYS.setOnClickListener((View view) -> {
               OnFavouriteSwitched();
        });

        switchCOOP.setOnClickListener((View view) -> {
                OnFavouriteSwitched();
        });

        switchICA.setOnClickListener((View view) -> {
                OnFavouriteSwitched();
        });

        switchLIDL.setOnClickListener((View view) -> {
                OnFavouriteSwitched();
        });

        btnCOOP.setOnClickListener((View view) -> {
                butik = STORA_COOP_VALSVIKEN; //"STORA_COOP_VALSVIKEN";
                OpenStoreActivity();
        });

        btnICA.setOnClickListener((View view) -> {
                butik = ICA_MAXI; //"ICA-MAXI";
                OpenStoreActivity();
        });

        btnLIDL.setOnClickListener((View view) -> {
                butik = LIDL; //"LIDL";
                OpenStoreActivity();
        });

        btnWILLYS.setOnClickListener((View view) -> {
                butik = WILLYS; //"WILLYS";
                OpenStoreActivity();
        });
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
        AccountSettings.AddUpdateCallback(this, () -> {
            switchWILLYS.setChecked(AccountSettings.IsWillysFavourited());
            switchCOOP.setChecked(AccountSettings.IsCOOPFavourited());
            switchICA.setChecked(AccountSettings.IsICAFavourited());
            switchLIDL.setChecked(AccountSettings.IsLIDLFavourited());
        });
    }

    public void OpenStoreActivity(){
        AccountSettings.RemoveUpdateCallback(this);
        Intent intent = new Intent(this, ReadDatabase.class);
        intent.putExtra("Butik", butik);
        startActivity(intent);
    }
}


