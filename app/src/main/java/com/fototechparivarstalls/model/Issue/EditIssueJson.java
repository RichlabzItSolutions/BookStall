package com.fototechparivarstalls.model.Issue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditIssueJson {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("issue_id")
    @Expose
    private String issue_id;

    @SerializedName("message")
    @Expose
    private String message;

    public EditIssueJson(String id, String issue_id, String message) {
        this.id = id;
        this.issue_id = issue_id;
        this.message = message;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
