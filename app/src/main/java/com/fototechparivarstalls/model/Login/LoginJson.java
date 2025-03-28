package com.fototechparivarstalls.model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginJson {

    @SerializedName("mobile_number")
    @Expose
    private String mobile_number;

    @SerializedName("password")
    @Expose
    private String password;


    public LoginJson(String mobile_number, String password) {
        this.mobile_number = mobile_number;
        this.password = password;
    }


    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
