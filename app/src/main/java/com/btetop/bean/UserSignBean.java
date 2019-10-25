package com.btetop.bean;

public class UserSignBean {

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : true
     */

    private String code;
    private String message;
    private Object userToken;
    private boolean data;

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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
