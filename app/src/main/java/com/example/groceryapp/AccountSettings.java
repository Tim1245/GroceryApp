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
    // Must be the first variable because it is not initialised before Init somehow otherwise
    private static final ValueEventListener DatabaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.i(ACCOUNT_SETTINGS_LOG_TAG, "Change to user settings detected");
            UserSettingsMessage settings = settings = GetSettingsFromSnapshot(snapshot);
            if (settings == null) {
                Log.e(ACCOUNT_SETTINGS_LOG_TAG, "Could not read the updated database snapshot");
                return;
            }
            last_settings = settings;
            Log.i(ACCOUNT_SETTINGS_LOG_TAG, "The new favourites settings are: " + settings.toString());
            for (Consumer<UserSettingsMessage> callback : Callbacks.values()) {
                try {
                    callback.accept(settings);
                }
                catch(Exception e) {
                    Log.e(ACCOUNT_SETTINGS_LOG_TAG, "Error in a callback", e);
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(ACCOUNT_SETTINGS_LOG_TAG, "This should never be triggered?", error.toException());
        }
    };

    private static AccountSettings Singleton = new AccountSettings();
    private static final Map<Object, Consumer<UserSettingsMessage>> Callbacks = new HashMap<>();
    private static FirebaseDatabase database;
    private static UserSettingsMessage last_settings = null;

    private static String ACCOUNT_SETTINGS_LOG_TAG = "ACCOUNT SETTINGS LOG";

    private static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    private static final String ICA_MAXI = "ICA-MAXI";
    private static final String LIDL = "LIDL";
    private static final String WILLYS = "WILLYS";

    private AccountSettings() {
        Init();
    }

    private static void Init() {
        Log.i(ACCOUNT_SETTINGS_LOG_TAG, "Initiated the account settings object");
        database = FirebaseDatabase.getInstance();
        UserManagement.RegisterOnLoginCallback(Singleton, AccountSettings::AddDatabaseListener);
        UserManagement.RegisterOnLogoutCallback(Singleton, AccountSettings::RemoveDatabaseListener);
    }

    private static void AddDatabaseListener() {
        try {
            database
                    .getReference()
                    .child("Users")
                    .child(UserManagement.GetUserUID())
                    .addValueEventListener(DatabaseListener);
            Log.i(ACCOUNT_SETTINGS_LOG_TAG, "Added a database listener");
        }
        catch (Exception e) {
            Log.e(ACCOUNT_SETTINGS_LOG_TAG, "Could not register database listener, (listener == null) = " + (DatabaseListener == null), e);
        }
    }

    private static void RemoveDatabaseListener() {
        try {
            database
                    .getReference()
                    .child("Users")
                    .child(UserManagement.GetUserUID())
                    .removeEventListener(DatabaseListener);
            last_settings = null;
            Log.i(ACCOUNT_SETTINGS_LOG_TAG, "Removed a database listener");
        }
        catch(Exception e) {
            Log.e(ACCOUNT_SETTINGS_LOG_TAG, "Could not remove database listener", e);
        }
    }

    public static void AddUserSettingsUpdateCallback(Object caller, Consumer<UserSettingsMessage> callback) {
        Log.i(ACCOUNT_SETTINGS_LOG_TAG, "Recieved new callback for user settings updated");
        Callbacks.put(caller, callback);
        if (last_settings != null) {
            Log.i(ACCOUNT_SETTINGS_LOG_TAG, "Triggering new callback immediately");
            callback.accept(last_settings);
        }
    }

    public static void RemoveUserSettingsUpdateCallback(Object caller) {
        Log.i(ACCOUNT_SETTINGS_LOG_TAG, "Removed a callback for user settings updated");
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
            Log.e(ACCOUNT_SETTINGS_LOG_TAG, "Could not create a UserSettingsMessage instance from snapshot", e);
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
            database
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
            Log.w(ACCOUNT_SETTINGS_LOG_TAG, "Encountered error when saving favourites", e);
        }
    }
}
