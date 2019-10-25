package com.btetop.bean;

public class HXUserInfo {

    /**
     * result : {"id":35,"userId":"143","password":"bteLttEOj5Q","nickName":"ban","headImage":"https://file.bte.top/common/avatar/6.png","createTime":"2018-07-23 16:29:15"}
     * groupid : 55236878204929
     */

    private ResultBean result;
    private String groupid;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public static class ResultBean {
        /**
         * id : 35
         * userId : 143
         * password : bteLttEOj5Q
         * nickName : ban
         * headImage : https://file.bte.top/common/avatar/6.png
         * createTime : 2018-07-23 16:29:15
         */

        private int id;
        private String userId;
        private String password;
        private String nickName;
        private String headImage;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
