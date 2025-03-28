package com.fototechparivarstalls.model.ChangePassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordJson {

    @SerializedName("current_password")
    @Expose
    private String current_password;

    @SerializedName("new_password")
    @Expose
    private String new_password;

    @SerializedName("confirm_password")
    @Expose
    private String confirm_password;


    public ChangePasswordJson(String current_password, String new_password, String confirm_password) {
        this.current_password = current_password;
        this.new_password = new_password;
        this.confirm_password = confirm_password;
    }


    public String getCurrent_password() {
        return current_password;
    }

    public void setCurrent_password(String current_password) {
        this.current_password = current_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }


}
