package com.fototechparivarstalls.model.PastEvents;

import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PastEventsResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String StatusCode;
    @SerializedName("data")
    @Expose
    private ArrayList<PastEventsDetails> data = null;


    public PastEventsResponse(String message, String statusCode, ArrayList<PastEventsDetails> data) {
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

    public ArrayList<PastEventsDetails> getData() {
        return data;
    }

    public void setData(ArrayList<PastEventsDetails> data) {
        this.data = data;
    }



}
