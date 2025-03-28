package com.fototechparivarstalls.model.GetAddons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AddAddonsJson {

    @SerializedName("booking_id")
    @Expose
    private String booking_id;

    @SerializedName("addons")
    @Expose
    private List<AddaddonsDetails> addons = null;

    public AddAddonsJson(String booking_id, List<AddaddonsDetails> addons) {
        this.booking_id = booking_id;
        this.addons = addons;
    }

    public List<AddaddonsDetails> getAddons() {
        return addons;
    }

    public void setAddons(List<AddaddonsDetails> addons) {
        this.addons = addons;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }
}