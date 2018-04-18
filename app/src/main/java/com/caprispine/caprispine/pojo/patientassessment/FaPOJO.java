package com.caprispine.caprispine.pojo.patientassessment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 07-04-2018.
 */

public class FaPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("problem_id")
    private String problemId;
    @SerializedName("date")
    private String date;
    @SerializedName("question_id")
    private String questionId;
    @SerializedName("answer_id")
    private String answerId;
    @SerializedName("answered_on")
    private String answeredOn;
    @SerializedName("answer_mark")
    private String answerMark;
    @SerializedName("total")
    private String total;
    @SerializedName("problem_description")
    private ProblemDescriptionPOJO problemDescriptionPOJO;

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

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnsweredOn() {
        return answeredOn;
    }

    public void setAnsweredOn(String answeredOn) {
        this.answeredOn = answeredOn;
    }

    public String getAnswerMark() {
        return answerMark;
    }

    public void setAnswerMark(String answerMark) {
        this.answerMark = answerMark;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ProblemDescriptionPOJO getProblemDescriptionPOJO() {
        return problemDescriptionPOJO;
    }

    public void setProblemDescriptionPOJO(ProblemDescriptionPOJO problemDescriptionPOJO) {
        this.problemDescriptionPOJO = problemDescriptionPOJO;
    }
}
