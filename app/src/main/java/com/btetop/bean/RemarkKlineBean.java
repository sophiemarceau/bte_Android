package com.btetop.bean;

import java.util.List;

public class RemarkKlineBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * exchange : 上海交易所
         * symbol : btc
         * pair : 比特币
         * klineDateTime : 1528440091109
         * klineDate : 2018-06-08 14:41:31
         * content : 今日大涨
         * status : 1
         * createrId : null
         * createTime : 2018-06-08 14:41:47
         */

        private int id;
        private String exchange;
        private String symbol;
        private String pair;
        private long klineDateTime;
        private String klineDate;
        private String content;
        private int status;
        private Object createrId;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getPair() {
            return pair;
        }

        public void setPair(String pair) {
            this.pair = pair;
        }

        public long getKlineDateTime() {
            return klineDateTime;
        }

        public void setKlineDateTime(long klineDateTime) {
            this.klineDateTime = klineDateTime;
        }

        public String getKlineDate() {
            return klineDate;
        }

        public void setKlineDate(String klineDate) {
            this.klineDate = klineDate;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getCreaterId() {
            return createrId;
        }

        public void setCreaterId(Object createrId) {
            this.createrId = createrId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
