package com.btetop.bean;

import java.util.List;

public class MarketCoinPairBean {

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : {"nextPage":1,"list":[{"id":396,"base":"AAC","quote":"ETH","exchange":"okex","industry":"币圈生态","amount":1.0149219545328839E7,"price":0.138546,"hotIndex":96.03,"airIndex":59.41,
     * "change":0.0712,"status":1,"createTime":1542609603000},{"id":188,"base":"STC","quote":"USDT","exchange":"okex","industry":"底层公链 内容","amount":3814959.7980433586,"price":0.0329,
     * "hotIndex":92.56,"airIndex":62.67,"change":0.119,"status":1,"createTime":1542609603000},{"id":27,"base":"RCT","quote":"ETH","exchange":"okex","industry":"防伪溯源","amount":4500677.75622749,
     * "price":0.030788,"hotIndex":91.32,"airIndex":64.4,"change":0.0056,"status":1,"createTime":1542609603000},{"id":39,"base":"ACE","quote":"BTC","exchange":"okex","industry":"娱乐",
     * "amount":944327.7016922293,"price":0.2753670408,"hotIndex":90.65,"airIndex":68.2,"change":0.0071,"status":1,"createTime":1542609603000},{"id":2,"base":"TRIO","quote":"ETH","exchange":"okex",
     * "industry":"旅游","amount":7284862.481165767,"price":0.094471,"hotIndex":85.19,"airIndex":64.53,"change":0.0244,"status":1,"createTime":1542609603000},{"id":521,"base":"ORS","quote":"ETH",
     * "exchange":"okex","industry":"预测博彩 防伪溯源","amount":1.0087148435448688E7,"price":0.276017,"hotIndex":84.04,"airIndex":56.36,"change":0.021,"status":1,"createTime":1542609603000},{"id":558,
     * "base":"HPB","quote":"ETH","exchange":"okex","industry":"底层公链","amount":1.5410858077225937E7,"price":12.898452,"hotIndex":83.87,"airIndex":52.92,"change":0.0031,"status":1,
     * "createTime":1542609603000},{"id":503,"base":"GTO","quote":"ETH","exchange":"okex","industry":"内容","amount":1.1841558225729164E8,"price":1.09908,"hotIndex":82.96,"airIndex":44.41,
     * "change":0.0248,"status":1,"createTime":1542609603000},{"id":504,"base":"TNB","quote":"ETH","exchange":"okex","industry":"内容","amount":6675793.9684673995,"price":0.168732,"hotIndex":82.38,
     * "airIndex":46.72,"change":0.0306,"status":1,"createTime":1542609603000},{"id":89,"base":"SWFTC","quote":"ETH","exchange":"okex","industry":"跨链 钱包","amount":3.209663511661216E7,
     * "price":0.041968,"hotIndex":82.3,"airIndex":54.63,"change":0.0081,"status":1,"createTime":1542609603000}]}
     */


    /**
     * nextPage : 1
     * list : [{"id":396,"base":"AAC","quote":"ETH","exchange":"okex","industry":"币圈生态","amount":1.0149219545328839E7,"price":0.138546,"hotIndex":96.03,"airIndex":59.41,"change":0.0712,
     * "status":1,"createTime":1542609603000},{"id":188,"base":"STC","quote":"USDT","exchange":"okex","industry":"底层公链 内容","amount":3814959.7980433586,"price":0.0329,"hotIndex":92.56,
     * "airIndex":62.67,"change":0.119,"status":1,"createTime":1542609603000},{"id":27,"base":"RCT","quote":"ETH","exchange":"okex","industry":"防伪溯源","amount":4500677.75622749,"price":0.030788,
     * "hotIndex":91.32,"airIndex":64.4,"change":0.0056,"status":1,"createTime":1542609603000},{"id":39,"base":"ACE","quote":"BTC","exchange":"okex","industry":"娱乐","amount":944327.7016922293,
     * "price":0.2753670408,"hotIndex":90.65,"airIndex":68.2,"change":0.0071,"status":1,"createTime":1542609603000},{"id":2,"base":"TRIO","quote":"ETH","exchange":"okex","industry":"旅游",
     * "amount":7284862.481165767,"price":0.094471,"hotIndex":85.19,"airIndex":64.53,"change":0.0244,"status":1,"createTime":1542609603000},{"id":521,"base":"ORS","quote":"ETH",
     * "exchange":"okex","industry":"预测博彩 防伪溯源","amount":1.0087148435448688E7,"price":0.276017,"hotIndex":84.04,"airIndex":56.36,"change":0.021,"status":1,"createTime":1542609603000},{"id":558,
     * "base":"HPB","quote":"ETH","exchange":"okex","industry":"底层公链","amount":1.5410858077225937E7,"price":12.898452,"hotIndex":83.87,"airIndex":52.92,"change":0.0031,"status":1,
     * "createTime":1542609603000},{"id":503,"base":"GTO","quote":"ETH","exchange":"okex","industry":"内容","amount":1.1841558225729164E8,"price":1.09908,"hotIndex":82.96,"airIndex":44.41,
     * "change":0.0248,"status":1,"createTime":1542609603000},{"id":504,"base":"TNB","quote":"ETH","exchange":"okex","industry":"内容","amount":6675793.9684673995,"price":0.168732,
     * "hotIndex":82.38,"airIndex":46.72,"change":0.0306,"status":1,"createTime":1542609603000},{"id":89,"base":"SWFTC","quote":"ETH","exchange":"okex","industry":"跨链 钱包",
     * "amount":3.209663511661216E7,"price":0.041968,"hotIndex":82.3,"airIndex":54.63,"change":0.0081,"status":1,"createTime":1542609603000}]
     */

    private int nextPage;
    private List<ListBean> list;

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 396
         * base : AAC
         * quote : ETH
         * exchange : okex
         * industry : 币圈生态
         * amount : 1.0149219545328839E7
         * price : 0.138546
         * hotIndex : 96.03
         * airIndex : 59.41
         * change : 0.0712
         * status : 1
         * createTime : 1542609603000
         */

        private int id;
        private String base;
        private String quote;
        private String exchange;
        private String industry;
        private double amount;
        private double price;
        private double hotIndex;
        private double airIndex;
        private double change;
        private int status;
        private long createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getHotIndex() {
            return hotIndex;
        }

        public void setHotIndex(double hotIndex) {
            this.hotIndex = hotIndex;
        }

        public double getAirIndex() {
            return airIndex;
        }

        public void setAirIndex(double airIndex) {
            this.airIndex = airIndex;
        }

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
