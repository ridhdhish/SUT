package com.example.ridhdhish.sut;

/**
 * Created by Ridhdhish on 01-02-2019.
 */

public class meetingDataModel {

    private String txtMeetingInterfaceTime;
    private String txtMeetingInterfaceMessage;
    private String txtMeetingInterfaceDate;

    public String getTxtMeetingInterfaceMessage(){
        return this.txtMeetingInterfaceMessage;
    }

    public String getTxtMeetingInterfaceTime(){
        return this.txtMeetingInterfaceTime;
    }

    public String getTxtMeetingInterfaceDate(){
        return this.txtMeetingInterfaceDate;
    }

    public void setTxtMeetingInterfaceMessage(String txtMeetingInterfaceMessage) {
        this.txtMeetingInterfaceMessage = txtMeetingInterfaceMessage;
    }

    public void setTxtMeetingInterfaceDate(String txtMeetingInterfaceDate) {
        this.txtMeetingInterfaceDate = txtMeetingInterfaceDate;
    }

    public void setTxtMeetingInterfaceTime(String txtMeetingInterfaceTime) {
        this.txtMeetingInterfaceTime = txtMeetingInterfaceTime;
    }
}
