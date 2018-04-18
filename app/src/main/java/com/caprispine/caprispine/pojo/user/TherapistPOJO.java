package com.caprispine.caprispine.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 27-03-2018.
 */

public class TherapistPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("profile_pic")
    private String profilePic;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("address")
    private String address;
    @SerializedName("country")
    private String country;
    @SerializedName("pin_code")
    private String pinCode;
    @SerializedName("dob")
    private String dob;
    @SerializedName("doj")
    private String doj;
    @SerializedName("gender")
    private String gender;
    @SerializedName("age")
    private String age;
    @SerializedName("martial_status")
    private String martialStatus;
    @SerializedName("height")
    private String height;
    @SerializedName("weight")
    private String weight;
    @SerializedName("branch_id")
    private String branchId;
    @SerializedName("starting_time")
    private String startingTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("degree")
    private String degree;
    @SerializedName("institution")
    private String institution;
    @SerializedName("university")
    private String university;
    @SerializedName("qualification_duration")
    private String qualificationDuration;
    @SerializedName("average")
    private String average;
    @SerializedName("organisation")
    private String organisation;
    @SerializedName("designation")
    private String designation;
    @SerializedName("experience_duration")
    private String experienceDuration;
    @SerializedName("nature_of_work")
    private String natureOfWork;
    @SerializedName("ending_date_of_contract")
    private String endingDateOfContract;
    @SerializedName("user")
    private UserPOJO userPOJO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getQualificationDuration() {
        return qualificationDuration;
    }

    public void setQualificationDuration(String qualificationDuration) {
        this.qualificationDuration = qualificationDuration;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getExperienceDuration() {
        return experienceDuration;
    }

    public void setExperienceDuration(String experienceDuration) {
        this.experienceDuration = experienceDuration;
    }

    public String getNatureOfWork() {
        return natureOfWork;
    }

    public void setNatureOfWork(String natureOfWork) {
        this.natureOfWork = natureOfWork;
    }

    public String getEndingDateOfContract() {
        return endingDateOfContract;
    }

    public void setEndingDateOfContract(String endingDateOfContract) {
        this.endingDateOfContract = endingDateOfContract;
    }

    public UserPOJO getUserPOJO() {
        return userPOJO;
    }

    public void setUserPOJO(UserPOJO userPOJO) {
        this.userPOJO = userPOJO;
    }
}
