package com.example.groceryapp;

import android.os.Bundle;
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
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginpage);

        TextView email_view = findViewById(R.id.email_text_field);
        TextView password_view = findViewById(R.id.password_text_field);
        Button login_btn = findViewById(R.id.login_btn);
        Button register_btn = findViewById(R.id.not_registered_btn);

        register_btn.setOnClickListener((View view) -> {
            Log.i("Login UI test", "User attempting to register");
            String email = email_view.getText().toString();
            String password = password_view.getText().toString();
            UserManagement.createUserDefault(email, password, (Task<AuthResult> task) -> {
                if (task.isSuccessful()) {
                    Log.i("Login UI test", "User has been created!");
                    finish();
                }
                else {
                    Log.i("Login UI test", "Could not create in user");
                }
            });
        });

        login_btn.setOnClickListener((View view) -> {
            Log.i("Login UI test", "User attempting to log in");
            String email = email_view.getText().toString();
            String password = password_view.getText().toString();
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
