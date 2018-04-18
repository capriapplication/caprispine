package com.caprispine.caprispine.pojo.problem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 04-12-2017.
 */

public class AnswersPOJO implements Serializable{
    String score;
    String problem_id;
    List<AnsPOJO> ansPOJOS;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(String problem_id) {
        this.problem_id = problem_id;
    }

    public List<AnsPOJO> getAnsPOJOS() {
        return ansPOJOS;
    }

    public void setAnsPOJOS(List<AnsPOJO> ansPOJOS) {
        this.ansPOJOS = ansPOJOS;
    }
}
