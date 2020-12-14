package com.example.ridhdhish.sut;

/**
 * Created by Ridhdhish on 25-01-2019.
 */

public class maintenanceDataModel {
    private String txtMaintenanceInterfaceMonth;
    private String txtMaintenanceInterfaceAmount;
    private String txtMaintenanceInterfacePenalty;
    private String txtMaintenanceInterfaceDueDate;
    private String txtMaintenanceInterfacePostedDate;

    public String getMonth() {
        return this.txtMaintenanceInterfaceMonth;
    }

    public String getAmount() {
        return this.txtMaintenanceInterfaceAmount;
    }

    public String getPenalty() {
        return this.txtMaintenanceInterfacePenalty;
    }

    public String getDueDate() {
        return this.txtMaintenanceInterfaceDueDate;
    }

    public String getPostDate() {
        return this.txtMaintenanceInterfacePostedDate;
    }
    public void setMonth(String txtMonth) {
        this.txtMaintenanceInterfaceMonth = txtMonth;
    }

    public void setAmount(String txtAmount) {
        this.txtMaintenanceInterfaceAmount = txtAmount;
    }

    public void setPenalty(String txtPenalty) {
        this.txtMaintenanceInterfacePenalty = txtPenalty;
    }

    public void setDueDate(String txtDueDate) {
        this.txtMaintenanceInterfaceDueDate = txtDueDate;
    }

    public void setPostDate(String txtPostDate) {
        this.txtMaintenanceInterfacePostedDate = txtPostDate;
    }
}
