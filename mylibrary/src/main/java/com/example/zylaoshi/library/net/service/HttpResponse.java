package com.example.zylaoshi.library.net.service;

/** 
 * @description: 最外层的json数据
 * @autour: zylaoshi
 * @date: 2017/11/29 15:05
*/
public class HttpResponse<T> {

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        data = data;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
