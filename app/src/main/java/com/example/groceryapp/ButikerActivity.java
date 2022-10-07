package com.example.groceryapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ButikerActivity extends AppCompatActivity {
    static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    static final String ICA_MAXI = "ICA-MAXI";
    static final String LIDL = "LIDL";
    static final String WILLYS = "WILLYS";

    // Request code was randomly generated
    static final int LOGIN_REQUEST_CODE = 809730;
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
           if (UserManagement.isUserLoggedIn()) {
               OnFavouriteSwitched();
           }
           else {
               startActivity(new Intent(this, LoginActivity.class));
           }
        });

        switchCOOP.setOnClickListener((View view) -> {
            if (UserManagement.isUserLoggedIn()) {
                OnFavouriteSwitched();
            }
            else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        switchICA.setOnClickListener((View view) -> {
            if (UserManagement.isUserLoggedIn()) {
                OnFavouriteSwitched();
            }
            else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        switchLIDL.setOnClickListener((View view) -> {
            if (UserManagement.isUserLoggedIn()) {
                OnFavouriteSwitched();
            }
            else {
                startActivity(new Intent(this, LoginActivity.class));
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != LOGIN_REQUEST_CODE) {
            return;
        }
    }

    void OnFavouriteSwitched() {
        Map<String, Object> user = new HashMap<String, Object>();
        user.put(WILLYS, switchWILLYS.isChecked());
        user.put(STORA_COOP_VALSVIKEN, switchCOOP.isChecked());
        user.put(ICA_MAXI, switchICA.isChecked());
        user.put(LIDL, switchLIDL.isChecked());
        try {
            String UID = UserManagement.GetUserUID();
            Log.i("FAVOURITES TEST", "Attempting to store updated favourites");
            database
                    .getReference()
                    .child("Users")
                    .child(UID)
                    .setValue(user)
                    .addOnSuccessListener((Void v) -> {
                        Log.i("FAVOURITES TEST", "Managed to add/update user favourites");
                    })
                    .addOnFailureListener((Exception e) -> {
                        Log.i("FAVOURITES TEST", "Could not add/update user favourites", e);
                    });
        }
        catch(Exception e) {
            Log.w("LOGIN ERROR", "Encountered error when saving favourites", e);
        }

    }

    private void SetFavouriteSwitches() {
        try {
            database
                    .getReference()
                    .child("Users")
                    .child(UserManagement.GetUserUID())
                    .get()
                    .addOnCompleteListener((Task<DataSnapshot> task) -> {
                        if (task.isSuccessful() == false) {
                            Log.i(TEST_TAG, "Could not load stored preferences", task.getException());
                        }
                        Object snapshot = task.getResult().getValue();
                        if (snapshot instanceof Map) {
                            Map<String, Boolean> data = (Map) snapshot;
                            switchWILLYS.setChecked(data.get(WILLYS));
                            switchCOOP.setChecked(data.get(STORA_COOP_VALSVIKEN));
                            switchICA.setChecked(data.get(ICA_MAXI));
                            switchWILLYS.setChecked(data.get(WILLYS));
                        }
                    });
        }
        catch(Exception e) {
            Log.i(TEST_TAG, "Encountered an error when attempting to load favourites", e);
        }
    }

    public void OpenStoreActivity(){
        Intent intent = new Intent(this, ReadDatabase.class);
        intent.putExtra("Butik", butik);
        startActivity(intent);
    }
}


