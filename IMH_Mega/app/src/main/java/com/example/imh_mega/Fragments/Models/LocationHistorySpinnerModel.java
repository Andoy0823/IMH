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
    @SerializedName("RtcDate")
    private String RtcDate;

    @Expose
    @SerializedName("RtcTime")
    private String RtcTime;

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

    public String getRtcDate() {
        return RtcDate;
    }

    public void setRtcDate(String rtcDate) {
        RtcDate = rtcDate;
    }

    public String getRtcTime() {
        return RtcTime;
    }

    public void setRtcTime(String rtcTime) {
        RtcTime = rtcTime;
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
