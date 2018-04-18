package com.caprispine.caprispine.pojo.patientassessment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 27-03-2018.
 */

public class NeuroExamPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("gcseye")
    private String gcseye;
    @SerializedName("gcsverbal")
    private String gcsverbal;
    @SerializedName("gcsmotor")
    private String gcsmotor;
    @SerializedName("ctfntime")
    private String ctfntime;
    @SerializedName("ctfnspeed")
    private String ctfnspeed;
    @SerializedName("ctfnerror")
    private String ctfnerror;
    @SerializedName("ctastime")
    private String ctastime;
    @SerializedName("ctasspeed")
    private String ctasspeed;
    @SerializedName("ctaserror")
    private String ctaserror;
    @SerializedName("cthstime")
    private String cthstime;
    @SerializedName("cthsspeed")
    private String cthsspeed;
    @SerializedName("cthserror")
    private String cthserror;
    @SerializedName("gaitsurface")
    private String gaitsurface;
    @SerializedName("gaitspeed")
    private String gaitspeed;
    @SerializedName("gaithorizontal")
    private String gaithorizontal;
    @SerializedName("gaitvertical")
    private String gaitvertical;
    @SerializedName("gaitpivot")
    private String gaitpivot;
    @SerializedName("gaitover")
    private String gaitover;
    @SerializedName("gaitaround")
    private String gaitaround;
    @SerializedName("gaitsteps")
    private String gaitsteps;
    @SerializedName("balanceleft")
    private String balanceleft;
    @SerializedName("balanceright")
    private String balanceright;
    @SerializedName("ftbowel")
    private String ftbowel;
    @SerializedName("ftbladder")
    private String ftbladder;
    @SerializedName("fttoilet")
    private String fttoilet;
    @SerializedName("ftgromming")
    private String ftgromming;
    @SerializedName("ftfeeding")
    private String ftfeeding;
    @SerializedName("fttransfer")
    private String fttransfer;
    @SerializedName("ftmobility")
    private String ftmobility;
    @SerializedName("ftdressing")
    private String ftdressing;
    @SerializedName("ftstairs")
    private String ftstairs;
    @SerializedName("ftbathing")
    private String ftbathing;
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

    public String getGcseye() {
        return gcseye;
    }

    public void setGcseye(String gcseye) {
        this.gcseye = gcseye;
    }

    public String getGcsverbal() {
        return gcsverbal;
    }

    public void setGcsverbal(String gcsverbal) {
        this.gcsverbal = gcsverbal;
    }

    public String getGcsmotor() {
        return gcsmotor;
    }

    public void setGcsmotor(String gcsmotor) {
        this.gcsmotor = gcsmotor;
    }

    public String getCtfntime() {
        return ctfntime;
    }

    public void setCtfntime(String ctfntime) {
        this.ctfntime = ctfntime;
    }

    public String getCtfnspeed() {
        return ctfnspeed;
    }

    public void setCtfnspeed(String ctfnspeed) {
        this.ctfnspeed = ctfnspeed;
    }

    public String getCtfnerror() {
        return ctfnerror;
    }

    public void setCtfnerror(String ctfnerror) {
        this.ctfnerror = ctfnerror;
    }

    public String getCtastime() {
        return ctastime;
    }

    public void setCtastime(String ctastime) {
        this.ctastime = ctastime;
    }

    public String getCtasspeed() {
        return ctasspeed;
    }

    public void setCtasspeed(String ctasspeed) {
        this.ctasspeed = ctasspeed;
    }

    public String getCtaserror() {
        return ctaserror;
    }

    public void setCtaserror(String ctaserror) {
        this.ctaserror = ctaserror;
    }

    public String getCthstime() {
        return cthstime;
    }

    public void setCthstime(String cthstime) {
        this.cthstime = cthstime;
    }

    public String getCthsspeed() {
        return cthsspeed;
    }

    public void setCthsspeed(String cthsspeed) {
        this.cthsspeed = cthsspeed;
    }

    public String getCthserror() {
        return cthserror;
    }

    public void setCthserror(String cthserror) {
        this.cthserror = cthserror;
    }

    public String getGaitsurface() {
        return gaitsurface;
    }

    public void setGaitsurface(String gaitsurface) {
        this.gaitsurface = gaitsurface;
    }

    public String getGaitspeed() {
        return gaitspeed;
    }

    public void setGaitspeed(String gaitspeed) {
        this.gaitspeed = gaitspeed;
    }

    public String getGaithorizontal() {
        return gaithorizontal;
    }

    public void setGaithorizontal(String gaithorizontal) {
        this.gaithorizontal = gaithorizontal;
    }

    public String getGaitvertical() {
        return gaitvertical;
    }

    public void setGaitvertical(String gaitvertical) {
        this.gaitvertical = gaitvertical;
    }

    public String getGaitpivot() {
        return gaitpivot;
    }

    public void setGaitpivot(String gaitpivot) {
        this.gaitpivot = gaitpivot;
    }

    public String getGaitover() {
        return gaitover;
    }

    public void setGaitover(String gaitover) {
        this.gaitover = gaitover;
    }

    public String getGaitaround() {
        return gaitaround;
    }

    public void setGaitaround(String gaitaround) {
        this.gaitaround = gaitaround;
    }

    public String getGaitsteps() {
        return gaitsteps;
    }

    public void setGaitsteps(String gaitsteps) {
        this.gaitsteps = gaitsteps;
    }

    public String getBalanceleft() {
        return balanceleft;
    }

    public void setBalanceleft(String balanceleft) {
        this.balanceleft = balanceleft;
    }

    public String getBalanceright() {
        return balanceright;
    }

    public void setBalanceright(String balanceright) {
        this.balanceright = balanceright;
    }

    public String getFtbowel() {
        return ftbowel;
    }

    public void setFtbowel(String ftbowel) {
        this.ftbowel = ftbowel;
    }

    public String getFtbladder() {
        return ftbladder;
    }

    public void setFtbladder(String ftbladder) {
        this.ftbladder = ftbladder;
    }

    public String getFttoilet() {
        return fttoilet;
    }

    public void setFttoilet(String fttoilet) {
        this.fttoilet = fttoilet;
    }

    public String getFtgromming() {
        return ftgromming;
    }

    public void setFtgromming(String ftgromming) {
        this.ftgromming = ftgromming;
    }

    public String getFtfeeding() {
        return ftfeeding;
    }

    public void setFtfeeding(String ftfeeding) {
        this.ftfeeding = ftfeeding;
    }

    public String getFttransfer() {
        return fttransfer;
    }

    public void setFttransfer(String fttransfer) {
        this.fttransfer = fttransfer;
    }

    public String getFtmobility() {
        return ftmobility;
    }

    public void setFtmobility(String ftmobility) {
        this.ftmobility = ftmobility;
    }

    public String getFtdressing() {
        return ftdressing;
    }

    public void setFtdressing(String ftdressing) {
        this.ftdressing = ftdressing;
    }

    public String getFtstairs() {
        return ftstairs;
    }

    public void setFtstairs(String ftstairs) {
        this.ftstairs = ftstairs;
    }

    public String getFtbathing() {
        return ftbathing;
    }

    public void setFtbathing(String ftbathing) {
        this.ftbathing = ftbathing;
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
