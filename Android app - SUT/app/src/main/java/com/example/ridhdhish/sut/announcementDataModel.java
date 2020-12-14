package com.example.ridhdhish.sut;

/**
 * Created by Acer on 31-01-2019.
 */
public class announcementDataModel {
    private String txtAnnouncementMessage;
    private String imgAnnouncement;
    private String txtAnnouncementDateTime;
    private String txtAnnouncementTitle;
    private String txtImgName;
    private String txtImgId;
    private String isImage;

    public String getTxtImgId() {
        return txtImgId;
    }

    public void setTxtImgId(String txtImgId) {
        this.txtImgId = txtImgId;
    }

    public void setTxtAnnouncementTitle(String txtAnnouncementTitle) {
        this.txtAnnouncementTitle = txtAnnouncementTitle;
    }

    public String getTxtAnnouncementTitle() {
        return txtAnnouncementTitle;
    }

    public void setTxtAnnouncementMessage(String txtAnnouncementMessage) {
        this.txtAnnouncementMessage = txtAnnouncementMessage;
    }

    public String getTxtAnnouncementMessage() {
        return this.txtAnnouncementMessage;
    }

    public void setImgAnnouncement(String imgAnnouncement) {
        this.imgAnnouncement = imgAnnouncement;
    }

    public String getImgAnnouncement() {
        return this.imgAnnouncement;
    }

    public void setImgName(String _txtImgName) {
        this.txtImgName = _txtImgName;
    }

    public String getImgName() {
        return this.txtImgName;
    }

    public void setTxtAnnouncementDateTime(String txtAnnouncementDateTime) {
        this.txtAnnouncementDateTime = txtAnnouncementDateTime;
    }

    public String getTxtAnnouncementDateTime() {
        return this.txtAnnouncementDateTime;
    }

    public void setIsImage(String isImage) {
        this.isImage = isImage;
    }

    public String getIsImage() {
        return this.isImage;
    }

}
