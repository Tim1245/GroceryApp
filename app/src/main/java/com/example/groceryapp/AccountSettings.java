package com.example.groceryapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AccountSettings {
    private static final Map<Object, Consumer<UserSettingsMessage>> Callbacks = new HashMap<>();
    private static FirebaseDatabase database;
    private static boolean initieated = false;
    private static UserSettingsMessage last_settings = null;

    private static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    private static final String ICA_MAXI = "ICA-MAXI";
    private static final String LIDL = "LIDL";
    private static final String WILLYS = "WILLYS";

    private static FirebaseDatabase db() {
        Log.i("DEBUG", "Getting db reference or init");
        Init();
        return database;
    }

    private static void Init() {
        if (initieated) {
            return;
        }
        if (database == null) {
            database = FirebaseDatabase.getInstance();
        }
        if (UserManagement.isUserLoggedIn() == false) {
            return;
        }
        try {
            database
                    .getReference()
                    .child("Users")
                    .child(UserManagement.GetUserUID())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.i("DEBUG", "Change detected");
                            UserSettingsMessage settings = GetSettingsFromSnapshot(snapshot);
                            if (settings == null) {
                                Log.i("DEBUG ERROR", "Updated data was not in assumed format");
                                return;
                            }
                            last_settings = settings;
                            for (Consumer<UserSettingsMessage> callback : Callbacks.values()) {
                                try {
                                    callback.accept(settings);
                                }
                                catch(Exception e) {
                                    Log.i("Account settings error", "Error in a callback", e);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
        catch (Exception e) {

        }
    }

    public static void AddUpdateCallback(Object caller, Consumer<UserSettingsMessage> callback) {
        Init();
        Callbacks.put(caller, callback);
        if (last_settings != null) {
            callback.accept(last_settings);
        }
    }

    public static void RemoveUpdateCallback(Object caller) {
        Callbacks.remove(caller);
    }

    private static UserSettingsMessage GetSettingsFromSnapshot(DataSnapshot snapshot) {
        try {
            if (snapshot.child("Favourites").getValue() instanceof Map) {
                Map<String, Boolean> data = (Map) snapshot.child("Favourites").getValue();
                return new UserSettingsMessage(
                        data.get(WILLYS),
                        data.get(STORA_COOP_VALSVIKEN),
                        data.get(ICA_MAXI),
                        data.get(LIDL)
                );
            }
        }
        catch(Exception e) {

        }
        return null;
    }

    public static void UpdateFavouriteStores(Boolean willys, Boolean ica, Boolean lidl, Boolean coop) {
        Map<String, Object> user = new HashMap<String, Object>();
        user.put(WILLYS, willys);
        user.put(STORA_COOP_VALSVIKEN, coop);
        user.put(ICA_MAXI, ica);
        user.put(LIDL, lidl);
        try {
            String UID = UserManagement.GetUserUID();
            Log.i("FAVOURITES TEST", "Attempting to store updated favourites");
            db()
                    .getReference()
                    .child("Users")
                    .child(UID)
                    .child("Favourites")
                    .setValue(user)
                    .addOnSuccessListener((Void v) -> {
                        Log.i("FAVOURITES TEST", "Managed to add/update user favourites");
                    })
                    .addOnFailureListener((Exception e) -> {
                        Log.i("FAVOURITES TEST", "Could not add/update user favourites", e);
                    });
        }
        catch(Exception e) {
            Log.w("ACCOUNT SETTINGS ERROR", "Encountered error when saving favourites", e);
        }
    }
}
