package com.btetop.bean;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.guoziwei.klinelib.util.DoubleUtil;

import java.util.List;

public class OptionalBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 5
         * userId : 40
         * exchange : okex
         * base : BTC
         * quote : USDT
         * createTime : 2018-07-13 10:06:40
         * price : 6238.6273
         * cnyPrice : 41424.49
         * change : -0.0136
         */

        private int id;
        private int userId;
        private String exchange;
        private String base;
        private String quote;
        private String createTime;
        private double price;
        private double cnyPrice;
        private double change;
        private double cnyQuoteAmount;


        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        private boolean last;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }


        public void setPrice(double price) {
            this.price = price;
        }



        public void setCnyPrice(double cnyPrice) {
            this.cnyPrice = cnyPrice;
        }

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }

        private String getturnover() {
            return "多少亿元";
        }

        public String getcnyPrice() {
            return "≈" + cnyPrice + "CNY";
        }


        public Drawable getFromatColorDrawable() {
            Drawable changeDrawable=ContextCompat.getDrawable(BteTopApplication.getInstance(),R.drawable.bg_green_radio);

            if (change > 0) {
                changeDrawable = ContextCompat.getDrawable(BteTopApplication.getContext(), R.drawable.bg_green_radio);
            } else if (change < 0) {
                changeDrawable = ContextCompat.getDrawable(BteTopApplication.getContext(), R.drawable.bg_red_radio);
            } else if (change == 0) {
            }

            return changeDrawable;
        }

        public String getPrice(){
            return DoubleUtil.formatDataCompare10(price);
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

        public String getCnyAmount() {
            String s;
            if (cnyQuoteAmount > 100000000) {
                s = DoubleUtil.format2Decimal(cnyQuoteAmount / 100000000) + "亿元";
            } else if (cnyQuoteAmount > 10000) {
                s = DoubleUtil.format2Decimal(cnyQuoteAmount / 10000) + "万元";
            } else {
                s = DoubleUtil.format2Decimal(cnyQuoteAmount) + "元";
            }
            return s;
        }
    }
}
