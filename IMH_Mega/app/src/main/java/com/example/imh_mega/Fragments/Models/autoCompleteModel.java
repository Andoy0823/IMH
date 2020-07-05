package com.example.imh_mega.Fragments.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class autoCompleteModel {

    @Expose
    @SerializedName("RiderFullName")
    private String RiderFullName;

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
