package com.btetop.net;

import com.google.gson.Gson;

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
import com.btetop.config.UrlConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

public class BteTopService {
    /**
     * 1.临时获取url地址
     */
    public static Observable<BaseBean<String>> getConfigUrl() {
        return RetrofitManager.getInstance().configUrl();
    }

    /**
     * 2.检查用户是否更新
     */
    public static Observable<BaseBean<UpdateInfoEntity.UpdateInfoData>> getAppUpdate(String type, String version) {
        return RetrofitManager.getInstance().appMessage(type, version);
    }

    /**
     * 3. 获取验证码
     */
    public static Observable<BaseBean> sendCode(String userName, String validate) {
        return RetrofitManager.getInstance().sendCode(userName, validate);
    }

    /**
     * 4.验证码登录或注册
     */
    public static Observable<BaseBean<UserInfo>> loginByCode(String mobile, String code, String deviceId, String inviteCode) {
        return RetrofitManager.getInstance().loginByCode(mobile, code, deviceId, inviteCode);
    }

    /**
     * 5.密码登录
     */
    public static Observable<BaseBean<UserInfo>> loginByPwd(String loginUsername, String pwd, String deviceId) {
        return RetrofitManager.getInstance().loginByPwd(loginUsername, pwd, deviceId);
    }

    /**
     * 6.重置密码
     */
    public static Observable<BaseBean> repeatPwd(String pwd1, String pwd2) {
        return RetrofitManager.getInstance().repeatPwd(pwd1, pwd2);
    }

    /**
     * 7. 用户信息
     */
    public static Observable<BaseBean<AccountInfoBean.AccountInfoData>> accountInfo() {
        return RetrofitManager.getInstance().accountInfo();
    }

    /**
     * 7. 用户信息
     */
    public static Observable<BaseBean<UserInfo>> UserInfo() {
        return RetrofitManager.getInstance().UserInfo();
    }


    /**
     * 8.获取用户当前投资额
     */
    public static Observable<BaseBean<UserCurrentLotBean.UserStrategyData>> getUserCurrentLot() {
        return RetrofitManager.getInstance().userCurrentLot();
    }

    /**
     * 9.获取已结束投资份额
     */
    public static Observable<BaseBean<UserCurrentLotBean.UserStrategyData>> getUserSettleLot() {
        return RetrofitManager.getInstance().userSettleLot();
    }

    /**
     * 10.判断用户是否正登陆
     */
    public static Observable<BaseBean<Boolean>> getUserOnLine() {
        return RetrofitManager.getInstance().userOnLine();
    }

    /**
     * 11.用户退出
     */
    public static Observable<BaseBean> userLogout() {
        return RetrofitManager.getInstance().userLogout();
    }

    /**
     * 12.获取最新的推荐列表
     */
    public static Observable<BaseBean<HomeLatestBean.HomeLatestsData>> getHomeLatests() {
        return RetrofitManager.getInstance().homeLatests();
    }

    /**
     * 13.获取首页显示的策略信息
     */
    public static Observable<BaseBean<List<ProductHomeBean.ProductHomeData>>> getHomeProduct() {
        return RetrofitManager.getInstance().homeProduct();
    }

    /**
     * 14.获取最近的10条新闻快讯
     */
    public static Observable<BaseBean<List<HomeMarketNewsBean.HomeMarketNewsData>>> getNewsLatest() {
        return RetrofitManager.getInstance().newsLatest();
    }

    /**
     * 15.分页获取策略列表
     */
    public static Observable<BaseBean<ProductListBean.ProductListData>> getProductList(int pageNo) {
        return RetrofitManager.getInstance().productList(pageNo);
    }

    /**
     * 16.用户邀请码
     */
    public static Observable<BaseBean<MyQrCodeBean.MyQrCodeData>> getUserMyQrCode(String shareUrl) {
        return RetrofitManager.getInstance().userMyQrCode(shareUrl);
    }

    /**
     * 17.用户邀请好友列表
     */
    public static Observable<BaseBean<ArrayList<InviteFriendsBean.InviteFriendsData>>> inviteList() {
        return RetrofitManager.getInstance().inviteList();
    }

    /**
     * 18.市场分析详情
     */
    public static Observable<BaseBean<MarketDescriptionBean.MarketDescriptionData>> getMarketDetails(String reportId) {
        return RetrofitManager.getInstance().marketDetails(reportId);
    }

    /**
     * 19.跟随策略详情
     */
    public static Observable<BaseBean<MineStrategyDetailBean.MineStrategyDetailData>> getFollowStrategy(String id) {
        return RetrofitManager.getInstance().followStrategy(id);
    }

