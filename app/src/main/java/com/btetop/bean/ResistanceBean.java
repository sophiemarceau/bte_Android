package com.btetop.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 合约狗压力线
 */
public class ResistanceBean {


    /**
     * id : 27524
     * baseAsset : null
     * contractType : null
     * snapshot : null
     * timestamp : 1533348000000
     * createTime : null
     * sellPrice5 : null
     * sellCumulativeAmount5 : null
     * sellPrice4 : null
     * sellCumulativeAmount4 : null
     * sellPrice3 : null
     * sellCumulativeAmount3 : null
     * sellPrice2 : 7488
     * sellCumulativeAmount2 : 19833
     * sellPrice1 : 7471.44
     * sellCumulativeAmount1 : 9971
     * buyPrice1 : 7388
     * buyCumulativeAmount1 : 9956
     * buyPrice2 : 7378
     * buyCumulativeAmount2 : 19856
     * buyPrice3 : 7366
     * buyCumulativeAmount3 : 28194
     * buyPrice4 : 7367.98
     * buyCumulativeAmount4 : 39650
     * buyPrice5 : 7368
     * buyCumulativeAmount5 : 49494
     */




    @SerializedName("buy_price_5")
    private double buyPrice5=Double.NaN;
    @SerializedName("buy_cumulativeAmount_5")
    private double buyCumulativeAmount5;

    @SerializedName("sell_price_5")
    private double sellPrice5=Double.NaN;
    @SerializedName("sell_cumulativeAmount_5")
    private double sellCumulativeAmount5;

    public double getBuyPrice5() {
        return buyPrice5;
    }

    public void setBuyPrice5(double buyPrice5) {
        this.buyPrice5 = buyPrice5;
    }

    public double getBuyCumulativeAmount5() {
        return buyCumulativeAmount5;
    }

    public void setBuyCumulativeAmount5(double buyCumulativeAmount5) {
        this.buyCumulativeAmount5 = buyCumulativeAmount5;
    }

    public double getSellPrice5() {
        return sellPrice5;
    }

    public void setSellPrice5(double sellPrice5) {
        this.sellPrice5 = sellPrice5;
    }

    public double getSellCumulativeAmount5() {
        return sellCumulativeAmount5;
    }

    public void setSellCumulativeAmount5(double sellCumulativeAmount5) {
        this.sellCumulativeAmount5 = sellCumulativeAmount5;
    }




}
