package com.example.myapplication.service;

import com.example.myapplication.model.LoginWrapper;
import com.example.myapplication.model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth")
    public Call<Token> login(@Body LoginWrapper login);
}
