package com.caprispine.caprispine.pojo.patientassessment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 20-03-2018.
 */

public class PainPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("pain_intensity")
    private String painIntensity;
    @SerializedName("threshold_site")
    private String thresholdSite;
    @SerializedName("pain_nature")
    private String painNature;
    @SerializedName("pain_onset")
    private String painOnset;
    @SerializedName("pain")
    private String pain;
    @SerializedName("feel_more_pain")
    private String feelMorePain;
    @SerializedName("aggravating_factors")
    private String aggravatingFactors;
    @SerializedName("relieving_factors")
    private String relievingFactors;
    @SerializedName("specify_aggrevating")
    private String specifyAggrevating;
    @SerializedName("specify_relieving")
    private String specifyRelieving;
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

    public String getPainIntensity() {
        return painIntensity;
    }

    public void setPainIntensity(String painIntensity) {
        this.painIntensity = painIntensity;
    }

    public String getThresholdSite() {
        return thresholdSite;
    }

    public void setThresholdSite(String thresholdSite) {
        this.thresholdSite = thresholdSite;
    }

    public String getPainNature() {
        return painNature;
    }

    public void setPainNature(String painNature) {
        this.painNature = painNature;
    }

    public String getPainOnset() {
        return painOnset;
    }

    public void setPainOnset(String painOnset) {
        this.painOnset = painOnset;
    }

    public String getPain() {
        return pain;
    }

    public void setPain(String pain) {
        this.pain = pain;
    }

    public String getFeelMorePain() {
        return feelMorePain;
    }

    public void setFeelMorePain(String feelMorePain) {
        this.feelMorePain = feelMorePain;
    }

    public String getAggravatingFactors() {
        return aggravatingFactors;
    }

    public void setAggravatingFactors(String aggravatingFactors) {
        this.aggravatingFactors = aggravatingFactors;
    }

    public String getRelievingFactors() {
        return relievingFactors;
    }

    public void setRelievingFactors(String relievingFactors) {
        this.relievingFactors = relievingFactors;
    }

    public String getSpecifyAggrevating() {
        return specifyAggrevating;
    }

    public void setSpecifyAggrevating(String specifyAggrevating) {
        this.specifyAggrevating = specifyAggrevating;
    }

    public String getSpecifyRelieving() {
        return specifyRelieving;
    }

    public void setSpecifyRelieving(String specifyRelieving) {
        this.specifyRelieving = specifyRelieving;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
