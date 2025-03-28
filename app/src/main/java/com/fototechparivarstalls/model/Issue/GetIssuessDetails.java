package com.fototechparivarstalls.model.Issue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIssuessDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("bookingId")
    @Expose
    private String bookingId;
    @SerializedName("issueId")
    @Expose
    private String issueId;

    @SerializedName("issue")
    @Expose
    private String issue;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("addedOn")
    @Expose
    private String addedOn;


    public GetIssuessDetails(String id, String userId, String bookingId, String issueId, String issue, String message, String status, String addedOn) {
        this.id = id;
        this.userId = userId;
        this.bookingId = bookingId;
        this.issueId = issueId;
        this.issue = issue;
        this.message = message;
        this.status = status;
        this.addedOn = addedOn;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }
}
