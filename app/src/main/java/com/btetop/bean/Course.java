package com.btetop.bean;

import com.btetop.utils.CommonUtils;

public class Course {

    private double income;
    private int userCount;
    private int count;
    private int point;
    private int notice;
    private int agencyCount;
    private String Type;
    private int Group;

    public int getPoint() {
        return point;
    }

    public int getGroup() {
        return Group;
    }

    public void setGroup(int group) {
        Group = group;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getIncome() {
        String getincome = CommonUtils.formatThousand(income);
        return getincome;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getUserCount() {
        String getUserCount = CommonUtils.formatThousand(userCount);
        return getUserCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getCount() {
        String getCount = CommonUtils.formatThousand(count);
        return getCount;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public void setPoint(int point) {
        this.point = point;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }


    public String getAgencyCount() {
        String getAgencyCount = CommonUtils.formatThousand(agencyCount);
        return getAgencyCount;
    }

    public void setAgencyCount(int agencyCount) {
        this.agencyCount = agencyCount;
    }


}
