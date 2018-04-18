package com.caprispine.caprispine.pojo.branch;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 19-03-2018.
 */

public class BranchPOJO implements Serializable {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<BranchResultPOJO> branchResultPOJOS;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<BranchResultPOJO> getBranchResultPOJOS() {
        return branchResultPOJOS;
    }

    public void setBranchResultPOJOS(List<BranchResultPOJO> branchResultPOJOS) {
        this.branchResultPOJOS = branchResultPOJOS;
    }
}
