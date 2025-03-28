package com.fototechparivarstalls.model.UpcomingEvents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpcomingEventsResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String StatusCode;
    @SerializedName("data")
    @Expose
    private ArrayList<UpcomingEventsDetails> data = null;


    public UpcomingEventsResponse(String message, String statusCode, ArrayList<UpcomingEventsDetails> data) {
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

    public ArrayList<UpcomingEventsDetails> getData() {
        return data;
    }

    public void setData(ArrayList<UpcomingEventsDetails> data) {
        this.data = data;
    }
}
