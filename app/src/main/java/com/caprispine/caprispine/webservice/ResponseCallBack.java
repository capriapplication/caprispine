package com.caprispine.caprispine.webservice;

import com.caprispine.caprispine.pojo.ResponsePOJO;

/**
 * Created by sunil on 29-12-2016.
 */

public interface ResponseCallBack<T> {
    public void onGetMsg(ResponsePOJO<T> responsePOJO);
}
