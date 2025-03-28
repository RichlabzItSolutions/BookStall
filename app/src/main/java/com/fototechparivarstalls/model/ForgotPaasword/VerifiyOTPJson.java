package com.fototechparivarstalls.model.ForgotPaasword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifiyOTPJson {

    @SerializedName("mobile_number")
    @Expose
    private String mobile_number;

    @SerializedName("otp")
    @Expose
    private String otp;


    public VerifiyOTPJson(String mobile_number, String otp) {
        this.mobile_number = mobile_number;
        this.otp = otp;
    }


    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