    /**
     * 20.赎回策略详情
     */
    public static Observable<BaseBean<MineStrategyDetailBean.MineStrategyDetailData>> getRedeemStrategy(String id) {
        return RetrofitManager.getInstance().redeemStrategy(id);
    }

    /**
     * 21.用户钱包地址
     */
    public static Observable<BaseBean<UserWalletAdress.DataBean>> getWalletAddress() {
        return RetrofitManager.getInstance().walletAddress();
    }

    /**
     * 22.首页公告
     */
    public static Observable<BaseBean<ArrayList<HomeNoticeBean.HomeNoticeData>>> getHomeNotice() {
        return RetrofitManager.getInstance().homeNotice();
    }

    /**
     * 23.意见反馈
     */
    public static Observable<BaseBean> getFeedBack(String content) {
        return RetrofitManager.getInstance().feedBack(content);
    }

    /**
     * 24.首页活动
     */
    public static Observable<BaseBean<HomeActiveBean.HomeActiveData>> getHomeAdvertise() {
        return RetrofitManager.getInstance().homeAdvertise();
    }

    /**
     * 25.首页活动详情页面
     */
    public static Observable<BaseBean<HomeActiveDetailsBean.HomeActiveDetailsData>> gethomeActiveDetail(String id) {
        return RetrofitManager.getInstance().homeAdvertiseDetail(id);
    }

    /**
     * 26.判断用户账号是否被注册
     */
    public static Observable<BaseBean<CheckNewOldUserPwdBean.CheckNewOldUserPwdData>> getCheckNewOldUserPassword(String loginUsername) {
        return RetrofitManager.getInstance().checkNewOldUserPassword(loginUsername);
    }

    /**
     * 27.第一次设置密码
     */
    public static Observable<BaseBean<UserLoginByMesBean.LoginData>> getInstallPassword(String loginUsername, String code, String newPassword, String deviceId) {
        return RetrofitManager.getInstance().installPassword(loginUsername, code, newPassword, deviceId);
    }

    /**
     * 28.获取首页鲁庄狗的人数和收益
     */
    public static Observable<BaseBean<UserCountAndIncomeBean.UserCountAndIncomeData>> getUserCountAndIncome() {
        return RetrofitManager.getInstance().userCountAndincome();
    }

    /**
     * 29.获取波段狗人数
     */
    public static Observable<BaseBean<BoDogUserCount>> bandDogUserCount() {
        return RetrofitManager.getInstance().bandDogUserCount();
    }

    public static Observable<BaseBean<ResearchDogCountBean>> getResearchDogCount() {
        return RetrofitManager.getInstance().getResearchDogCount();
    }

    /**
     * 获得lineChart
     */
    public static Observable<BaseBean<LineChartBean.DataBean>> getLineChartBean() {
        return RetrofitManager.getInstance().getLineChartBean();
    }

    /**
     * 金字塔图价格分布图
     */
    public static Observable<BaseBean<StackedBarChartNeagtiveBean.DataBean>> getStackedBarChartNeagtiveBean(String sysmbol) {
        return RetrofitManager.getInstance().getNeagtiveBeanObservable(sysmbol);
    }


//    Observable<BaseBean<KlineBean>> getKlineObservable(@Query("exchange") String exchange,
//                                                       @Query("base") String symbol,
//                                                       @Query("quote") String pair,
//                                                       @Query("start") long start,
//                                                       @Query("end") long end,
//                                                       @Query("size") int size,
//                                                       @Query("type") String type);

    /**
     * k线图 fix me 参数顺序 应该是startTime 在前
     */
    public static Observable<BaseBean<KlineBean>> getKlineBean(String exchange, String base, String quote,
                                                               long endTime, long startTime, String type) {
        Map<String, String> maps = new HashMap<>();
        maps.put("exchange", exchange);
        maps.put("base", base);
        maps.put("quote", quote);
        if (endTime != 0) {
            maps.put("end", "" + endTime);
        }
        if (startTime != 0) {
            maps.put("start", "" + startTime);
        }
        maps.put("type", type);
        maps.put("size", "1000");

        return RetrofitManager.getInstance().getKlineObservable(maps);
    }

    /**
     * 点评k线图
     */
    public static Observable<BaseBean<RemarkKlineBean>> getRemarkKlineBean(long start, long end) {
        return RetrofitManager.getInstance().getRemarkKlineObservable(
                "上海交易所", "BTC", "比特币",
                start, end);
    }

