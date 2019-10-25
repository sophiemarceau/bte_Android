package com.btetop.net;

import com.btetop.bean.AbnormalOrderBean;
import com.btetop.bean.AbnormityBean;
import com.btetop.bean.AccountInfoBean;
import com.btetop.bean.AirBoardBean;
import com.btetop.bean.ArtBoardIdBean;
import com.btetop.bean.BannerInfo;
import com.btetop.bean.BaseBean;
import com.btetop.bean.BoDogUserCount;
import com.btetop.bean.BurnedBean;
import com.btetop.bean.CheckNewOldUserPwdBean;
import com.btetop.bean.CoinPairBean;
import com.btetop.bean.CommentBean;
import com.btetop.bean.DeleteArtBoardBean;
import com.btetop.bean.DepthBean;
import com.btetop.bean.DetailDadanBean;
import com.btetop.bean.DetailDepthBean;
import com.btetop.bean.DogsBean;
import com.btetop.bean.GroupUserInfo;
import com.btetop.bean.HXRegisterBean;
import com.btetop.bean.HXUserInfo;
import com.btetop.bean.HomeActiveBean;
import com.btetop.bean.HomeActiveDetailsBean;
import com.btetop.bean.HomeHeadInfoBean;
import com.btetop.bean.HomeLatestBean;
import com.btetop.bean.HomeMarketNewsBean;
import com.btetop.bean.HomeNoticeBean;
import com.btetop.bean.HourShortCommentBean;
import com.btetop.bean.InviteFriendsBean;
import com.btetop.bean.KlineBean;
import com.btetop.bean.KlineVolumeBean;
import com.btetop.bean.LineChartBean;
import com.btetop.bean.MarketCoinPairBean;
import com.btetop.bean.MarketDescriptionBean;
import com.btetop.bean.MessageItemBean;
import com.btetop.bean.MineStrategyDetailBean;
import com.btetop.bean.MiningBean;
import com.btetop.bean.MiningRuleBean;
import com.btetop.bean.MiningSchedule;
import com.btetop.bean.MiningSignBean;
import com.btetop.bean.MyQrCodeBean;
import com.btetop.bean.MyReleaseBeanOuter;
import com.btetop.bean.OptionalBean;
import com.btetop.bean.OssConfigBean;
import com.btetop.bean.OssInfoBean;
import com.btetop.bean.PostDetailBean;
import com.btetop.bean.ProductHomeBean;
import com.btetop.bean.ProductListBean;
import com.btetop.bean.RemarkKlineBean;
import com.btetop.bean.ReplyListBean;
import com.btetop.bean.ResearchDogCountBean;
import com.btetop.bean.ResistanceBean;
import com.btetop.bean.SearchCoinBean;
import com.btetop.bean.ShequListBean;
import com.btetop.bean.StackedBarChartNeagtiveBean;
import com.btetop.bean.UnreadMesBean;
import com.btetop.bean.UpdateInfoEntity;
import com.btetop.bean.UserCheck;
import com.btetop.bean.UserCountAndIncomeBean;
import com.btetop.bean.UserCurrentLotBean;
import com.btetop.bean.UserHeadImage;
import com.btetop.bean.UserInfo;
import com.btetop.bean.UserLoginByMesBean;
import com.btetop.bean.UserWalletAdress;
import com.btetop.bean.V2DogsBean;
import com.btetop.bean.WalletTransaction;
import com.btetop.bean.ZiJinBean;
import com.btetop.bean.ZiXuanResultBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lje on 2018/1/11.
 */

public interface BteTopServiceInterface {
    /**
     * 1.临时获取url地址
     *
     * @return
     */
    @GET("app/api/config/url")
    Observable<BaseBean<String>> configUrl();

