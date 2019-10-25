package com.btetop.bean;

import android.text.TextUtils;

import java.util.List;

public class ShequListBean {

    /**
     * total : 0
     * postlist : [{"id":2,"type":1,"title":"","content":"2.发布内容：（1）底部引导语与首页输入框保持一致（2）输入内容不限制字数（3）点击发布后返回首页并返回\u201c系统审核后将发布在社区\u201d（4）输入框有内容时，点击返回需弹窗确认","shareCount":0,"likeCount":2,"commentCount":1,"newShare":0,"newLike":0,"newComment":0,"auditStatusId":1002,"userId":208,"status":1,"updateTime":"2018-10-31 15:33:34","createTime":"2018-10-30 15:20:22","hasLike":null,"userName":null,"postTime":"1天以前"},{"id":1,"type":1,"title":"","content":"一、功能概述：\n社区功能：仅支持文字的发表，用户发布内容后需后台审核后才可显示在app中。用户可对发布的所有内容进行点赞、评论、转发。\n\n二、功能详述\n社区主页功能点：\n\n  （1）输入框：引导文字需支持自定义，支持后台修改\n\n （2）对内容的点赞、评论、分享：数量均为0时，为第一条内容的样式。不为0时为第三条内容样式。用户本人点赞后，为第三条样式\n\n  （3）消息中心：有新消息时，消息中心和tab拦均需要标记未读消息数量，最多标记99。\n\n（4）未读消息：区分我发布的内容和我评论其他内容的消息。我发布的：点赞评论分享均标记未读。我评论的：只标记我评论题主给我的回复。","shareCount":2,"likeCount":2,"commentCount":1,"newShare":1,"newLike":0,"newComment":0,"auditStatusId":1002,"userId":208,"status":1,"updateTime":"2018-10-30 14:32:00","createTime":"2018-10-30 14:31:55","hasLike":null,"userName":null,"postTime":"1天以前"}]
     */

    private String introducer;


    private int total;
    private List<PostlistBean> postlist;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PostlistBean> getPostlist() {
        return postlist;
    }

    public String getIntroducer() {
        if (TextUtils.isEmpty(introducer)) {
            return "";
        }
        return introducer;
    }

    public void setPostlist(List<PostlistBean> postlist) {
        this.postlist = postlist;
    }

    public static class PostlistBean {
        /**
         * id : 2
         * type : 1
         * title :
         * content : 2.发布内容：（1）底部引导语与首页输入框保持一致（2）输入内容不限制字数（3）点击发布后返回首页并返回“系统审核后将发布在社区”（4）输入框有内容时，点击返回需弹窗确认
         * shareCount : 0
         * likeCount : 2
         * commentCount : 1
         * newShare : 0
         * newLike : 0
         * newComment : 0
         * auditStatusId : 1002
         * userId : 208
         * status : 1
         * updateTime : 2018-10-31 15:33:34
         * createTime : 2018-10-30 15:20:22
         * hasLike : null
         * userName : null
         * postTime : 1天以前
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
            String temp="分享";
            if (shareCount==0) {

            }else {
                temp=""+shareCount;

            }
            return temp;
        }

        public void addShareCount() {
            this.shareCount ++;
        }

        public void addLikeCount() {
            this.likeCount ++;
        }

        public void addCommentCount(){
            this.commentCount++;
        }

        public String getLikeCount() {
            String temp="点赞";
            if (likeCount==0) {

            }else {
                temp=""+likeCount;

            }
            return temp;
        }


        public String getCommentCount() {
            String temp="评论";
            if (commentCount==0) {

            }else {
                temp=""+commentCount;

            }
            return temp;
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

        public boolean getHasLike() {
            if (hasLike==0) {
               return false;
            }else {
               return true;
            }
        }

        public String getIcon() {
            return icon;
        }
        public void addHasLike(){
            this.hasLike++;
        }


        public String getUserName() {
            if (TextUtils.isEmpty(userName)) {
                return "";
            }
            return userName;
        }


        public String getPostTime() {
            return postTime;
        }

        public void setPostTime(String postTime) {
            this.postTime = postTime;
        }
    }
}
