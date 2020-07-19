package com.example.imh_mega.Fragments.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class incidentFinalModel {

    @Expose
    @SerializedName("RiderID")
    private String RiderID;

    @Expose
    @SerializedName("FinalImpactLatitude")
    private String FinalImpactLatitude;

    @Expose
    @SerializedName("FinalImpactLongitude")
    private String FinalImpactLongitude;

    @Expose
    @SerializedName("HospitalName")
    private String HospitalName;

    @Expose
    @SerializedName("PoliceName")
    private String PoliceName;

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    public String getRiderID() {
        return RiderID;
    }

    public void setRiderID(String riderID) {
        RiderID = riderID;
    }

    public String getFinalImpactLatitude() {
        return FinalImpactLatitude;
    }

    public void setFinalImpactLatitude(String finalImpactLatitude) {
        FinalImpactLatitude = finalImpactLatitude;
    }

    public String getFinalImpactLongitude() {
        return FinalImpactLongitude;
    }

    public void setFinalImpactLongitude(String finalImpactLongitude) {
        FinalImpactLongitude = finalImpactLongitude;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getPoliceName() {
        return PoliceName;
    }

    public void setPoliceName(String policeName) {
        PoliceName = policeName;
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
