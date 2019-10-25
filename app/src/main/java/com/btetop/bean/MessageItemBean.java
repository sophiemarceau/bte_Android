package com.btetop.bean;

import java.util.List;

public class MessageItemBean {

    /**
     * data : [{"id":1,"title":"今日行情报告","type":"1","summary":"比特币行情过山车","keywords":"行情过山车","description":"比特币行情过山车","content":"比特币行情过山车，投资者不知所措","createTime":"2018-10-15 17:42:37",
     * "createId":null,"sendStatus":0,"sendTime":null,"sendUserId":0,"pushStatus":0,"pushDate":null,"pushUserId":null,"pushUserName":null},{"id":2,"title":"今日行情报告","type":"1",
     * "summary":"比特币行情过山车2","keywords":"行情过山车","description":"比特币行情过山车","content":"比特币行情过山车，投资者不知所措","createTime":"2018-10-16 17:42:37","createId":null,"sendStatus":0,"sendTime":null,
     * "sendUserId":0,"pushStatus":0,"pushDate":null,"pushUserId":null,"pushUserName":null}]
     * totalNum : 11
     */

    private int totalNum;
    private List<DataBean> data;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : 今日行情报告
         * type : 1
         * summary : 比特币行情过山车
         * keywords : 行情过山车
         * description : 比特币行情过山车
         * content : 比特币行情过山车，投资者不知所措
         * createTime : 2018-10-15 17:42:37
         * createId : null
         * sendStatus : 0
         * sendTime : null
         * sendUserId : 0
         * pushStatus : 0
         * pushDate : null
         * pushUserId : null
         * pushUserName : null
         */

        private int id;
        private String title;
        private String type;
        private String summary;
        private String keywords;
        private String description;
        private String content;
        private String createTime;
        private String redirectUrl;
        private Object createId;
        private int sendStatus;
        private Object sendTime;
        private int sendUserId;
        private int pushStatus;
        private Object pushDate;
        private Object pushUserId;
        private Object pushUserName;

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getCreateId() {
            return createId;
        }

        public void setCreateId(Object createId) {
            this.createId = createId;
        }

        public int getSendStatus() {
            return sendStatus;
        }

        public void setSendStatus(int sendStatus) {
            this.sendStatus = sendStatus;
        }

        public Object getSendTime() {
            return sendTime;
        }

        public void setSendTime(Object sendTime) {
            this.sendTime = sendTime;
        }

        public int getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(int sendUserId) {
            this.sendUserId = sendUserId;
        }

        public int getPushStatus() {
            return pushStatus;
        }

        public void setPushStatus(int pushStatus) {
            this.pushStatus = pushStatus;
        }

        public Object getPushDate() {
            return pushDate;
        }

        public void setPushDate(Object pushDate) {
            this.pushDate = pushDate;
        }

        public Object getPushUserId() {
            return pushUserId;
        }

        public void setPushUserId(Object pushUserId) {
            this.pushUserId = pushUserId;
        }

        public Object getPushUserName() {
            return pushUserName;
        }

        public void setPushUserName(Object pushUserName) {
            this.pushUserName = pushUserName;
        }
    }
}