    /**
     * 获取交易对
     */
    public static Observable<BaseBean<List<CoinPairBean>>> getCoinPairBean(String name, String coinType) {
        return RetrofitManager.getInstance().getCoinPairObservable(name, coinType);
    }

    /**
     * 分享加积分
     */
    public static Observable<BaseBean> createShare(String uid, String channel, String deviceId, String shareType,
                                                   String pageType, String url, long timestamp, long checkCode) {
        return RetrofitManager.getInstance().createShare(uid, channel, deviceId, shareType, pageType, url, timestamp, checkCode);
    }

    /**
     * 分享減积分
     */
    public static Observable<BaseBean> cancelShare(String uid, String channel, String deviceId, String shareType,
                                                   String pageType, String url, long timestamp, long checkCode) {
        return RetrofitManager.getInstance().cancelShare(uid, channel, deviceId, shareType, pageType, url, timestamp, checkCode);
    }

    /**
     * k线短评
     */
    public static Observable<BaseBean<List<HourShortCommentBean>>> getHourShortComment(String symbol) {
        return RetrofitManager.getInstance().getHourShortComment(symbol);
    }

    /**
     * 自选列表
     */
    public static Observable<BaseBean<OptionalBean>> getOptionList() {
        return RetrofitManager.getInstance().getOptionList();
    }

    /**
     * 添加自选
     */
    public static Observable<BaseBean> addOption(String exchange, String base, String quote) {
        return RetrofitManager.getInstance().addOption(exchange, base, quote);
    }

    /**
     * 删除自选
     */
    public static Observable<BaseBean> deleteOption(String exchange, String base, String quote) {
        return RetrofitManager.getInstance().deleteOptional(exchange, base, quote);
    }


    /**
     * 用户是否注册到环信(需传token)true代表已经注册过 falae代表没有注册过
     */
    public static Observable<BaseBean<HXRegisterBean>> getUserWhetherRegister() {
        return RetrofitManager.getInstance().getUserWhetherRegister();
    }

    /**
     * 获取用户所有头像
     */
    public static Observable<BaseBean<UserHeadImage>> getUserHeadImage() {
        return RetrofitManager.getInstance().getUserHeadImage();
    }

    /**
     * 注册并添加到聊天室(需传token)
     */
    public static Observable<BaseBean<HXUserInfo>> addUserAndRoom(String roomName, String nickName, String userHeadUrl) {
        return RetrofitManager.getInstance().addUserAndRoom(roomName, nickName, userHeadUrl);
    }

    /**
     * 获取聊天室人数
     */
    public static Observable<BaseBean<GroupUserInfo>> getGroupUserCount(String username) {
        return RetrofitManager.getInstance().getGroupUserCount(username);
    }

    /**
     * 获取所有期货币对信息
     */
    public static Observable<BaseBean<List<CoinPairBean>>> getCurrency() {
        return RetrofitManager.getInstance().getCurrency();
    }

    /**
     * k线图
     */
    public static Observable<BaseBean<KlineBean>> getFutureKlineBean(String exchange, String base, String quote,
                                                                     long endTime, long startTime, String type) {
        Map<String, String> maps = new HashMap<>();
        maps.put("exchange", exchange);
        maps.put("base", base);
        maps.put("quote", quote);
        if (endTime != 0) {
            maps.put("end", "" + endTime);
        }
        if (startTime != 0) {
            maps.put("start", "" + startTime);
        }
        maps.put("type", type);
        maps.put("size", "1000");

        return RetrofitManager.getInstance().getFutureKlineObservable(maps);
    }

    public static Observable<BaseBean<List<AbnormityBean>>> getAbnormity(String exchange, String base, String quote) {
        Map<String, String> maps = new HashMap<>();

        maps.put("exchange", exchange);
        maps.put("base", base);
        maps.put("quote", quote);
        maps.put("size", "1000");

        return RetrofitManager.getInstance().getAbnormity(maps);

    }

    /**
     * 用户执行每日签到
     */
    public static Observable<BaseBean<UserCheck>> doUserSign(String terminal) {
        return RetrofitManager.getInstance().doUserSign(terminal);
    }

    /**
     * 用户是否已经执行今日签到
     */
    public static Observable<BaseBean> checkUserSingOrNo() {
        return RetrofitManager.getInstance().checkUserSingOrNo();
    }

    /**
     * 工具狗信息集合
     */
    public static Observable<BaseBean<DogsBean>> getDogsInfo() {
        return RetrofitManager.getInstance().getDogsInfo();
    }
   /**
     * 获取banner信息
     */
    public static Observable<BaseBean<List<BannerInfo>>> getBannerInfo() {
        return RetrofitManager.getInstance().getBannerInfo();


    }

