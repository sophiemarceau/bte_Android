package com.btetop.bean;

import com.btetop.utils.FormatUtil;

public class MiningSchedule {

    /**
     * amount : 100
     * message : 算力收集中
     * digStart : 2018-11-12 00:00:00
     * status : 1
     */

    private double amount;
    private String message;
    private String digStart;
    private int status;
    private String income;

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getAmount() {
        return FormatUtil.doubleTrans1(amount);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDigStart() {
        return digStart;
    }

    public void setDigStart(String digStart) {
        this.digStart = digStart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
