package com.btetop.bean;

import java.util.List;

public class UserHeadImage {

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : {"result":["https://file.bte.top/common/avatar/1.png","https://file.bte.top/common/avatar/2.png","https://file.bte.top/common/avatar/3.png","https://file.bte.top/common/avatar/4.png",
     * "https://file.bte.top/common/avatar/5.png","https://file.bte.top/common/avatar/6.png","https://file.bte.top/common/avatar/7.png","https://file.bte.top/common/avatar/8.png","https://file.bte
     * .top/common/avatar/9.png"]}
     */

    private List<String> result;

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
