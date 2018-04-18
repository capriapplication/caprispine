package com.caprispine.caprispine.pojo.problem;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 03-04-2018.
 */

public class AnsPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("question_id")
    private String questionId;
    @SerializedName("answer")
    private String answer;
    @SerializedName("answer_description")
    private String answerDescription;
    @SerializedName("status")
    private String status;
    @SerializedName("order")
    private String order;
    @SerializedName("answer_mark")
    private String answerMark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAnswerMark() {
        return answerMark;
    }

    public void setAnswerMark(String answerMark) {
        this.answerMark = answerMark;
    }
}
