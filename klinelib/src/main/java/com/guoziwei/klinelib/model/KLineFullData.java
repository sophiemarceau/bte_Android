package com.guoziwei.klinelib.model;

import android.support.annotation.NonNull;

import com.guoziwei.klinelib.util.DateUtils;

/**
 * chart data model
 */

public class KLineFullData implements Comparable{

    private double close;
    private double high;
    private double low;
    private double open;
    private double vol;
    private long date;
    private String dateString;
    private double amountVol;
    private double avePrice;
    private double total;
    private double maSum;
    private double ma5;
    private double ma10;
    private double ma20;
    private double ma30;

    private double ema7;
    private double ema30;

    private double dif;
    private double dea;
    private double macd;

    private double k;
    private double d;
    private double j;


    private double sar;


    //boll线
    private double ub;
    private double dn;
    private double mb;


    //rsi线
    private double rsi6;
    private double rsi12;
    private double rsi24;
    private Double rsv;

    //K线短评
    private double bubbleY;
    private String bubbleText;

    //净成交量
    private double ycBuyVolume;
    private double ycSellVolume;

    //压力线
//    private double sellPrice1=Double.NaN;
//    private double sellPrice1Amount;
//    private double sellPrice2=Double.NaN;
//    private double sellPrice2Amount;
//    private double buyPrice1=Double.NaN;
//    private double buyPrice1Amount;
//    private double buyPrice2=Double.NaN;
//    private double buyPrice2Amount;

    private double buyPrice5=Double.NaN;
    private double buyPrice5Amount;

    private double sellPrice5=Double.NaN;

    private double sellPrice5Amount;



    //异常订单 托单 撤销托单 压单 撤销压单
    /**
     * 大单异动-k线数据 ： orderType字段枚举
     buy托单／cancelbuy撤销托单
     sell压单／cancelsell撤销压单
     buyburned 多单爆仓／sellburned 空单爆仓
     */





    //多单爆仓
    private int ycBuyBurnedCount;
    private double ycBuyBurnedPrice=Double.NaN;

    //空单爆仓
    private int ycSellBurnedCount;
    private double ycSellBurnedPrice=Double.NaN;


    //净成交量 //不是bug 大单买入算卖出 大单卖出算买入 对倒
    public int getNetTradeCount() {
        return tradeBuyCount-tradeSellCount;
    }



    //多单挂单数量
    private int ycBuyCount;
    private double ycBuyPrice=Double.NaN;
    //多单撤单数量
    private int ycCancelBuyCount;



    public int getYcCancelBuyCount() {
        return ycCancelBuyCount;
    }

    public void setYcCancelBuyCount(int ycCancelBuyCount) {
        this.ycCancelBuyCount = ycCancelBuyCount;
    }

    public int getYcCancelSellCount() {
        return ycCancelSellCount;
    }

    public void setYcCancelSellCount(int ycCancelSellCount) {
        this.ycCancelSellCount = ycCancelSellCount;
    }

    //空单挂单数量
    private int ycSellCount;
    private double ycSellPrice=Double.NaN;
    //空单撤单数量
    private int ycCancelSellCount;

    public int getNetYcBuyCount() {
        return ycBuyCount - ycCancelBuyCount;
    }
    public int getNetYcSellCount() {
        return ycSellCount - ycCancelSellCount;
    }


    //阀值
    private int threshold=0;

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    private int tradeBuyCount;
    private int tradeSellCount;

    public int getTradeBuyCount() {
        return tradeBuyCount;
    }

    public void setTradeBuyCount(int tradeBuyCount) {
        this.tradeBuyCount = tradeBuyCount;
    }

    public int getTradeSellCount() {
        return tradeSellCount;
    }

    public void setTradeSellCount(int tradeSellCount) {
        this.tradeSellCount = tradeSellCount;
    }

    //大单成交数量
    public int bigBuyCount;
    public int bigSellCount;

    public int getBigBuyCount() {
        return bigBuyCount;
    }

    public void setBigBuyCount(int bigBuyCount) {
        this.bigBuyCount = bigBuyCount;
    }

    public int getBigSellCount() {
        return bigSellCount;
    }

    public void setBigSellCount(int bigSellCount) {
        this.bigSellCount = bigSellCount;
    }



    public int getYcBuyBurnedCount() {
        return ycBuyBurnedCount;
    }

    public void setYcBuyBurnedCount(int ycBuyBurnedCount) {
        this.ycBuyBurnedCount = ycBuyBurnedCount;
    }

    public double getYcBuyBurnedPrice() {
        return ycBuyBurnedPrice;
    }

    public void setYcBuyBurnedPrice(double ycBuyBurnedPrice) {
        this.ycBuyBurnedPrice = ycBuyBurnedPrice;
    }



    public void setVol(double vol) {
        this.vol = vol;
    }


    public int getYcBuyCount() {
        return ycBuyCount;
    }

    public void setYcBuyCount(int ycBuyCount) {
        this.ycBuyCount = ycBuyCount;
    }

    public double getYcBuyPrice() {
        return ycBuyPrice;
    }

    public void setYcBuyPrice(double ycBuyPrice) {
        this.ycBuyPrice = ycBuyPrice;
    }



    public int getYcSellCount() {
        return ycSellCount;
    }

    public void setYcSellCount(int ycSellCount) {
        this.ycSellCount = ycSellCount;
    }

    public double getYcSellPrice() {
        return ycSellPrice;
    }

    public void setYcSellPrice(double ydSellPrice) {
        this.ycSellPrice = ydSellPrice;
    }

    public int getYcSellBurnedCount() {
        return ycSellBurnedCount;
    }

