package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/13.
 */

public class BaseBean<T> implements Serializable {
    private String code;
    private String message;
    private String userToken;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserToken() {
        return userToken;
    }

    public T getData() {
        return data;
    }
}

