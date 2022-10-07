package com.example.groceryapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Consumer;

public class AccountSettings {
    private static Map<Object, Runnable> Callbacks = new HashMap<Object, Runnable>();
    private static FirebaseDatabase database;

    private static Boolean fav_WILLYS = false;
    public static Boolean IsWillysFavourited() {
        return fav_WILLYS;
    }

    private static Boolean fav_COOP = false;
    public static Boolean IsCOOPFavourited() {
        return fav_COOP;
    }

    private static Boolean fav_ICA = false;
    public static boolean IsICAFavourited() {
        return fav_ICA;
    }

    private static Boolean fav_LIDL = false;
    public static Boolean IsLIDLFavourited() {
        return fav_LIDL;
    }

    private static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    private static final String ICA_MAXI = "ICA-MAXI";
    private static final String LIDL = "LIDL";
    private static final String WILLYS = "WILLYS";

    private static FirebaseDatabase db() {
        Log.i("DEBUG", "Getting db reference or init");
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            Init();
        }
        return database;
    }

    private static void Init() {
        if (UserManagement.isUserLoggedIn()) {
            try {
                database
                        .getReference()
                        .child("Users")
                        .child(UserManagement.GetUserUID())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.i("DEBUG", "Change detected");
                                OnFavouriteStoresUpdated(snapshot);
                                for (Runnable callback : Callbacks.values()) {
                                    try {
                                        callback.run();
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
    }

    public static void AddUpdateCallback(Object caller, Runnable callback) {
        Callbacks.put(caller, callback);
        db();
    }

    public static void RemoveUpdateCallback(Object caller) {
        Callbacks.remove(caller);
    }

    private static void OnFavouriteStoresUpdated(DataSnapshot snapshot) {
        Log.i("DEBUG", "Setting values in accordance to update");
        if (snapshot.child("Favourites").getValue() instanceof Map) {
            Map<String, Boolean> data = (Map) snapshot.child("Favourites").getValue();
            fav_WILLYS = data.get(WILLYS);
            fav_COOP = data.get(STORA_COOP_VALSVIKEN);
            fav_ICA = data.get(ICA_MAXI);
            fav_LIDL = data.get(LIDL);
        }
        else {
            Log.i("DEBUG ERROR", "Updated data was not in assumed format");
        }
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
