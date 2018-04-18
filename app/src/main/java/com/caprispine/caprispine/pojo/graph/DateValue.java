package com.caprispine.caprispine.pojo.graph;

import java.io.Serializable;

/**
 * Created by sunil on 12-04-2018.
 */

public class DateValue implements Serializable {
    String date;
    String value;

    public DateValue(String date, String value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
