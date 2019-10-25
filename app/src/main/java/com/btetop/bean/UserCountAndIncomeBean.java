package com.btetop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ouyou on 2018/6/19.
 */

public class UserCountAndIncomeBean {
    public static class UserCountAndIncomeData implements Serializable {
        private double totalIncome;
        private WeeklySummaryBean weeklySummary;
        private int userCount;
        private int recentRecommendCount;
        private List<WeeklyListBean> weeklyList;

        public double getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(double totalIncome) {
            this.totalIncome = totalIncome;
        }

        public WeeklySummaryBean getWeeklySummary() {
            return weeklySummary;
        }

        public void setWeeklySummary(WeeklySummaryBean weeklySummary) {
            this.weeklySummary = weeklySummary;
        }

        public int getUserCount() {
            return userCount;
        }

        public void setUserCount(int userCount) {
            this.userCount = userCount;
        }

        public int getRecentRecommendCount() {
            return recentRecommendCount;
        }

        public void setRecentRecommendCount(int recentRecommendCount) {
            this.recentRecommendCount = recentRecommendCount;
        }

        public List<WeeklyListBean> getWeeklyList() {
            return weeklyList;
        }

        public void setWeeklyList(List<WeeklyListBean> weeklyList) {
            this.weeklyList = weeklyList;
        }

        public static class WeeklySummaryBean {
            /**
             * id : 4
             * title : 撸庄狗本周战报（06月25日-07月01日）
             * coinCount : 2
             * coinProfit : 0.07
             * maxCoin : ABT
             * maxProfit : 0.07
             * lowCoin : null
             * lowProfit : null
             * content : 【撸庄狗】上周盈利0.07%，共撸2个优质币；其中：ABT本周收益最多，最高涨幅达0.07%。其他币种收益如下：
             * start : 2018-06-25 00:00:00
             * end : 2018-07-01 00:00:00
             * pageView : 0
             * userView : null
             * ipView : null
             * createTime : 2018-07-02 12:23:36
             * createrId : null
             * pushStatus : 0
             * pushDate : null
             * pushUserId : null
             * pushUserName : null
             * asset : ABT,ABT
             * status : 1
             */

            private int id;
            private String title;
            private int coinCount;
            private double coinProfit;
            private String maxCoin;
            private double maxProfit;
            private Object lowCoin;
            private Object lowProfit;
            private String content;
            private String start;
            private String end;
            private int pageView;
            private Object userView;
            private Object ipView;
            private String createTime;
            private Object createrId;
            private int pushStatus;
            private Object pushDate;
            private Object pushUserId;
            private Object pushUserName;
            private String asset;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(int coinCount) {
                this.coinCount = coinCount;
            }

            public double getCoinProfit() {
                return coinProfit;
            }

            public void setCoinProfit(double coinProfit) {
                this.coinProfit = coinProfit;
            }

            public String getMaxCoin() {
                return maxCoin;
            }

            public void setMaxCoin(String maxCoin) {
                this.maxCoin = maxCoin;
            }

            public double getMaxProfit() {
                return maxProfit;
            }

            public void setMaxProfit(double maxProfit) {
                this.maxProfit = maxProfit;
            }

            public Object getLowCoin() {
                return lowCoin;
            }

            public void setLowCoin(Object lowCoin) {
                this.lowCoin = lowCoin;
            }

            public Object getLowProfit() {
                return lowProfit;
            }