    /**
     * 2.检查用户是否更新
     *
     * @param type
     * @param version
     * @return
     */
    @GET("app/api/config/update")
    Observable<BaseBean<UpdateInfoEntity.UpdateInfoData>> appMessage(@Query("type") String type,//app类型 0 安卓 1 IOS
                                                                     @Query("version") String version);//当前app版本 规则如 1.0.0

    /**
     * 3. 获取验证码
     *
     * @param userName
     * @param validate
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/sms_v2/login")
    Observable<BaseBean> sendCode(@Field("loginUsername") String userName, @Field("validate") String validate);//手机号

    /**
     * 4.验证码登录或注册
     *
     * @param mobile
     * @param code
     * @param inviteCode
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/user_v2/login")
    Observable<BaseBean<UserInfo>> loginByCode(@Field("loginUsername") String mobile,//手机号
                                               @Field("code") String code,
                                               @Field("deviceId") String deviceId,
                                               @Field("inviteCode") String inviteCode);//密码

    /**
     * 5.密码登录
     *
     * @param loginUsername
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/user_v2/loginPwd")
    Observable<BaseBean<UserInfo>> loginByPwd(@Field("loginUsername") String loginUsername,//手机号
                                              @Field("pwd") String pwd,//密码
                                              @Field("deviceId") String deviceId);//密码

    /**
     * 6.重置密码
     *
     * @param pwd1
     * @param pwd2
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/user_v2/password")
    Observable<BaseBean> repeatPwd(@Field("pwd1") String pwd1,//新密码
                                   @Field("pwd2") String pwd2);//重新输入新密码


    /**
     * 7. 用户信息
     *
     * @return
     */
    @POST("app/api/account/info")
    Observable<BaseBean<AccountInfoBean.AccountInfoData>> accountInfo();//token值

    /**
     * 7. 用户信息
     *
     * @return
     */
    @POST("app/api/user_v2/info")
    Observable<BaseBean<UserInfo>> UserInfo();//token值

    /**
     * 8.获取用户当前投资额
     *
     * @return
     */
    @POST("app/api/account/hold")
    Observable<BaseBean<UserCurrentLotBean.UserStrategyData>> userCurrentLot();//token值


    /**
     * 9.获取已结束投资份额
     *
     * @return
     */
    @POST("app/api/account/settle")
    Observable<BaseBean<UserCurrentLotBean.UserStrategyData>> userSettleLot();//token值

    /**
     * 10.判断用户是否正登陆
     *
     * @return
     */

    @POST("app/api/user_v2/online")
    Observable<BaseBean<Boolean>> userOnLine();//token值

    /**
     * 11.用户退出
     *
     * @return
     */
    @POST("app/api/user_v2/logout")
    Observable<BaseBean> userLogout();//token值

    /**
     * 12.获取最新的推荐列表
     */
    @POST("app/api/advise/latests")
    Observable<BaseBean<HomeLatestBean.HomeLatestsData>> homeLatests();

    /**
     * 13.获取首页显示的策略信息
     */
    @POST("app/api/v2/product/home")
    Observable<BaseBean<List<ProductHomeBean.ProductHomeData>>> homeProduct();

    /**
     * 14.获取最近的10条新闻快讯
     */
    @POST("app/api/news/latest")
    Observable<BaseBean<List<HomeMarketNewsBean.HomeMarketNewsData>>> newsLatest();

    /**
     * 15.分页获取策略列表
     */
    @FormUrlEncoded
    @POST("app/api/product/list")
    Observable<BaseBean<ProductListBean.ProductListData>> productList(@Field("pageNo") int pageNo);

    /**
     * 16.用户邀请码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/user_v2/myQrCode")
    Observable<BaseBean<MyQrCodeBean.MyQrCodeData>> userMyQrCode(@Field("url") String shareUrl);

    /**
     * 17.用户邀请好友列表
     *
     * @return
     */
    @POST("app/api/user_v2/invite/list")
    Observable<BaseBean<ArrayList<InviteFriendsBean.InviteFriendsData>>> inviteList();//token值

