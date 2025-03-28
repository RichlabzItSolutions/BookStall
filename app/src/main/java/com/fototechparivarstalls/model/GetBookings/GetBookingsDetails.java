package com.fototechparivarstalls.model.GetBookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookingsDetails {

    @SerializedName("bookingId")
    @Expose
    private String bookingId;

    @SerializedName("eventImage")
    @Expose
    private String eventImage;

    @SerializedName("eventId")
    @Expose
    private String eventId;

    @SerializedName("eventName")
    @Expose
    private String eventName;

    @SerializedName("eventMobile")
    @Expose
    private String eventMobile;
    @SerializedName("eventAddress")
    @Expose
    private String eventAddress;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("stallName")
    @Expose
    private String stallName;

    @SerializedName("sqMts")
    @Expose
    private String sqMts;

    @SerializedName("unit_price")
    @Expose
    private String unit_price;

    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("bookingStatus")
    @Expose
    private String bookingStatus;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;

    @SerializedName("paidAmount")
    @Expose
    private String paidAmount;
    @SerializedName("balance")
    @Expose
    private String balance;



    public GetBookingsDetails(String bookingId, String eventImage, String eventId, String eventName, String eventMobile, String eventAddress, String userId, String firstName, String lastName, String stallName, String sqMts, String mobileNumber, String unit_price , String totalAmount , String discount,  String bookingStatus, String paymentStatus, String paidAmount, String balance) {
        this.bookingId = bookingId;
        this.eventImage = eventImage;
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventMobile = eventMobile;
        this.eventAddress = eventAddress;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.stallName = stallName;
        this.sqMts = sqMts;
        this.mobileNumber= mobileNumber;
        this.unit_price = unit_price;
        this.totalAmount= totalAmount;
        this.discount = discount;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.paidAmount = paidAmount;
        this.balance = balance;
    }


    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventMobile() {
        return eventMobile;
    }

    public void setEventMobile(String eventMobile) {
        this.eventMobile = eventMobile;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public String getSqMts() {
        return sqMts;
    }

    public void setSqMts(String sqMts) {
        this.sqMts = sqMts;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
