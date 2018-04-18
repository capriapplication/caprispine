package com.caprispine.caprispine.pojo.patientassessment.motor;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 22-03-2018.
 */

public class KneePOJO {
    @SerializedName("id")
    private String id;
    @SerializedName("left_tone1")
    private String leftTone1;
    @SerializedName("left_tone2")
    private String leftTone2;
    @SerializedName("left_power1")
    private String leftPower1;
    @SerializedName("left_power2")
    private String leftPower2;
    @SerializedName("left_rom1")
    private String leftRom1;
    @SerializedName("left_rom2")
    private String leftRom2;
    @SerializedName("right_rom1")
    private String rightRom1;
    @SerializedName("right_rom2")
    private String rightRom2;
    @SerializedName("right_power1")
    private String rightPower1;
    @SerializedName("right_power2")
    private String rightPower2;
    @SerializedName("right_tone1")
    private String rightTone1;
    @SerializedName("right_tone2")
    private String rightTone2;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("image")
    private String image;
    @SerializedName("date")
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeftTone1() {
        return leftTone1;
    }

    public void setLeftTone1(String leftTone1) {
        this.leftTone1 = leftTone1;
    }

    public String getLeftTone2() {
        return leftTone2;
    }

    public void setLeftTone2(String leftTone2) {
        this.leftTone2 = leftTone2;
    }

    public String getLeftPower1() {
        return leftPower1;
    }

    public void setLeftPower1(String leftPower1) {
        this.leftPower1 = leftPower1;
    }

    public String getLeftPower2() {
        return leftPower2;
    }

    public void setLeftPower2(String leftPower2) {
        this.leftPower2 = leftPower2;
    }

    public String getLeftRom1() {
        return leftRom1;
    }

    public void setLeftRom1(String leftRom1) {
        this.leftRom1 = leftRom1;
    }

    public String getLeftRom2() {
        return leftRom2;
    }

    public void setLeftRom2(String leftRom2) {
        this.leftRom2 = leftRom2;
    }

    public String getRightRom1() {
        return rightRom1;
    }

    public void setRightRom1(String rightRom1) {
        this.rightRom1 = rightRom1;
    }

    public String getRightRom2() {
        return rightRom2;
    }

    public void setRightRom2(String rightRom2) {
        this.rightRom2 = rightRom2;
    }

    public String getRightPower1() {
        return rightPower1;
    }

    public void setRightPower1(String rightPower1) {
        this.rightPower1 = rightPower1;
    }

    public String getRightPower2() {
        return rightPower2;
    }

    public void setRightPower2(String rightPower2) {
        this.rightPower2 = rightPower2;
    }

    public String getRightTone1() {
        return rightTone1;
    }

    public void setRightTone1(String rightTone1) {
        this.rightTone1 = rightTone1;
    }

    public String getRightTone2() {
        return rightTone2;
    }

    public void setRightTone2(String rightTone2) {
        this.rightTone2 = rightTone2;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
