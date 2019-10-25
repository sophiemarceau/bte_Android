package com.guoziwei.klinelib.util;

import com.guoziwei.klinelib.model.BOLLEntity;
import com.guoziwei.klinelib.model.KDJ;
import com.guoziwei.klinelib.model.KLineFullData;
import com.guoziwei.klinelib.model.MACD;
import com.guoziwei.klinelib.model.RSIEntity;
import com.guoziwei.klinelib.model.VolumeProfileEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dell on 2017/11/9.
 * http://www.dashixiong.cn/kline/klineformula/9686.html
 */

public class DataUtils {


    /**
     * calculate average price and ma data
     */
    public static List<KLineFullData> calculateHisData(List<KLineFullData> list, KLineFullData lastData) {


        List<Double> ma5List = calculateMA(5, list);
        List<Double> ma10List = calculateMA(10, list);
        List<Double> ma20List = calculateMA(20, list);
        List<Double> ma30List = calculateMA(30, list);

        List<Double> ema7List=calculateEmaList(7,list);
        List<Double> ema30List=calculateEmaList(30,list);

        List<Double> sarList=calculateSarList(0.02f,0.2f,list);


        MACD macd = new MACD(list);
        List<Double> bar = macd.getMACD();
        List<Double> dea = macd.getDEA();
        List<Double> dif = macd.getDIF();
        KDJ kdj = new KDJ(list);
        ArrayList<Double> d = kdj.getD();
        ArrayList<Double> k = kdj.getK();
        ArrayList<Double> j = kdj.getJ();
        ArrayList<Double> rsv = kdj.getRsv();

        BOLLEntity bollEntity = new BOLLEntity(list, 20);
        List<Float> uPs = bollEntity.getUPs();
        List<Float> dNs = bollEntity.getDNs();
        List<Float> mBs = bollEntity.getMBs();

        RSIEntity rsiEntity6 = new RSIEntity(list, 6);
        RSIEntity rsiEntity12 = new RSIEntity(list, 12);
        RSIEntity rsiEntity24 = new RSIEntity(list, 24);

        double amountVol = 0;
        if (lastData != null) {
            amountVol = lastData.getAmountVol();
        }
        for (int i = 0; i < list.size(); i++) {
            KLineFullData hisData = list.get(i);

            hisData.setMa5(ma5List.get(i));
            hisData.setMa10(ma10List.get(i));
            hisData.setMa20(ma20List.get(i));
            hisData.setMa30(ma30List.get(i));

            hisData.setEma7(ema7List.get(i));
            hisData.setEma30(ema30List.get(i));

            hisData.setSar(sarList.get(i));

            hisData.setMacd(bar.get(i));
            hisData.setDea(dea.get(i));
            hisData.setDif(dif.get(i));

            hisData.setD(d.get(i));
            hisData.setK(k.get(i));
            hisData.setJ(j.get(i));
            hisData.setRsv(rsv.get(i));

            hisData.setUb(uPs.get(i));
            hisData.setMb(mBs.get(i));
            hisData.setDn(dNs.get(i));

            hisData.setRsi6(rsiEntity6.getRSIs().get(i));
            hisData.setRsi12(rsiEntity12.getRSIs().get(i));
            hisData.setRsi24(rsiEntity24.getRSIs().get(i));


            amountVol += hisData.getVol();
            hisData.setAmountVol(amountVol);
            if (i > 0) {
                double total = hisData.getVol() * hisData.getClose() + list.get(i - 1).getTotal();
                hisData.setTotal(total);
                double avePrice = total / amountVol;
                hisData.setAvePrice(avePrice);
            } else if (lastData != null) {
                double total = hisData.getVol() * hisData.getClose() + lastData.getTotal();
                hisData.setTotal(total);
                double avePrice = total / amountVol;
                hisData.setAvePrice(avePrice);
            } else {
                hisData.setAmountVol(hisData.getVol());
                hisData.setAvePrice(hisData.getClose());
                hisData.setTotal(hisData.getAmountVol() * hisData.getAvePrice());
            }

        }
        return list;
    }

    public static List<KLineFullData> calculateHisData(List<KLineFullData> list) {
        return calculateHisData(list, null);
    }

    public static List<KLineFullData> calculateRightListHisData(List<KLineFullData> newDatas, List<KLineFullData> oldDatas) {
        if (newDatas == null || newDatas.size() == 0 || oldDatas==null || oldDatas.size()==0) {
            return null;
        }

        List<KLineFullData> completeHisData = new ArrayList<>();





        //组装新的数据
        completeHisData.addAll(oldDatas);
        //completeHisData.remove(oldDatas.get(oldDatas.size() - 1));


        for(int i=0;i<newDatas.size();i++){
            KLineFullData newData = newDatas.get(i);
            int index= isInHistoryData(newData,completeHisData);
            if(index == -1){
                completeHisData.add(newData);
            }
            else{
                completeHisData.remove(index);
                completeHisData.add(index,newData);

            }
        }

        List<KLineFullData> data = calculateHisData(completeHisData, null);

        return data;
    }


