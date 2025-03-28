package com.fototechparivarstalls.model.Issue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteIssueJson {

    @SerializedName("id")
    @Expose
    private String id;



    public DeleteIssueJson(String id) {
        this.id = id;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