    public void setYcSellBurnedCount(int ycSellBurnedCount) {
        this.ycSellBurnedCount = ycSellBurnedCount;
    }

    public double getYcSellBurnedPrice() {
        return ycSellBurnedPrice;
    }

    public void setYcSellBurnedPrice(double ycSellBurnedPrice) {
        this.ycSellBurnedPrice = ycSellBurnedPrice;
    }

    public String getBubbleText() {
        return bubbleText;
    }

    public void setBubbleText(String bubbleText) {
        this.bubbleText = bubbleText;
    }



    public double getBubbleY() {
        return bubbleY;
    }

    public void setBubbleY(double bubbleY) {
        this.bubbleY = bubbleY;
    }

    public double getRsi6() {
        return rsi6;
    }

    public void setRsi6(double rsi6) {
        this.rsi6 = rsi6;
    }

    public double getRsi12() {
        return rsi12;
    }

    public void setRsi12(double rsi12) {
        this.rsi12 = rsi12;
    }

    public double getRsi24() {
        return rsi24;
    }

    public void setRsi24(double rsi24) {
        this.rsi24 = rsi24;
    }
    public double getUb() {
        return ub;
    }

    public void setUb(double ub) {
        this.ub = ub;
    }

    public double getDn() {
        return dn;
    }

    public void setDn(double dn) {
        this.dn = dn;
    }

    public double getMb() {
        return mb;
    }

    public void setMb(double mb) {
        this.mb = mb;
    }


    public double getDif() {
        return dif;
    }


    public double getSar() {
        return sar;
    }

    public void setSar(double sar) {
        this.sar = sar;
    }

    public KLineFullData() {
    }

    public KLineFullData(double open, double close, double high, double low, double vol, long date) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.vol = vol;
        this.date = date;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(long vol) {
        this.vol = vol;
    }


    public double getAvePrice() {
        return avePrice;
    }

    public void setAvePrice(double avePrice) {
        this.avePrice = avePrice;
    }


    public double getAmountVol() {
        return amountVol;
    }

    public void setAmountVol(double amountVol) {
        this.amountVol = amountVol;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMa5() {
        return ma5;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    public double getMa10() {
        return ma10;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }

    public double getMa20() {
        return ma20;
    }

    public void setMa20(double ma20) {
        this.ma20 = ma20;
    }

    public double getMa30() {
        return ma30;
    }

    public void setMa30(double ma30) {
        this.ma30 = ma30;
    }


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KLineFullData data = (KLineFullData) o;

        return date == data.date;
    }

    @Override
    public int hashCode() {
        return (int) (date ^ (date >>> 32));
    }

    public double getMaSum() {
        return maSum;
    }

    public void setMaSum(double maSum) {
        this.maSum = maSum;
    }

    public void setDif(double dif) {
        this.dif = dif;
    }

    public double getDea() {
        return dea;
    }

    public void setDea(double dea) {
        this.dea = dea;
    }

    public double getMacd() {
        return macd;
    }

    public void setMacd(double macd) {
        this.macd = macd;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getJ() {
        return j;
    }

    public void setJ(double j) {
        this.j = j;
    }

    public String getHumanDate() {
        return DateUtils.formatDateTime1(date);
    }

    public String getData() {
        return DateUtils.formatDateTime1(date);
    }

    public double getEma7() {
        return ema7;
    }

    public void setEma7(double ema7) {
        this.ema7 = ema7;
    }

    public double getEma30() {
        return ema30;
    }

    public void setEma30(double ema30) {
        this.ema30 = ema30;
    }


    @Override
    public String toString() {
        return "HisData{" +
                "close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", vol=" + vol +
                ", date=" + date +
                ", amountVol=" + amountVol +
                ", avePrice=" + avePrice +
                ", total=" + total +
                ", maSum=" + maSum +
                ", ma5=" + ma5 +
                ", ma10=" + ma10 +
                ", ma20=" + ma20 +
                ", ma30=" + ma30 +
                ", dif=" + dif +
                ", dea=" + dea +
                ", macd=" + macd +
                ", k=" + k +
                ", d=" + d +
                ", j=" + j +
                '}';
    }

    public void setRsv(Double rsv) {
        this.rsv = rsv;
    }

    public double getRsv() {
        return this.rsv;
    }


    public double getYcBuyVolume() {
        return ycBuyVolume;
    }

    public void setYcBuyVolume(double ycBuyVolume) {
        this.ycBuyVolume = ycBuyVolume;
    }

    public double getYcSellVolume() {
        return ycSellVolume;
    }

    public double getSellVolume1(){
        return 0- ycSellVolume;

    }


    public void setYcSellVolume(double ycSellVolume) {
        this.ycSellVolume = ycSellVolume;
    }


    public double getBuyPrice5() {
        return buyPrice5;
    }

    public void setBuyPrice5(double buyPrice5) {
        this.buyPrice5 = buyPrice5;
    }

    public double getBuyPrice5Amount() {
        return buyPrice5Amount;
    }

    public void setBuyPrice5Amount(double buyPrice5Amount) {
        this.buyPrice5Amount = buyPrice5Amount;
    }

    public double getSellPrice5() {
        return sellPrice5;
    }

    public void setSellPrice5(double sellPrice5) {
        this.sellPrice5 = sellPrice5;
    }

    public double getSellPrice5Amount() {
        return sellPrice5Amount;
    }

    public void setSellPrice5Amount(double sellPrice5Amount) {
        this.sellPrice5Amount = sellPrice5Amount;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        KLineFullData newData = (KLineFullData) o;
        return (int)(this.getDate() - ((KLineFullData) o).getDate());

    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
