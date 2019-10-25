package com.example.zylaoshi.library.net.service;

/**
 * @auther zylaoshi
 * @date 2017/12/1 14:30
 */

public class FileEntity {


    /**
     * err_code : 0
     * err_msg : {"filename":{"name":"43eb9cb8cd8fc8a4e544968350b03a3a.png","url":"http:\/\/store1.xinyongjinku.com\/storage.php?c=index&a=view&access_key=204efee7f441bafbe36bed7ba25be09c&file=43eb9cb8cd8fc8a4e544968350b03a3a.png"}}
     */

    private int err_code;
    private String err_msg;

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }
}