    //判断这条记录是否在列表中存在
    public static int isInHistoryData(KLineFullData data,List<KLineFullData> list){
        if(list==null || list.size() == 0 || data == null) return -1;
        for(int i=0;i<list.size();i++){
            KLineFullData oldData = list.get(i);
            if(oldData.getDate() == data.getDate()){
                return i;
            }
        }
        return  -1;
    }
    /**
     * according to the history data list, calculate a new data
     */
    public static KLineFullData calculateHisData(KLineFullData newData, List<KLineFullData> hisDatas) {

        KLineFullData lastData = hisDatas.get(hisDatas.size() - 1);
        double amountVol = lastData.getAmountVol();

        newData.setMa5(calculateLastMA(5, hisDatas));
        newData.setMa10(calculateLastMA(10, hisDatas));
        newData.setMa20(calculateLastMA(20, hisDatas));
        newData.setMa30(calculateLastMA(30, hisDatas));

        amountVol += newData.getVol();
        newData.setAmountVol(amountVol);

        double total = newData.getVol() * newData.getClose() + lastData.getTotal();
        newData.setTotal(total);
        double avePrice = total / amountVol;
        newData.setAvePrice(avePrice);

        MACD macd = new MACD(hisDatas);
        List<Double> bar = macd.getMACD();
        newData.setMacd(bar.get(bar.size() - 1));
        List<Double> dea = macd.getDEA();
        newData.setDea(dea.get(dea.size() - 1));
        List<Double> dif = macd.getDIF();
        newData.setDif(dif.get(dif.size() - 1));
        KDJ kdj = new KDJ(hisDatas);
        ArrayList<Double> d = kdj.getD();
        newData.setD(d.get(d.size() - 1));
        ArrayList<Double> k = kdj.getK();
        newData.setK(k.get(k.size() - 1));
        ArrayList<Double> j = kdj.getJ();
        newData.setJ(j.get(j.size() - 1));

        return newData;
    }

    /**
     * calculate MA value, return a double list
     * @param dayCount for example: 5, 10, 20, 30
     */
    public static List<Double> calculateMA(int dayCount, List<KLineFullData> data) {
        dayCount--;
        List<Double> result = new ArrayList<>(data.size());
        for (int i = 0, len = data.size(); i < len; i++) {
            if (i < dayCount) {
                result.add(Double.NaN);
                continue;
            }
            double sum = 0;
            for (int j = 0; j < dayCount; j++) {
                sum += data.get(i - j).getOpen();
            }
            result.add(+(sum / dayCount));
        }
        return result;
    }

    /**
     * calculate last MA value, return a double value
     */
    public static double calculateLastMA(int dayCount, List<KLineFullData> data) {
        dayCount--;
        double result = Double.NaN;
        for (int i = 0, len = data.size(); i < len; i++) {
            if (i < dayCount) {
                result = Double.NaN;
                continue;
            }
            double sum = 0;
            for (int j = 0; j < dayCount; j++) {
                sum += data.get(i - j).getOpen();
            }
            result = (+(sum / dayCount));
        }
        return result;
    }

    public static List<Double> calculateEmaList(int dayCount, List<KLineFullData> kLineBeen) {

        ArrayList<Double> emaList = new ArrayList<>();

        double eMA = 0.0f;
        double close = 0f;
        if (kLineBeen != null && kLineBeen.size() > 0) {
            for (int i = 0; i < kLineBeen.size(); i++) {
                close = kLineBeen.get(i).getClose();
                if (i == 0) {
                    eMA = close;
                } else {
                    eMA = eMA * (dayCount - 1) / (dayCount + 1) + close * 2 / (dayCount + 1);
                }
                emaList.add(eMA);
            }
        }

        return emaList;
    }

