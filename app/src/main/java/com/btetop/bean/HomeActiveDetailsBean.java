package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by ouyou on 2018/5/10.
 */

public class HomeActiveDetailsBean {

    public static class HomeActiveDetailsData implements Serializable {
        /**
         * "id": 1,
         * "title": "测试活动",
         * "summary": "测试活动测试活动1111",
         * "content": "<p>222222222</p>",
         * "keywords": "1111",
         * "description": "1111",
         * "image": "http://bte-hk.oss-cn-hongkong.aliyuncs.com/bte/image/1525861996736.png",
         * "url": "https://github.com/",
         * "begin": "2018-05-08 11:23:21",
         * "end": "2018-05-11 11:23:24",
         * "status": 1,
         * "createTime": "2018-05-10 09:54:28"
         */

        private String id;
        private String title;
        private String summary;
        private String content;
        private String keywords;
        private String description;
        private String image;
        private String url;
        private String begin;
        private String status;
        private String createTime;
        private String shareUrl;

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBegin() {
            return begin;
        }

        public void setBegin(String begin) {
            this.begin = begin;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
