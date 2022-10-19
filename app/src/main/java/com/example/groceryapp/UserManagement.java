package com.example.groceryapp;

import com.google.firebase.auth.*;

import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class UserManagement {
    static final String USER_MANAGEMENT_LOG_TAG = "User Management Logs";
    private static Map<Object, Runnable> OnLoginCallbacks = new HashMap<>();
    private static Map<Object, Runnable> OnLogoutCallbacks = new HashMap<>();

    static FirebaseAuth auth = null;


    static private FirebaseAuth getAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    private static void CallOnLoginCallbacks() {
        for (Runnable callback : OnLoginCallbacks.values()) {
            try {
                callback.run();
            }
            catch (Exception e) {
                Log.i(USER_MANAGEMENT_LOG_TAG, "Error in login callback", e);
            }
        }
    }

    public static void RegisterOnLoginCallback(Object caller, Runnable callback) {
        OnLoginCallbacks.put(caller, callback);
        if (isUserLoggedIn()) {
            callback.run();
        }
    }

    public static void RemoveOnLoginCallback(Object caller) {
        OnLoginCallbacks.remove(caller);
    }

    private static void CallOnLogoutCallbacks() {
        for (Runnable callback : OnLogoutCallbacks.values()) {
            try {
                callback.run();
            }
            catch (Exception e) {
                Log.i(USER_MANAGEMENT_LOG_TAG, "Error in logout callback", e);
            }
        }
    }

    public static void RegisterOnLogoutCallback(Object caller, Runnable callback) {
        OnLogoutCallbacks.put(caller, callback);
    }

    public static void RemoveOnLogoutCallback(Object caller) {
        OnLogoutCallbacks.remove(caller);
    }

    public static void createUserDefault(String email, String password, TextView email_view, TextView password_view, OnCompleteListener<AuthResult> onComplete) {
        LoginAndEmailFailure loginAndEmailFailure = new LoginAndEmailFailure();
        getAuth()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onComplete)
                .addOnCompleteListener((Task<AuthResult> task) -> {
                    if (task.isSuccessful()) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Created new user");
                        CallOnLoginCallbacks();
                        return;
                    }
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Could not create user due to bad credentials.", task.getException());
                        loginAndEmailFailure.errorPassWordShort(password_view);
                    }
                    else if (task.getException() instanceof  FirebaseAuthUserCollisionException) {
                        loginAndEmailFailure.errorMessageEmailExist(email_view);
                        Log.i(USER_MANAGEMENT_LOG_TAG, "A user with that mail address already exists", task.getException());
                    }
                    else {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Miscellaneous error when creating user", task.getException());
                    }
                });
    }

    public static void createUserDefault(String email, String password, TextView email_view, TextView password_view) {
        createUserDefault(email, password, email_view, password_view, null);
    }

    public static FirebaseUser getUserInfo() {
        return getAuth().getCurrentUser();
    }

    public static void loginUser(String email, String password, TextView email_view, TextView password_view, OnCompleteListener<AuthResult> onComplete) {
        LoginAndEmailFailure loginAndEmailFailure = new LoginAndEmailFailure();
        getAuth()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onComplete)
                .addOnCompleteListener((Task<AuthResult> task) -> {
                    if (task.isSuccessful()) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Logged in user");
                        CallOnLoginCallbacks();

                        return;
                    }
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Could not log in. Likely incorrect password", task.getException());
                        loginAndEmailFailure.errorMessagePassword(password_view);
                    }
                    else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Could not log in. No user with that mail address", task.getException());
                        loginAndEmailFailure.errorMessageEmail(email_view);
                    }
                    else {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Could not log in. Misc error", task.getException());
                    }
                });
    }

    public static void loginUser(String email, String password, TextView email_view, TextView password_view) {
        loginUser(email, password, email_view, password_view, null);
    }

    public static void passwordReset(String email, OnCompleteListener<Void> onComplete) {
        getAuth()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener(onComplete)
                .addOnCompleteListener((Task<Void> task) -> {
                    if (task.isSuccessful()) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Sent password reset mail");
                    }
                    else {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Could not send password reset mail", task.getException());
                    }
                });
    }

    public static void passwordReset(String email) {
        passwordReset(email, null);
    }

    public static void signOut() {
        CallOnLogoutCallbacks();
        getAuth().signOut();
    }

    public static void updatePassword(String email, OnCompleteListener<Void> onComplete) {
        getAuth()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener(onComplete)
                .addOnCompleteListener((Task<Void> task) -> {
                    if (task.isSuccessful()) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Sent password reset email.");
                    }
                    else {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Could not send password reset email");
                    }
                });
    }

    public static void updatePassword(String email) {
        updatePassword(email, null);
    }

    public static void updateUserEmail(String newMail) {
        getAuth()
                .getCurrentUser()
                .updateEmail(newMail)
                .addOnCompleteListener((Task<Void> task) -> {
                    if (task.isSuccessful()) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Successfully changed users mail address.");
                    } else {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Could not change users mail address.", task.getException());
                    }
                });
    }

    public static void sendVerificationEmail(OnCompleteListener<Void> onComplete) {
        getAuth()
                .getCurrentUser()
                .sendEmailVerification()
                .addOnCompleteListener(onComplete)
                .addOnCompleteListener((Task<Void> task) -> {
                    if (task.isSuccessful()) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Sent verification email.");
                    }
                    else {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Could not send verification email.", task.getException());
                    }
        });
    }

    public static void sendVerificationEmail() {
        sendVerificationEmail(null);
    }

    public static void deleteUserAccount(OnCompleteListener<Void> onComplete) {
        getAuth()
                .getCurrentUser()
                .delete()
                .addOnCompleteListener(onComplete)
                .addOnCompleteListener((Task<Void> task) -> {
                    if (task.isSuccessful()) {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "User successfully deleted.");
                    }
                    else {
                        Log.i(USER_MANAGEMENT_LOG_TAG, "Could not delete user.", task.getException());
                    }
                });
    }

    public static void deleteUserAccount() {
        deleteUserAccount(null);
    }

    public static boolean isUserLoggedIn() {
        return getUserInfo() != null;
    }

    public static String GetUserUID() throws UserNotAuthenticatedException {
        if (isUserLoggedIn()) {
            return getAuth().getCurrentUser().getUid();
        }
        throw new UserNotAuthenticatedException();
    }
}