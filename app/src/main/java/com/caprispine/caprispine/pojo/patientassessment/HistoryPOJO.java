package com.caprispine.caprispine.pojo.patientassessment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 20-03-2018.
 */

public class HistoryPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("problem_cause")
    private String problemCause;
    @SerializedName("medical_problem")
    private String medicalProblem;
    @SerializedName("any_surgery")
    private String anySurgery;
    @SerializedName("present_treatment")
    private String presentTreatment;
    @SerializedName("smoking")
    private String smoking;
    @SerializedName("alcholic")
    private String alcholic;
    @SerializedName("fever_chill")
    private String feverChill;
    @SerializedName("diabetes")
    private String diabetes;
    @SerializedName("blood_pressure")
    private String bloodPressure;
    @SerializedName("heart_disease")
    private String heartDisease;
    @SerializedName("bleeding_disorder")
    private String bleedingDisorder;
    @SerializedName("recent_infection")
    private String recentInfection;
    @SerializedName("red_flags")
    private String redFlags;
    @SerializedName("yellow_flags")
    private String yellowFlags;
    @SerializedName("limitations")
    private String limitations;
    @SerializedName("past_surgery")
    private String pastSurgery;
    @SerializedName("allergy")
    private String allergy;
    @SerializedName("osteoporotic")
    private String osteoporotic;
    @SerializedName("any_implants")
    private String anyImplants;
    @SerializedName("herediatary_disease")
    private String herediataryDisease;
    @SerializedName("remark")
    private String remark;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("date")
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblemCause() {
        return problemCause;
    }

    public void setProblemCause(String problemCause) {
        this.problemCause = problemCause;
    }

    public String getMedicalProblem() {
        return medicalProblem;
    }

    public void setMedicalProblem(String medicalProblem) {
        this.medicalProblem = medicalProblem;
    }

    public String getAnySurgery() {
        return anySurgery;
    }

    public void setAnySurgery(String anySurgery) {
        this.anySurgery = anySurgery;
    }

    public String getPresentTreatment() {
        return presentTreatment;
    }

    public void setPresentTreatment(String presentTreatment) {
        this.presentTreatment = presentTreatment;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getAlcholic() {
        return alcholic;
    }

    public void setAlcholic(String alcholic) {
        this.alcholic = alcholic;
    }

    public String getFeverChill() {
        return feverChill;
    }

    public void setFeverChill(String feverChill) {
        this.feverChill = feverChill;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getHeartDisease() {
        return heartDisease;
    }

    public void setHeartDisease(String heartDisease) {
        this.heartDisease = heartDisease;
    }

    public String getBleedingDisorder() {
        return bleedingDisorder;
    }

    public void setBleedingDisorder(String bleedingDisorder) {
        this.bleedingDisorder = bleedingDisorder;
    }

    public String getRecentInfection() {
        return recentInfection;
    }

    public void setRecentInfection(String recentInfection) {
        this.recentInfection = recentInfection;
    }

    public String getRedFlags() {
        return redFlags;
    }

    public void setRedFlags(String redFlags) {
        this.redFlags = redFlags;
    }

    public String getYellowFlags() {
        return yellowFlags;
    }

    public void setYellowFlags(String yellowFlags) {
        this.yellowFlags = yellowFlags;
    }

    public String getLimitations() {
        return limitations;
    }

    public void setLimitations(String limitations) {
        this.limitations = limitations;
    }

    public String getPastSurgery() {
        return pastSurgery;
    }

    public void setPastSurgery(String pastSurgery) {
        this.pastSurgery = pastSurgery;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getOsteoporotic() {
        return osteoporotic;
    }

    public void setOsteoporotic(String osteoporotic) {
        this.osteoporotic = osteoporotic;
    }

    public String getAnyImplants() {
        return anyImplants;
    }

    public void setAnyImplants(String anyImplants) {
        this.anyImplants = anyImplants;
    }

    public String getHerediataryDisease() {
        return herediataryDisease;
    }

    public void setHerediataryDisease(String herediataryDisease) {
        this.herediataryDisease = herediataryDisease;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
