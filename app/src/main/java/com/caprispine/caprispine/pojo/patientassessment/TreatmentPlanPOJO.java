package com.caprispine.caprispine.pojo.patientassessment;

import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 14-04-2018.
 */

public class TreatmentPlanPOJO {
    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("goal")
    private String goal;
    @SerializedName("therapy")
    private String therapy;
    @SerializedName("planned_treatment")
    private String plannedTreatment;
    @SerializedName("date")
    private String date;
    @SerializedName("treatment")
    private TreatmentPOJO treatment;

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

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public String getPlannedTreatment() {
        return plannedTreatment;
    }

    public void setPlannedTreatment(String plannedTreatment) {
        this.plannedTreatment = plannedTreatment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TreatmentPOJO getTreatment() {
        return treatment;
    }

    public void setTreatment(TreatmentPOJO treatment) {
        this.treatment = treatment;
    }
}
