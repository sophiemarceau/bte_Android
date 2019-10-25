package com.btetop.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/13.
 */

public class UserCurrentLotBean {

    public static class UserStrategyData implements Serializable {
        private String count;
        private ArrayList<UserStrategyDateDetails> details;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public ArrayList<UserStrategyDateDetails> getDetails() {
            return details;
        }

        public void setDetails(ArrayList<UserStrategyDateDetails> details) {
            this.details = details;
        }

        public static class UserStrategyDateDetails implements Serializable {
            private String productId;
            private String productBatchId;
            private String productBatchName;
            private String assetType;
            private double productNetValue;
            private double ror;
            private ArrayList<UserStrategySmallDetails> details;

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public double getRor() {
                return ror;
            }

            public void setRor(double ror) {
                this.ror = ror;
            }

            public String getProductBatchId() {
                return productBatchId;
            }

            public void setProductBatchId(String productBatchId) {
                this.productBatchId = productBatchId;
            }

            public String getProductBatchName() {
                return productBatchName;
            }

            public void setProductBatchName(String productBatchName) {
                this.productBatchName = productBatchName;
            }

            public String getAssetType() {
                return assetType;
            }

            public void setAssetType(String assetType) {
                this.assetType = assetType;
            }

            public double getProductNetValue() {
                return productNetValue;
            }

            public void setProductNetValue(double productNetValue) {
                this.productNetValue = productNetValue;
            }

            public ArrayList<UserStrategySmallDetails> getDetails() {
                return details;
            }

            public void setDetails(ArrayList<UserStrategySmallDetails> details) {
                this.details = details;
            }

            public static class UserStrategySmallDetails implements Serializable {
                private String id;
                private double principal;//本金
                private double amount;//当前额
                private double ror;//收益率
                private String status;//状态

                public double getPrincipal() {
                    return principal;
                }

                public void setPrincipal(double principal) {
                    this.principal = principal;
                }

                public double getRor() {
                    return ror;
                }

                public void setRor(double ror) {
                    this.ror = ror;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }

        }

    }


}
