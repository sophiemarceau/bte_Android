package com.guoziwei.klinelib.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.NaN;

/**
 * Created by loro on 2017/3/7.
 */

public class BOLLEntity {

    private int kReate = 2;
    private List<Float> UPs;
    private List<Float> MBs;
    private List<Float> DNs;

    /**
     * 得到BOLL指标
     */
    public BOLLEntity(List<KLineFullData> kLineBeens, int n) {
        this(kLineBeens, n, NaN);
    }

    /**
     * 得到BOLL指标
     */
    public BOLLEntity(List<KLineFullData> kLineBeens, int n, float defult) {
        UPs = new ArrayList<>();
        MBs = new ArrayList<>();
        DNs = new ArrayList<>();

        float ma = 0.0f;
        float md = 0.0f;
        float mb = 0.0f;
        float up = 0.0f;
        float dn = 0.0f;

        if (kLineBeens != null && kLineBeens.size() > 0) {
            float closeSum = 0.0f;
            float sum = 0.0f;
            int index = 0;
            int index2 = n - 1;
            for (int i = 0; i < kLineBeens.size(); i++) {
                int k = i - n + 1;
                if (i >= n) {
                    index = 20;
                } else {
                    index = i + 1;
                }
                //N日内的收盘价
                closeSum = getSumClose(k, i, kLineBeens);
                ma = closeSum / index;
                sum = getSum(k, i, ma, kLineBeens);
                md = (float) Math.sqrt(sum / index);
                mb = ma;
                up = mb + (kReate * md);
                dn = mb - (kReate * md);

                if (i < index2) {
                    mb = defult;
                    up = defult;
                    dn = defult;
                }

                UPs.add(up);
                MBs.add(mb);
                DNs.add(dn);
            }

        }

    }

    private Float getSum(Integer a, Integer b, Float ma, List<KLineFullData> kLineBeens) {
        if (a < 0)
            a = 0;
        KLineFullData kLineBean;
        float sum = 0.0f;
        for (int i = a; i <= b; i++) {
            kLineBean = kLineBeens.get(i);
            sum += ((kLineBean.getClose() - ma) * (kLineBean.getClose() - ma));
        }

        return sum;
    }


    private Float getSumClose(Integer a, Integer b, List<KLineFullData> kLineBeens) {
        if (a < 0)
            a = 0;
        KLineFullData kLineBean;
        float close = 0.0f;
        for (int i = a; i <= b; i++) {
            kLineBean = kLineBeens.get(i);
            close += kLineBean.getClose();
        }

        return close;
    }


    public List<Float> getUPs() {
        return UPs;
    }

    public List<Float> getMBs() {
        return MBs;
    }

    public List<Float> getDNs() {
        return DNs;
    }
}
