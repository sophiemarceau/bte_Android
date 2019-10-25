package com.guoziwei.klinelib.model;

import android.support.annotation.NonNull;

public class VolumeProfileEntity implements Comparable<VolumeProfileEntity>{
    //区间价格
    private double x_n;
    private double x_n_1;
    //区间
    private int R;

    //交易量
    private double volume=0.f;

    private static double longestValue;


    private boolean longest;


    public boolean isLongest() {
        return longest;
    }

    public void setLongest(boolean longest) {
        this.longest = longest;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getX_n() {
        return x_n;
    }

    public void setX_n(double x_n) {
        this.x_n = x_n;
    }

    public double getX_n_1() {
        return x_n_1;
    }

    public void setX_n_1(double x_n_1) {
        this.x_n_1 = x_n_1;
    }

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public static double getLongestValue() {
        return longestValue;
    }

    public static void setLongestValue(double longestValue) {
        VolumeProfileEntity.longestValue = longestValue;
    }

    @Override
    public int compareTo(@NonNull VolumeProfileEntity o) {
        if (this.volume < o.volume)
            return -1;
        else if (this.volume > o.volume)
            return 1;
        else
            return 0;
    }
}
