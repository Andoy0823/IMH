package com.example.imh_mega.Fragments.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class searchRiderModel {

    @Expose
    @SerializedName("RiderFullName")
    private String RiderFullName;

    @Expose
    @SerializedName("rtcTable.RiderID")
    private String RiderID;

    @Expose
    @SerializedName("rtcTable.RtcLatitude")
    private String RtcLatitude;

    @Expose
    @SerializedName("rtcTable.RtcLongitude")
    private String RtcLongitude;

    @Expose
    @SerializedName("Message")
    private String Message;

    @Expose
    @SerializedName("Success")
    private boolean Success;

    public String getRiderFullName() {
        return RiderFullName;
    }

    public void setRiderFullName(String riderFullName) {
        RiderFullName = riderFullName;
    }

    public String getRiderID() {
        return RiderID;
    }

    public void setRiderID(String riderID) {
        RiderID = riderID;
    }

    public String getRtcLatitude() {
        return RtcLatitude;
    }

    public void setRtcLatitude(String rtcLatitude) {
        RtcLatitude = rtcLatitude;
    }

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
