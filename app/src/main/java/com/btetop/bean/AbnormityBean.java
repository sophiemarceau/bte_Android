package com.btetop.bean;

import com.blankj.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AbnormityBean {

        /**
         * price : 7768.92
         * direction : 2
         * directionName : sell
         * count : 10654
         * directionDesc : 空单
         * statusDesc : 成交
         * datetime : 1533114563
         * date : 2018-08-01 17:09:23
         */

        private double price;
        private int direction;
        private String directionName;
        private int count;
        private String directionDesc;
        private String statusDesc;

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    private long datetime;
        private String date;

        public String getPrice() {
            return ""+price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public String getDirectionName() {
            return directionName;
        }

        public void setDirectionName(String directionName) {
            this.directionName = directionName;
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

    public String getTime1String() {
            DateFormat format=new SimpleDateFormat("HH:mm");
            return TimeUtils.millis2String(datetime,format);
        }

    public String getTime2String() {
        DateFormat format = new SimpleDateFormat("MM/dd");
        return TimeUtils.millis2String(datetime, format);
    }
        public long getDatetime(){
            return datetime;
        }


        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
}
