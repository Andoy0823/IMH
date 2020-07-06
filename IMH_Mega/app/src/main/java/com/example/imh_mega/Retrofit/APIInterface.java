package com.example.imh_mega.Retrofit;

import com.example.imh_mega.Fragments.Models.LocationHistorySpinnerModel;
import com.example.imh_mega.Fragments.Models.autoCompleteModel;
import com.example.imh_mega.Fragments.Models.searchRiderModel;
import com.example.imh_mega.Login.Models.VipModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @GET("app_homeatcrider.php")
    Call<List<autoCompleteModel>> getRiderNames();

    @GET("app_homesearchrider.php")
    Call<List<searchRiderModel>> getInformation();

    @GET("app_locationhistory.php")
    Call<List<LocationHistorySpinnerModel>> getHistowry();

}
