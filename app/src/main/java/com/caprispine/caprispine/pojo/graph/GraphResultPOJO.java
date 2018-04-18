package com.caprispine.caprispine.pojo.graph;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 12-04-2018.
 */

public class GraphResultPOJO implements Serializable {
    String line_name;
    List<DateValue> dateValues;

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public List<DateValue> getDateValues() {
        return dateValues;
    }

    public void setDateValues(List<DateValue> dateValues) {
        this.dateValues = dateValues;
    }
}
