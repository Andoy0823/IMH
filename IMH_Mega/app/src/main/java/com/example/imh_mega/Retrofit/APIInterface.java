package com.example.imh_mega.Retrofit;

import com.example.imh_mega.Fragments.Models.CoordinateLatitudeModel;
import com.example.imh_mega.Fragments.Models.CoordinateLongitudeModel;
import com.example.imh_mega.Fragments.Models.IncidentCheckerModel;
import com.example.imh_mega.Fragments.Models.LocationHistorySpinnerModel;
import com.example.imh_mega.Fragments.Models.autoCompleteModel;
import com.example.imh_mega.Fragments.Models.incidentFinalModel;
import com.example.imh_mega.Fragments.Models.incidentInitialModel;
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

    @FormUrlEncoded
    @POST("app_insertToIncidentFinal.php")

    Call<incidentFinalModel> insertToFinal(@Field("FinalImpactLatitude") String FinalLatitude,
                                           @Field("FinalImpactLongitude") String FinalLongitude,
                                           @Field("HospitalName") String Hospital,
                                           @Field("PoliceName") String Police);

    @GET("app_homeatcrider.php")
    Call<List<autoCompleteModel>> getRiderNames();

    @GET("app_homesearchrider.php")
    Call<List<searchRiderModel>> getInformation();

    @GET("app_locationhistory.php")
    Call<List<LocationHistorySpinnerModel>> getHistowry();

    @GET("app_locationhistorylatitude.php")
    Call<List<CoordinateLatitudeModel>> getLatitudeList();

    @GET("app_locationhistorylongitude.php")
    Call<List<CoordinateLongitudeModel>> getLongitudeList();

    @GET("app_incidentInitial.php")
    Call<List<incidentInitialModel>> getInitialIncident();

    @GET("app_incidentChecker.php")
    Call<List<IncidentCheckerModel>> checkIncident();

}
