package com.example.groceryapp;

import com.google.firebase.auth.*;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserManagement {
    static FirebaseAuth auth = null;
    static private FirebaseAuth getAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    static void createUserDefault(String email, String password) {
        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("Price Debug", "Inside listener");
                if (task.isSuccessful()) {
                    Log.i("Price Debug", "Created new user");
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Log.i("Price Debug", "Could not create user due to bad credentials.", task.getException());
                    }
                    else {
                        Log.i("Price Debug", "Miscellaneous error when creating user", task.getException());
                    }
                }
            }
        };
        getAuth()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    static FirebaseUser getUserInfo() {
        return getAuth().getCurrentUser();
    }

    static void loginUser(String email, String password) {
        OnCompleteListener<AuthResult> listener =  new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("Price Debug", "Finished log in attempt 1");
                if (task.isSuccessful()) {
                    Log.i("Price Debug", "Logged in user");
                }
                else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Log.i("Price Debug", "Could not log in. Likely incorrect password", task.getException());
                    }
                    else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Log.i("Price Debug", "Could not log in. No user with that mail address", task.getException());
                    }
                    else {
                        Log.i("Price Debug", "Could not log in. Misc error", task.getException());
                    }
                }
                Log.i("Price Debug", "Finished log in attempt");
            }
        };
        Log.i("Price Debug", "Beginning login attempt");
        getAuth()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    static void passwordReset(String email) {
        OnCompleteListener<Void> listener =  new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("Price Debug", "Sent password reset mail");
                }
                else {
                    Log.i("Price Debug", "Could not send password reset mail", task.getException());
                }
            }
        };
        getAuth().sendPasswordResetEmail(email).addOnCompleteListener(listener);
    }

    static void signOut() {
        getAuth().signOut();
    }

    static boolean isUserLoggedIn() {
        return getUserInfo() == null;
    }
}
