package com.example.zylaoshi.library.net.exception;

/**
 * @description: 异常描述
 * @autour: zylaoshi
 * @date: 2017/11/29 10:39
 */

public class ResponeThrowable extends Exception {

    public int code;
    public String message;

    public static final String UNKNOWN = "不能识别的错误";
    public static final String PARSE_ERROR = "json解析格式错误";


    public ResponeThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ResponeThrowable(Throwable throwable, String message) {
        super(throwable);
        this.message = message;

    }

    public int getCode() {
        return code;
    }
    public void setCode(int code){
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String msg) {
        this.message = msg;
    }
}
