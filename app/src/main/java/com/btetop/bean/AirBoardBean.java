package com.btetop.bean;


import android.support.v4.content.ContextCompat;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.guoziwei.klinelib.util.DoubleUtil;

import java.io.Serializable;

public class AirBoardBean implements Serializable {

    public static final String SerializableTag = "AirBoardBean";


    private double price;
    private String exchange;
    private String baseAsset;
    private String quoteAsset;


    private Double revenue;
    private int artBoardId;
    private double costPrice;
    private boolean checkFlag;
    private double change;


    //合约
    private int direction;
    private int ratio;


    //删除用途
    private int index;

    public AirBoardBean(String exchange, String baseAsset, String quoteAsset) {
        this.exchange = exchange;
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
    }

    public int getArtBoardId() {
        return artBoardId;
    }

    public void setArtBoardId(int artBoardId) {
        this.artBoardId = artBoardId;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }




    public String getPrice() {

        return DoubleUtil.formatDataCompare10(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public void setBaseAsset(String baseAsset) {
        this.baseAsset = baseAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public void setQuoteAsset(String quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    public int getFromatColor() {

        int changeColor = ContextCompat.getColor(BteTopApplication.getContext(), R.color.color_626A75_80);
        if (revenue!=null) {
            if (revenue>0) {
                changeColor = ContextCompat.getColor(BteTopApplication.getContext(), R.color.xuanfu_green);
            }else {
                changeColor = ContextCompat.getColor(BteTopApplication.getContext(), R.color.xuanfu_red);
            }
        }else {
            if (change > 0) {
                changeColor = ContextCompat.getColor(BteTopApplication.getContext(), R.color.xuanfu_green);
            } else if (change < 0) {
                changeColor = ContextCompat.getColor(BteTopApplication.getContext(), R.color.xuanfu_red);
            } else if (change == 0) {
            }
        }

        return changeColor;
    }


    public String getRevenueString(){
        String changeString="";
        if (revenue>0) {
            changeString="+" + DoubleUtil.format2Decimal(revenue ) + "%";
        }else if(revenue<0){
            changeString="" + DoubleUtil.format2Decimal(revenue ) + "%";
        }else if(revenue==0){
            changeString="" + DoubleUtil.format2Decimal(revenue ) + "%";
        }
        return changeString;
    }
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public Double getRevenue() {
        return revenue;

//        return DoubleUtil.formatDataCompare10(price);
    }


    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }


    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirBoardBean)) return false;

        AirBoardBean that = (AirBoardBean) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (Double.compare(that.revenue, revenue) != 0) return false;
        if (exchange != null ? !exchange.equals(that.exchange) : that.exchange != null)
            return false;
        if (baseAsset != null ? !baseAsset.equals(that.baseAsset) : that.baseAsset != null)
            return false;
        return quoteAsset != null ? quoteAsset.equals(that.quoteAsset) : that.quoteAsset == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (exchange != null ? exchange.hashCode() : 0);
        result = 31 * result + (baseAsset != null ? baseAsset.hashCode() : 0);
        result = 31 * result + (quoteAsset != null ? quoteAsset.hashCode() : 0);
        temp = Double.doubleToLongBits(revenue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static class RequestBodyClass {
        public String exchange;
        public String base;
        public String quote;
        public int direction;
        public int ratio;
        public double costPrice;
    }

}
