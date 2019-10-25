package com.btetop.bean;

public class CurrencyBean {

    /**
     * exchange : okex
     * name : BTC/季度合约
     * symbol : BTCQUARTER
     * baseAsset : BTC
     * quoteAsset : QUARTER
     */

    private String exchange;
    private String name;
    private String symbol;
    private String baseAsset;
    private String quoteAsset;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
}
