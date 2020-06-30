package com.example.imh_mega.Retrofit;

import com.example.imh_mega.Login.Models.VipModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("app_login.php")

    Call<VipModel> loginUser(@Field("VipUsername") String vipUsername,
                             @Field("VipPassword") String vipPassword);

}
