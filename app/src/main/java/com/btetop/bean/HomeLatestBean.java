package com.btetop.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/21.
 */

public class HomeLatestBean {

    public static class HomeLatestsData implements Serializable {

        private ArrayList<CoinInfo> list;
        private HomeLatestsReport report;

        public HomeLatestsReport getReport() {
            return report;
        }

        public void setReport(HomeLatestsReport report) {
            this.report = report;
        }

        public ArrayList<CoinInfo> getList() {
            return list;
        }

        public void setList(ArrayList<CoinInfo> list) {
            this.list = list;
        }

        public static class HomeLatestsReport implements Serializable {
            private String id;
            private String summary;
            private String date;
            private String title;
            private String content;
            private String tag;
            private String pv;

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getPv() {
                return pv;
            }

            public void setPv(String pv) {
                this.pv = pv;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

        }

    }
}
