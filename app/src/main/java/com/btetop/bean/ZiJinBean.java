package com.btetop.bean;

import com.guoziwei.klinelib.model.KLineFullData;

import java.util.ArrayList;
import java.util.List;

public class ZiJinBean {

        /**
         * kline : [["07-27",0,4.5677224E8,0,4.5677224E8],["07-28",4.5677224E8,4.5794752E8,4.5677224E8,4.5794752E8],["07-29",4.5794752E8,9.18845856E8,4.5794752E8,9.18845856E8],["07-30",9.18845856E8,8.86401488E8,8.86401488E8,9.18845856E8],["07-31",8.86401488E8,6.80800528E8,6.80800528E8,8.86401488E8],["08-01",6.80800528E8,5.60663008E8,5.60663008E8,6.80800528E8],["08-02",5.60663008E8,5.66721344E8,5.60663008E8,5.66721344E8],["08-03",5.66721344E8,9.52015312E8,5.66721344E8,9.52015312E8],["08-04",9.52015312E8,6.25679232E8,6.25679232E8,9.52015312E8],["08-05",6.25679232E8,6.25523856E8,6.25523856E8,6.25679232E8],["08-06",6.25523856E8,6.39987104E8,6.25523856E8,6.39987104E8],["08-07",6.39987104E8,7.7676712E8,6.39987104E8,7.7676712E8],["08-08",7.7676712E8,4.84750544E8,4.84750544E8,7.7676712E8],["08-09",4.84750544E8,6.49083904E8,4.84750544E8,6.49083904E8],["08-10",6.49083904E8,4.1246352E8,4.1246352E8,6.49083904E8],["08-11",4.1246352E8,1.7958544E8,1.7958544E8,4.1246352E8],["08-12",1.7958544E8,9.9621248E7,9.9621248E7,1.7958544E8],["08-13",9.9621248E7,1.15015424E8,9.9621248E7,1.15015424E8],["08-14",1.15015424E8,1.21004704E8,1.15015424E8,1.21004704E8],["08-15",1.21004704E8,3.0739216E8,1.21004704E8,3.0739216E8],["08-16",3.0739216E8,6.7060016E7,6.7060016E7,3.0739216E8],["08-17",6.7060016E7,4.7545056E7,4.7545056E7,6.7060016E7],["08-18",4.7545056E7,-1.7491752E8,-1.7491752E8,4.7545056E7],["08-19",-1.7491752E8,-7.003872E7,-1.7491752E8,-7.003872E7],["08-20",-7.003872E7,5.1183776E7,-7.003872E7,5.1183776E7],["08-21",5.1183776E7,1.13473616E8,5.1183776E7,1.13473616E8],["08-22",1.13473616E8,-2.73791104E8,-2.73791104E8,1.13473616E8],["08-23",-2.73791104E8,-4.59744304E8,-4.59744304E8,-2.73791104E8],["08-24",-4.59744304E8,-4.02052E8,-4.59744304E8,-4.02052E8]]
         * h1 : {"datetime":0,"count":0,"buyCount":0,"sellCount":0,"netCount":0,"amount":3.52167008E8,"buyAmount":1.99655504E8,"sellAmount":1.52511504E8,"netAmount":4.7144E7}
         * h4 : {"datetime":0,"count":0,"buyCount":0,"sellCount":0,"netCount":0,"amount":8.04546224E8,"buyAmount":4.30002416E8,"sellAmount":3.74543808E8,"netAmount":5.5458608E7}
         * d1 : {"datetime":0,"count":0,"buyCount":0,"sellCount":0,"netCount":0,"amount":5.201813184E9,"buyAmount":2.585010432E9,"sellAmount":2.616802752E9,"netAmount":-3.179232E7}
         */

        private List<TradeBean> trade;
        private List<List<String>> kline;


        public List<TradeBean> getFlowList() {
            return trade;
        }

        public List<KLineFullData> getKlineList(){
            List<KLineFullData> kLineFullDataList=new ArrayList<>();
            for (List<String> strings : kline) {
                KLineFullData data=new KLineFullData();
                data.setDateString(strings.get(0));
                data.setOpen(Double.parseDouble(strings.get(1)));
                data.setClose(Double.parseDouble(strings.get(2)));
                data.setLow(Double.parseDouble(strings.get(3)));
                data.setHigh(Double.parseDouble(strings.get(4)));
                kLineFullDataList.add(data);
            }

            return kLineFullDataList;
        }



        public static class TradeBean {
            private float buy;
            private float sell;
            private String name;
            private float net;

            public float getBuy() {
                return buy;
            }

            public float getSell() {
                return sell;
            }

            public float getNet() {
                return net;
            }

            public String getName() {
                return name;
            }


        }

}
