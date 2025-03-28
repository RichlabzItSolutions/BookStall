package com.fototechparivarstalls.model.Documents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Documents {

    @SerializedName("confirmationLetter")
    @Expose
    private String confirmationLetter;
    @SerializedName("gatePass")
    @Expose
    private String gatePass;
    @SerializedName("transportLetter")
    @Expose
    private String transportLetter;


    public Documents(String confirmationLetter, String gatePass, String transportLetter) {
        this.confirmationLetter = confirmationLetter;
        this.gatePass = gatePass;
        this.transportLetter = transportLetter;
    }

    public String getConfirmationLetter() {
        return confirmationLetter;
    }

    public void setConfirmationLetter(String confirmationLetter) {
        this.confirmationLetter = confirmationLetter;
    }

    public String getGatePass() {
        return gatePass;
    }

    public void setGatePass(String gatePass) {
        this.gatePass = gatePass;
    }

    public String getTransportLetter() {
        return transportLetter;
    }

    public void setTransportLetter(String transportLetter) {
        this.transportLetter = transportLetter;
    }
}
