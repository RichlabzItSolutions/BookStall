package com.fototechparivarstalls.model.Issue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIssuesDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("issueTitle")
    @Expose
    private String issueTitle;

    public GetIssuesDetails(String id, String issueTitle) {
        this.id = id;
        this.issueTitle = issueTitle;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }


}
