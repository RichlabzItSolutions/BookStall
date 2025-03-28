package com.fototechparivarstalls.model.Issue;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetIssuessResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String StatusCode;
    @SerializedName("data")
    @Expose
    private ArrayList<GetIssuessDetails> data = null;


    public GetIssuessResponse(String message, String statusCode, ArrayList<GetIssuessDetails> data) {
        this.message = message;
        this.StatusCode = statusCode;
        this.data = data;
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

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public ArrayList<GetIssuessDetails> getData() {
        return data;
    }

    public void setData(ArrayList<GetIssuessDetails> data) {
        this.data = data;
    }
}
