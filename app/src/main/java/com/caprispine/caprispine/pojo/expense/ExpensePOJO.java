package com.caprispine.caprispine.pojo.expense;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 30-03-2018.
 */

public class ExpensePOJO {
    @SerializedName("exp_id")
    private String expId;
    @SerializedName("exp_date")
    private String expDate;
    @SerializedName("exp_billno")
    private String expBillno;
    @SerializedName("exp_vendor")
    private String expVendor;
    @SerializedName("exp_total")
    private String expTotal;
    @SerializedName("exp_branch_id")
    private String expBranchId;
    @SerializedName("exp_status")
    private String expStatus;

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getExpBillno() {
        return expBillno;
    }

    public void setExpBillno(String expBillno) {
        this.expBillno = expBillno;
    }

    public String getExpVendor() {
        return expVendor;
    }

    public void setExpVendor(String expVendor) {
        this.expVendor = expVendor;
    }

    public String getExpTotal() {
        return expTotal;
    }

    public void setExpTotal(String expTotal) {
        this.expTotal = expTotal;
    }

    public String getExpBranchId() {
        return expBranchId;
    }

    public void setExpBranchId(String expBranchId) {
        this.expBranchId = expBranchId;
    }

    public String getExpStatus() {
        return expStatus;
    }

    public void setExpStatus(String expStatus) {
        this.expStatus = expStatus;
    }
}
