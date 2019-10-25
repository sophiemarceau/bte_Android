package com.guoziwei.klinelib.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loro on 2017/3/7.
 */

public class RSIEntity {

    private ArrayList<Float> RSIs;

    /**
     * @param n 几日
     */
    public RSIEntity(List<KLineFullData> kLineBeens, int n) {
        this(kLineBeens, n, 100);
    }

    /**
     * @param n      几日
     * @param defult 不足N日时的默认值
     */
    public RSIEntity(List<KLineFullData> kLineBeens, int n, float defult) {
        RSIs = new ArrayList<>();
        float upSum = 1.f;
        float downSum = 1.f;

        if (kLineBeens.size() < n) {
            for (KLineFullData kLineBeen : kLineBeens) {
                RSIs.add(Float.NaN);
            }
        } else {

            //计算差值
            List<Float> deltas = getDeltas(kLineBeens, n);
            //计算第n-1个值
            for (int i = 0; i < n - 1; i++) {
                Float delta = deltas.get(i);
                if (delta > 0) {
                    upSum += delta;
                } else {
                    downSum += -delta;
                }


                RSIs.add(Float.NaN);
            }
            //第n个值
            {
                Float delta = deltas.get(n);
                if (delta > 0) {
                    upSum += delta;
                } else {
                    downSum += -delta;
                }
                upSum = upSum / n;
                downSum = downSum / n;

                float rs = 0.0f;
                if (downSum == 0) {
                    rs = Float.MAX_VALUE;
                } else {
                    rs = upSum / downSum;
                }

                float rsi = 100 - (100 / (1 + rs));
                RSIs.add(rsi);
            }

            //计算n后面的值
            for (int i = n; i < kLineBeens.size(); i++) {
                Float delta = deltas.get(i);
                float upval = 0.0f;
                float downval = 0.0f;


                if (delta > 0) {
                    upval = delta;
                    downval = 0.f;
                } else {
                    upval = 0.f;
                    downval = -delta;
                }

                upSum = (upSum * (n - 1) + upval) / n;
                downSum = (downSum * (n - 1) + downval) / n;
                float rs = 0.0f;
                if (downSum == 0) {
                    rs = Float.MAX_VALUE;
                } else {
                    rs = upSum / downSum;
                }
                float rsi = (float) (100 - 100. / (1. + rs));

                RSIs.add(rsi);

            }
        }


    }


    private List<Float> getDeltas(List<KLineFullData> kLineBeens, int n) {
        List<Float> deltasList = new ArrayList<>();
        deltasList.add(0.f);

        if (kLineBeens.size() < n) {
            for (int i = 1; i < n; i++) {
                float closeT = (float) kLineBeens.get(i).getClose();
                float closeY = (float) kLineBeens.get(i - 1).getClose();
                float delta = closeT - closeY;
                deltasList.add(delta);
            }
        } else {
            for (int i = 1; i < kLineBeens.size(); i++) {
                float closeT = (float) kLineBeens.get(i).getClose();
                float closeY = (float) kLineBeens.get(i - 1).getClose();
                float delta = closeT - closeY;
                deltasList.add(delta);
            }

        }


        return deltasList;

    }

    public ArrayList<Float> getRSIs() {
        return RSIs;
    }
}
