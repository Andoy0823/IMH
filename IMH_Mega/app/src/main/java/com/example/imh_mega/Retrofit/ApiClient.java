package com.example.imh_mega.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String API_BASE_URL = "http://dlsudimh.com/Functions/";
    public static Retrofit retrofit = null;
    public static Gson gson;

    public static Retrofit getAPIClient(){

        if (retrofit == null){

            gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
            return retrofit;
    }

}
