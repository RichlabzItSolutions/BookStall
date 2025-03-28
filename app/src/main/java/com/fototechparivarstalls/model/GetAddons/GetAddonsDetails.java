package com.fototechparivarstalls.model.GetAddons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddonsDetails {

    @SerializedName("addonId")
    @Expose
    private int addonId;
    @SerializedName("addon")
    @Expose
    private String addon;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("qty")
    @Expose
    private int qty;

    private int selectedQty = 0;




    public int getAddonId() {
        return addonId;
    }

    public void setAddonId(int addonId) {
        this.addonId = addonId;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


    public int getSelectedQty() {
        return selectedQty;
    }

    public void setSelectedQty(int selectedQty) {
        this.selectedQty = selectedQty;
    }


}