    /**
     * 18.市场分析详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/advise/report")
    Observable<BaseBean<MarketDescriptionBean.MarketDescriptionData>> marketDetails(@Field("reportId") String reportId);//token值

    /**
     * 19.跟随策略详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/product/subscribe/detail")
    Observable<BaseBean<MineStrategyDetailBean.MineStrategyDetailData>> followStrategy(@Field("id") String id);

    /**
     * 20.赎回策略详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/product/redeem/detail")
    Observable<BaseBean<MineStrategyDetailBean.MineStrategyDetailData>> redeemStrategy(@Field("id") String id);

    /**
     * 21.用户钱包地址
     *
     * @return
     */
    @POST("app/api/user_v2/wallet/address")
    Observable<BaseBean<UserWalletAdress.DataBean>> walletAddress();//token值

    /**
     * 22.首页公告
     *
     * @return
     */
    @POST("app/api/announcement")
    Observable<BaseBean<ArrayList<HomeNoticeBean.HomeNoticeData>>> homeNotice();

    /**
     * 23.意见反馈
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/user_v2/feedback")
    Observable<BaseBean> feedBack(@Field("content") String content);

    /**
     * 24.首页活动
     *
     * @return
     */
    @POST("app/api/activity")
    Observable<BaseBean<HomeActiveBean.HomeActiveData>> homeAdvertise();

    /**
     * 25.首页活动详情页面
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/activity/detail")
    Observable<BaseBean<HomeActiveDetailsBean.HomeActiveDetailsData>> homeAdvertiseDetail(@Field("id") String id);

    /**
     * 26.判断用户账号是否设置密码
     *
     * @param loginUsername
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/user_v2/newOldUserPassword")
    Observable<BaseBean<CheckNewOldUserPwdBean.CheckNewOldUserPwdData>> checkNewOldUserPassword(@Field("loginUsername") String loginUsername);

    /**
     * 27.第一次设置密码
     *
     * @param loginUsername
     * @param code
     * @param newPassword
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/user_v2/installPassword")
    Observable<BaseBean<UserLoginByMesBean.LoginData>> installPassword(@Field("loginUsername") String loginUsername,
                                                                       @Field("code") String code,
                                                                       @Field("newPassword") String newPassword,
                                                                       @Field("deviceId") String deviceId);

    /**
     * 28.获取首页鲁庄狗的人数和收益
     */
    @POST("app/api/lzdog/summary")
    Observable<BaseBean<UserCountAndIncomeBean.UserCountAndIncomeData>> userCountAndincome();

    /**
     * 29.获取波段狗人数
     */
    @POST("app/api/bandDog/summary")
    Observable<BaseBean<BoDogUserCount>> bandDogUserCount();


    /**
     * 研究狗
     *
     * @return
     */
    @GET("app/api/researchDog/getResearchDogCount")
    Observable<BaseBean<ResearchDogCountBean>> getResearchDogCount();

    /**
     * 首页的折线图
     */
    @POST("app/api/market/line?symbol=BTC")
    Observable<BaseBean<LineChartBean.DataBean>> getLineChartBean();

    /**
     * 金字塔图
     */
    @POST("app/api/dc/trade")
    Observable<BaseBean<StackedBarChartNeagtiveBean.DataBean>> getNeagtiveBeanObservable(@Query("symbol") String symbol);

//    /**
//     * k 线图
//     *
//     * @param size 取值数量
//     * @param type 类型,取值分钟 1m 5m 15m 30m 小时 1h 4h 天1d 周1w 月1mo,建议先限制在1d以下
//     */
//    @GET("app/api/kline/line")
//    Observable<BaseBean<KlineBean>> getKlineObservable(@Query("exchange") String exchange,
//                                                       @Query("base") String symbol,
//                                                       @Query("quote") String pair,
//                                                       @Query("start") long start,
//                                                       @Query("end") long end,
//                                                       @Query("size") int size,
//                                                       @Query("type") String type);


