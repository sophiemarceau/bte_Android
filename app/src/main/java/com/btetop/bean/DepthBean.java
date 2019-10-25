package com.btetop.bean;

public class DepthBean {

        /**
         * price : 7071.64
         * depth : 0.013
         * type : ASK
         * count : 39383
         */

        private double price;
        private double depth;
        private String type;
    private double count;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getDepth() {
            return depth;
        }

        public void setDepth(double depth) {
            this.depth = depth;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    public double getCount() {
            return count;
        }

    public String getCountString() {
        String str = String.format("%.2f ", count);
        return str;
    }

        public void setCount(int count) {
            this.count = count;
        }
}
