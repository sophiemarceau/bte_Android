package com.btetop.bean;

import java.io.Serializable;

public class UserInfo implements Serializable {


    private String tel;
    private String email;
    private String name;
    private String avator;
    private int reset;
    private int isLzDogUser;
    private int isBandDogUser;
    private int isFutureDogUser;
    private int point;
    private String inviteCode;
    private String wxBindStatus;
    private Integer isOpenBoard;

    public String getWxBindStatus() {
        return wxBindStatus;
    }

    public void setWxBindStatus(String wxBindStatus) {
        this.wxBindStatus = wxBindStatus;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public int getReset() {
        return reset;
    }

    public void setReset(int reset) {
        this.reset = reset;
    }

    public int getIsLzDogUser() {
        return isLzDogUser;
    }

    public void setIsLzDogUser(int isLzDogUser) {
        this.isLzDogUser = isLzDogUser;
    }

    public int getIsBandDogUser() {
        return isBandDogUser;
    }

    public void setIsBandDogUser(int isBandDogUser) {
        this.isBandDogUser = isBandDogUser;
    }

    public int getIsFutureDogUser() {
        return isFutureDogUser;
    }

    public void setIsFutureDogUser(int isFutureDogUser) {
        this.isFutureDogUser = isFutureDogUser;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public boolean isOpenBoard() {
        if (isOpenBoard == null) {
            return false;
        }

        if (isOpenBoard == 1) {
            return true;
        } else {
            return false;
        }
    }
}
