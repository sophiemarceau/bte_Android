package com.btetop.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ouyou on 2018/5/9.
 */

public class HomeNoticeBean {

    public static class HomeNoticeData implements Serializable {
        private String id;
        private String title;

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
    }
}
