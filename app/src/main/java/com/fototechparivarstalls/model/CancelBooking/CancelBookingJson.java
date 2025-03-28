package com.fototechparivarstalls.model.CancelBooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelBookingJson {

    @SerializedName("booking_id")
    @Expose
    private String booking_id;

    @SerializedName("reason")
    @Expose
    private String reason;

    public CancelBookingJson(String booking_id, String reason) {
        this.booking_id = booking_id;
        this.reason = reason;
    }


    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
