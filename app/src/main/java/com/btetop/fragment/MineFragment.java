package com.btetop.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.activity.AboutUsActivity;
import com.btetop.activity.BTEGroupActivity;
import com.btetop.activity.CommonWebViewActivity;
import com.btetop.activity.FeedBackActivity;
import com.btetop.activity.InvitingFriendsResultActivity;
import com.btetop.activity.OverDrawActivity;
import com.btetop.activity.PersonSettingActivity;
import com.btetop.activity.ServerSettingActivity;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.adapter.MyMineDetailsAdapter;
import com.btetop.application.BteTopApplication;
import com.btetop.base.BaseFragment;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.bean.AccountInfoBean;
import com.btetop.bean.BaseBean;
import com.btetop.bean.UserCurrentLotBean.UserStrategyData;
import com.btetop.bean.UserInfo;
import com.btetop.config.UrlConfig;
import com.btetop.manager.DataCleanManager;
import com.btetop.message.RouteMessage;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.FormatUtil;
import com.btetop.utils.GlideCircleTransform;
import com.btetop.utils.PhoneNumUtils;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.btetop.utils.TypeFaceUtils;
import com.btetop.widget.MyListView;
import com.btetop.widget.floatprice.FloatPriceService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zylaoshi.library.utils.LogUtil;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;

import rx.functions.Action1;

