package com.btetop.bean;

public class SearchCoinBean {

    /**
     * exchange : binance
     * base : BTC
     * quote : USDT
     * price : 6780.81
     * volume : 39083.61934
     * cnyPrice : 46584.16
     * quoteAmount : 2.5891593192270055E8
     * cnyQuoteAmount : 1.77875245231E9
     * change : 0.0497
     */

    private String exchange;
    private String base;
    private String quote;
    private double price;
    private double volume;
    private double cnyPrice;
    private double quoteAmount;
    private double cnyQuoteAmount;
    private double change;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getCnyPrice() {
        return cnyPrice;
    }

    public void setCnyPrice(double cnyPrice) {
        this.cnyPrice = cnyPrice;
    }

    public double getQuoteAmount() {
        return quoteAmount;
    }

    public void setQuoteAmount(double quoteAmount) {
        this.quoteAmount = quoteAmount;
    }

    public double getCnyQuoteAmount() {
        return cnyQuoteAmount;
    }

    public void setCnyQuoteAmount(double cnyQuoteAmount) {
        this.cnyQuoteAmount = cnyQuoteAmount;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }
}
