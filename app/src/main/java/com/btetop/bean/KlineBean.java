package com.btetop.bean;


import com.google.gson.annotations.SerializedName;

import android.support.v4.content.ContextCompat;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.guoziwei.klinelib.util.DoubleUtil;

import java.util.List;

public class KlineBean {

    /**
     * ticker : {"exchange":"okex","symbol":"btc","pair":"btc/usdt","price":7633.3633,"cnyPrice":48877.18,"high":7743.7355,"low":7597.5647,"open":7633.3633,"close":7631.3633,"vol":25049.3828,"amountVol":1.5782939012E8,"change":0.02}
     * kline : [{"date":1525838640000,"timestamp":9181.91,"close":9177.41,"high":9186.97,"low":9174.47,"open":9181.91,"vol":11996},{"date":1525838580000,"timestamp":9186.75,"close":9181,"high":9188.36,"low":9181,"open":9186.75,"vol":5388},{"date":1525838520000,"timestamp":9188,"close":9185.87,"high":9192.02,"low":9180,"open":9188,"vol":9204},{"date":1525838460000,"timestamp":9203.52,"close":9191.09,"high":9206.5,"low":9188,"open":9203.52,"vol":22316},{"date":1525838400000,"timestamp":9208.97,"close":9203.52,"high":9211.7,"low":9200,"open":9208.97,"vol":10350},{"date":1525838340000,"timestamp":9212.71,"close":9208.02,"high":9213.88,"low":9208,"open":9212.71,"vol":11844},{"date":1525838280000,"timestamp":9215.05,"close":9211.15,"high":9215.05,"low":9210,"open":9215.05,"vol":2072},{"date":1525838220000,"timestamp":9215.44,"close":9214.57,"high":9215.44,"low":9213.71,"open":9215.44,"vol":3746},{"date":1525838160000,"timestamp":9210.99,"close":9215.44,"high":9215.44,"low":9210.99,"open":9210.99,"vol":8258},{"date":1525838100000,"timestamp":9211.09,"close":9210.65,"high":9216.21,"low":9207.32,"open":9211.09,"vol":18560},{"date":1525838040000,"timestamp":9203.59,"close":9211.09,"high":9211.35,"low":9202,"open":9203.59,"vol":5108},{"date":1525837980000,"timestamp":9209.44,"close":9202,"high":9212.25,"low":9202,"open":9209.44,"vol":5706},{"date":1525837920000,"timestamp":9207.75,"close":9209.23,"high":9216.22,"low":9207.75,"open":9207.75,"vol":8996},{"date":1525837860000,"timestamp":9214.13,"close":9209.12,"high":9217.76,"low":9206.32,"open":9214.13,"vol":3662},{"date":1525837800000,"timestamp":9214.05,"close":9213.06,"high":9217.76,"low":9207.1,"open":9214.05,"vol":5566},{"date":1525837740000,"timestamp":9220.14,"close":9213.99,"high":9221.03,"low":9213.99,"open":9220.14,"vol":1838},{"date":1525837680000,"timestamp":9212.24,"close":9219.98,"high":9224.1,"low":9210,"open":9212.24,"vol":8438},{"date":1525837620000,"timestamp":9219,"close":9212.06,"high":9219,"low":9212.06,"open":9219,"vol":8820},{"date":1525837560000,"timestamp":9238.71,"close":9219,"high":9240.47,"low":9219,"open":9238.71,"vol":14016},{"date":1525837500000,"timestamp":9238.96,"close":9240.14,"high":9245,"low":9237.95,"open":9238.96,"vol":4016},{"date":1525837440000,"timestamp":9238.88,"close":9239.91,"high":9244.59,"low":9237.95,"open":9238.88,"vol":2794},{"date":1525837380000,"timestamp":9238.2,"close":9238.88,"high":9244.98,"low":9237.61,"open":9238.2,"vol":3082},{"date":1525837320000,"timestamp":9235.21,"close":9239.1,"high":9243.75,"low":9234.58,"open":9235.21,"vol":12792},{"date":1525837260000,"timestamp":9234.37,"close":9235.13,"high":9243.35,"low":9234.37,"open":9234.37,"vol":8306},{"date":1525837200000,"timestamp":9230,"close":9234.37,"high":9235.1,"low":9227.87,"open":9230,"vol":4938},{"date":1525837140000,"timestamp":9228.77,"close":9228,"high":9230.29,"low":9221,"open":9228.77,"vol":10176},{"date":1525837080000,"timestamp":9233.84,"close":9226.65,"high":9233.84,"low":9225.6,"open":9233.84,"vol":1142},{"date":1525837020000,"timestamp":9232.67,"close":9233.84,"high":9233.84,"low":9224.46,"open":9232.67,"vol":1256},{"date":1525836960000,"timestamp":9237.03,"close":9227.95,"high":9237.54,"low":9227.64,"open":9237.03,"vol":6368},{"date":1525836900000,"timestamp":9229.58,"close":9233.16,"high":9237.56,"low":9224.46,"open":9229.58,"vol":4654},{"date":1525836840000,"timestamp":9225.82,"close":9229.62,"high":9236,"low":9221.95,"open":9225.82,"vol":8686},{"date":1525836780000,"timestamp":9217.35,"close":9226.03,"high":9229.67,"low":9217.35,"open":9217.35,"vol":1904},{"date":1525836720000,"timestamp":9220,"close":9218.4,"high":9220,"low":9215.59,"open":9220,"vol":1470},{"date":1525836660000,"timestamp":9217.39,"close":9220,"high":9220,"low":9215.19,"open":9217.39,"vol":16592},{"date":1525836600000,"timestamp":9214.57,"close":9219,"high":9220,"low":9213,"open":9214.57,"vol":2186},{"date":1525836540000,"timestamp":9214.51,"close":9215.76,"high":9217.63,"low":9209.78,"open":9214.51,"vol":7382},{"date":1525836480000,"timestamp":9206.08,"close":9214.86,"high":9214.86,"low":9205.95,"open":9206.08,"vol":6234},{"date":1525836420000,"timestamp":9208.4,"close":9205.95,"high":9217.86,"low":9205.95,"open":9208.4,"vol":8924},{"date":1525836360000,"timestamp":9213.4,"close":9208.02,"high":9217.88,"low":9208.02,"open":9213.4,"vol":3962},{"date":1525836300000,"timestamp":9222.78,"close":9215.12,"high":9225,"low":9213.97,"open":9222.78,"vol":2464},{"date":1525836240000,"timestamp":9221.55,"close":9222.78,"high":9226.66,"low":9218.78,"open":9221.55,"vol":4176},{"date":1525836180000,"timestamp":9225,"close":9221.55,"high":9225,"low":9219,"open":9225,"vol":7090},{"date":1525836060000,"timestamp":9222.83,"close":9225.58,"high":9228.37,"low":9217.91,"open":9222.83,"vol":8514},{"date":1525836000000,"timestamp":9213.21,"close":9227.43,"high":9228.98,"low":9208.87,"open":9213.21,"vol":17884},{"date":1525835940000,"timestamp":9198.69,"close":9212.05,"high":9216.51,"low":9196.44,"open":9198.69,"vol":8574},{"date":1525835880000,"timestamp":9198.88,"close":9200,"high":9200.43,"low":9196.62,"open":9198.88,"vol":7566},{"date":1525835820000,"timestamp":9195.16,"close":9196.88,"high":9200.02,"low":9195.16,"open":9195.16,"vol":7810},{"date":1525835760000,"timestamp":9196.05,"close":9195.45,"high":9197.39,"low":9188.23,"open":9196.05,"vol":16620},{"date":1525835700000,"timestamp":9204.05,"close":9195.35,"high":9204.63,"low":9189.25,"open":9204.05,"vol":17448},{"date":1525835640000,"timestamp":9200,"close":9204.06,"high":9206.99,"low":9200,"open":9200,"vol":6318},{"date":1525835580000,"timestamp":9215.54,"close":9200.5,"high":9218.29,"low":9200.02,"open":9215.54,"vol":14434},{"date":1525835520000,"timestamp":9211.32,"close":9215.54,"high":9224,"low":9210.4,"open":9211.32,"vol":31306},{"date":1525835460000,"timestamp":9180.03,"close":9211.32,"high":9212.27,"low":9180.03,"open":9180.03,"vol":24186},{"date":1525835400000,"timestamp":9175.28,"close":9180.03,"high":9182.2,"low":9171,"open":9175.28,"vol":27320},{"date":1525835280000,"timestamp":9180.01,"close":9175.28,"high":9180.01,"low":9167,"open":9180.01,"vol":29832},{"date":1525835220000,"timestamp":9178.74,"close":9182.4,"high":9191.37,"low":9169.46,"open":9178.74,"vol":30084},{"date":1525835160000,"timestamp":9182,"close":9178.74,"high":9183.06,"low":9168,"open":9182,"vol":22576},{"date":1525835100000,"timestamp":9192.9,"close":9182,"high":9195.55,"low":9178,"open":9192.9,"vol":19612},{"date":1525835040000,"timestamp":9201.48,"close":9195.51,"high":9202.47,"low":9190,"open":9201.48,"vol":37484},{"date":1525834980000,"timestamp":9219.86,"close":9201.48,"high":9219.86,"low":9201,"open":9219.86,"vol":18266},{"date":1525834920000,"timestamp":9235.43,"close":9219.89,"high":9236.51,"low":9219.89,"open":9235.43,"vol":15558},{"date":1525834800000,"timestamp":9246.62,"close":9235.42,"high":9249.63,"low":9234.93,"open":9246.62,"vol":10692},{"date":1525834740000,"timestamp":9249.93,"close":9243,"high":9249.93,"low":9241.26,"open":9249.93,"vol":1762},{"date":1525834680000,"timestamp":9261.06,"close":9250.62,"high":9262.53,"low":9244.92,"open":9261.06,"vol":2980},{"date":1525834620000,"timestamp":9260.03,"close":9262.75,"high":9266.01,"low":9253.65,"open":9260.03,"vol":6320},{"date":1525834560000,"timestamp":9245.86,"close":9258.9,"high":9262.67,"low":9245.81,"open":9245.86,"vol":9992},{"date":1525834500000,"timestamp":9238.43,"close":9246.14,"high":9248.99,"low":9238.43,"open":9238.43,"vol":7470},{"date":1525834440000,"timestamp":9238.42,"close":9238.43,"high":9241.24,"low":9238.42,"open":9238.42,"vol":5238},{"date":1525834380000,"timestamp":9237.94,"close":9238.33,"high":9238.42,"low":9236.54,"open":9237.94,"vol":14098},{"date":1525834320000,"timestamp":9242.62,"close":9237.94,"high":9248.14,"low":9236,"open":9242.62,"vol":5548},{"date":1525834260000,"timestamp":9236.95,"close":9244.18,"high":9245.29,"low":9236.5,"open":9236.95,"vol":2982},{"date":1525834200000,"timestamp":9249.07,"close":9241.07,"high":9249.07,"low":9238.43,"open":9249.07,"vol":4210},{"date":1525834140000,"timestamp":9260.42,"close":9249.07,"high":9260.42,"low":9248.64,"open":9260.42,"vol":1878},{"date":1525834080000,"timestamp":9260.62,"close":9260.42,"high":9261.88,"low":9260.42,"open":9260.62,"vol":484},{"date":1525834020000,"timestamp":9252.01,"close":9260.42,"high":9263.31,"low":9252.01,"open":9252.01,"vol":1850},{"date":1525833960000,"timestamp":9258.53,"close":9255.33,"high":9259.9,"low":9254.33,"open":9258.53,"vol":13860},{"date":1525833900000,"timestamp":9261.49,"close":9258.53,"high":9265.27,"low":9258.52,"open":9261.49,"vol":3108},{"date":1525833840000,"timestamp":9266,"close":9260.92,"high":9266,"low":9256.92,"open":9266,"vol":1552},{"date":1525833780000,"timestamp":9275,"close":9268.89,"high":9275,"low":9266,"open":9275,"vol":16720},{"date":1525833720000,"timestamp":9265.64,"close":9275,"high":9275.71,"low":9265.2,"open":9265.64,"vol":5560},{"date":1525833660000,"timestamp":9259.73,"close":9269.81,"high":9271.64,"low":9259.73,"open":9259.73,"vol":3756},{"date":1525833600000,"timestamp":9247.22,"close":9259.55,"high":9262.41,"low":9247.22,"open":9247.22,"vol":5614},{"date":1525833540000,"timestamp":9251,"close":9247.15,"high":9253,"low":9240.45,"open":9251,"vol":4296},{"date":1525833480000,"timestamp":9236.75,"close":9251,"high":9255.35,"low":9236.13,"open":9236.75,"vol":9018},{"date":1525833420000,"timestamp":9245.51,"close":9236.75,"high":9246.03,"low":9232,"open":9245.51,"vol":14006},{"date":1525833360000,"timestamp":9250.47,"close":9246.58,"high":9252.32,"low":9233.32,"open":9250.47,"vol":21886},{"date":1525833300000,"timestamp":9266.69,"close":9249.86,"high":9266.69,"low":9247.87,"open":9266.69,"vol":18628},{"date":1525833240000,"timestamp":9270.32,"close":9266.69,"high":9273.03,"low":9265.36,"open":9270.32,"vol":924},{"date":1525833180000,"timestamp":9269.22,"close":9274.36,"high":9287.48,"low":9269.22,"open":9269.22,"vol":4548},{"date":1525833120000,"timestamp":9270.45,"close":9270.01,"high":9278.05,"low":9270.01,"open":9270.45,"vol":1194},{"date":1525833060000,"timestamp":9265.26,"close":9269.22,"high":9273.43,"low":9262.48,"open":9265.26,"vol":11386},{"date":1525833000000,"timestamp":9281.79,"close":9262.01,"high":9281.79,"low":9262.01,"open":9281.79,"vol":3210},{"date":1525832940000,"timestamp":9286.4,"close":9279.21,"high":9286.4,"low":9272.98,"open":9286.4,"vol":14758},{"date":1525832880000,"timestamp":9289.88,"close":9284.95,"high":9294.15,"low":9280,"open":9289.88,"vol":3764},{"date":1525832820000,"timestamp":9294.58,"close":9287.07,"high":9297.33,"low":9286.97,"open":9294.58,"vol":3120},{"date":1525832760000,"timestamp":9272.89,"close":9294.59,"high":9294.95,"low":9270.8,"open":9272.89,"vol":23518},{"date":1525832700000,"timestamp":9258.5,"close":9272.89,"high":9272.89,"low":9258.5,"open":9258.5,"vol":7078},{"date":1525832640000,"timestamp":9258.5,"close":9257.74,"high":9258.5,"low":9251.33,"open":9258.5,"vol":4078},{"date":1525832580000,"timestamp":9249.28,"close":9258.5,"high":9258.5,"low":9249.01,"open":9249.28,"vol":1278},{"date":1525832520000,"timestamp":9244.59,"close":9252.17,"high":9258.5,"low":9240,"open":9244.59,"vol":14998}]
     */

