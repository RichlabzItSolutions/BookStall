package com.fototechparivarstalls.model.PaymentHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentHistoryDetails {

    @SerializedName("paidAmount")
    @Expose
    private String paidAmount;
    @SerializedName("paidDate")
    @Expose
    private String paidDate;

    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("refNumber")
    @Expose
    private String refNumber;

    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("eventName")
    @Expose
    private String eventName;

    @SerializedName("stallName")
    @Expose
    private String stallName;
    @SerializedName("paidBy")
    @Expose
    private String paidBy;

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("collectedBy")
    @Expose
    private String collectedBy;


    public PaymentHistoryDetails(String paidAmount, String paidDate, String paymentMode, String refNumber, String note, String eventName, String stallName, String paidBy, String mobile, String collectedBy) {
        this.paidAmount = paidAmount;
        this.paidDate = paidDate;
        this.paymentMode = paymentMode;
        this.refNumber = refNumber;
        this.note = note;
        this.eventName = eventName;
        this.stallName = stallName;
        this.paidBy = paidBy;
        this.mobile = mobile;
        this.collectedBy = collectedBy;
    }


    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(String collectedBy) {
        this.collectedBy = collectedBy;
    }
}
