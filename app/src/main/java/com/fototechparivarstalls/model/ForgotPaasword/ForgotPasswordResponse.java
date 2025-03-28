package com.fototechparivarstalls.model.ForgotPaasword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("StatusCode")
    @Expose
    private String StatusCode;


    public ForgotPasswordResponse( String message, String StatusCode) {
        this.message = message;
        this.StatusCode = StatusCode;

    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String StatusCode) {
        this.StatusCode = StatusCode;
    }


}
