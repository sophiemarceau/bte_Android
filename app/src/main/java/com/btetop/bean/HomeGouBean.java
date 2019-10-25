package com.btetop.bean;

public class HomeGouBean {
    /**
     * exchange : okex
     * quote : USDT
     * date : 2018-06-20
     * price : 6606.84
     * change : -1.69
     * trend : 持续看涨
     * operation : 持续看涨
     * symbol : BTC
     * icon : http://47.94.217.12:18081/app/images/icon/btc.png
     */

    private String exchange;
    private String quote;
    private String date;
    private double price;
    private double change;
    private String trend;
    private String operation;
    private String symbol;
    private String icon;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
