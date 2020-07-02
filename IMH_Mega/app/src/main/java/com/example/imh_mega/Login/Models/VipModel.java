package com.example.imh_mega.Login.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VipModel {

    @Expose
    @SerializedName("VipFullName")
    private String VipFullName;

    @Expose
    @SerializedName("VipUsername")
    private String VipUsername;

    @Expose
    @SerializedName("VipPassword")
    private String VipPassword;

    @Expose
    @SerializedName("VipConfirmPassword")
    private String VipConfirmPassword;

    @Expose
    @SerializedName("VipPhone")
    private String VipPhone;

    @Expose
    @SerializedName("VipAddress")
    private String VipAddress;

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    public String getVipFullName() {
        return VipFullName;
    }

    public void setVipFullName(String vipFullName) {
        VipFullName = vipFullName;
    }

    public String getVipUsername() {
        return VipUsername;
    }

    public void setVipUsername(String vipUsername) {
        VipUsername = vipUsername;
    }

    public String getVipPassword() {
        return VipPassword;
    }

    public void setVipPassword(String vipPassword) {
        VipPassword = vipPassword;
    }

    public String getVipConfirmPassword() {
        return VipConfirmPassword;
    }

    public void setVipConfirmPassword(String vipConfirmPassword) {
        VipConfirmPassword = vipConfirmPassword;
    }

    public String getVipPhone() {
        return VipPhone;
    }

    public void setVipPhone(String vipPhone) {
        VipPhone = vipPhone;
    }

    public String getVipAddress() {
        return VipAddress;
    }

    public void setVipAddress(String vipAddress) {
        VipAddress = vipAddress;
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
