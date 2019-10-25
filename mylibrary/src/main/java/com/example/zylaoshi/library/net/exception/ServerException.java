package com.example.zylaoshi.library.net.exception;

/**
 * @description: 服务器 返回错误码处理
 * @autour: zylaoshi
 * @date: 2017/11/29 10:38
 */

public class ServerException extends RuntimeException {
    private int errCode = 0;

    public ServerException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}
