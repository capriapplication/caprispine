package com.caprispine.caprispine.pojo.appointment;

import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 14-04-2018.
 */

public class AppointmentPOJO {
    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("therapist_id")
    private String therapistId;
    @SerializedName("booking_date")
    private String bookingDate;
    @SerializedName("booking_time")
    private String bookingTime;
    @SerializedName("status")
    private String status;
    @SerializedName("reason")
    private String reason;
    @SerializedName("branch_id")
    private String branchId;
    @SerializedName("therapist")
    private TherapistPOJO therapistPOJO;
    @SerializedName("patient")
    private PatientPOJO patientPOJO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(String therapistId) {
        this.therapistId = therapistId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public TherapistPOJO getTherapistPOJO() {
        return therapistPOJO;
    }

    public void setTherapistPOJO(TherapistPOJO therapistPOJO) {
        this.therapistPOJO = therapistPOJO;
    }

    public PatientPOJO getPatientPOJO() {
        return patientPOJO;
    }

    public void setPatientPOJO(PatientPOJO patientPOJO) {
        this.patientPOJO = patientPOJO;
    }
}
