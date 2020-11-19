package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.model.LoginWrapper;
import com.example.myapplication.model.Token;
import com.example.myapplication.service.AuthService;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private EditText textEmail;
    private EditText textPassword;
    private Button buttonLogin;

    private AuthService authService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        textPassword = (EditText) findViewById(R.id.editTextTextPassword);

    }

    public void loginUser(View view){
        String email = textEmail.getText().toString();
        String pass = textPassword.getText().toString();

        if(email.isEmpty()){
            textEmail.setError("Ingrese email");
        }
        if(pass.isEmpty()){
            textPassword.setError("Ingrese contraseña");
        }else{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http:/10.0.2.2:8080") //localhost for emulator
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            authService = retrofit.create(AuthService.class);
            executorService.execute(onLoginClicked(authService,email,pass));
        }
    }

    public Runnable onLoginClicked(AuthService authService,String email,String pass){
        return new Runnable()
        {
            @Override
            public void run(){
                try{
                    Response<Token> response = authService.login(new LoginWrapper(email, pass)).execute();
                    Token token = response.body();
                    SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE );
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("TOKEN_KEY",token.getToken());
                    editor.apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    finish();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        } ;
    }
}