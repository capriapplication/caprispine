package com.caprispine.caprispine.pojo.report;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 29-03-2018.
 */

public class IncomePOJO {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("id")
    private String id;
    @SerializedName("treatment_id")
    private String treatmentId;
    @SerializedName("dose")
    private String dose;
    @SerializedName("therapist_id")
    private String therapistId;
    @SerializedName("time_in")
    private String timeIn;
    @SerializedName("time_out")
    private String timeOut;
    @SerializedName("comment")
    private String comment;
    @SerializedName("signature")
    private String signature;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("branch_id")
    private String branchId;
    @SerializedName("amount")
    private String amount;
    @SerializedName("date")
    private String date;
    @SerializedName("patient_first_name")
    private String patient_first_name;
    @SerializedName("patient_last_name")
    private String patient_last_name;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(String treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(String therapistId) {
        this.therapistId = therapistId;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatient_first_name() {
        return patient_first_name;
    }

    public void setPatient_first_name(String patient_first_name) {
        this.patient_first_name = patient_first_name;
    }

    public String getPatient_last_name() {
        return patient_last_name;
    }

    public void setPatient_last_name(String patient_last_name) {
        this.patient_last_name = patient_last_name;
    }
}
