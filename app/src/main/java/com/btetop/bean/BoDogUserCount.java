package com.btetop.bean;

import java.util.List;

public class BoDogUserCount {
        /**
         * userCount : 100
         * baseList : [{"id":11,"exchange":"okex","base":"BTC","quote":"USDT","status":1,"type":"band_dog","createrId":1,"price":6484.7,"createTime":"2018-06-26 16:02:42"},{"id":12,
         * "exchange":"okex","base":"ETH","quote":"USDT","status":1,"type":"band_dog","createrId":1,"price":459.7779,"createTime":"2018-06-26 16:03:35"},{"id":13,"exchange":"okex","base":"BCH",
         * "quote":"USDT","status":1,"type":"band_dog","createrId":1,"price":754.8749,"createTime":"2018-06-26 16:04:06"},{"id":14,"exchange":"okex","base":"EOS","quote":"USDT","status":1,
         * "type":"band_dog","createrId":1,"price":8.7148,"createTime":"2018-06-26 16:04:39"}]
         */

        private int userCount;
        private List<BaseListBean> baseList;

        public int getUserCount() {
            return userCount;
        }

        public void setUserCount(int userCount) {
            this.userCount = userCount;
        }

        public List<BaseListBean> getBaseList() {
            return baseList;
        }

        public void setBaseList(List<BaseListBean> baseList) {
            this.baseList = baseList;
        }

        public static class BaseListBean {
            /**
             * id : 11
             * exchange : okex
             * base : BTC
             * quote : USDT
             * status : 1
             * type : band_dog
             * createrId : 1
             * price : 6484.7
             * createTime : 2018-06-26 16:02:42
             */

            private int id;
            private String exchange;
            private String base;
            private String quote;
            private int status;
            private String type;
            private int createrId;
            private double price;
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

            public String getBase() {
                return base;
            }

            public void setBase(String base) {
                this.base = base;
            }

            public String getQuote() {
                return quote;
            }

            public void setQuote(String quote) {
                this.quote = quote;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getCreaterId() {
                return createrId;
            }

            public void setCreaterId(int createrId) {
                this.createrId = createrId;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : 48
     */


