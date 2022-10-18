package com.example.groceryapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Login UI test", "User opened the login page");
        super.onCreate(savedInstanceState);

        // Code used to find bugs previously
        //StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
        //        .detectLeakedClosableObjects()
        //        .build());

        setContentView(R.layout.loginpage);

        TextView email_view = findViewById(R.id.email_text_field);
        TextView password_view = findViewById(R.id.password_text_field);
        Button login_btn = findViewById(R.id.login_btn);
        Button register_btn = findViewById(R.id.register_user_btn);

        register_btn.setOnClickListener((View view) -> {
            Log.i("Login UI test", "User attempting to register");
            String email = email_view.getText().toString();
            String password = password_view.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Log.i("Login UI test", "User attempting to login without password or email");
                return;
            }
            UserManagement.createUserDefault(email, password, (Task<AuthResult> task) -> {
                if (task.isSuccessful()) {
                    Log.i("Login UI test", "User has been created!");
                    finish();
                }
                else {
                    Log.i("Login UI test", "Could not create in user");
                }
            });
            Log.i("Login UI test", "Past creating the callback");
        });

        login_btn.setOnClickListener((View view) -> {
            Log.i("Login UI test", "User attempting to log in");
            String email = email_view.getText().toString();
            String password = password_view.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Log.i("Login UI test", "User attempting to login without password or email");
                return;
            }
            UserManagement.loginUser(email, password, (Task<AuthResult> task) -> {
                if (task.isSuccessful()) {
                    Log.i("Login UI test", "User has been logged in!");
                    finish();
                }
                else {
                    Log.i("Login UI test", "Could not log in user");
                }
            });
        });
    }
}
