package com.caprispine.caprispine.pojo.problem;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 03-04-2018.
 */

public class QuestionPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("problem_id")
    private String problemId;
    @SerializedName("question")
    private String question;
    @SerializedName("question_description")
    private String questionDescription;
    @SerializedName("status")
    private String status;
    @SerializedName("order")
    private String order;
    @SerializedName("ans")
    private List<AnsPOJO> ansPOJOS;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
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

    public List<AnsPOJO> getAnsPOJOS() {
        return ansPOJOS;
    }

    public void setAnsPOJOS(List<AnsPOJO> ansPOJOS) {
        this.ansPOJOS = ansPOJOS;
    }
}
