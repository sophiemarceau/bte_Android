package com.btetop.bean;

public class UnreadMesBean {

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : 3
     */

    private String code;
    private String message;
    private Object userToken;
    private String data;

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

    public Object getUserToken() {
        return userToken;
    }

    public void setUserToken(Object userToken) {
        this.userToken = userToken;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
