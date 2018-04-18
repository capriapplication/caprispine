package com.caprispine.caprispine.pojo.patientassessment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 19-03-2018.
 */

public class ChiefComplaintPOJO implements Serializable{

    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("complaints")
    private String complaints;
    @SerializedName("problem_duration")
    private String problemDuration;
    @SerializedName("problem_before")
    private String problemBefore;
    @SerializedName("date")
    private String date;

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

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getProblemDuration() {
        return problemDuration;
    }

    public void setProblemDuration(String problemDuration) {
        this.problemDuration = problemDuration;
    }

    public String getProblemBefore() {
        return problemBefore;
    }

    public void setProblemBefore(String problemBefore) {
        this.problemBefore = problemBefore;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