/**
 * Created by Administrator on 2018/3/13.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final String LOGIN_OUT = "退出账户";
    private static final String CLEAN_CACHE = "清理缓存";
    TextView tv_mine_capital;
    ImageView iv_mine_capital;
    RelativeLayout ll_mine_capital;
    private TextView mMoney;
    private TextView mBtc;
    private RelativeLayout mCurrentLot;//当前投额
    private RelativeLayout mOverLot;//已结束投额
    private TextView mTvLot, mTvOver;
    private View mLotLine, mOverLine;
    private String token;
    private RelativeLayout mNoDataLayout;
    private TextView mInvestmentAmount;//投资额
    private MyMineDetailsAdapter detailsAdapter;
    private MyListView myListView;
    private RelativeLayout mUserSet;
    private TextView mUserRecharge;
    private TextView mUserPhone;
    private ScrollView mineScroll;
    private boolean trade;
    private LinearLayout accountDetailLayout;
    private String accountDollar;
    private String accountBtc;
    private String accountPoint;//积分
    private TextView mTvPoint;
    private LinearLayout mineCeLueLayout;
    private boolean isOpen = false;
    private RelativeLayout followLayout;
    private ImageView followMore, followUp, mine_logo;
    private RelativeLayout friendLayout;
    private RelativeLayout aboutLayout;
    private TextView mLoginOut;
    private Dialog mLogoutDialog;
    private TextView mTvFriend;
    private String currentCount = "";
    private String settleCount = "";
    private TextView ceLueCount;
    private RelativeLayout feedBcakLayout;//意见反馈
    private RelativeLayout cacheLayout;//清理缓存
    private RelativeLayout bte_group_layout;//加入官方群
    private RelativeLayout server_setting;//服务设置
    private TextView mCache;
    private LinearLayout ji_fen_layout;
    private TextView tvXuanfu;

    private static MineFragment _instance = null;

    public static MineFragment newInstance() {
        if (_instance == null)
            _instance = new MineFragment();
        return _instance;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateViews();
        }
    };

    @Override
    protected int attachLayoutId() {
        return R.layout.mine_fragment_layout;
    }

    @Override
    protected void initView(View view) {
        mMoney = view.findViewById(R.id.tv_dollar);
        mine_logo = view.findViewById(R.id.mine_logo);
        mBtc = view.findViewById(R.id.tv_btc);
        mTvPoint = view.findViewById(R.id.tv_jifen);
        mTvLot = view.findViewById(R.id.tv_lot);
        mTvOver = view.findViewById(R.id.tv_tactics);
        mLotLine = view.findViewById(R.id.lot_line);
        mOverLine = view.findViewById(R.id.tactics_line);
        mCurrentLot = view.findViewById(R.id.lot_layout);
        mCurrentLot.setOnClickListener(this);
        mOverLot = view.findViewById(R.id.tactics_layout);
        mOverLot.setOnClickListener(this);
        mNoDataLayout = view.findViewById(R.id.no_data_layout);
        mInvestmentAmount = view.findViewById(R.id.tv_investment_amount);
        myListView = view.findViewById(R.id.mine_listView);
        mUserSet = view.findViewById(R.id.mine_user_set);
        mUserSet.setOnClickListener(this);
        mUserRecharge = view.findViewById(R.id.mine_chong_zhi);
        mUserRecharge.setOnClickListener(this);

        //暂时不显示充值
        mUserRecharge.setVisibility(View.GONE);
        mUserPhone = view.findViewById(R.id.mine_user_phone);

        mineScroll = view.findViewById(R.id.mine_ScrollView);
        mineScroll.smoothScrollTo(0, 0);
        accountDetailLayout = view.findViewById(R.id.account_details_layout);
        mineCeLueLayout = view.findViewById(R.id.mine_ce_lue_layout);
        followLayout = view.findViewById(R.id.mine_gen_sui_layout);
        followLayout.setOnClickListener(this);
        followMore = view.findViewById(R.id.gen_sui_more);
        followUp = view.findViewById(R.id.gen_sui_up);
        friendLayout = view.findViewById(R.id.mine_friend_layout);
        friendLayout.setOnClickListener(this);
        aboutLayout = view.findViewById(R.id.mine_about_layout);
        aboutLayout.setOnClickListener(this);
        mLoginOut = view.findViewById(R.id.tv_login_out);
        mLoginOut.setOnClickListener(this);
        mTvFriend = view.findViewById(R.id.tv_mine_friend);
        ceLueCount = view.findViewById(R.id.gen_sui_income);
        feedBcakLayout = view.findViewById(R.id.mine_feedback_layout);
        feedBcakLayout.setOnClickListener(this);
        cacheLayout = view.findViewById(R.id.mine_cache_clean_layout);
        bte_group_layout = view.findViewById(R.id.bte_group_layout);
        server_setting = view.findViewById(R.id.server_setting);
        cacheLayout.setOnClickListener(this);
        server_setting.setOnClickListener(this);
        bte_group_layout.setOnClickListener(this);
        mCache = view.findViewById(R.id.tv_cache_mine);
        ji_fen_layout = view.findViewById(R.id.ji_fen_layout);
        ji_fen_layout.setOnClickListener(this);
        tvXuanfu = view.findViewById(R.id.xuanfu_tv);
        tvXuanfu.setOnClickListener(this);
        tv_mine_capital = view.findViewById(R.id.tv_mine_capital);
        ll_mine_capital = view.findViewById(R.id.ll_mine_capital);
        iv_mine_capital = view.findViewById(R.id.iv_mine_capital);
        ll_mine_capital.setOnClickListener(this);
    }

    @Override
    protected void initData() throws NullPointerException {

        if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
            //跳转至登录
            EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_POP_BACK_PAGE));
            Intent intent = new Intent(getActivity(), YZMLoginActivity.class);
            intent.putExtra("gotoTarget", RouteMessage.MESSAGE_SHOW_MY_PAGE);
            startActivity(intent);

        } else {
            setMyViews();
        }

    }

    @Override
    public boolean statisticsFragment() {
        return true;
    }

    private void setMyViews() {
        initAccountInfo();
        showCurrentLot();
        showCaChe();

    }

    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(0, 300);
    }

    String avator = "";
    String name = "";

    private void updateViews() {
        UserInfo currentUserInfo = UserService.getCurrentUserInfo();
        if (currentUserInfo != null) {
            avator = currentUserInfo.getAvator();
            name = currentUserInfo.getName();
            updateAvator(avator, name);
        }
    }

    public void updateAvator(String avator, String name) {
        Glide.with(getActivity())
                .load(avator)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .placeholder(R.mipmap.mine_top_logo)
                .transform(new GlideCircleTransform(getActivity()))
                .into(mine_logo);

        mUserPhone.setText(name);
        if (name.length() == 11) {
            mUserPhone.setText(PhoneNumUtils.phoneHideNum(name));
        } else if (name.indexOf("@") != -1) {
            mUserPhone.setText(PhoneNumUtils.EmailHideNum(name));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                //跳转至登录
                EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_POP_BACK_PAGE));
                Intent intent = new Intent(getActivity(), YZMLoginActivity.class);
                intent.putExtra("gotoTarget", RouteMessage.MESSAGE_SHOW_MY_PAGE);
                startActivity(intent);
            } else {
                setMyViews();
            }
        }
    }


    private void showCaChe() {
        String cache = "";
        try {
            cache = DataCleanManager.getTotalCacheSize(getActivity());
            if ("0K".equals(cache)) {
                mCache.setText("0M");
            } else {
                mCache.setText(cache);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户信息
     */
    private void initAccountInfo() {

        BteTopService.accountInfo().compose(RxUtil.<BaseBean<AccountInfoBean.AccountInfoData>>mainAsync())
                .subscribe(new Action1<BaseBean<AccountInfoBean.AccountInfoData>>() {
                    @Override
                    public void call(BaseBean<AccountInfoBean.AccountInfoData> accountInfo) {

                        if (accountInfo != null) {
                            String code = accountInfo.getCode();
                            String message = accountInfo.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    accountDollar = FormatUtil.doubleTrans1(accountInfo.getData().getLegalAccount().getLegalBalance());
                                    mMoney.setText(accountDollar);
                                    accountBtc = FormatUtil.doubleTrans1(accountInfo.getData().getBtcAccount().getBalance());
                                    mBtc.setText(accountBtc);
                                    accountPoint = accountInfo.getData().getPoint();
                                    if (null != accountInfo && !"".equals(accountInfo)) {
                                        mTvPoint.setText(accountPoint);
                                    } else {
                                        mTvPoint.setText("0");
                                    }
                                    tv_mine_capital.setText("¥" + FormatUtil.doubleTrans1(accountInfo.getData().getTotalAsset()));
                                    TypeFaceUtils.setTVTypeFace(getActivity(), mTvPoint);
                                    TypeFaceUtils.setTVTypeFace(getActivity(), mMoney);
                                    TypeFaceUtils.setTVTypeFace(getActivity(), mBtc);
                                    String friendAccount = accountInfo.getData().getFriends();
                                    if (null != friendAccount && !"".equals(friendAccount)) {
                                        if (!"0".equals(friendAccount)) {
                                            mTvFriend.setText(friendAccount + "个好友");
                                        } else {
                                            mTvFriend.setText("");
                                        }
                                    } else {
                                        mTvFriend.setText("");
                                    }
                                    String holdCountCount = accountInfo.getData().getHoldCount();
                                    String settCount = accountInfo.getData().getSettleCount();
                                    showCeLueCount(holdCountCount, settCount);
                                } else {
                                    if (message != null) {
                                        ToastUtils.showShortToast(message);
                                    }
                                    mTvFriend.setText("");
                                }

                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        LogUtil.print("网络未连接");
                        mTvFriend.setText("");
                    }
                });
    }


    private void showCeLueCount(String holdCountCount, String settCount) {
        currentCount = holdCountCount;
        settleCount = settCount;
        StringBuilder countBuilder = new StringBuilder();
        if (!"".equals(currentCount) && !"".equals(settleCount)) {
            if (!"0".equals(currentCount) && !"0".equals(settleCount)) {
                countBuilder.append("当前").append(currentCount).append("笔")
                        .append("  ").append("赎回").append(settleCount).append("笔");
            } else if ("0".equals(currentCount) && !"0".equals(settCount)) {
                countBuilder.append("赎回").append(settleCount).append("笔");
            } else if ("0".equals(settleCount) && !"0".equals(currentCount)) {
                countBuilder.append("当前").append(currentCount).append("笔");
            }
        } else {
            if ("".equals(currentCount)) {
                if (!"0".equals(settleCount)) {
                    countBuilder.append("赎回").append(settleCount).append("笔");
                }
            } else if ("".equals(settleCount)) {
                if (!"0".equals(currentCount)) {
                    countBuilder.append("跟随").append(currentCount).append("笔");
                }
            }
        }

        String amount = countBuilder.toString();
        if (null != amount && !"".equals(amount)) {
            ceLueCount.setText(amount);
        } else {
            ceLueCount.setText("");
        }
    }

    /**
     * 获取当前投资份额
     */
    private void initCurrentLot() {

        BteTopService.getUserCurrentLot().compose(RxUtil.<BaseBean<UserStrategyData>>mainAsync())
                .subscribe(new Action1<BaseBean<UserStrategyData>>() {
                    @Override
                    public void call(BaseBean<UserStrategyData> currentLotBean) {

                        if (currentLotBean != null) {
                            String code = currentLotBean.getCode();
                            String message = currentLotBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    UserStrategyData currentData = currentLotBean.getData();
                                    if (currentData != null && !"".equals(currentData)) {
                                        ArrayList<UserStrategyData.UserStrategyDateDetails> bigDetails = currentData.getDetails();
                                        if (bigDetails.size() > 0 && bigDetails != null) {
                                            followLayout.setVisibility(View.VISIBLE);
                                            mNoDataLayout.setVisibility(View.GONE);
                                            myListView.setVisibility(View.VISIBLE);
                                            detailsAdapter = new MyMineDetailsAdapter(getActivity(), bigDetails, "current");
                                            myListView.setAdapter(detailsAdapter);
                                        } else {
                                            mNoDataLayout.setVisibility(View.VISIBLE);
                                            myListView.setVisibility(View.GONE);
                                            followLayout.setVisibility(View.GONE);
                                        }

                                    }
                                } else {
                                    if (message != null && !"".equals(message)) {
                                        ToastUtils.showShortToast(message);
                                        mNoDataLayout.setVisibility(View.VISIBLE);
                                        myListView.setVisibility(View.GONE);
                                    }

                                }
                            }
                        } else {
                            mNoDataLayout.setVisibility(View.VISIBLE);
                            myListView.setVisibility(View.GONE);
                            followLayout.setVisibility(View.GONE);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        mNoDataLayout.setVisibility(View.VISIBLE);
                        myListView.setVisibility(View.GONE);
                        System.out.println("MineFragment-->获取用户当前投资额---链接服务器失败");
                    }
                });
    }

    /**
     * 获取已结束策略
     */
    private void initSettleLot() {

        BteTopService.getUserSettleLot().compose(RxUtil.<BaseBean<UserStrategyData>>mainAsync())
                .subscribe(new Action1<BaseBean<UserStrategyData>>() {
                    @Override
                    public void call(BaseBean<UserStrategyData> currentLotBean) {

                        if (currentLotBean != null) {
                            String code = currentLotBean.getCode();
                            String message = currentLotBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    UserStrategyData settleData = currentLotBean.getData();
                                    if (settleData != null && !"".equals(settleData)) {
                                        ArrayList<UserStrategyData.UserStrategyDateDetails> bigDetails = settleData.getDetails();
                                        if (bigDetails.size() != 0 && bigDetails != null) {
                                            mNoDataLayout.setVisibility(View.GONE);
                                            myListView.setVisibility(View.VISIBLE);
                                            detailsAdapter = new MyMineDetailsAdapter(getActivity(), bigDetails, "settle");
                                            myListView.setAdapter(detailsAdapter);
                                        } else {
                                            mNoDataLayout.setVisibility(View.VISIBLE);
                                            myListView.setVisibility(View.GONE);
                                        }

                                    }
                                } else {
                                    if (message != null && !"".equals(message)) {
                                        ToastUtils.showShortToast(message);
                                        mNoDataLayout.setVisibility(View.VISIBLE);
                                        myListView.setVisibility(View.GONE);
                                    }

                                }
                            }
                        } else {
                            mNoDataLayout.setVisibility(View.VISIBLE);
                            myListView.setVisibility(View.GONE);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        mNoDataLayout.setVisibility(View.VISIBLE);
                        myListView.setVisibility(View.GONE);
                        System.out.println("MineFragment-->获取用户已结束投资---链接服务器失败");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lot_layout:
                showCurrentLot();
                break;
            case R.id.tactics_layout:
                showOverLot();
                break;
            /*
            case R.id.mine_chong_zhi://充值
                startActivity(new Intent(getActivity(), RechargeActivity.class));
                break;
            */
            case R.id.server_setting:
                startActivity(new Intent(getActivity(), ServerSettingActivity.class));
                break;
            case R.id.mine_user_set:
                startActivity(new Intent(getActivity(), PersonSettingActivity.class));
                break;
            case R.id.bte_group_layout:
                startActivity(new Intent(getActivity(), BTEGroupActivity.class));
                break;
            case R.id.mine_gen_sui_layout:
                if (isOpen == false) {
                    isOpen = true;
                    followUp.setVisibility(View.VISIBLE);
                    followMore.setVisibility(View.GONE);
                    mineCeLueLayout.setVisibility(View.VISIBLE);
                } else {
                    isOpen = false;
                    followUp.setVisibility(View.GONE);
                    followMore.setVisibility(View.VISIBLE);
                    mineCeLueLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_login_out:
                showLogoutDialog(LOGIN_OUT);
                break;
            case R.id.mine_friend_layout:
                startActivity(new Intent(getActivity(), InvitingFriendsResultActivity.class));
                break;
            case R.id.mine_about_layout:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.mine_cache_clean_layout:
                showLogoutDialog(CLEAN_CACHE);
                break;
            case R.id.mine_feedback_layout:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.ji_fen_layout:
                Intent intent = new Intent(getContext(), CommonWebViewActivity.class);
                int point = UserService.getCurrentUserInfo().getPoint();
                String jifenUrl = UrlConfig.JI_FEN_URL + point;
                intent.putExtra("url", jifenUrl);
                startActivity(intent);
                break;
            case R.id.xuanfu_tv:
                Intent intent1 = new Intent(getActivity(), OverDrawActivity.class);
                startActivity(intent1);

                break;
            case R.id.ll_mine_capital:
                Intent capital = new Intent(getContext(), CommonWebViewActivity.class);
                capital.putExtra("url", UrlConfig.MINE_CAPITAL);
                startActivity(capital);
                break;

        }
    }

    /**
     * 显示退出对话框
     */
    public void showLogoutDialog(String message) {
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.logout_dialog_layout, null);
        initDialogView(message, view);
        mLogoutDialog = new Dialog(getActivity(), R.style.MyDialogStyle);
        mLogoutDialog.setContentView(view);
        mLogoutDialog.setCancelable(false);
        mLogoutDialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        mLogoutDialog.getWindow().setGravity(Gravity.CENTER);
        mLogoutDialog.show();
    }

    private void initDialogView(final String message, View view) {
        final TextView mTvContent = view.findViewById(R.id.dialog_content);
        TextView mTvCancel = view.findViewById(R.id.tv_cancel);

        TextView mTvSure = view.findViewById(R.id.tv_sure);
        if (LOGIN_OUT.equals(message)) {
            mTvContent.setText("确定要退出登录吗?");
        } else if (CLEAN_CACHE.equals(message)) {
            mTvContent.setText("确定清除缓存吗?");
        }
        mTvSure.setText("确定");
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissLogoutDialog();
            }
        });
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LOGIN_OUT.equals(message)) {
                    loginOutMyAccount();
                } else if (CLEAN_CACHE.equals(message)) {
                    try {
                        DataCleanManager.clearAllCache(getActivity());
                        showCaChe();
                        ToastUtils.showShortToast("缓存清理完毕");
                        dismissLogoutDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void dismissLogoutDialog() {
        if (mLogoutDialog != null) {
            mLogoutDialog.dismiss();
        }
    }

    /**
     * 退出登录
     */
    private void loginOutMyAccount() {

        BteTopService.userLogout().compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean userLogoutBean) {
                        if (userLogoutBean != null) {
                            String code = userLogoutBean.getCode();
                            String message = userLogoutBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    //清空本地用户信息
                                    UserService.setCurrentUserToken("");
                                    UserService.setChatUserName("");
                                    UserService.setChatUserAvatar("");

                                    //环信退出
                                    EMClient.getInstance().logout(true);

                                    EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_HOME_PAGE, null));
                                    EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_LOGIN_OUT_SUCCESS, null));
                                    ToastUtils.showShortToast("退出账户");

                                    //关闭悬浮长
                                    if (OverDrawActivity.mService != null) {
                                        OverDrawActivity.mService = null;
                                        BteTopApplication.getInstance().unbindService(OverDrawActivity.conn);
                                    }
                                    BteTopApplication.getInstance().stopService(new Intent(BteTopApplication.getInstance(), FloatPriceService.class));
                                    dismissLogoutDialog();
                                } else {
                                    if (message != null) {
                                        ToastUtils.showShortToast(message);
                                    }
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        System.out.println("MineFragment-->退出用户---链接服务器失败");
                    }
                });
    }

    /**
     * 显示已结束投资
     */
    private void showOverLot() {
        mOverLot.setBackgroundColor(getActivity().getResources().getColor(R.color.base_color));
        mTvOver.setTextColor(getResources().getColor(R.color.color_626A75));
        mOverLine.setVisibility(View.VISIBLE);
        mCurrentLot.setBackgroundColor(getActivity().getResources().getColor(R.color.color_EDFOF2));
        mTvLot.setTextColor(getResources().getColor(R.color.color_626A75_60));
        mLotLine.setVisibility(View.INVISIBLE);
        initSettleLot();
    }

    /**
     * 显示当前投资份额
     */
    private void showCurrentLot() {
        mCurrentLot.setBackgroundColor(getActivity().getResources().getColor(R.color.base_color));
        mTvLot.setTextColor(getResources().getColor(R.color.color_626A75));
        mLotLine.setVisibility(View.VISIBLE);
        mOverLot.setBackgroundColor(getActivity().getResources().getColor(R.color.color_EDFOF2));
        mTvOver.setTextColor(getResources().getColor(R.color.color_626A75_60));
        mOverLine.setVisibility(View.INVISIBLE);
        initCurrentLot();
    }
}