    @GET("app/api/kline/line")
    Observable<BaseBean<KlineBean>> getKlineObservable(@QueryMap Map<String, String> options);


    /**
     * K线点评
     *
     * @param exchange
     * @param symbol
     * @param pair
     * @param beginKlineDateTime
     * @param endKlineDateTime
     * @return
     */
    @POST("app/api/klineComment/getKlineCommentList")
    Observable<BaseBean<RemarkKlineBean>> getRemarkKlineObservable(@Query("exchange") String exchange,
                                                                   @Query("symbol") String symbol,
                                                                   @Query("pair") String pair,
                                                                   @Query("beginKlineDateTime") long beginKlineDateTime,
                                                                   @Query("endKlineDateTime") long endKlineDateTime);


    /**
     * 获取交易对
     *
     * @param exchange 交易所代码
     * @param base     币种代码 如 btc
     * @return
     */
    @GET("app/api/exchange/info")
    Observable<BaseBean<List<CoinPairBean>>> getCoinPairObservable(@Query("exchange") String exchange,
                                                                   @Query("base") String base);

    /**
     * 分享加积分
     *
     * @return
     */
    @POST("app/api/share/createShare")
    Observable<BaseBean> createShare(@Query("uid") String uid,
                                     @Query("channel") String channel,
                                     @Query("deviceId") String deviceId,
                                     @Query("shareType") String shareType,
                                     @Query("pageType") String pageType,
                                     @Query("url") String url,
                                     @Query("timestamp") long timestamp,
                                     @Query("checkCode") long checkCode);

    /**
     * 分享減积分
     *
     * @return
     */
    @POST("app/api/share/cancelShare")
    Observable<BaseBean> cancelShare(@Query("uid") String uid,
                                     @Query("channel") String channel,
                                     @Query("deviceId") String deviceId,
                                     @Query("shareType") String shareType,
                                     @Query("pageType") String pageType,
                                     @Query("url") String url,
                                     @Query("timestamp") long timestamp,
                                     @Query("checkCode") long checkCode);


    @POST("app/api/klineComment/getComments")
    Observable<BaseBean<List<HourShortCommentBean>>> getHourShortComment(@Query("symbol") String symbol);


    @POST("app/api/market/optionalList")
    Observable<BaseBean<OptionalBean>> getOptionList();

    @POST("app/api/market/addOptional")
    Observable<BaseBean> addOption(@Query("exchange") String exchange,
                                   @Query("base") String base,
                                   @Query("quote") String quote);

    @POST("app/api/market/deleteOptional")
    Observable<BaseBean> deleteOptional(@Query("exchange") String exchange,
                                        @Query("base") String base,
                                        @Query("quote") String quote);

    @POST("app/api/emChat/userWhetherRegister")
    Observable<BaseBean<HXRegisterBean>> getUserWhetherRegister();

    @POST("app/api/emChat/getUserHeadImage")
    Observable<BaseBean<UserHeadImage>> getUserHeadImage();

    @POST("app/api/emChat/addUserAndRoom")
    Observable<BaseBean<HXUserInfo>> addUserAndRoom(@Query("roomName") String roomName,
                                                    @Query("nickName") String nickName,
                                                    @Query("headImage") String userHeadUrl);

    @POST("app/api/emChat/getRoomInfo")
    Observable<BaseBean<GroupUserInfo>> getGroupUserCount(@Query("base") String base);

    @GET("app/api/future/currency")
    Observable<BaseBean<List<CoinPairBean>>> getCurrency();

    @GET("app/api/future/abnormity")
    Observable<BaseBean<List<AbnormityBean>>> getAbnormity(@QueryMap Map<String, String> params);


    @POST("app/api/user/ifCheckin")
    Observable<BaseBean> checkUserSingOrNo();

