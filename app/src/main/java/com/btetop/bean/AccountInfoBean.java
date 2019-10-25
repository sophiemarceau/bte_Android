package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/13.
 */

public class AccountInfoBean {

    public static class AccountInfoData implements Serializable {
        private double allAmount;
        private String tel;
        private String point;//积分
        private String friends;//邀请好友个数
        private String holdCount;//邀请好友个数
        private String settleCount;//邀请好友个数
        private double totalAsset;//资产

        public double getTotalAsset() {
            return totalAsset;
        }

        public void setTotalAsset(double totalAsset) {
            this.totalAsset = totalAsset;
        }

        public String getHoldCount() {
            return holdCount;
        }

        public void setHoldCount(String holdCount) {
            this.holdCount = holdCount;
        }

        public String getSettleCount() {
            return settleCount;
        }

        public void setSettleCount(String settleCount) {
            this.settleCount = settleCount;
        }

        public String getFriends() {
            return friends;
        }

        public void setFriends(String friends) {
            this.friends = friends;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        private AccountInfo legalAccount;//币种
        private AccountInfo btcAccount;//btc

        public double getAllAmount() {
            return allAmount;
        }

        public void setAllAmount(double allAmount) {
            this.allAmount = allAmount;
        }

        public AccountInfo getLegalAccount() {
            return legalAccount;
        }

        public void setLegalAccount(AccountInfo legalAccount) {
            this.legalAccount = legalAccount;
        }

        public AccountInfo getBtcAccount() {
            return btcAccount;
        }

        public void setBtcAccount(AccountInfo btcAccount) {
            this.btcAccount = btcAccount;
        }

        public static class AccountInfo implements Serializable {
            private String type;
            private double balance;
            private double legalBalance;//金额

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public double getLegalBalance() {
                return legalBalance;
            }

            public void setLegalBalance(double legalBalance) {
                this.legalBalance = legalBalance;
            }
        }

    }

}
