package com.caprispine.caprispine.pojo.graph;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 12-04-2018.
 */

public class GraphPOJO implements Serializable{
    List<GraphResultPOJO> graphResultPOJOS;

    public List<GraphResultPOJO> getGraphResultPOJOS() {
        return graphResultPOJOS;
    }

    public void setGraphResultPOJOS(List<GraphResultPOJO> graphResultPOJOS) {
        this.graphResultPOJOS = graphResultPOJOS;
    }
}