    private TickerBean ticker;
    private List<KlineBeanInner> kline;

    public TickerBean getTicker() {
        return ticker;
    }

    public void setTicker(TickerBean ticker) {
        this.ticker = ticker;
    }

    public List<KlineBeanInner> getKline() {
        return kline;
    }

    public void setKline(List<KlineBeanInner> kline) {
        this.kline = kline;
    }

    public static class TickerBean {
        /**
         * exchange : okex
         * symbol : btc
         * pair : btc/usdt
         * price : 7633.3633
         * cnyPrice : 48877.18
         * high : 7743.7355
         * low : 7597.5647
         * open : 7633.3633
         * close : 7631.3633
         * vol : 25049.3828
         * amountVol : 1.5782939012E8
         * change : 0.02
         */

        private String exchange;
        private String symbol;
        private String pair;
        private double price;
        private double cnyPrice;
        @SerializedName("high")
        private double highX;
        @SerializedName("low")
        private double lowX;
        @SerializedName("open")
        private double openX;
        @SerializedName("close")
        private double closeX;
        @SerializedName("vol")
        private double volX;
        private double amountVol;
        private double change;
        private double airIndex;



        @SerializedName("extra")
        private KlineExtraBean extra;

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getPair() {
            return pair;
        }

        public void setPair(String pair) {
            this.pair = pair;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getCnyPrice() {
            return cnyPrice;
        }

        public void setCnyPrice(double cnyPrice) {
            this.cnyPrice = cnyPrice;
        }

        public double getHighX() {
            return highX;
        }

        public void setHighX(double highX) {
            this.highX = highX;
        }

        public double getLowX() {
            return lowX;
        }

        public void setLowX(double lowX) {
            this.lowX = lowX;
        }

        public double getOpenX() {
            return openX;
        }

        public void setOpenX(double openX) {
            this.openX = openX;
        }

        public double getCloseX() {
            return closeX;
        }

        public void setCloseX(double closeX) {
            this.closeX = closeX;
        }

        public double getVolX() {
            return volX;
        }

        public void setVolX(double volX) {
            this.volX = volX;
        }

        public double getAmountVol() {
            return amountVol;
        }

        public void setAmountVol(double amountVol) {
            this.amountVol = amountVol;
        }


        public String getFormatChange(){

            String changeString="";
            if (change>0) {
                changeString="+" + DoubleUtil.format2Decimal(change * 100) + "%";
            }else if(change<0){
                changeString="" + DoubleUtil.format2Decimal(change * 100) + "%";
            }else if(change==0){
                changeString="" + DoubleUtil.format2Decimal(change * 100) + "%";
            }
            return changeString;
        }

        public int getFromatColor(){
            int changeColor=ContextCompat.getColor(BteTopApplication.getContext(), R.color.main_info_color_red);
            if (change>0) {
                changeColor=ContextCompat.getColor(BteTopApplication.getContext(), R.color.color_e8e28);
            }else if(change<0){
                changeColor=ContextCompat.getColor(BteTopApplication.getContext(), R.color.main_info_color_red);
            }else if(change==0){
                changeColor=ContextCompat.getColor(BteTopApplication.getContext(), R.color.color_626A75_80);
            }
            return changeColor;
        }

        public void setChange(double change) {
            this.change = change;
        }
        public String getAirString(){
            return DoubleUtil.formatDataCompare10(airIndex) + "";
        }
        public double getAir(){
            return airIndex/100;
        }
    }

    public static class KlineBeanInner {
        /**
         * date : 1525838640000
         * timestamp : 9181.91
         * close : 9177.41
         * high : 9186.97
         * low : 9174.47
         * open : 9181.91
         * vol : 11996
         */

        @SerializedName("date")
        private long date;
        @SerializedName("amount")
        private double amount;
        @SerializedName("close")
        private double close;
        @SerializedName("high")
        private double high;
        @SerializedName("low")
        private double low;
        @SerializedName("open")
        private double open;
        @SerializedName("vol")
        private double vol;
        @SerializedName("extra")
        private KlineExtraBean extra;

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
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

        public void setVol(int vol) {
            this.vol = vol;
        }
         public KlineExtraBean getExtra() {
            return extra;
        }

        public void setExtra(KlineExtraBean extra) {
            this.extra = extra;
        }
    }
}
