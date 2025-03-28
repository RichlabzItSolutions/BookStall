package com.fototechparivarstalls.model.PaymentHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentHistoryJson {

    @SerializedName("booking_id")
    @Expose
    private String booking_id;

    public PaymentHistoryJson(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }
}
