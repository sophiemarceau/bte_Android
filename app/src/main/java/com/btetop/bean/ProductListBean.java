package com.btetop.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ProductListBean {

    public static class ProductListData implements Serializable {
        private int nextPage;
        private ArrayList<ProductListDetails> details;

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public ArrayList<ProductListDetails> getDetails() {
            return details;
        }

        public void setDetails(ArrayList<ProductListDetails> details) {
            this.details = details;
        }

        public static class ProductListDetails implements Serializable {
            private int id;
            private String name;
            private String type;
            private String desc;
            private int period;
            private int status;
            private int riskValue;
            private int riskLevel;
            private double ror;
            private int userCount;
            private double amount;
            private double dayMaxIncrease;
            private double weekMaxIncrease;
            private double monthMaxIncrease;
            private int requestTimes;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getPeriod() {
                return period;
            }

            public void setPeriod(int period) {
                this.period = period;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getRiskValue() {
                return riskValue;
            }

            public void setRiskValue(int riskValue) {
                this.riskValue = riskValue;
            }

            public int getRiskLevel() {
                return riskLevel;
            }

            public void setRiskLevel(int riskLevel) {
                this.riskLevel = riskLevel;
            }

            public double getRor() {
                return ror;
            }

            public void setRor(double ror) {
                this.ror = ror;
            }

            public int getUserCount() {
                return userCount;
            }

            public void setUserCount(int userCount) {
                this.userCount = userCount;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public double getDayMaxIncrease() {
                return dayMaxIncrease;
            }

            public void setDayMaxIncrease(double dayMaxIncrease) {
                this.dayMaxIncrease = dayMaxIncrease;
            }

            public double getWeekMaxIncrease() {
                return weekMaxIncrease;
            }

            public void setWeekMaxIncrease(double weekMaxIncrease) {
                this.weekMaxIncrease = weekMaxIncrease;
            }

            public double getMonthMaxIncrease() {
                return monthMaxIncrease;
            }

            public void setMonthMaxIncrease(double monthMaxIncrease) {
                this.monthMaxIncrease = monthMaxIncrease;
            }

            public int getRequestTimes() {
                return requestTimes;
            }

            public void setRequestTimes(int requestTimes) {
                this.requestTimes = requestTimes;
            }
        }
    }
}