    @POST("app/api/user/checkin")
    Observable<BaseBean<UserCheck>> doUserSign(@Query("terminal") String terminal);

    @POST("app/api/dogs")
    Observable<BaseBean<DogsBean>> getDogsInfo();

    @GET("app/api/future/kline")
    Observable<BaseBean<KlineBean>> getFutureKlineObservable(@QueryMap Map<String, String> options);


    @GET("app/api/future/resistance/line")
    Observable<BaseBean<List<ResistanceBean>>> getResistance(@QueryMap Map<String, String> params);

    @GET("app/api/future/abnormity/line")
    Observable<BaseBean<List<AbnormalOrderBean>>> getAbnormityOrder(@QueryMap Map<String, String> params);

    @GET("app/api/future/volume/line")
    Observable<BaseBean<List<KlineVolumeBean>>> getVolume(@QueryMap Map<String, String> params);


    @POST("app/api/v2/banner/")
    Observable<BaseBean<List<BannerInfo>>> getBannerInfo();

    @GET("app/api/future/burned")
    Observable<BaseBean<List<BurnedBean>>> getBurned(@QueryMap Map<String, String> parmas);

    @GET("app/api/future/depth")
    Observable<BaseBean<List<DepthBean>>> getDepth(@Query("exchange") String exchange,
                                                   @Query("base") String base,
                                                   @Query("quote") String quote);

    @GET("app/api/trade/spot/hugeDeal")
    Observable<BaseBean<DetailDadanBean>> getDetailDadan(@Query("base") String base);

    //币种详情深度
    @GET("app/api/trade/spot/depth")
    Observable<BaseBean<DetailDepthBean>> getDetailDepth(@Query("base") String base);

    @POST("app/api/future/switch")
    Observable<BaseBean> switchFuture(@Query("onOff") String onOff);

    @POST("app/api/lzdog/openLzDog")
    Observable<BaseBean> switchLuzhuang(@Query("on_off") String onOff);

    @POST("app/api/bandDog/openCloseBandDog")
    Observable<BaseBean> switchBoduan(@Query("on_off") String onOff);

    @POST("app/api/future/trade")
    Observable<BaseBean<ZiJinBean>> getZijin(@Query("base") String base);

    @POST("app/api/event")
    Observable<BaseBean> addEvent(@Query("channel") String channel,
                                  @Query("module") String module,
                                  @Query("type") String type,
                                  @Query("target") String target,
                                  @Query("exchange") String exchange,
                                  @Query("base") String base,
                                  @Query("quote") String quote);

    @POST("app/api/user_v2/dogs")
    Observable<BaseBean<List<V2DogsBean>>> getV2Dogs();

    @POST("app/api/user_v2/update")
    Observable<BaseBean> UpdateUserInfo(@Query("name") String name,
                                        @Query("avator") String avator);


    //场外大宗钱包转账列表
    @GET("app/api/address/txs")
    Observable<BaseBean<WalletTransaction>> getBigWalletTransaction();

    @GET("app/sts/config")
    Observable<BaseBean<OssConfigBean>> getOSSConfigInfo();

    @GET("app/sts/credential")
    Observable<BaseBean<OssInfoBean>> getOSSInfo();

    //微信绑定
    @POST("app/api/user_v2/wx/bind")
    Observable<BaseBean> bindWx(@Query("code") String code);

    //微信解绑
    @POST("app/api/user_v2/wx/unbind")
    Observable<BaseBean> unBindWx();

    //获取搜索获得的交易对数据
    @POST("app/api/ticker/{base}")
    Observable<BaseBean<List<SearchCoinBean>>> getSearchCoinPair(@Path("base") String base);


    @POST("app/api/artboard/data")
    Observable<BaseBean<List<AirBoardBean>>> getAirBoardList();

    @POST("app/api/artboard/single/set")
    @Headers("Content-Type: application/json")
    Observable<BaseBean<ArtBoardIdBean>> setAirBoard(@Body RequestBody params);


