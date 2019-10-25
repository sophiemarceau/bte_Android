package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by ouyou on 2018/5/9.
 */

public class HomeActiveBean {

    public static class HomeActiveData implements Serializable {
        private String image;//活动图地址
        private String url;//跳转的地址
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
