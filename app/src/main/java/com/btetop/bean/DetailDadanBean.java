package com.btetop.bean;

import com.blankj.utilcode.util.TimeUtils;
import com.guoziwei.klinelib.util.DoubleUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailDadanBean {
    private static final String TAG = "DetailDadanBean";
    public final static String RENDER_HEADER1="detail_dadan_header1";//近7日大单卖出
    public final static String RENDER_HEADER2="detail_dadan_header2";//数量 大单卖出 价格
    public final static String RENDER_HEADER2_DETAIL="detail_dadan_header2_detail";
    public final static String RENDER_HEADER2_DETAIL_FOOT = "detail_dadan_header2_foot";

    public final static String RENDER_REMARK="detail_dadan_remark";
    public final static String RENDER_HEADER3="detail_dadan_header3";//时间 交易所 类型 价格 数量
    public final static String RENDER_HEADER3_DETAIL="detail_dadan_header3_DETAIL";




    /**
     * hugeList : [{"date":"2018-09-06 16:38:17","directionDesc":"买入","totalPrice":"¥1900万","price":"$1881.19","count":"1672个","exchange":"binance","timestamp":1536223097620},{"date":"2018-09-06 16:38:17","directionDesc":"卖出","totalPrice":"¥1800万","price":"$1782.18","count":"1584个","exchange":"okex","timestamp":1536223097620},{"date":"2018-09-06 16:38:17","directionDesc":"卖出","totalPrice":"¥1700万","price":"$1683.17","count":"1496个","exchange":"bitfinex","timestamp":1536223097620},{"date":"2018-09-06 16:38:17","directionDesc":"卖出","totalPrice":"¥1600万","price":"$1584.16","count":"1408个","exchange":"huobi","timestamp":1536223097620},{"date":"2018-09-06 16:38:17","directionDesc":"卖出","totalPrice":"¥1500万","price":"$1485.15","count":"1320个","exchange":"huobi","timestamp":1536223097620},{"date":"2018-09-06 16:38:17","directionDesc":"卖出","totalPrice":"¥1400万","price":"$1386.14","count":"1232个","exchange":"huobi","timestamp":1536223097620},{"date":"2018-09-06 16:38:17","directionDesc":"卖出","totalPrice":"¥1300万","price":"$1287.13","count":"1144个","exchange":"huobi","timestamp":1536223097620},{"date":"2018-09-06 16:38:17","directionDesc":"卖出","totalPrice":"¥1200万","price":"$1188.1200000000001","count":"1056个","exchange":"huobi","timestamp":1536223097620},{"date":"2018-09-06 16:38:17","directionDesc":"卖出","totalPrice":"¥1100万","price":"$1089.1100000000001","count":"968个","exchange":"huobi","timestamp":1536223097620},{"date":"2018-09-06 16:38:17","directionDesc":"卖出","totalPrice":"¥1000万","price":"$990.1","count":"880个","exchange":"huobi","timestamp":1536223097620}]
     * rankList : [{"price":18981,"bidAmount":100,"askAmount":2345},{"price":17982,"bidAmount":100,"askAmount":2345},{"price":16983,"bidAmount":100,"askAmount":2345},{"price":15984,"bidAmount":100,"askAmount":2345},{"price":14985,"bidAmount":100,"askAmount":2345},{"price":13986,"bidAmount":100,"askAmount":2345},{"price":12987,"bidAmount":100,"askAmount":2345},{"price":11988,"bidAmount":100,"askAmount":2345},{"price":10989,"bidAmount":100,"askAmount":2345},{"price":9990,"bidAmount":100,"askAmount":2345}]
     * hugeAsk : 99
     * remark : *超级深度为huobi、binance、okex、bitfinex的btc/usdt交易对的深度合并
     * hugeBid : 100
     */

    private float hugeAsk;
    private String remark;
    private float hugeBid;
    private List<HugeListBean> hugeList;
    private List<RankListBean> rankList;

    public List<MergeDetailDadanBean> getAllData(){

        List<MergeDetailDadanBean> list = new ArrayList<>();
        MergeDetailDadanBean bean=new MergeDetailDadanBean();
        //
        bean.setRenderStyle(RENDER_HEADER1);
        bean.setHeader1Ask(hugeAsk);
        bean.setHeaer1Bid(hugeBid);
        bean.setHeader2DetailMax(0);
        list.add(bean);
        //数量 大单卖出 价格
        if (rankList!=null && rankList.size()>0) {
            //
            MergeDetailDadanBean bean1=new MergeDetailDadanBean();
            bean1.setRenderStyle(RENDER_HEADER2);
            list.add(bean1);
            for (int i = 0; i < rankList.size(); i++) {
                RankListBean rankListBean = rankList.get(i);
                MergeDetailDadanBean bean2=new MergeDetailDadanBean();
                bean2.setRenderStyle(RENDER_HEADER2_DETAIL);
                bean2.setHeader2DetailPrice(rankListBean.getPrice());
                bean2.setHeader2DetailAskAmount(rankListBean.getAskAmount());
                bean2.setHeader2DetailBidAmount(rankListBean.getBidAmount());

                if (i != rankList.size() - 1) {
                    if (bean2.getHeader2DetailMax() < rankListBean.getAskAmount()) {
                        bean2.setHeader2DetailMax(rankListBean.getAskAmount());
                    }

                    if (bean2.getHeader2DetailMax() < rankListBean.getAskAmount()) {

                        bean2.setHeader2DetailMax(rankListBean.getBidAmount());
                    }
                }


                //最后一个特殊处理
                if (i == rankList.size() - 1) {
                    bean2.setRenderStyle(RENDER_HEADER2_DETAIL_FOOT);
                }
                list.add(bean2);
            }

            //
            MergeDetailDadanBean bean3=new MergeDetailDadanBean();
            bean3.setRenderStyle(RENDER_REMARK);
            bean3.setMarker(remark);
            list.add(bean3);

        }
        //时间 交易所 类型 价格 数量
        if (hugeList!=null && hugeList.size()>0) {
            MergeDetailDadanBean bean4=new MergeDetailDadanBean();
            bean4.setRenderStyle(RENDER_HEADER3);
            list.add(bean4);
            //
            for (HugeListBean hugeListBean : hugeList) {
                MergeDetailDadanBean bean5=new MergeDetailDadanBean();
                bean5.setRenderStyle(RENDER_HEADER3_DETAIL);
                bean5.setTimeStamp(hugeListBean.timestamp);
                bean5.setExchange(hugeListBean.exchange);
                bean5.setDirectionDesc(hugeListBean.directionDesc);
                bean5.setDirectionName(hugeListBean.directionName);
                bean5.setPrice(hugeListBean.price);
                bean5.setCount(hugeListBean.count);
                bean5.setCountPrice(hugeListBean.totalPrice);
                bean5.setUnit(hugeListBean.getUnit());
                list.add(bean5);
            }

        }
        return list;
    }

    public double getHugeAsk() {
        return hugeAsk;
    }

    public void setHugeAsk(int hugeAsk) {
        this.hugeAsk = hugeAsk;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getHugeBid() {
        return hugeBid;
    }

    public void setHugeBid(int hugeBid) {
        this.hugeBid = hugeBid;
    }

    public List<HugeListBean> getHugeList() {
        return hugeList;
    }

    public void setHugeList(List<HugeListBean> hugeList) {
        this.hugeList = hugeList;
    }

    public List<RankListBean> getRankList() {
        return rankList;
    }

    public void setRankList(List<RankListBean> rankList) {
        this.rankList = rankList;
    }

    public static class HugeListBean {
        /**
         * date : 2018-09-06 16:38:17
         * directionDesc : 买入
         * totalPrice : ¥1900万
         * price : $1881.19
         * count : 1672个
         * exchange : binance
         * timestamp : 1536223097620
         */

        private String date;
        private String directionDesc;
        private float totalPrice;


        private float price;
        private float count;
        private String exchange;
        private long timestamp;
        private String directionName;
        private String unit;


        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(float totalPrice) {
            this.totalPrice = totalPrice;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public double getCount() {
            return count;
        }

        public void setCount(float count) {
            this.count = count;
        }

        public String getDirectionName() {
            return directionName;
        }

        public void setDirectionName(String directionName) {
            this.directionName = directionName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDirectionDesc() {
            return directionDesc;
        }

        public void setDirectionDesc(String directionDesc) {
            this.directionDesc = directionDesc;
        }


        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class RankListBean {
        /**
         * price : 18981
         * bidAmount : 100
         * askAmount : 2345
         */

        private float price;
        private float bidAmount;
        private float askAmount;

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getBidAmount() {
            return bidAmount;
        }

        public void setBidAmount(float bidAmount) {
            this.bidAmount = bidAmount;
        }

        public float getAskAmount() {
            return askAmount;
        }

        public void setAskAmount(float askAmount) {
            this.askAmount = askAmount;
        }
    }

    public static class MergeDetailDadanBean{
        //根据这个字段选择渲染
        private String renderStyle;
        private float header1Ask;
        private float heaer1Bid;

        private float header2DetailPrice;
        private float header2DetailBidAmount;
        private float header2DetailAskAmount;


        private static  float header2DetailMax;

        private String marker;


        private long timeStamp;
        private String exchange;
        private String directionDesc;//买入 卖出
        private float price;
        private float count;
        private float countPrice;
        private String directionName;
        private String unit;

        public void setHeader1Ask(float header1Ask) {
            this.header1Ask = header1Ask;
        }

        public float getHeader2DetailMax() {
            return header2DetailMax;
        }

        public void setHeader2DetailMax(float header2DetailMax) {
            this.header2DetailMax = header2DetailMax;
        }

        public double getHeaer1Bid() {
            return heaer1Bid;
        }

        public void setHeaer1Bid(float heaer1Bid) {
            this.heaer1Bid = heaer1Bid;
        }

        public double getHeader2DetailPrice() {
            return header2DetailPrice;
        }

        public void setHeader2DetailPrice(float header2DetailPrice) {
            this.header2DetailPrice = header2DetailPrice;
        }

        public double getHeader2DetailBidAmount() {
            return header2DetailBidAmount;
        }

        public void setHeader2DetailBidAmount(float header2DetailBidAmount) {
            this.header2DetailBidAmount = header2DetailBidAmount;
        }

        public double getHeader2DetailAskAmount() {
            return header2DetailAskAmount;
        }

        public String getHeader2DetailAskAmountString() {

            String hi = DoubleUtil.getDaDanFormat(header2DetailAskAmount);
            return hi;
        }

        public String getHeader2DetailBidAmountString() {

            String hi = DoubleUtil.getDaDanFormat(header2DetailBidAmount);
            return hi;
        }


        public void setHeader2DetailAskAmount(float header2DetailAskAmount) {
            this.header2DetailAskAmount = header2DetailAskAmount;
        }


        public String getMarker() {
            return marker;
        }

        public void setMarker(String marker) {
            this.marker = marker;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getDirectionDesc() {
            return directionDesc;
        }

        public void setDirectionDesc(String directionDesc) {
            this.directionDesc = directionDesc;
        }

        public double getPrice() {
            return price;
        }

        public double getCount() {
            return count;
        }

        public double getCountPrice() {
            return countPrice;
        }

        public String getDirectionName() {
            return directionName;
        }

        public void setDirectionName(String directionName) {
            this.directionName = directionName;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void setCount(float count) {
            this.count = count;
        }

        public void setCountPrice(float countPrice) {
            this.countPrice = countPrice;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getRenderStyle() {
            return renderStyle;
        }

        public void setRenderStyle(String renderStyle) {
            this.renderStyle = renderStyle;
        }

        public double getHeader1Ask() {
            return header1Ask;
        }

        public void setHeader1Ask(int header1Ask) {
            this.header1Ask = header1Ask;
        }


        public String getTime1String() {
            DateFormat format = new SimpleDateFormat("HH:mm");
            return TimeUtils.millis2String(timeStamp, format);
        }

        public String getTime2String() {
            DateFormat format = new SimpleDateFormat("MM/dd");
            return TimeUtils.millis2String(timeStamp, format);
        }


        public String getHeader1AskString() {
            String daDanFormat = DoubleUtil.getDaDanFormat(header1Ask);
            return daDanFormat;
        }

        public String getHeader1BidString() {

            String daDanFormat = DoubleUtil.getDaDanFormat(heaer1Bid);
            return daDanFormat;
        }

        public String getHeader1ContentPrice() {
            String h = DoubleUtil.getDadanPriceFormat(header2DetailPrice);
            return h;
        }

        public String getHeader2ContentPrice() {
            String daDanFormat = DoubleUtil.getDadanPriceFormat(price);
            return daDanFormat;
        }

        public String getHeader2ContentCountPrice() {

            String h = DoubleUtil.getDaDanFormat(countPrice);
            return unit + h;
        }
    }

}
