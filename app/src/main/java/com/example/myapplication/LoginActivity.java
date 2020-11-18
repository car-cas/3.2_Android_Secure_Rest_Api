package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText textEmail;
    private EditText textPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        textPassword = (EditText) findViewById(R.id.editTextTextPassword);

    }

    public void loginUser(View view){
        String email = textEmail.getText().toString();
        String pass = textPassword.getText().toString();

        if(email.isEmpty()){
            textEmail.setError("Ingrese email valido");
        }
        if(pass.isEmpty()){
            textPassword.setError("Ingrese contraseña valida");
        }

    }
}