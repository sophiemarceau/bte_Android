package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/25.
 */

public class MineStrategyDetailBean {

    public static class MineStrategyDetailData implements Serializable {
        /**
         * "productId": 15,
         * "productName": "BTC基金一期",
         * "payAmount": 100000,
         * "realAmount": 99999,
         * "fee": 1,
         * "dateTime": "2018-04-24 18:42:55",
         * "sn": null,
         * "status": "已确认",
         * "assetType": "BTC",
         * "units": 100,
         * "netValue": 2,
         * "confirmTime": "2018-04-24 18:42:59"
         */
        private String productId;//产品Id
        private String productName;//名称
        private double payAmount;//付款金额
        private double realAmount;//实际金额
        private double currentAmount;//跟随本金
        private double redeemAmount;//赎回本金

        public double getCurrentAmount() {
            return currentAmount;
        }

        public void setCurrentAmount(double currentAmount) {
            this.currentAmount = currentAmount;
        }

        public double getRedeemAmount() {
            return redeemAmount;
        }

        public void setRedeemAmount(double redeemAmount) {
            this.redeemAmount = redeemAmount;
        }

        public double getRor() {
            return ror;
        }

        public void setRor(double ror) {
            this.ror = ror;
        }

        private double fee;//费率
        private double ror;//收益率
        private String dateTime;//时间
        private String sn;//流水号
        private String status;//状态
        private String assetType;//货币类型
        private double units;//份额
        private double netValue;//份额
        private String confirmTime;//确认时间

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public double getNetValue() {
            return netValue;
        }

        public void setNetValue(double netValue) {
            this.netValue = netValue;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public double getRealAmount() {
            return realAmount;
        }

        public void setRealAmount(double realAmount) {
            this.realAmount = realAmount;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAssetType() {
            return assetType;
        }

        public void setAssetType(String assetType) {
            this.assetType = assetType;
        }

        public double getUnits() {
            return units;
        }

        public void setUnits(double units) {
            this.units = units;
        }
    }
}
