package com.fototechparivarstalls.model;

public class DownloadsModel {

    private String LeadsName;
    private int imgId;


    public DownloadsModel(String leadsName, int imgId) {
        this.LeadsName = leadsName;
        this.imgId = imgId;
    }

    public String getLeadsName() {
        return LeadsName;
    }

    public void setLeadsName(String leadsName) {
        LeadsName = leadsName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
