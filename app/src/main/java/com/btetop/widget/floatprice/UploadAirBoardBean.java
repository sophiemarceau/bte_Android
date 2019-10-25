package com.btetop.widget.floatprice;

import java.io.Serializable;

public class UploadAirBoardBean implements Serializable{
    private String exchange;
    private String base;
    private String quote;
    private int direction;
    private int ratio;
    private float costPrice;

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

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadAirBoardBean)) return false;

        UploadAirBoardBean that = (UploadAirBoardBean) o;

        if (direction != that.direction) return false;
        if (ratio != that.ratio) return false;
        if (Float.compare(that.costPrice, costPrice) != 0) return false;
        if (exchange != null ? !exchange.equals(that.exchange) : that.exchange != null)
            return false;
        if (base != null ? !base.equals(that.base) : that.base != null) return false;
        return quote != null ? quote.equals(that.quote) : that.quote == null;
    }

    @Override
    public int hashCode() {
        int result = exchange != null ? exchange.hashCode() : 0;
        result = 31 * result + (base != null ? base.hashCode() : 0);
        result = 31 * result + (quote != null ? quote.hashCode() : 0);
        result = 31 * result + direction;
        result = 31 * result + ratio;
        result = 31 * result + (costPrice != +0.0f ? Float.floatToIntBits(costPrice) : 0);
        return result;
    }
}
