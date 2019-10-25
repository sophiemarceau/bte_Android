package com.btetop.bean;

public class KlineVolumeBean {

        /**
         * datetime : 1533272400000
         * buyCount : 252438
         * sellCount : 201164
         * netCount : 51274
         * buyAmount : 1.88338675612E9
         * sellAmount : 1.50082360924E9
         * netAmount : 3.8256314688E8
         */

        private long datetime;
        private int buyCount;
        private int sellCount;
        private int netCount;
        private double buyAmount;
        private double sellAmount;
        private double netAmount;

        public long getDatetime() {
            return datetime;
        }

        public void setDatetime(long datetime) {
            this.datetime = datetime;
        }

        public int getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(int buyCount) {
            this.buyCount = buyCount;
        }

        public int getSellCount() {
            return sellCount;
        }

        public void setSellCount(int sellCount) {
            this.sellCount = sellCount;
        }

        public int getNetCount() {
            return netCount;
        }

        public void setNetCount(int netCount) {
            this.netCount = netCount;
        }

        public double getBuyAmount() {
            return buyAmount;
        }

        public void setBuyAmount(double buyAmount) {
            this.buyAmount = buyAmount;
        }

        public double getSellAmount() {
            return sellAmount;
        }

        public void setSellAmount(double sellAmount) {
            this.sellAmount = sellAmount;
        }

        public double getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(double netAmount) {
            this.netAmount = netAmount;
        }
}
