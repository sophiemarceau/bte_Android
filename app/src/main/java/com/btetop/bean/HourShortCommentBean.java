package com.btetop.bean;

public class HourShortCommentBean {


    /**
     * id : 16
     * exchange : binance
     * symbol : BTC
     * pair : USDT
     * klineDateTime : 1529888400000
     * klineDate : 2018-06-25 09:00:00
     * content : 今日暴涨zzz
     * status : 1
     * createrId : null
     * createTime : 2018-06-25 10:26:25
     * dateTime : null
     * markerPlace : null
     */

    private int id;
    private String exchange;
    private String symbol;
    private String pair;
    private long klineDateTime;
    private String klineDate;
    private String content;
    private int status;
    private Object createrId;
    private String createTime;
    private Object dateTime;
    private int markerPlace;

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public long getKlineDateTime() {
        return klineDateTime;
    }

    public void setKlineDateTime(long klineDateTime) {
        this.klineDateTime = klineDateTime;
    }

    public String getKlineDate() {
        return klineDate;
    }

    public void setKlineDate(String klineDate) {
        this.klineDate = klineDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Object createrId) {
        this.createrId = createrId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getDateTime() {
        return dateTime;
    }

    public void setDateTime(Object dateTime) {
        this.dateTime = dateTime;
    }

    public int getMarkerPlace() {
        return markerPlace;
    }

}