            public void setLowProfit(Object lowProfit) {
                this.lowProfit = lowProfit;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public int getPageView() {
                return pageView;
            }

            public void setPageView(int pageView) {
                this.pageView = pageView;
            }

            public Object getUserView() {
                return userView;
            }

            public void setUserView(Object userView) {
                this.userView = userView;
            }

            public Object getIpView() {
                return ipView;
            }

            public void setIpView(Object ipView) {
                this.ipView = ipView;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getCreaterId() {
                return createrId;
            }

            public void setCreaterId(Object createrId) {
                this.createrId = createrId;
            }

            public int getPushStatus() {
                return pushStatus;
            }

            public void setPushStatus(int pushStatus) {
                this.pushStatus = pushStatus;
            }

            public Object getPushDate() {
                return pushDate;
            }

            public void setPushDate(Object pushDate) {
                this.pushDate = pushDate;
            }

            public Object getPushUserId() {
                return pushUserId;
            }

            public void setPushUserId(Object pushUserId) {
                this.pushUserId = pushUserId;
            }

            public Object getPushUserName() {
                return pushUserName;
            }

            public void setPushUserName(Object pushUserName) {
                this.pushUserName = pushUserName;
            }

            public String getAsset() {
                return asset;
            }

            public void setAsset(String asset) {
                this.asset = asset;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class WeeklyListBean {
            /**
             * id : 2
             * exchange : okex
             * baseAsset : ABT
             * quoteAsset : USDT
             * price : 0.5504
             * cnyPrice : null
             * recommendPrice : 0.5335
             * recommendCnyPrice : null
             * maxPrice : 0.6083
             * maxCnyPrice : null
             * yes : 0
             * no : 0
             * status : 1
             * createrId : 2
             * creater : 神仙老虎茶
             * createTime : null
             * date : 06/25 11:29
             * avgNumber : 14.02
             * param : 14.02
             * operateContent : null
             * buySale : null
             * recommendStatus : null
             * dogType : null
             * recommendTime : null
             * likeActive : false
             * hateActive : false
             * sum : 14.02
             */

            private int id;
            private String exchange;
            private String baseAsset;
            private String quoteAsset;
            private double price;
            private Object cnyPrice;
            private double recommendPrice;
            private Object recommendCnyPrice;
            private double maxPrice;
            private Object maxCnyPrice;
            private int yes;
            private int no;
            private int status;
            private int createrId;
            private String creater;
            private Object createTime;
            private String date;
            private String avgNumber;
            private double param;
            private Object operateContent;
            private Object buySale;
            private Object recommendStatus;
            private Object dogType;
            private Object recommendTime;
            private boolean likeActive;
            private boolean hateActive;
            private double sum;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getExchange() {
                return exchange;
            }

            public void setExchange(String exchange) {
                this.exchange = exchange;
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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public Object getCnyPrice() {
                return cnyPrice;
            }

            public void setCnyPrice(Object cnyPrice) {
                this.cnyPrice = cnyPrice;
            }

            public double getRecommendPrice() {
                return recommendPrice;
            }

            public void setRecommendPrice(double recommendPrice) {
                this.recommendPrice = recommendPrice;
            }

            public Object getRecommendCnyPrice() {
                return recommendCnyPrice;
            }

            public void setRecommendCnyPrice(Object recommendCnyPrice) {
                this.recommendCnyPrice = recommendCnyPrice;
            }

            public double getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(double maxPrice) {
                this.maxPrice = maxPrice;
            }

            public Object getMaxCnyPrice() {
                return maxCnyPrice;
            }

            public void setMaxCnyPrice(Object maxCnyPrice) {
                this.maxCnyPrice = maxCnyPrice;
            }

            public int getYes() {
                return yes;
            }

            public void setYes(int yes) {
                this.yes = yes;
            }

            public int getNo() {
                return no;
            }

            public void setNo(int no) {
                this.no = no;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCreaterId() {
                return createrId;
            }

            public void setCreaterId(int createrId) {
                this.createrId = createrId;
            }

            public String getCreater() {
                return creater;
            }

            public void setCreater(String creater) {
                this.creater = creater;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getAvgNumber() {
                return avgNumber;
            }

            public void setAvgNumber(String avgNumber) {
                this.avgNumber = avgNumber;
            }

            public double getParam() {
                return param;
            }

            public void setParam(double param) {
                this.param = param;
            }

            public Object getOperateContent() {
                return operateContent;
            }

            public void setOperateContent(Object operateContent) {
                this.operateContent = operateContent;
            }

            public Object getBuySale() {
                return buySale;
            }

            public void setBuySale(Object buySale) {
                this.buySale = buySale;
            }

            public Object getRecommendStatus() {
                return recommendStatus;
            }

            public void setRecommendStatus(Object recommendStatus) {
                this.recommendStatus = recommendStatus;
            }

            public Object getDogType() {
                return dogType;
            }

            public void setDogType(Object dogType) {
                this.dogType = dogType;
            }

            public Object getRecommendTime() {
                return recommendTime;
            }

            public void setRecommendTime(Object recommendTime) {
                this.recommendTime = recommendTime;
            }

            public boolean isLikeActive() {
                return likeActive;
            }

            public void setLikeActive(boolean likeActive) {
                this.likeActive = likeActive;
            }

            public boolean isHateActive() {
                return hateActive;
            }

            public void setHateActive(boolean hateActive) {
                this.hateActive = hateActive;
            }

            public double getSum() {
                return sum;
            }

            public void setSum(double sum) {
                this.sum = sum;
            }
        }
    }
}