    public static List<Double> calculateSarList(float step, float maxStep, List<KLineFullData> list) {
        ArrayList<Double> sarList = new ArrayList<>();
//记录是否初始化过
        double INIT_VALUE = -100;
        //加速因子
        double af = 0;
        //极值
        double ep = INIT_VALUE;
        //判断是上涨还是下跌  false：下跌
        boolean lasttrend = false;
        double SAR = 0;

        for (int i = 0; i < list.size()-1; i++) {
            //上一个周期的sar
            double priorSAR = SAR;
            KLineFullData item = list.get(i);
            if (lasttrend) {
                //上涨
                if (ep == INIT_VALUE || ep < item.getHigh()) {
                    //重新初始化值
                    ep = item.getHigh();
                    af = Math.min(af + step, maxStep);
                }
                SAR = priorSAR + af * (ep - priorSAR);
                double lowestPrior2Lows = Math.min(list.get(Math.max(1, i) - 1).getLow(), list.get(i).getLow());
                if (SAR > list.get(i + 1).getLow()) {
                    SAR = ep;
                    //重新初始化值
                    af = 0;
                    ep = INIT_VALUE;
                    lasttrend = !lasttrend;

                } else if (SAR > lowestPrior2Lows) {
                    SAR = lowestPrior2Lows;
                }
            } else {
                if (ep == INIT_VALUE || ep > list.get(i).getLow()) {
                    //重新初始化值
                    ep = list.get(i).getLow();
                    af = Math.min(af + step, maxStep);
                }
                SAR = priorSAR + af * (ep - priorSAR);
                double highestPrior2Highs = Math.max(list.get(Math.max(1, i) - 1).getHigh(), list.get(i).getHigh());
                if (SAR < list.get(i + 1).getHigh()) {
                    SAR = ep;
                    //重新初始化值
                    af = 0;
                    ep = INIT_VALUE;
                    lasttrend = !lasttrend;

                } else if (SAR < highestPrior2Highs) {
                    SAR = highestPrior2Highs;
                }
            }
            sarList.add(SAR);
        }

        //确保和 传入的list size一致，
        int size = list.size() - sarList.size();
        for (int i = 0; i < size; i++) {
            sarList.add(0,Double.NaN);
        }

        return sarList;
    }

    /**
     * @return ref 20180913-Volume Profile算法.pdf
     * 下闭区间，上开区间
     */
    public static List<VolumeProfileEntity> calculateVolumeProfileList(int formX, int toX,
                                                                       double lowest, double highest,
                                                                       List<KLineFullData> klineData) {

        int N = 62;
        double TotalRange = highest - lowest;
        double step = TotalRange / N;
        List<VolumeProfileEntity> rangeList = new ArrayList<>(N);
        //存储区间
        for (int i = 0; i < N; i++) {
            VolumeProfileEntity rangeItemBean = new VolumeProfileEntity();
            rangeItemBean.setX_n(lowest + step * i);
            rangeItemBean.setX_n_1(lowest + step * (i + 1));
            rangeItemBean.setR(i);
            rangeList.add(rangeItemBean);
        }


        //累计每根k线交易量
        toX=Math.min(toX,klineData.size()-1);
        formX=Math.max(formX,0);
        for (int i = formX; i <= toX; i++) {

            KLineFullData kData = klineData.get(i);
            double kVol = kData.getVol();
            int Rlow = 0, Rhigh = 0;//文档中的R1,R5

            //每根k线落入的区间范围
            for (VolumeProfileEntity volumeProfileEntity : rangeList) {
                //最高区间判断
                if (kData.getHigh() > volumeProfileEntity.getX_n() && kData.getHigh() <= volumeProfileEntity.getX_n_1()) {
                    Rhigh = volumeProfileEntity.getR();

                }
                //判断最高区间
                if (kData.getLow() > volumeProfileEntity.getX_n() && kData.getLow() <= volumeProfileEntity.getX_n_1()) {
                    Rlow = volumeProfileEntity.getR();
                }
            }

            if (Rhigh == Rlow) {
                rangeList.get(Rhigh).setVolume(rangeList.get(Rhigh).getVolume()+kVol);
            } else {
                for (int i1 = Rlow; i1 <= Rhigh; i1++) {
                    if (i1 == Rlow) {
                        double v = (kVol / (kData.getHigh() - kData.getLow())) * (rangeList.get(i1).getX_n_1() - kData.getLow());
                        rangeList.get(i1).setVolume(rangeList.get(i1).getVolume()+v);
                    } else if (i1 == Rhigh) {
                        double v = (kVol / (kData.getHigh() - kData.getLow())) * (kData.getHigh() - rangeList.get(i1).getX_n());
                        rangeList.get(i1).setVolume(rangeList.get(i1).getVolume()+v);
                    } else {
                        double v = (kVol / (kData.getHigh() - kData.getLow()) ) * step;
                        rangeList.get(i1).setVolume(rangeList.get(i1).getVolume()+v);
                    }
                }
            }


        }

        //挑选出最长的做标记
        VolumeProfileEntity max = Collections.max(rangeList);
        max.setLongest(true);
        VolumeProfileEntity.setLongestValue(max.getVolume());


        return rangeList;


    }




}
