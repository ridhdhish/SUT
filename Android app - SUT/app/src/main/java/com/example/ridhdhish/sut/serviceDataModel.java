package com.example.ridhdhish.sut;

/**
 * Created by Ridhdhish on 31-01-2019.
 */

public class serviceDataModel {

     public String txtServiceInterface;
    public String txtServiceInterfaceid;
    public String img;
    //private Integer imgServiceInterface;

    public String getTxtServiceInterface() {
        return this.txtServiceInterface;
    }

    public String getLayoutServiceInterface() {
        return this.txtServiceInterfaceid;
    }

    public void setTxtServiceInterface(String txtService) {
        this.txtServiceInterface = txtService;
    }

    public void setTxtServiceInterfaceid(String txtServiceId) {
        this.txtServiceInterfaceid = txtServiceId;
    }

    public void setImage(String _img) {
        this.img = _img;
    }

    public String getImage() {
        return this.img;
    }


}
