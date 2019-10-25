package com.btetop.bean;

/**
 * 合约狗 异常订单
 */
public class AbnormalOrderBean {

    /**
     * orders : [{"count":316465,"orderType":"support","datetime":1533265874079,"price":7400},{"count":316465,"orderType":"cancel","datetime":1533265874079,"price":7400}]
     * datetime : 1533265860000
     */

        /**
         * count : 316465
         * orderType : support
         * datetime : 1533265874079
         * price : 7400
         */

        private int count;
        private String orderType;
        private long datetime;
        private double price;

        public int getCount() {
            return count;
        }

        public int getNeagtiveCount(){
            return 0-count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public long getDatetime() {
            return datetime;
        }

        public void setDatetime(long datetime) {
            this.datetime = datetime;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
}
