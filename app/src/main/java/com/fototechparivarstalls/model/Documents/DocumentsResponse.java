package com.fototechparivarstalls.model.Documents;

import com.fototechparivarstalls.model.Login.UserData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentsResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String StatusCode;
    @SerializedName("documents")
    @Expose
    private Documents documents;


 /*   public DocumentsResponse(String StatusCode, String message, Documents documents) {
        this.StatusCode = StatusCode;
        this.message = message;
        this.documents = documents;
    }*/


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String StatusCode) {
        this.StatusCode = StatusCode;
    }


    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }
}
