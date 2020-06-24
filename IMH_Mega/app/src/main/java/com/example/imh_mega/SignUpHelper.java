package com.example.imh_mega;

public class SignUpHelper {

    String vipName, username, password, phone, address;

    public SignUpHelper() {
    }

    public SignUpHelper(String vipName, String username, String password, String phone, String address) {
        this.vipName = vipName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
