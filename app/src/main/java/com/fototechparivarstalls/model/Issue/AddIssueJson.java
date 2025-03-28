package com.fototechparivarstalls.model.Issue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddIssueJson {

    @SerializedName("booking_id")
    @Expose
    private String booking_id;

    @SerializedName("issue_id")
    @Expose
    private String issue_id;

    @SerializedName("message")
    @Expose
    private String message;

    public AddIssueJson(String booking_id, String issue_id, String message) {
        this.booking_id = booking_id;
        this.issue_id = issue_id;
        this.message = message;
    }


    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(String issue_id) {
        this.issue_id = issue_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
