package com.example.imh_mega.Fragments.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoordinateLatitudeModel {

    @Expose
    @SerializedName("RtcLatitude")
    private String RtcLatitude;

    @Expose
    @SerializedName("Message")
    private String Message;

    @Expose
    @SerializedName("Success")
    private boolean Success;

    public String getRtcLatitude() {
        return RtcLatitude;
    }

    public void setRtcLatitude(String rtcLatitude) {
        RtcLatitude = rtcLatitude;
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
