package com.hyphenate.easeui.net;

public class HXUserInfo {


    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : {"result":{"id":3,"userId":"112","password":"bte3dKxIaxc","nickName":"promise","headImage":"https://file.bte.top/common/avatar/1.png","createTime":"2018-07-19 18:40:03"}}
     */

    private String code;
    private String message;
    private String userToken;
    private DataBean data;

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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * result : {"id":3,"userId":"112","password":"bte3dKxIaxc","nickName":"promise","headImage":"https://file.bte.top/common/avatar/1.png","createTime":"2018-07-19 18:40:03"}
         */

        private ResultBean result;

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * id : 3
             * userId : 112
             * password : bte3dKxIaxc
             * nickName : promise
             * headImage : https://file.bte.top/common/avatar/1.png
             * createTime : 2018-07-19 18:40:03
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
}