    /**
     * 开启/关闭合约狗接口
     */
    public static Observable<BaseBean> switchFuture(String i) {
        return RetrofitManager.getInstance().switchFuture(i);
    }

    public static Observable<BaseBean> switchLuzhuang(String i) {
        return RetrofitManager.getInstance().switchLuzhuang(i);
    }

    public static Observable<BaseBean> switchBoduan(String i) {
        return RetrofitManager.getInstance().switchBoduan(i);
    }


    public static Observable<BaseBean<List<V2DogsBean>>> getV2Dogs() {
        return RetrofitManager.getInstance().getV2Dogs();
    }

    public static Observable<BaseBean<List<BurnedBean>>> getBurned(String exchange, String base,
                                                                   String quote) {


        Map<String, String> maps = new HashMap<>();
        maps.put("exchange", exchange);
        maps.put("base", base);
        maps.put("quote", quote);

        return RetrofitManager.getInstance().getBurned(maps);

    }

    public static Observable<BaseBean<List<DepthBean>>> getDepth(String exchange, String base,
                                                                 String quote) {

        return RetrofitManager.getInstance().getDepth(exchange, base, quote);
    }

    public static Observable<BaseBean<DetailDadanBean>> getDetailDadanBean(String base) {

        return RetrofitManager.getInstance().getDetailDadan(base);
    }

    public static Observable<BaseBean<DetailDepthBean>> getDetailDepth(String base) {
        return RetrofitManager.getInstance().getDetailDepth(base);
    }

    public static Observable<BaseBean<ZiJinBean>> getZijin(String base) {
        return RetrofitManager.getInstance().getZijin(base);

    }


    /**
     * 增加一条事件日志
     */
    public static Observable<BaseBean> addEvent(String channel, String module,
                                                String type, String target,
                                                String exchange, String base,
                                                String quote) {
        return RetrofitManager.getInstance().addEvent(channel, module,
                type, target,
                exchange, base,
                quote);


    }

    //获取场外大额钱包转账列表
    public static Observable<BaseBean<WalletTransaction>> getBigWalletTranstion() {
        return RetrofitManager.getInstance().getBigWalletTransaction();

    }

    public static Observable<BaseBean> UpdateUserInfo(String name, String avator) {
        return RetrofitManager.getInstance().UpdateUserInfo(name, avator);
    }

    public static Observable<BaseBean<OssConfigBean>> getOSSConfigInfo() {
        return RetrofitManager.getInstance().getOSSConfigInfo();
    }

    public static Observable<BaseBean<OssInfoBean>> getOSSInfo() {
        return RetrofitManager.getInstance().getOSSInfo();
    }


    public static Observable<BaseBean> bindWx(String code) {
        return RetrofitManager.getInstance().bindWx(code);
    }

    public static Observable<BaseBean> unBindWx() {
        return RetrofitManager.getInstance().unBindWx();
    }

    public static Observable<BaseBean<List<SearchCoinBean>>> getSearchCoinPair(String base) {
        return RetrofitManager.getInstance().getSearchCoinPair(base);
    }

    public static Observable<BaseBean<List<AirBoardBean>>> getAirBoardList() {
        return RetrofitManager.getInstance().getAirBoardList();
    }

    public static Observable<BaseBean> delArtBoard(int position) {
        DeleteArtBoardBean bean = new DeleteArtBoardBean(position);
        return RetrofitManager.getInstance().deleteAirBoard(bean);
    }

    public static Observable<BaseBean<ArtBoardIdBean>> setArtBoard(AirBoardBean boardBeans) {
        AirBoardBean.RequestBodyClass body = new AirBoardBean.RequestBodyClass();
        body.exchange = boardBeans.getExchange();
        body.base = boardBeans.getBaseAsset();
        body.quote = boardBeans.getQuoteAsset();
        body.costPrice = boardBeans.getCostPrice();
        body.direction = boardBeans.getDirection();
        body.ratio = boardBeans.getRatio();

        String text = new Gson().toJson(body);
        RequestBody body1 = RequestBody.create(MediaType.parse("text/plain"), text);
        return RetrofitManager.getInstance().setAirBoard(body1);
    }

    public static Observable<BaseBean> setOptional(List<ZiXuanResultBean> beanList){

        String text = new Gson().toJson(beanList);

        return RetrofitManager.getInstance().setOptional(text);


    }

