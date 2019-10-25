package com.btetop.bean;

import java.util.List;

public class MyReleaseBeanOuter {

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : {"myCommnetCount":0,"myRelease":[{"id":12,"type":1,"title":"","content":"sfsdsfjsldjkf","shareCount":9,"likeCount":10,"commentCount":1,"newShare":0,"newLike":0,"newComment":0,"auditStatusId":1002,"userId":40,"status":1,"updateTime":"2018-11-06 15:25:56","createTime":"2018-10-31 17:57:36","hasLike":1,"userName":null,"postTime":"18天以前","icon":"http://bte-beijing.oss-cn-beijing.aliyuncs.com/user/image/defaulticon.png"},{"id":11,"type":1,"title":"","content":"sdfsdfsdfsdfsadfsdf","shareCount":0,"likeCount":9,"commentCount":1,"newShare":0,"newLike":0,"newComment":0,"auditStatusId":1002,"userId":40,"status":1,"updateTime":"2018-11-09 14:48:40","createTime":"2018-10-31 17:57:12","hasLike":1,"userName":null,"postTime":"18天以前","icon":"http://bte-beijing.oss-cn-beijing.aliyuncs.com/user/image/defaulticon.png"}],"myReleaseCount":0,"nextPage":-1}
     */


    /**
     * myCommnetCount : 0 myRelease : [{"id":12,"type":1,"title":"","content":"sfsdsfjsldjkf","shareCount":9,"likeCount":10,"commentCount":1,"newShare":0,"newLike":0,"newComment":0,"auditStatusId":1002,"userId":40,"status":1,"updateTime":"2018-11-06
     * 15:25:56","createTime":"2018-10-31 17:57:36","hasLike":1,"userName":null,"postTime":"18天以前","icon":"http://bte-beijing.oss-cn-beijing.aliyuncs.com/user/image/defaulticon.png"},{"id":11,"type":1,"title":"","content":"sdfsdfsdfsdfsadfsdf","shareCount":0,"likeCount":9,"commentCount":1,"newShare":0,"newLike":0,"newComment":0,"auditStatusId":1002,"userId":40,"status":1,"updateTime":"2018-11-09
     * 14:48:40","createTime":"2018-10-31 17:57:12","hasLike":1,"userName":null,"postTime":"18天以前","icon":"http://bte-beijing.oss-cn-beijing.aliyuncs.com/user/image/defaulticon.png"}]
     * myReleaseCount : 0 nextPage : -1
     */

    private int myCommnetCount;
    private int myReleaseCount;
    private int nextPage;
    private List<MyReleaseBean> myRelease;

    public int getMyCommnetCount() {
        return myCommnetCount;
    }

    public void setMyCommnetCount(int myCommnetCount) {
        this.myCommnetCount = myCommnetCount;
    }

    public int getMyReleaseCount() {
        return myReleaseCount;
    }

    public void setMyReleaseCount(int myReleaseCount) {
        this.myReleaseCount = myReleaseCount;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<MyReleaseBean> getMyRelease() {
        return myRelease;
    }

    public void setMyRelease(List<MyReleaseBean> myRelease) {
        this.myRelease = myRelease;
    }

    public static class MyReleaseBean {
        /**
         * id : 12 type : 1 title : content : sfsdsfjsldjkf shareCount : 9 likeCount : 10
         * commentCount : 1 newShare : 0 newLike : 0 newComment : 0 auditStatusId : 1002 userId : 40
         * status : 1 updateTime : 2018-11-06 15:25:56 createTime : 2018-10-31 17:57:36 hasLike : 1
         * userName : null postTime : 18天以前 icon : http://bte-beijing.oss-cn-beijing.aliyuncs.com/user/image/defaulticon.png
         */

        private int id;
        private int type;
        private String title;
        private String content;
        private int shareCount;
        private int likeCount;
        private int commentCount;
        private int newShare;
        private int newLike;
        private int newComment;
        private int auditStatusId;
        private int userId;
        private int status;
        private String updateTime;
        private String createTime;
        private int hasLike;
        private String userName;
        private String postTime;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getShareCount() {
            return shareCount+"";
        }
        public void addShareCount(){
            shareCount++;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

        public String getLikeCount() {
            return likeCount+"";
        }

        public void addLikeCount(){
            likeCount++;
        }

        public void addHasLike(){
            hasLike=1;
        }
        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getCommentCount() {
            return commentCount+"";
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getNewShare() {
            return newShare;
        }

        public void setNewShare(int newShare) {
            this.newShare = newShare;
        }

        public boolean isShowNewLike(){
            if (newLike==0) {
                return false;
            }else {
               return true;
            }
        }
        public String getNewLike() {
            return newLike+"";
        }

        public void setNewLike(int newLike) {
            this.newLike = newLike;
        }

        public String getNewComment() {
            return newComment+"";
        }


        public boolean isShowNewComment(){
            if (newComment==0) {
                return false;
            }else {
               return true;
            }
        }

        public void setNewComment(int newComment) {
            this.newComment = newComment;
        }

        public int getAuditStatusId() {
            return auditStatusId;
        }

        public void setAuditStatusId(int auditStatusId) {
            this.auditStatusId = auditStatusId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean getHasLike() {
            if (hasLike==1) {
               return true;
            }else {
                return false;
            }
        }

        public void setHasLike(int hasLike) {
            this.hasLike = hasLike;
        }

        public String getUserName() {
            return userName==null?"":userName;
        }


        public String getPostTime() {
            return postTime;
        }

        public void setPostTime(String postTime) {
            this.postTime = postTime;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
