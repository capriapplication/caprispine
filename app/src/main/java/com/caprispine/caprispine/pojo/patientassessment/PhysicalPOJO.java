package com.caprispine.caprispine.pojo.patientassessment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 21-03-2018.
 */

public class PhysicalPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("blood_pressure")
    private String bloodPressure;
    @SerializedName("temperature")
    private String temperature;
    @SerializedName("heart_rate")
    private String heartRate;
    @SerializedName("respiratory_rate")
    private String respiratoryRate;
    @SerializedName("posture")
    private String posture;
    @SerializedName("galt")
    private String galt;
    @SerializedName("scare_type")
    private String scareType;
    @SerializedName("swelling")
    private String swelling;
    @SerializedName("description")
    private String description;
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

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(String respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public String getPosture() {
        return posture;
    }

    public void setPosture(String posture) {
        this.posture = posture;
    }

    public String getGalt() {
        return galt;
    }

    public void setGalt(String galt) {
        this.galt = galt;
    }

    public String getScareType() {
        return scareType;
    }

    public void setScareType(String scareType) {
        this.scareType = scareType;
    }

    public String getSwelling() {
        return swelling;
    }

    public void setSwelling(String swelling) {
        this.swelling = swelling;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
