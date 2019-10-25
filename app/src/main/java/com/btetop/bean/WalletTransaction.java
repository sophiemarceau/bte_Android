package com.btetop.bean;

import com.blankj.utilcode.util.TimeUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class WalletTransaction implements Serializable {

    private int nextPage;
    private List<WalletTractionDetail> details;

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<WalletTractionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<WalletTractionDetail> details) {
        this.details = details;
    }

    public class WalletTractionDetail implements Serializable{
        public String type;
        public BigDecimal amount;
        public String date;
        public long timestamp;

        public BigDecimal getAmount() {
            return amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }



        public String getTime1String() {
            DateFormat format=new SimpleDateFormat("HH:mm");
            return TimeUtils.millis2String(timestamp,format);
        }

        public String getTime2String() {
            DateFormat format = new SimpleDateFormat("MM/dd");
            return TimeUtils.millis2String(timestamp, format);
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

    }



}
