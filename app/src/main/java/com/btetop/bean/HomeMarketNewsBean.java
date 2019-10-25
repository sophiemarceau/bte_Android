package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/22.
 */

public class HomeMarketNewsBean {

    public static class HomeMarketNewsData implements Serializable {
        private int id;
        private String title;
        private String content;
        private String date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
