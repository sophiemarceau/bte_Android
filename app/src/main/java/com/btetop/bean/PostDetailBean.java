package com.btetop.bean;

import java.util.List;

public class PostDetailBean {
        /**
         * post : {"id":12,"type":1,"title":"","content":"sfsdsfjsldjkf","shareCount":8,"likeCount":4,"commentCount":1,"newShare":0,"newLike":0,"newComment":0,"auditStatusId":1002,"userId":40,"status":1,"updateTime":"2018-11-01 13:23:12","createTime":"2018-10-31 17:57:36","hasLike":1,"userName":"韭菜宝宝0579","postTime":"19小时以前"}
         * postReply : [{"id":6,"postId":12,"content":"您好！","read":1,"comment":1,"userId":112,"status":1,"createTime":"2018-11-01 13:23:08","userName":"韭菜宝宝7525","postReplyItemList":null,"icon":"http://bte-beijing.oss-cn-beijing.aliyuncs.com/user/image/defaulticon.png"}]
         */

        private PostBean post;
        private List<PostReplyBean> postReply;

        public PostBean getPost() {
            return post;
        }

        public void setPost(PostBean post) {
            this.post = post;
        }

        public List<PostReplyBean> getPostReply() {
            return postReply;
        }

        public void setPostReply(List<PostReplyBean> postReply) {
            this.postReply = postReply;
        }

        public static class PostBean {
            /**
             * id : 12
             * type : 1
             * title :
             * content : sfsdsfjsldjkf
             * shareCount : 8
             * likeCount : 4
             * commentCount : 1
             * newShare : 0
             * newLike : 0
             * newComment : 0
             * auditStatusId : 1002
             * userId : 40
             * status : 1
             * updateTime : 2018-11-01 13:23:12
             * createTime : 2018-10-31 17:57:36
             * hasLike : 1
             * userName : 韭菜宝宝0579
             * postTime : 19小时以前
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

            public String getIcon() {
                return icon;
            }
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

            public int getShareCount() {
                return shareCount;
            }

            public void setShareCount(int shareCount) {
                this.shareCount = shareCount;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public int getCommentCount() {
                return commentCount;
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

            public int getNewLike() {
                return newLike;
            }

            public void setNewLike(int newLike) {
                this.newLike = newLike;
            }

            public int getNewComment() {
                return newComment;
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

            public int getHasLike() {
                return hasLike;
            }

            public void setHasLike(int hasLike) {
                this.hasLike = hasLike;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPostTime() {
                return postTime;
            }

            public void setPostTime(String postTime) {
                this.postTime = postTime;
            }
        }

        public static class PostReplyBean {
            /**
             * id : 6
             * postId : 12
             * content : 您好！
             * read : 1
             * comment : 1
             * userId : 112
             * status : 1
             * createTime : 2018-11-01 13:23:08
             * userName : 韭菜宝宝7525
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
            private String userName;
            private List<PostReplyItemListBean> postReplyItemList;
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

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public List<PostReplyItemListBean> getPostReplyItemList() {
                return postReplyItemList;
            }

            public void setPostReplyItemList(List<PostReplyItemListBean> postReplyItemList) {
                this.postReplyItemList = postReplyItemList;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
    }

    public static class PostReplyItemListBean {
        /**
         * id : 51
         * postReplyId : 117
         * parentId : 0
         * content : Ttttt
         * read : 1
         * userId : 35
         * receiveUserId : 40
         * status : 1
         * createTime : 2018-11-07 15:16:30
         * postReplyItemId : 51
         * sendUserName :
         * receiveUserName :
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
