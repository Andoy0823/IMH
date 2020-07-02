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

    @FormUrlEncoded
    @POST("app_register.php")

    Call<VipModel> registerUser(@Field("VipFullName") String vipFullName,
                                @Field("VipUsername") String vipUsername,
                                @Field("VipPassword") String vipPassword,
                                @Field("VipConfirmPassword") String vipConfirmPassword,
                                @Field("VipPhone") String vipPhone,
                                @Field("VipAddress") String vipAddress);

}
