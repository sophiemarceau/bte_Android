package com.btetop.bean;

public class CoinPairBean {


    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : [{"exchange":"binance","symbol":"BTCUSDT","baseAsset":"BTC","quoteAsset":"USDT"},{"exchange":"huobi","symbol":"BTCUSDT","baseAsset":"BTC","quoteAsset":"USDT"},{"exchange":"okex",
     * "symbol":"BTCUSDT","baseAsset":"BTC","quoteAsset":"USDT"},{"exchange":"bitfinex","symbol":"BTCUSD","baseAsset":"BTC","quoteAsset":"USD"},{"exchange":"bitfinex","symbol":"BTCEUR",
     * "baseAsset":"BTC","quoteAsset":"EUR"},{"exchange":"bitfinex","symbol":"BTCGBP","baseAsset":"BTC","quoteAsset":"GBP"},{"exchange":"bitfinex","symbol":"BTCJPY","baseAsset":"BTC",
     * "quoteAsset":"JPY"}]
     */

    /**
     * exchange : binance
     * symbol : BTCUSDT
     * baseAsset : BTC
     * quoteAsset : USDT
     */

    private String exchange;
    private String symbol;
    private String baseAsset;
    private String quoteAsset;
    private String name;
    private String price;
    private boolean isChecked;


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setBaseAsset(String baseAsset) {
        this.baseAsset = baseAsset;
    }

    public void setQuoteAsset(String quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public String getSymbol() {
        return symbol;
    }


    public String getBaseAsset() {
        return baseAsset;
    }


    public String getQuoteAsset() {
        return quoteAsset;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoinPairBean)) return false;

        CoinPairBean that = (CoinPairBean) o;

        if (exchange != null ? !exchange.equals(that.exchange) : that.exchange != null)
            return false;
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;
        if (baseAsset != null ? !baseAsset.equals(that.baseAsset) : that.baseAsset != null)
            return false;
        if (quoteAsset != null ? !quoteAsset.equals(that.quoteAsset) : that.quoteAsset != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = exchange != null ? exchange.hashCode() : 0;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (baseAsset != null ? baseAsset.hashCode() : 0);
        result = 31 * result + (quoteAsset != null ? quoteAsset.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
