package com.fototechparivarstalls.model.GetBookings;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetBookingsResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String StatusCode;
    @SerializedName("data")
    @Expose
    private ArrayList<GetBookingsDetails> data = null;


    public GetBookingsResponse(String message, String statusCode, ArrayList<GetBookingsDetails> data) {
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

    public ArrayList<GetBookingsDetails> getData() {
        return data;
    }

    public void setData(ArrayList<GetBookingsDetails> data) {
        this.data = data;
    }

}
