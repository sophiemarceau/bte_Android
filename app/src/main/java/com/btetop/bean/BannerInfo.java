package com.btetop.bean;

public class BannerInfo {

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : [{"name":"测试1","url":"https://www.baidu.com/","target":null}]
     */
    /**
     * name : 测试1
     * url : https://www.baidu.com/
     * target : null
     */

    private String name;
    private String url;
    private String target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
