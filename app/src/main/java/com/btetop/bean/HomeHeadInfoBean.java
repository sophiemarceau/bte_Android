package com.btetop.bean;

public class HomeHeadInfoBean {

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : {"name":"global","code":"global","logo":"","airIndex":24.96,"amount":3.056895177069E10,"amountChange":2.3521,"highestRisk":0.2001,"higherRisk":0.492,"lowerRisk":0.3077,"lowestRisk":0,
     * "netAmount":-9.971371929E7,"netAmountChange":-3.4917}
     */

        /**
         * name : global
         * code : global
         * logo :
         * airIndex : 24.96
         * amount : 3.056895177069E10
         * amountChange : 2.3521
         * highestRisk : 0.2001
         * higherRisk : 0.492
         * lowerRisk : 0.3077
         * lowestRisk : 0
         * netAmount : -9.971371929E7
         * netAmountChange : -3.4917
         */

        private String name;
        private String code;
        private String logo;
        private double airIndex;
        private double amount;
        private double amountChange;
        private double highestRisk;
        private double higherRisk;
        private double lowerRisk;
        private int lowestRisk;
        private double netAmount;
        private double netAmountChange;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public double getAirIndex() {
            return airIndex;
        }

        public void setAirIndex(double airIndex) {
            this.airIndex = airIndex;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getAmountChange() {
            return amountChange;
        }

        public void setAmountChange(double amountChange) {
            this.amountChange = amountChange;
        }

        public double getHighestRisk() {
            return highestRisk;
        }

        public void setHighestRisk(double highestRisk) {
            this.highestRisk = highestRisk;
        }

        public double getHigherRisk() {
            return higherRisk;
        }

        public void setHigherRisk(double higherRisk) {
            this.higherRisk = higherRisk;
        }

        public double getLowerRisk() {
            return lowerRisk;
        }

        public void setLowerRisk(double lowerRisk) {
            this.lowerRisk = lowerRisk;
        }

        public int getLowestRisk() {
            return lowestRisk;
        }

        public void setLowestRisk(int lowestRisk) {
            this.lowestRisk = lowestRisk;
        }

        public double getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(double netAmount) {
            this.netAmount = netAmount;
        }

        public double getNetAmountChange() {
            return netAmountChange;
        }

        public void setNetAmountChange(double netAmountChange) {
            this.netAmountChange = netAmountChange;
        }
    }
