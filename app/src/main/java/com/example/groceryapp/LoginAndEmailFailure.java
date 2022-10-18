package com.example.groceryapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class LoginAndEmailFailure extends AppCompatActivity {
    private TextView localEmailView;
    private TextView localPasswordView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
    }

    public void checkPasswordAndEmail(String email, String password, TextView email_view, TextView password_view) {

        if (email.isEmpty() && password.isEmpty()) {
            password_view.requestFocus();
            email_view.requestFocus();
            password_view.setError("No password!");
            email_view.setError("No email!");
        }
        else if (email.isEmpty()) {
            email_view.requestFocus();
            email_view.setError("No email!");
        }
        else if (password.isEmpty()) {
            password_view.requestFocus();
            password_view.setError("No password!");
        }
    }

    public void errorMessageEmail(TextView email_view) {
        email_view.requestFocus();
        email_view.setError("Wrong Email!");
    }

    public void errorMessagePassword(TextView password_view) {
        password_view.requestFocus();
        password_view.setError("Wrong Password!");
    }

    public void errorMessageEmailExist(TextView email_view) {
        email_view.requestFocus();
        email_view.setError("Email already exists");
    }

}
