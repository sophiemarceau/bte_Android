package com.btetop.bean;

import com.blankj.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class BurnedBean {

        /**
         * contractType : quarter
         * price : 6918.97
         * count : 11608
         * directionDesc : 卖出平多
         * statusDesc : 全部成交
         * datetime : 1533396320000
         * date : 2018-08-04 23:25:20
         * direction : sell
         */

        private String contractType;
        private double price;
        private int count;
        private String directionDesc;
        private String statusDesc;
        private long datetime;
        private String date;
        private String direction;

        public String getContractType() {
            return contractType;
        }

        public void setContractType(String contractType) {
            this.contractType = contractType;
        }

        public String getPrice() {
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            return decimalFormat.format(price);
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDirectionDesc() {
            return directionDesc;
        }

        public void setDirectionDesc(String directionDesc) {
            this.directionDesc = directionDesc;
        }

        public String getStatusDesc() {
            return statusDesc;
        }

        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }

        public long getDatetime() {
            return datetime;
        }

        public void setDatetime(long datetime) {
            this.datetime = datetime;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }


    public String getTimeString() {
        DateFormat format=new SimpleDateFormat("HH:mm");
        return TimeUtils.millis2String(datetime,format);
    }

    public String getTimeString1() {
        DateFormat format = new SimpleDateFormat("MM/dd");
        return TimeUtils.millis2String(datetime, format);
    }
}
