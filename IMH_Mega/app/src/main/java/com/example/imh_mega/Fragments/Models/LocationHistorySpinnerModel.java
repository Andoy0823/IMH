package com.example.imh_mega.Fragments.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationHistorySpinnerModel {

    @Expose
    @SerializedName("RtcID")
    private Integer RtcID;

    @Expose
    @SerializedName("RtcLatitude")
    private String RtcLatitude;

    @Expose
    @SerializedName("RtcLongitude")
    private String RtcLongitude;

    @Expose
    @SerializedName("RtcTimestamp")
    private String RtcTimestamp;

    @Expose
    @SerializedName("Message")
    private String Message;

    @Expose
    @SerializedName("Success")
    private boolean Success;

    public Integer getRtcID() {
        return RtcID;
    }

    public void setRtcID(Integer rtcID) {
        RtcID = rtcID;
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

    public String getRtcTimestamp() {
        return RtcTimestamp;
    }

    public void setRtcTimestamp(String rtcTimestamp) {
        RtcTimestamp = rtcTimestamp;
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
