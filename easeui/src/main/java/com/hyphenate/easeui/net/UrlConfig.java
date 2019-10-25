package com.hyphenate.easeui.net;

public class UrlConfig {

    //全局debug开关
    public static final boolean DEBUG = true;

    /**
     * 基础url  暂时先用哪儿借的的测试数据
     * //
     * //
     * //
     */
    public static String BASE_URL = "https://m.bte.top/";      //正式地址
    public static String H5_BASE_URL = "https://m.bte.top/";//H5正式


//    public final static String BASE_URL = "http://47.94.217.12:18081/";//崔哥接口测试
//    public final static String H5_BASE_URL = "https://l.bte.top/";//楚东阳本地H5测试


    public final static String onLine_url = "https://m.bte.top/";

    public static boolean onLine() {
        if (BASE_URL.equals(onLine_url)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * H5页面地址
     */
    public static String H5_URL = H5_BASE_URL + "wechat/index";//首页地址
    public static String CE_LUE_URL = H5_BASE_URL + "wechat/strategy";//策略地址
    public static String DETAILS_H5_URL = H5_BASE_URL + "wechat/strategyDetail/";//策略详情详情地址
    public static String MINE_MY_QRCODE_URL = H5_BASE_URL + "wechat/register";//我的页面邀请码地址
    public static String HOME_LINE_CHART_URL = H5_BASE_URL + "wechat/indexLine";//首页图标地址
    public static String MARKET_FEN_XI_DETAIL_URL = H5_BASE_URL + "wechat/researchReport";//市场分析详情页面
    public static String HANG_QING_URL = H5_BASE_URL + "wechat/currencyList";//行情Url
    public static String LU_DOG_RUL = H5_BASE_URL + "wechat/feature";//鲁庄狗URl
    public static String BO_GOU_H5_URL = H5_BASE_URL + "wechat/bandDog";//波段狗
    public static String LU_DOG_REPORT_RUL = H5_BASE_URL + "wechat/featureReport";//鲁庄狗战报URl
    public static String LU_DOG_INTEGRAL_RUL = H5_BASE_URL + "wechat/integralCheats";//鲁庄狗积分URl
    public static final String MARKET_DETAIL_URl = H5_BASE_URL + "wechat/marketDetail/";//k线下面的详情页

    /**
     * 比特易用户使用协议地址
     */
    public static String AGREEMENT_URL = "https://m.bte.top/wechat/protocol/";


    //初始化数据
    public static final int INTT_NUMBER = 0x1;
    //获取写入权限的code码
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = INTT_NUMBER + 1;

    public static String GOTODOGPAGE = "首页点击撸庄狗";//首页点击撸庄狗
    public static String GOTOBANDRATIODOGPAGE = "首页点击波段狗";//首页点击波段狗
    public static String MOREBUTTON = "首页点击策略服务更多";//首页点击策略服务更多
    public static String GOTOTRADEDATAPAGE = "首页跳转行情页面";//首页跳转行情页面
    public static String JUMPTODETAIL = "首页点击搜索";//首页点击搜索
    public static String JUMPTODETAILS = "首页点击市场分析";//首页点击市场分析
    public static String JUMPTOSTRATEGYFOLLOW = "首页点击策略服务";//首页点击策略服务
    public static String JUMPTOSTRATEGYDETAIL = "策略列表点击某行";//策略列表点击某行
    public static String COIN1 = "首页币种1";//首页币种1
    public static String COIN2 = "首页币种2";//首页币种1
    public static String COIN3 = "首页币种3";//首页币种1
    public static String COIN4 = "首页币种4";//首页币种1


    //bug_tag用户信息
    public final static String BUG_TEL_FLAG="bug_tel_flag";
    public final static String BUG_BUILD_TIME_FLAG="bug_build_time_flag";
    public final static String BUG_GIT_VERSION_FLAG="bug_git_version_flag";

}