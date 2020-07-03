package com.example.imh_mega.Fragments.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdtLocModel {

    @Expose
    @SerializedName("RtcLongitude")
    private String RtcLongitude;

    @Expose
    @SerializedName("RtcLatitude")
    private String RtcLatitude;

    @Expose
    @SerializedName("RiderID")
    private String RiderID;

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    public String getRtcLongitude() {
        return RtcLongitude;
    }

    public void setRtcLongitude(String rtcLongitude) {
        RtcLongitude = rtcLongitude;
    }

    public String getRtcLatitude() {
        return RtcLatitude;
    }

    public void setRtcLatitude(String rtcLatitude) {
        RtcLatitude = rtcLatitude;
    }

    public String getRiderID() {
        return RiderID;
    }

    public void setRiderID(String riderID) {
        RiderID = riderID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
