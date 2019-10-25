package com.btetop.bean;

/**
 * 线形图的popvindow信息
 */
public class LineChartExtraBean {
    private Long date;
    private Float volume;
    private Float price;

    public LineChartExtraBean(Long date, Float volume, Float price) {
        this.date = date;
        this.volume = volume;
        this.price = price;
    }

    public long getDate() {
        return date;
    }

    public float getVolume() {
        return volume;
    }

    public float getPrice() {
        return price;
    }
}