    @Headers("Content-Type: application/json")
    @POST("app/api/artboard/del")
    Observable<BaseBean> deleteAirBoard(@Body DeleteArtBoardBean code);

    @POST("app/api/artboard/onoff")
    @Headers("Content-Type: application/json")
    Observable<BaseBean> setOnOff(@Body RequestBody params);

    @GET("app/api/exchange/global/summary/")
    Observable<BaseBean<HomeHeadInfoBean>> getHomeHeadInfo();

    @GET("app//api/currencypair/list")
    Observable<BaseBean<List<ZiXuanResultBean>>> getZixuanResult(@Query("key") String s);

    @POST("app//api/market/batch/optional")
    @FormUrlEncoded
    Observable<BaseBean> setOptional(@Field("pairs") String params);

    @GET("app/api/message/list")
    Observable<BaseBean<MessageItemBean>> getMessageList(@Query("offset") int index,
                                                         @Query("pageSize") int page);

    @GET("app/api/dig/info")
    Observable<BaseBean<MiningBean>> getMiningInfo();

    @GET("app/api/dig/rule")
    Observable<MiningRuleBean> getMiningRule();

    @POST("app/api/dig/sign")
    Observable<BaseBean<MiningSignBean>> doMiningSign();

    @GET("app/api/dig/schedule")
    Observable<BaseBean<MiningSchedule>> doMiningSchedule();
    @GET("app/api/post/list")
    Observable<BaseBean<ShequListBean>> getSheQuList(@Query("pageNo") int index,
                                                     @Query("pageSize") int page);

    @GET("app/api/post/like/add")
    Observable<BaseBean> addThumb(@Query("postId") int postId);

    @GET("app/api/post/share/add")
    Observable<BaseBean> addShare(@Query("postId") int postId);

    @POST("app/api/post/add")
    Observable<BaseBean> addShequPost(@Query("content") String content);


    @GET("app/api/post/detail")
    Observable<BaseBean<PostDetailBean>> getPostDetail(@Query("postId") int id);


    //添加评论
    @POST("app//api/post/comment/add")
    @FormUrlEncoded
    Observable<BaseBean> postCommentAdd(@Field("postId") int id, @Field("content") String content);

    @POST("app/api/dig/get")
    Observable<BaseBean> getCalculate();

    @POST("app/api/post/comment/reply/list")
    Observable<BaseBean<ReplyListBean>> getReplyListBean(@Query("postReplyId") int postReplyId,@Query("pageNo") int pageNo,@Query("pageSize") int pageSize);

    @POST("app/api/dig/share")
    Observable<BaseBean> doDigShare();

    @POST("app/api/post/comment/reply/add")
    @FormUrlEncoded
    Observable<BaseBean> postReplyAdd(@Field("postReplyId") int postReplyId,@Field("content") String content,@Field("postReplyItemId") int postReplyItemId);

    @GET("app/api/post/myRelease")
    Observable<BaseBean<MyReleaseBeanOuter>> getRelease(@Query("pageNo") int index,@Query("pageSize") int page);


    @GET("app/api/post/readall")
    Observable<BaseBean> setAllRead(@Query("readType") int type);

    @GET("app/api/post/myComment")
    Observable<BaseBean<CommentBean>> getMyComment(@Query("pageNo") int index, @Query("pageSize") int page);

    @POST("app/api/currency/24h/info")
    Observable<BaseBean<MarketCoinPairBean>> getCurrencypair(@Query("industry")String industry,
                                                             @Query("pageNo")String pageNo,
                                                             @Query("pageSize") String pageSize,
                                                             @Query("exchange") String exchange,
                                                             @Query("sort") String sort,
                                                             @Query("sortType") String sortType);

    @GET("app/api/message/unread")
    Observable<UnreadMesBean> UnReadMessage();

}
