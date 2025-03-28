package com.fototechparivarstalls.model;

public class ItemQuantityPrice {
    int quantity;
    double price;
    int addOnId;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAddOnId() {
        return addOnId;
    }

    public void setAddOnId(int addOnId) {
        this.addOnId = addOnId;
    }

    public ItemQuantityPrice(int quantity, double price, int addOnId) {
        this.quantity = quantity;
        this.price = price;
        this.addOnId = addOnId;
    }
}