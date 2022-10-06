package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ButikerActivity extends AppCompatActivity {
    static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    static final String ICA_MAXI = "ICA-MAXI";
    static final String LIDL = "LIDL";
    static final String WILLYS = "WILLYS";

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
        //setContentView(R.layout.activity_butiker);

        setContentView(R.layout.activity_stores);

        ImageView btnWILLYS = findViewById(R.id.willysbtn);
        switchWILLYS = findViewById(R.id.switch_willys);
        ImageView btnCOOP = findViewById(R.id.coopbtn);
        switchCOOP = findViewById(R.id.switch_coop);
        ImageView btnICA = findViewById(R.id.maxibtn);
        switchICA = findViewById(R.id.switch_maxi);
        ImageView btnLIDL = findViewById(R.id.lidlbtn);
        switchLIDL = findViewById(R.id.switch_lidl);

        /* Button btnWILLYS = findViewById(R.id.btnWILLYS);
        switchWILLYS = findViewById(R.id.Switch_Favourite_Willys);
        Button btnCOOP = findViewById(R.id.btnCOOP);
        switchCOOP = findViewById(R.id.Switch_Favourite_Coop);
        Button btnICA = findViewById(R.id.btnICA);
        switchICA = findViewById(R.id.Switch_Favourite_ICA);
        Button btnLIDL = findViewById(R.id.btnLIDL);
        switchLIDL = findViewById(R.id.Switch_Favourite_Lidl);*/

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
                OpenActivity();
        });

        btnICA.setOnClickListener((View view) -> {
                butik = ICA_MAXI; //"ICA-MAXI";
                OpenActivity();
        });

        btnLIDL.setOnClickListener((View view) -> {
                butik = LIDL; //"LIDL";
                OpenActivity();
        });

        btnWILLYS.setOnClickListener((View view) -> {
                butik = WILLYS; //"WILLYS";
                OpenActivity();
        });
    }

    void OnFavouriteSwitched() {
        UserManagement.RequireUserLogin(this);
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

    public void OpenActivity(){
        Intent intent = new Intent(this, ReadDatabase.class);
        intent.putExtra("Butik", butik);
        startActivity(intent);
    }
}


