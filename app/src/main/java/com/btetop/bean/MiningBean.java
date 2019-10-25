package com.btetop.bean;

public class MiningBean {


    /**
     * task : {"feedback":5,"read":5,"bindWx":5,"sign":0,"reportStatus":0,"share":5,"invite":500,"reportUrl":"","followWx":5}
     * info : {"id":1,"userId":15,"userName":"19913141980","totalPower":37,"totalIncome":0,"status":1,"signStatus":1,"inviteCount":0,"openTime":"2018-10-31 10:14:02","digStartTime":null,
     * "digEndTime":null,"createTime":"2018-10-30 15:09:58"}
     */

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private TaskBean task;
    private InfoBean info;

    public TaskBean getTask() {
        return task;
    }

    public void setTask(TaskBean task) {
        this.task = task;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class TaskBean {
        /**
         * feedback : 5
         * read : 5
         * bindWx : 5
         * sign : 0
         * reportStatus : 0
         * share : 5
         * invite : 500
         * reportUrl :
         * followWx : 5
         */

        private int feedback;
        private int read;
        private int bindWx;
        private int sign;
        private int reportStatus;
        private int share;
        private int invite;
        private String reportUrl;
        private int followWx;

        public int getFeedback() {
            return feedback;
        }

        public void setFeedback(int feedback) {
            this.feedback = feedback;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public int getBindWx() {
            return bindWx;
        }

        public void setBindWx(int bindWx) {
            this.bindWx = bindWx;
        }

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public int getReportStatus() {
            return reportStatus;
        }

        public void setReportStatus(int reportStatus) {
            this.reportStatus = reportStatus;
        }

        public int getShare() {
            return share;
        }

        public void setShare(int share) {
            this.share = share;
        }

        public int getInvite() {
            return invite;
        }

        public void setInvite(int invite) {
            this.invite = invite;
        }

        public String getReportUrl() {
            return reportUrl;
        }

        public void setReportUrl(String reportUrl) {
            this.reportUrl = reportUrl;
        }

        public int getFollowWx() {
            return followWx;
        }

        public void setFollowWx(int followWx) {
            this.followWx = followWx;
        }
    }

    public static class InfoBean {
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
        private int totalPower;
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

        public int getTotalPower() {
            return totalPower;
        }

        public void setTotalPower(int totalPower) {
            this.totalPower = totalPower;
        }

        public String getTotalIncome() {
            return  totalIncome;
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
}