    public static Observable<BaseBean> setOnoff(int onOff) {
        String text = "{\"status\":" + onOff + "}";
        RequestBody body1 = RequestBody.create(MediaType.parse("text/plain"), text);
        return RetrofitManager.getInstance().setOnOff(body1);

    }

    public static Observable<BaseBean<HomeHeadInfoBean>> getHomeHeadInfo() {
        return RetrofitManager.getInstance().getHomeHeadInfo();
    }

    public static Observable<BaseBean<List<ZiXuanResultBean>>> getZixuanResult(String s){
        return RetrofitManager.getInstance().getZixuanResult(s);
    }

    public static Observable<BaseBean<MessageItemBean>> getMessageList(int index, int page) {
        return RetrofitManager.getInstance().getMessageList(index,page);
    }

    public static Observable<BaseBean<ShequListBean>> getShequList(int index){
        return RetrofitManager.getInstance().getSheQuList(index, UrlConfig.PAGE_SIZE);
    }

    public static Observable<BaseBean> addThumb(int id){
        return RetrofitManager.getInstance().addThumb(id);
    }

    public static Observable<BaseBean> addShare(int id){
        return RetrofitManager.getInstance().addShare(id);
    }

    public static Observable<BaseBean> addShequPost(String content){
        return RetrofitManager.getInstance().addShequPost(content);
    }

    public static Observable<BaseBean<PostDetailBean>> getPostDetail(int id){
        return RetrofitManager.getInstance().getPostDetail(id);
    }

    public static Observable<BaseBean> postCommentAdd(int id,String content){
        return RetrofitManager.getInstance().postCommentAdd(id,content);

    }

    /**
     * 挖矿信息集合
     */
    public static Observable<BaseBean<MiningBean>> getMiningInfo() {
        return RetrofitManager.getInstance().getMiningInfo();
    }


    /**
     * 挖矿规则
     */
    public static Observable<MiningRuleBean> getMiningRule() {
        return RetrofitManager.getInstance().getMiningRule();
    }

    /**
     * 挖矿连续签到
     */
    public static Observable<BaseBean<MiningSignBean>> doMiningSign() {
        return RetrofitManager.getInstance().doMiningSign();
    }

    /**
     * 挖矿计划 时间
     */
    public static Observable<BaseBean<MiningSchedule>> doMiningSchedule() {
        return RetrofitManager.getInstance().doMiningSchedule();
    }
    /**
     * 挖矿计划 时间
     */
    public static Observable<BaseBean> getCalculate() {
        return RetrofitManager.getInstance().getCalculate();
    }
//    getReplyListBean
    public static Observable<BaseBean<ReplyListBean>> getReplyListBean(int postReplyId,int pageNo){
        return RetrofitManager.getInstance().getReplyListBean(postReplyId,pageNo,UrlConfig.PAGE_SIZE);
    }



    /**
     * 分享后增加算力
     */
    public static Observable<BaseBean> doDigShare() {
        return RetrofitManager.getInstance().doDigShare();

    }

    public static Observable<BaseBean> postReplyAdd(int postReplyId,String content,int postReplyItemId){
        return RetrofitManager.getInstance().postReplyAdd(postReplyId,content,postReplyItemId);
    }

    public static Observable<BaseBean<MyReleaseBeanOuter>> getRelease(int index){
        return RetrofitManager.getInstance().getRelease(index,UrlConfig.PAGE_SIZE);

    }

    /**
     * 获得消息中心，我发布的 我评论的角标
     * @return
     */
    public static Observable<BaseBean<MyReleaseBeanOuter>> getMessageCount(){
        return RetrofitManager.getInstance().getRelease(0,0);

    }



    /**
     * 社区，全部已读
     * @param type 1：我的发布 2：我的评论
     * @return
     */
    public static Observable<BaseBean> setAllRead(int type){
        return RetrofitManager.getInstance().setAllRead(type);
    }

    public static Observable<BaseBean<CommentBean>> getMyComment(int index){
        return RetrofitManager.getInstance().getMyComment(index,UrlConfig.PAGE_SIZE);
    }

 /**
     * 行情页币种信息
     */
    public static Observable<BaseBean<MarketCoinPairBean>> getCurrencypair(String industry,
                                                                           String pageNo,
                                                                           String pageSize,
                                                                           String exchange,
                                                                           String sort,
                                                                           String sortType) {
        return RetrofitManager.getInstance().getCurrencypair(industry,
                pageNo,
                pageSize,
                exchange,
                sort,
                sortType);
    }

    /**
     * 分享后增加算力
     */
    public static Observable<UnreadMesBean> UnReadMessage() {
        return RetrofitManager.getInstance().UnReadMessage();
    }
}
