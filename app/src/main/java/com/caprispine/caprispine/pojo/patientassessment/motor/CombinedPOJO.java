package com.caprispine.caprispine.pojo.patientassessment.motor;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 22-03-2018.
 */

public class CombinedPOJO {

    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("cervical_spine")
    private String cervicalSpine;
    @SerializedName("thoracic_spine")
    private String thoracicSpine;
    @SerializedName("lumbar_spine")
    private String lumbarSpine;
    @SerializedName("date")
    private String date;
    @SerializedName("image")
    private String image;

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

    public String getCervicalSpine() {
        return cervicalSpine;
    }

    public void setCervicalSpine(String cervicalSpine) {
        this.cervicalSpine = cervicalSpine;
    }

    public String getThoracicSpine() {
        return thoracicSpine;
    }

    public void setThoracicSpine(String thoracicSpine) {
        this.thoracicSpine = thoracicSpine;
    }

    public String getLumbarSpine() {
        return lumbarSpine;
    }

    public void setLumbarSpine(String lumbarSpine) {
        this.lumbarSpine = lumbarSpine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
