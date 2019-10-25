package com.btetop.message;

import android.os.Bundle;

/**
 * Created by Administrator on 2018/3/5.
 */


//用于传递主视图间的切换
public class RouteMessage {
    private String message;
    private Bundle bundle;

    public final static String MESSAGE_LOGIN_SUCCESS = "登陆成功";
    public final static String MESSAGE_LOGIN_OUT_SUCCESS = "退出登陆成功";

    public final static String MESSAGE_POP_BACK_PAGE = "回退一个窗口";
    public final static String MESSAGE_SHOW_HOME_PAGE = "显示首页";
    public final static String MESSAGE_SHOW_CE_LUE_PAGE = "显示策略页面";
    public final static String MESSAGE_SHOW_MARKET_DATA = "显示行情页面";
    public final static String MESSAGE_SHOW_GET_SCORE = "显示赚积分页面";
    public final static String MESSAGE_SHOW_MY_PAGE = "显示我的账户页面";


    public final static String MESSAGE_SHOW_LU_DOG_PAGE = "显示撸庄狗页面";
    public final static String MESSAGE_SHOW_BO_DOG_PAGE = "显示波段狗页面";
    public final static String MESSAGE_SHOW_RESEARCH_DOG_PAGE = "显示研究狗页面";
    public final static String MESSAGE_SHOW_HEYUE_DOG_PAGE = "显示合约狗页面";
    public final static String MESSAGE_SHOW_DINGPAN_DOG_PAGE = "显示盯盘狗页面";


    public final static String MESSAGE_SHOW_URL = "显示指定url"; //url在bundle中定义


    public RouteMessage(String message, Bundle bundle) {
        this.message = message;
        this.bundle = bundle;
    }


    public RouteMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
