package com.caprispine.caprispine.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 13-04-2018.
 */

public class AllUserPOJO {
    @SerializedName("patient")
    List<PatientPOJO> patientPOJOS;
    @SerializedName("therapist")
    List<TherapistPOJO> therapistPOJOS;
    @SerializedName("staff")
    List<StaffPOJO> staffPOJOS;
    @SerializedName("admin")
    List<AdminUserPOJO> adminUserPOJOS;

    public List<PatientPOJO> getPatientPOJOS() {
        return patientPOJOS;
    }

    public void setPatientPOJOS(List<PatientPOJO> patientPOJOS) {
        this.patientPOJOS = patientPOJOS;
    }

    public List<TherapistPOJO> getTherapistPOJOS() {
        return therapistPOJOS;
    }

    public void setTherapistPOJOS(List<TherapistPOJO> therapistPOJOS) {
        this.therapistPOJOS = therapistPOJOS;
    }

    public List<StaffPOJO> getStaffPOJOS() {
        return staffPOJOS;
    }

    public void setStaffPOJOS(List<StaffPOJO> staffPOJOS) {
        this.staffPOJOS = staffPOJOS;
    }

    public List<AdminUserPOJO> getAdminUserPOJOS() {
        return adminUserPOJOS;
    }

    public void setAdminUserPOJOS(List<AdminUserPOJO> adminUserPOJOS) {
        this.adminUserPOJOS = adminUserPOJOS;
    }
}
