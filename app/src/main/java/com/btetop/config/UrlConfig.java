package com.btetop.config;

public class UrlConfig {

    /**
     * 基础url  暂时先用哪儿借的的测试数据
     * //
     * //
     * //
     */
    public static String BASE_URL = "https://m.bte.top/";      //正式地址
    public static String H5_BASE_URL = "https://m.bte.top/v2/wechat/";//H5正式


//    public final static String BASE_URL = "https://l.bte.top";//崔哥接口测试
//    public final static String H5_BASE_URL = "https://l.bte.top/v2/wechat/";//楚东阳本地H5测试
//    public final static String H5_BASE_URL = "http://172.16.24.90:8081/v2/wechat/";//楚东阳本地H5测试


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
    public static final String H5_URL = H5_BASE_URL + "index";//首页地址
    public static final String CE_LUE_URL = H5_BASE_URL + "strategy";//策略地址
    public static final String CE_LUE_DETAILS_URL = H5_BASE_URL + "strategyDetail/";//策略详情详情地址
    public static final String MINE_MY_QRCODE_URL = H5_BASE_URL + "register";//我的页面邀请码地址
    public static final String HOME_LINE_CHART_URL = H5_BASE_URL + "indexLine";//首页图标地址
    public static final String MARKET_REPORT_URL = H5_BASE_URL + "researchReport";//市场分析详情页面
    public static final String HANG_QING_URL = H5_BASE_URL + "currencyList";//行情Url
    public static final String LU_DOG_RUL = H5_BASE_URL + "feature";//鲁庄狗URl
    public static final String BO_GOU_H5_URL = H5_BASE_URL + "bandDog";//波段狗
    public static final String LU_DOG_REPORT_RUL = H5_BASE_URL + "featureReport";//    鲁庄狗战报URl
    public static final String LU_DOG_INTEGRAL_RUL = H5_BASE_URL + "integralCheats";//鲁庄狗积分URl
    public static final String MARKET_DETAIL_URl = H5_BASE_URL + "marketDetail/";//k线下面的详情页
    public static final String RESEARCH_DOG_URL = H5_BASE_URL + "researchDog";//研究狗
    public static final String HEYUE_DOG_URL = H5_BASE_URL + "contractDog";//合约狗
    public static final String HOME_GET_COIN_URL = H5_BASE_URL + "integralCheats";//获取积分
    public static final String HEYUE_DOG_GET_COIN_URL = H5_BASE_URL + "integralCheats?source=contractDog";//获取积分
    public static final String DINGPAN_URL = H5_BASE_URL + "stealingMonitorIndicator";//盯盘
    public static final String DINGPAN_HOME_URL = H5_BASE_URL + "stealingDog";//盯盘狗首页
    public static final String CHAINSEARCH_HOME_URL = H5_BASE_URL + "chainsearch";//首页链查查
    public static final String AGREEMENT_URL = H5_BASE_URL+"protocol/";//比特易用户使用协议地址
    public static final String HOME_INDEX_HEADER_URL = H5_BASE_URL + "indexheader";//首页头部地址
    public static final String HOME_INDEX_HEADER_URL_AIRINDEX = H5_BASE_URL + "marketoverview2?tab=airIndex";//首页头部地址空气指数
    public static final String HOME_INDEX_HEADER_URL_AMOUNT = H5_BASE_URL + "marketoverview2?tab=amount";//首页头部地址交易规模
    public static final String HOME_INDEX_HEADER_URL_NETFLOW = H5_BASE_URL + "marketoverview2?tab=netFlow";//首页头部地址资金流向
    public static final String MINE_CAPITAL = H5_BASE_URL + "assets  ";//我的资产url地址
    public static final String MINING_INDEX_URL = BASE_URL + "ad/20181024/";//挖矿url地址



    public static final String JI_FEN_URL =H5_BASE_URL+"integrallist?point=" ;

    //初始化数据
    public static final int INTT_NUMBER = 0x1;
    //获取写入权限的code码
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = INTT_NUMBER + 1;




    //bug_tag用户信息
    public final static String BUG_TEL_FLAG = "bug_tel_flag";
    public final static String BUG_BUILD_TIME_FLAG = "bug_build_time_flag";
    public final static String BUG_GIT_VERSION_FLAG = "bug_git_version_flag";


    //分享信息
    public final static String DEFAULT_SHARE_URL = H5_URL;
    public final static String DEFAULT_SHARE_TITLE = "比特易，数字货币市场专业分析工具";
    public final static String DEFAULT_SHARE_DESC = "比特易是业界领先的数字货币市场专业分析工具，软银中国资本(SBCVC)、蓝驰创投(BlueRun Ventures)战略投资，玩转比特币，多用比特易\"";

    //页面刷新时间
    public final static int REFRESH_TIMES = 3000;

    //自选不刷新标志
   public static boolean NO_REFRESH_HANGQING=false;

   //社区pagesize
    public static final int PAGE_SIZE=10;


}