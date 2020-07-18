package com.example.imh_mega.Fragments.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class incidentInitialModel {

    @Expose
    @SerializedName("Impact_Timestamp")
    private String Timestamp;

    @Expose
    @SerializedName("Impact_Latitude")
    private String Latitude;

    @Expose
    @SerializedName("Impact_Longitude")
    private String Longitude;

    @Expose
    @SerializedName("Success")
    private boolean Success;

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
