package com.example.imh_mega.Fragments.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoordinateLongitudeModel {

    @Expose
    @SerializedName("RtcLongitude")
    private String RtcLongitude;

    @Expose
    @SerializedName("Message")
    private String Message;

    @Expose
    @SerializedName("Success")
    private boolean Success;

    public String getRtcLongitude() {
        return RtcLongitude;
    }

    public void setRtcLongitude(String rtcLongitude) {
        RtcLongitude = rtcLongitude;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
