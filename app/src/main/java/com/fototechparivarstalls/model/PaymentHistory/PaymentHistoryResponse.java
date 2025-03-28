package com.fototechparivarstalls.model.PaymentHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentHistoryResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String StatusCode;
    @SerializedName("data")
    @Expose
    private ArrayList<PaymentHistoryDetails> data = null;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;


    public PaymentHistoryResponse(String message, String statusCode, ArrayList<PaymentHistoryDetails> data, String totalAmount) {
        this.message = message;
        this.StatusCode = statusCode;
        this.data = data;
        this.totalAmount = totalAmount;
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

    public ArrayList<PaymentHistoryDetails> getData() {
        return data;
    }

    public void setData(ArrayList<PaymentHistoryDetails> data) {
        this.data = data;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

}
