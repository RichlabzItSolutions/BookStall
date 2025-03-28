package com.fototechparivarstalls.model.PastEvents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PastEventsDetails {

    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("eventImage")
    @Expose
    private String eventImage;
    @SerializedName("eventStartDate")
    @Expose
    private String eventStartDate;
    @SerializedName("eventEndDate")
    @Expose
    private String eventEndDate;
    @SerializedName("eventMobile")
    @Expose
    private String eventMobile;
    @SerializedName("eventAddress")
    @Expose
    private String eventAddress;


    public PastEventsDetails(String eventId, String userId, String eventName, String eventImage, String eventStartDate, String eventEndDate, String eventMobile, String eventAddress) {
        this.eventId = eventId;
        this.userId = userId;
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventMobile = eventMobile;
        this.eventAddress = eventAddress;
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
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
}
