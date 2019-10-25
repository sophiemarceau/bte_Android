package com.btetop.bean;

public class MiningSignBean {

        /**
         * id : 1
         * userId : 15
         * userName : 19913141980
         * totalPower : 37
         * totalIncome : 0
         * status : 1
         * signStatus : 1
         * inviteCount : 0
         * openTime : 2018-10-31 10:14:02
         * digStartTime : null
         * digEndTime : null
         * createTime : 2018-10-30 15:09:58
         */

        private int id;
        private int userId;
        private String userName;
        private String totalPower;
        private String totalIncome;
        private int status;
        private int signStatus;
        private int inviteCount;
        private String openTime;
        private Object digStartTime;
        private Object digEndTime;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTotalPower() {
            return totalPower;
        }

        public void setTotalPower(String totalPower) {
            this.totalPower = totalPower;
        }

        public String getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(String totalIncome) {
            this.totalIncome = totalIncome;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(int signStatus) {
            this.signStatus = signStatus;
        }

        public int getInviteCount() {
            return inviteCount;
        }

        public void setInviteCount(int inviteCount) {
            this.inviteCount = inviteCount;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public Object getDigStartTime() {
            return digStartTime;
        }

        public void setDigStartTime(Object digStartTime) {
            this.digStartTime = digStartTime;
        }

        public Object getDigEndTime() {
            return digEndTime;
        }

        public void setDigEndTime(Object digEndTime) {
            this.digEndTime = digEndTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
}
