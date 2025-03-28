package com.fototechparivarstalls.model.ForgotPaasword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordJson {

    @SerializedName("mobile_number")
    @Expose
    private String mobile_number;


    public ForgotPasswordJson(String mobile_number) {
        this.mobile_number = mobile_number;

    }


    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }


}
