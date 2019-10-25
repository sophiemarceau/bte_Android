package com.btetop.bean;

import java.util.List;

public class CommentBean {
    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : {"myCommnetCount":0,"myReleaseCount":0,"nextPage":-1,"myComment":[{"id":123,"postReplyId":117,"parentId":0,"content":"继续回复吧","read":1,"userId":40,"receiveUserId":40,"status":1,"createTime":"2018-11-13 15:04:44","postReplyItemId":123,"sendUserName":"韭菜宝宝0579","receiveUserName":"你","lastReplyContent":"6666","postId":52},{"id":67,"postReplyId":117,"parentId":0,"content":"ghhh","read":1,"userId":66,"receiveUserId":40,"status":1,"createTime":"2018-11-08 11:55:17","postReplyItemId":67,"sendUserName":"韭菜宝宝3163","receiveUserName":"你","lastReplyContent":"6666","postId":52},{"id":51,"postReplyId":117,"parentId":0,"content":"Ttttt","read":1,"userId":35,"receiveUserId":40,"status":1,"createTime":"2018-11-07 15:16:30","postReplyItemId":51,"sendUserName":"韭菜宝宝4765","receiveUserName":"你","lastReplyContent":"6666","postId":52},{"id":14,"postReplyId":29,"parentId":0,"content":"123","read":1,"userId":208,"receiveUserId":40,"status":1,"createTime":"2018-11-06 13:33:15","postReplyItemId":14,"sendUserName":"韭菜宝宝8325","receiveUserName":"你","lastReplyContent":"2.发布内容：（1）底部引导语与首页输入框保持一致（2）输入内容不限制字数（3）点击发布后返回首页并返回\u201c系统审核后将发布在社区\u201d（4）输入框有内容时，点击返回需弹窗确认","postId":8}]}
     */

    /**
     * myCommnetCount : 0 myReleaseCount : 0 nextPage : -1 myComment :
     * [{"id":123,"postReplyId":117,"parentId":0,"content":"继续回复吧","read":1,"userId":40,"receiveUserId":40,"status":1,"createTime":"2018-11-13
     * 15:04:44","postReplyItemId":123,"sendUserName":"韭菜宝宝0579","receiveUserName":"你","lastReplyContent":"6666","postId":52},{"id":67,"postReplyId":117,"parentId":0,"content":"ghhh","read":1,"userId":66,"receiveUserId":40,"status":1,"createTime":"2018-11-08
     * 11:55:17","postReplyItemId":67,"sendUserName":"韭菜宝宝3163","receiveUserName":"你","lastReplyContent":"6666","postId":52},{"id":51,"postReplyId":117,"parentId":0,"content":"Ttttt","read":1,"userId":35,"receiveUserId":40,"status":1,"createTime":"2018-11-07
     * 15:16:30","postReplyItemId":51,"sendUserName":"韭菜宝宝4765","receiveUserName":"你","lastReplyContent":"6666","postId":52},{"id":14,"postReplyId":29,"parentId":0,"content":"123","read":1,"userId":208,"receiveUserId":40,"status":1,"createTime":"2018-11-06
     * 13:33:15","postReplyItemId":14,"sendUserName":"韭菜宝宝8325","receiveUserName":"你","lastReplyContent":"2.发布内容：（1）底部引导语与首页输入框保持一致（2）输入内容不限制字数（3）点击发布后返回首页并返回\u201c系统审核后将发布在社区\u201d（4）输入框有内容时，点击返回需弹窗确认","postId":8}]
     */

    private int myCommnetCount;
    private int myReleaseCount;
    private int nextPage;
    private List<MyCommentBean> myComment;

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

    public List<MyCommentBean> getMyComment() {
        return myComment;
    }

    public void setMyComment(List<MyCommentBean> myComment) {
        this.myComment = myComment;
    }

    public static class MyCommentBean {
        /**
         * id : 123 postReplyId : 117 parentId : 0 content : 继续回复吧 read : 1 userId : 40
         * receiveUserId : 40 status : 1 createTime : 2018-11-13 15:04:44 postReplyItemId : 123
         * sendUserName : 韭菜宝宝0579 receiveUserName : 你 lastReplyContent : 6666 postId : 52
         */

        private int id;
        private int postReplyId;
        private int parentId;
        private String content;
        private int read;
        private int userId;
        private int receiveUserId;
        private int status;
        private String createTime;
        private int postReplyItemId;
        private String sendUserName;
        private String receiveUserName;
        private String lastReplyContent;
        private int postId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPostReplyId() {
            return postReplyId;
        }

        public void setPostReplyId(int postReplyId) {
            this.postReplyId = postReplyId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getReceiveUserId() {
            return receiveUserId;
        }

        public void setReceiveUserId(int receiveUserId) {
            this.receiveUserId = receiveUserId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getPostReplyItemId() {
            return postReplyItemId;
        }

        public void setPostReplyItemId(int postReplyItemId) {
            this.postReplyItemId = postReplyItemId;
        }

        public String getSendUserName() {
            return sendUserName;
        }

        public void setSendUserName(String sendUserName) {
            this.sendUserName = sendUserName;
        }

        public String getReceiveUserName() {
            return receiveUserName;
        }

        public void setReceiveUserName(String receiveUserName) {
            this.receiveUserName = receiveUserName;
        }

        public String getLastReplyContent() {
            return lastReplyContent;
        }

        public void setLastReplyContent(String lastReplyContent) {
            this.lastReplyContent = lastReplyContent;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }
    }
}
