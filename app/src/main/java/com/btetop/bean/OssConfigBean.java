package com.btetop.bean;

public class OssConfigBean {

    /**
     * endPoint : oss-cn-beijing.aliyuncs.com
     * bucketName : bte-test
     */

    private String endPoint;
    private String bucketName;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
