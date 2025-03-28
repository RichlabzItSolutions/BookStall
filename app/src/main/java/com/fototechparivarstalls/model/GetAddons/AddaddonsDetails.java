package com.fototechparivarstalls.model.GetAddons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddaddonsDetails {

    @SerializedName("addon_id")
    @Expose
    private int addon_id;

    @SerializedName("qty")
    @Expose
    private int qty;

    public AddaddonsDetails(int addon_id, int qty) {
        this.addon_id = addon_id;
        this.qty = qty;
    }

    public int getAddon_id() {
        return addon_id;
    }

    public void setAddon_id(int addon_id) {
        this.addon_id = addon_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
