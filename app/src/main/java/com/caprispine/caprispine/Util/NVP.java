package com.caprispine.caprispine.Util;

import java.io.Serializable;

/**
 * Created by sunil on 19-12-2017.
 */

public class NVP implements Serializable{
    /**
     * Default Constructor taking a name and a value. The value may be null.
     *
     * BasicNameValuePair@param value The value.
     */
    String name;
    String value;
    public NVP(String name, String value) {
        this.name=name;
        this.value=value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NVP{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
