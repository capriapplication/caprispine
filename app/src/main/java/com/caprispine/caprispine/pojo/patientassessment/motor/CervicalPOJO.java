package com.caprispine.caprispine.pojo.patientassessment.motor;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 22-03-2018.
 */

public class CervicalPOJO {
    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("flexion")
    private String flexion;
    @SerializedName("extension")
    private String extension;
    @SerializedName("side_flexion_left")
    private String sideFlexionLeft;
    @SerializedName("side_flexion_right")
    private String sideFlexionRight;
    @SerializedName("rotation_left")
    private String rotationLeft;
    @SerializedName("rotation_right")
    private String rotationRight;
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

    public String getFlexion() {
        return flexion;
    }

    public void setFlexion(String flexion) {
        this.flexion = flexion;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getSideFlexionLeft() {
        return sideFlexionLeft;
    }

    public void setSideFlexionLeft(String sideFlexionLeft) {
        this.sideFlexionLeft = sideFlexionLeft;
    }

    public String getSideFlexionRight() {
        return sideFlexionRight;
    }

    public void setSideFlexionRight(String sideFlexionRight) {
        this.sideFlexionRight = sideFlexionRight;
    }

    public String getRotationLeft() {
        return rotationLeft;
    }

    public void setRotationLeft(String rotationLeft) {
        this.rotationLeft = rotationLeft;
    }

    public String getRotationRight() {
        return rotationRight;
    }

    public void setRotationRight(String rotationRight) {
        this.rotationRight = rotationRight;
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
