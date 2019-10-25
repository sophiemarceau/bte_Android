package com.btetop.bean;

import java.util.List;

public class ReplyListBean {

    /**
     * nextPage : -1
     * postReplyItemlist : [{"id":68,"postReplyId":117,"parentId":66,"content":"在一块儿","read":1,"userId":66,"receiveUserId":66,"status":1,"createTime":"2018-11-08 11:56:31","postReplyItemId":68,"sendUserName":"韭菜宝宝3163","receiveUserName":"韭菜宝宝3163","lastReplyContent":null,"postId":null},{"id":67,"postReplyId":117,"parentId":0,"content":"ghhh","read":1,"userId":66,"receiveUserId":40,"status":1,"createTime":"2018-11-08 11:55:17","postReplyItemId":67,"sendUserName":"韭菜宝宝3163","receiveUserName":"韭菜宝宝0579","lastReplyContent":null,"postId":null},{"id":66,"postReplyId":117,"parentId":51,"content":"gghg","read":1,"userId":66,"receiveUserId":35,"status":1,"createTime":"2018-11-08 11:55:07","postReplyItemId":66,"sendUserName":"韭菜宝宝3163","receiveUserName":"韭菜宝宝4765","lastReplyContent":null,"postId":null},{"id":51,"postReplyId":117,"parentId":0,"content":"Ttttt","read":1,"userId":35,"receiveUserId":40,"status":1,"createTime":"2018-11-07 15:16:30","postReplyItemId":51,"sendUserName":"韭菜宝宝4765","receiveUserName":"韭菜宝宝0579","lastReplyContent":null,"postId":null}]
     * postReply : {"id":117,"postId":52,"content":"8888","read":1,"comment":1,"userId":40,"status":1,"createTime":"2018-11-07 11:03:15","replyPostId":117,"userName":"韭菜宝宝0579","postReplyItemList":null,"icon":"http://bte-beijing.oss-cn-beijing.aliyuncs.com/user/image/defaulticon.png"}
     */

    private int nextPage;
    private PostReplyBean postReply;
    private List<PostReplyItemlistBean> postReplyItemlist;

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public PostReplyBean getPostReply() {
        return postReply;
    }

    public void setPostReply(PostReplyBean postReply) {
        this.postReply = postReply;
    }

    public List<PostReplyItemlistBean> getPostReplyItemlist() {
        return postReplyItemlist;
    }

    public void setPostReplyItemlist(List<PostReplyItemlistBean> postReplyItemlist) {
        this.postReplyItemlist = postReplyItemlist;
    }

    public static class PostReplyBean {
        /**
         * id : 117
         * postId : 52
         * content : 8888
         * read : 1
         * comment : 1
         * userId : 40
         * status : 1
         * createTime : 2018-11-07 11:03:15
         * replyPostId : 117
         * userName : 韭菜宝宝0579
         * postReplyItemList : null
         * icon : http://bte-beijing.oss-cn-beijing.aliyuncs.com/user/image/defaulticon.png
         */

        private int id;
        private int postId;
        private String content;
        private int read;
        private int comment;
        private int userId;
        private int status;
        private String createTime;
        private int replyPostId;
        private String userName;
        private Object postReplyItemList;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
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

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getReplyPostId() {
            return replyPostId;
        }

        public void setReplyPostId(int replyPostId) {
            this.replyPostId = replyPostId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getPostReplyItemList() {
            return postReplyItemList;
        }

        public void setPostReplyItemList(Object postReplyItemList) {
            this.postReplyItemList = postReplyItemList;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class PostReplyItemlistBean {
        /**
         * id : 68
         * postReplyId : 117
         * parentId : 66
         * content : 在一块儿
         * read : 1
         * userId : 66
         * receiveUserId : 66
         * status : 1
         * createTime : 2018-11-08 11:56:31
         * postReplyItemId : 68
         * sendUserName : 韭菜宝宝3163
         * receiveUserName : 韭菜宝宝3163
         * lastReplyContent : null
         * postId : null
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
        private Object lastReplyContent;
        private Object postId;

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

        public Object getLastReplyContent() {
            return lastReplyContent;
        }

        public void setLastReplyContent(Object lastReplyContent) {
            this.lastReplyContent = lastReplyContent;
        }

        public Object getPostId() {
            return postId;
        }

        public void setPostId(Object postId) {
            this.postId = postId;
        }
    }
}
