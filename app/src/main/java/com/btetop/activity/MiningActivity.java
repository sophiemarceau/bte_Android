package com.btetop.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.MiningBean;
import com.btetop.bean.MiningSchedule;
import com.btetop.bean.MiningSignBean;
import com.btetop.config.Constant;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.MiningRulesDialog;
import com.btetop.dialog.ShareMiningPicDialog;
import com.btetop.dialog.SignInDialog;
import com.btetop.monitor.M;
import com.btetop.net.BteTopService;
import com.btetop.service.MiningService;
import com.btetop.utils.CommonUtils;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.zylaoshi.library.utils.DisplayUtil;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.btetop.utils.DateUtils.getDateTime;

public class MiningActivity extends BaseActivity {
    @BindView(R.id.img_fan_hui)
    ImageView imgFanHui;
    @BindView(R.id.tran_title_detail)
    TextView tranTitleDetail;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.tv_calculate)
    TextView tvCalculate;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_mining_rule)
    TextView tvMiningRule;
    @BindView(R.id.tv_mining_middle1)
    TextView tvMiningMiddle1;
    @BindView(R.id.tv_mining_middle2)
    TextView tv_mining_middle2;
    @BindView(R.id.tv_mining_middle3)
    TextView tv_mining_middle3;
    @BindView(R.id.tv_calculate_add)
    TextView tvCalculateAdd;
    @BindView(R.id.tv_fixed_task)
    TextView tvFixedTask;
    @BindView(R.id.ll_mining_1)
    LinearLayout llMining1;
    @BindView(R.id.ll_mining_2)
    LinearLayout llMining2;
    @BindView(R.id.ll_mining_3)
    LinearLayout llMining3;
    @BindView(R.id.ll_mining_4)
    LinearLayout llMining4;
    @BindView(R.id.tv_mining_invite_friend)
    TextView tvMiningInviteFriend;
    @BindView(R.id.tv_mining_keep_login)
    TextView tvMiningKeepLogin;
    @BindView(R.id.tv_mining_day_share)
    TextView tvMiningDayShare;
    @BindView(R.id.tv_mining_read_report)
    TextView tvMiningReadRetport;
    @BindView(R.id.tv_mining_btc_count)
    TextView tvMiningBtcCount;
    @BindView(R.id.tv_mining_info_middle)
    TextView tvMiningInfoMiddle;
    @BindView(R.id.tv_mining_info_above)
    TextView tvMiningInfoAbove;
    @BindView(R.id.tv_mining_info_below)
    TextView tvMiningInfoBelow;
    @BindView(R.id.iv_mining_4)
    ImageView ivMining4;
    @BindView(R.id.tv_mining_4)
    TextView tvMining4;
    @BindView(R.id.ll_task)
    LinearLayout llTask;
    @BindView(R.id.iv_mining_background)
    ImageView iv_mining_background;
    @BindView(R.id.iv_mining_bg2)
    ImageView iv_mining_bg2;
    @BindView(R.id.tv_mining_notice)
    SimpleMarqueeView tv_mining_notice;

    long day;
    long hour;
    long minute;
    long second;

    private boolean dayNotAlready = false;
    private boolean hourNotAlready = false;
    private boolean minuteNotAlready = false;
    private boolean secondNotAlready = false;


    private MiningBean.TaskBean taskBean;
    private MiningBean.InfoBean info;
    List<String> strList;
    private Timer timer;
    private int refreshTimes = 0;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (tvMiningInfoMiddle == null) {
                        return;
                    }
                    if (day < 0 && hour < 0 && minute < 0) {
                        refreshData();
                    }
                    tvMiningInfoMiddle.setText(day + "天" + hour + "时" + minute + "分" + second + "秒");
                    break;

                case 1:
                    if (tv_mining_middle2 == null) {
                        return;
                    }
                    if (day <= 0 && hour <= 0 && minute <= 0 && second <= 0) {
                        refreshTimes++;
                        if (refreshTimes <= 3) {
                            refreshData();
                        }
                    }

                    long endHour = day * 24 + hour;
                    String endHourStr = "";
                    String endMinStr = "";
                    if (endHour < 0)
                        endHour = 00;

                    if (minute > 0 || hour > 0) {
                        if (minute < 10) {
                            endMinStr = "0" + minute;
                        } else {
                            endMinStr = minute + "";
                        }
                        if (endHour < 10) {
                            endHourStr = "0" + endHour;
                            tv_mining_middle2.setText(endHourStr + ":" + endMinStr);
                        } else {
                            tv_mining_middle2.setText(endHour + ":" + endMinStr);
                        }
                    } else {
                        tv_mining_middle2.setText(second + "");
                    }


                    break;
            }
        }
    };


    @Override
    public int intiLayout() {
        return R.layout.fragment_mining;
    }

    @Override
    public void initView() {

        tranTitleDetail.setText("全民挖矿");
        M.monitor().onEvent(this, Constant.MINING_HOME);

        //动态设置球的位置
        int screenHeight = ScreenUtils.getScreenHeight();
        int screenWidth = ScreenUtils.getScreenWidth();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.mining_home_middle);
        int iv_mining_bg2Height = bitmap.getHeight();
        int iv_mining_bg2Width = bitmap.getWidth();

        llTask.measure(0, 0);
        int ll_task_height = llTask.getMeasuredHeight();

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) iv_mining_bg2.getLayoutParams();
        marginLayoutParams.topMargin = (screenHeight - iv_mining_bg2Height - ll_task_height - DisplayUtil.dp2px(this, 40)) / 2;
        marginLayoutParams.leftMargin = (screenWidth - iv_mining_bg2Width) / 2;
        iv_mining_bg2.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
        resetData();
    }

    private void resetData() {
        day = 0;
        hour = 0;
        minute = 0;
        second = 0;
        dayNotAlready = false;
        hourNotAlready = false;
        minuteNotAlready = false;
        secondNotAlready = false;
    }


    public void refreshData() {
        initBaseInfo();
        initActionSchedule();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void initActionSchedule() {
        BteTopService.doMiningSchedule()
                .compose(RxUtil.<BaseBean<MiningSchedule>>mainAsync())
                .subscribe(new Action1<BaseBean<MiningSchedule>>() {
                    @Override
                    public void call(BaseBean<MiningSchedule> baseBean) {
                        if (baseBean != null) {

                            tvMiningBtcCount.setText("奖励池:" + baseBean.getData().getAmount() + "BTC");
                            String digStart = baseBean.getData().getDigStart();
                            doAction(baseBean.getData().getStatus(),
                                    digStart,
                                    baseBean.getData().getIncome(),
                                    baseBean.getData().getMessage());
                            if (digStart != null) {
                                convertTime(digStart);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * status -2 活动暂停 前端控制无法操作即可
     * status  0 活动未开始 获取digStart进行倒计时
     * status  1 算力收集中 获取digStart进行倒计时
     * status  2 挖矿 获取income显示收益信息，可点击获取
     *
     * @param status
     * @param digStart
     * @param income
     * @param message
     */

    private void doAction(int status, String digStart, String income, String message) {

        strList = CommonUtils.getStrList(message, 25);
        SimpleMF<String> marqueeFactory = new SimpleMF(this);
        marqueeFactory.setData(strList);
        tv_mining_notice.setMarqueeFactory(marqueeFactory);
        switch (status) {
            case 0:
                NoStartTimer(digStart);
                break;
            case 1:
                CollectCalculate(digStart, message);
                break;
            case 2:
                canGetIncome(income);
                break;
            case -2:

                break;
        }

    }

    private void canGetIncome(String income) {
        tvMiningMiddle1.setVisibility(View.VISIBLE);
        tvMiningMiddle1.setText(income + "");
        tvMiningInfoAbove.setVisibility(View.GONE);
        tvMiningInfoMiddle.setVisibility(View.GONE);
        tvMiningInfoBelow.setVisibility(View.GONE);
        tv_mining_middle2.setVisibility(View.GONE);
        tv_mining_middle3.setVisibility(View.GONE);
        tv_mining_notice.setVisibility(View.VISIBLE);
    }

    private void CollectCalculate(String digStart, String message) {
        tv_mining_middle2.setVisibility(View.VISIBLE);
        tv_mining_middle3.setVisibility(View.VISIBLE);
        tvMiningInfoAbove.setVisibility(View.GONE);
        tvMiningInfoMiddle.setVisibility(View.GONE);
        tvMiningInfoBelow.setVisibility(View.GONE);
        tvMiningMiddle1.setVisibility(View.GONE);
        tv_mining_notice.setVisibility(View.VISIBLE);
        StartThread(1);
    }

    private void NoStartTimer(String digStart) {
        tvMiningInfoAbove.setVisibility(View.VISIBLE);
        tvMiningInfoMiddle.setVisibility(View.VISIBLE);
        tvMiningInfoBelow.setVisibility(View.VISIBLE);
        tvMiningMiddle1.setVisibility(View.GONE);
        tv_mining_middle2.setVisibility(View.GONE);
        tv_mining_middle3.setVisibility(View.GONE);
        tv_mining_notice.setVisibility(View.VISIBLE);
        StartThread(0);
    }

    //格式化时间 并且赋值
    //拿到秒数后 开始倒计时
    public void convertTime(String digStart) {
        long time = getDateTime(digStart).getTime();
        long timeDQ = new Date().getTime();
        long date = time - timeDQ;
//        day = date / (1000 * 60 * 60 * 24);
//        hour = (date / (1000 * 60 * 60) - day * 24);
//        minute = ((date / (60 * 1000)) - day * 24 * 60 - hour * 60);
//        second = (date / 1000) - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60;
        long totalSecond = date / 1000;
        CountDownStart(totalSecond);
    }

    //开启线程
    private boolean isRun = true;

    private void StartThread(int type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    try {
                        Thread.sleep(1000); // sleep 1000ms
                        Message message = Message.obtain();
                        message.what = type;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void initBaseInfo() {
        MiningService.loadMiningInfo(new MiningService.loadMiningInfo() {
            @Override
            public void success(MiningBean data) {
                info = MiningService.getMiningBean().getInfo();
                tvCalculate.setText("算力:" + info.getTotalPower());
                String totalIncome = info.getTotalIncome();
                double income;
                try {
                    income = Double.parseDouble(totalIncome);
                } catch (Exception e) {
                    income = 0;
                }
                if (income == 0) {
                    tvCount.setText("0");
                } else {
                    tvCount.setText(totalIncome + "");
                }
//                tvMiningBtcCount.setText();

                taskBean = MiningService.getMiningBean().getTask();
                tvMiningInviteFriend.setText("+" + taskBean.getInvite());

                if (taskBean.getSign() != 0) {
                    tvMiningKeepLogin.setText("+" + taskBean.getSign());
                } else {
                    tvMiningKeepLogin.setText("连续签到" + info.getSignStatus() + "天");
                }
                if (taskBean.getShare() != 0) {
                    tvMiningDayShare.setText("+" + taskBean.getShare());
                } else {
                    tvMiningDayShare.setText("今日已分享");
                }
                if (taskBean.getRead() != 0) {
                    tvMiningReadRetport.setText("+" + taskBean.getRead());
                } else {
                    tvMiningReadRetport.setText("今日已阅读");
                }

                if (taskBean.getReportStatus() == 0) {
                    Glide.with(MiningActivity.this).load(R.mipmap.iv_mining_read_retport_no).into(ivMining4);
                    tvMining4.setTextColor(Color.parseColor("#4e517b"));
                    tvMiningReadRetport.setTextColor(Color.parseColor("#4e517b"));
                } else {
                    Glide.with(MiningActivity.this).load(R.mipmap.iv_mining_read_retport_yes).into(ivMining4);
                    tvMining4.setTextColor(Color.parseColor("#ffffff"));
                    tvMiningReadRetport.setTextColor(Color.parseColor("#ffffff"));
                }
            }

            @Override
            public void open() {
            }

            @Override
            public void noOpen(String inviteCount) {
            }
        });
    }


    @Override
    public boolean statisticsActivity() {
        return false;
    }


    @OnClick({R.id.img_fan_hui,
            R.id.ll_mining_1,
            R.id.ll_mining_2,
            R.id.ll_mining_3,
            R.id.ll_mining_4,
            R.id.tv_mining_rule,
            R.id.tv_fixed_task,
            R.id.tv_calculate,
            R.id.tv_count,
            R.id.tv_mining_middle1,
    })
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.img_fan_hui:
                finish();
                break;
            //邀请好友
            case R.id.ll_mining_1:
                M.monitor().onEvent(this, Constant.MINING_INVITE);
                startActivityWithUrl(UrlConfig.MINING_INDEX_URL + "invite");
                break;
            //连续签到
            case R.id.ll_mining_2:
                if (taskBean.getSign() != 0) {
                    doMiningSign();
                }
                break;
            //每日分享
            case R.id.ll_mining_3:
                ShareMiningPicDialog shareMiningPicDialog = ShareMiningPicDialog.newInstance(this, new ShareMiningPicDialog.doShare() {
                    @Override
                    public void doShare() {
                        refreshData();
                    }
                });
                shareMiningPicDialog.show(getSupportFragmentManager(), "MiningActivity");
                break;
            //阅读报告
            case R.id.ll_mining_4:
                if (taskBean.getReportStatus() == 1 && taskBean.getRead() != 0)
                    startActivityWithUrl(taskBean.getReportUrl());
                break;
            //挖矿规则
            case R.id.tv_mining_rule:
                M.monitor().onEvent(this, Constant.MINING_ACTION_RULE);
                new MiningRulesDialog().show(this);
                break;
            //固定任务
            case R.id.tv_fixed_task:
                startActivityWithUrl(UrlConfig.MINING_INDEX_URL + "mission");
//                startActivity(new Intent(MiningActivity.this, MiningTaskActivity.class));
                break;
            //我的算力
            case R.id.tv_calculate:
                startActivityWithUrl(UrlConfig.MINING_INDEX_URL + "energy");
                M.monitor().onEvent(this, Constant.MINING_HOME_CALCULATE);
                break;
            //我的账户余额
            case R.id.tv_count:
                M.monitor().onEvent(this, Constant.MINING_WALLET_GET);
                startActivityWithUrl(UrlConfig.MINING_INDEX_URL + "wallet");
                break;
            //获取收益
            case R.id.tv_mining_middle1:
                getCalculate();
                break;
        }
    }

    private void doMiningSign() {
        BteTopService.doMiningSign()
                .compose(RxUtil.<BaseBean<MiningSignBean>>mainAsync())
                .subscribe(new Action1<BaseBean<MiningSignBean>>() {
                    @Override
                    public void call(BaseBean<MiningSignBean> signBeanBaseBean) {
                        if (signBeanBaseBean != null) {
                            if ("0000".equals(signBeanBaseBean.getCode())) {
                                new SignInDialog().show(MiningActivity.this, signBeanBaseBean.getData().getSignStatus(), new SignInDialog.SignInListener() {
                                    @Override
                                    public void onCancel() {
                                        refreshData();
                                    }

                                    @Override
                                    public void onCertain() {
                                        refreshData();
                                    }
                                });
                            } else {
                                ToastUtils.showShortToast(signBeanBaseBean.getMessage() + "");
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    private void getCalculate() {
        BteTopService.getCalculate()
                .compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean != null) {
                            if ("0000".equals(baseBean.getCode())) {
                                ToastUtils.showShortToast(baseBean.getMessage());
                                refreshData();
                            } else {
                                ToastUtils.showShortToast(baseBean.getMessage());
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public void startActivityWithUrl(String url) {
        Intent amount = new Intent(this, CommonWebViewActivity.class);
        amount.putExtra("url", url);
        amount.putExtra("showShare", false);
        startActivity(amount);
    }

    private void endLoop() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        endLoop();
        timer.cancel();
    }


    private void initData(long totalSeconds) {
        resetData();

        if (totalSeconds > 0) {
            secondNotAlready = true;
            second = totalSeconds;
            if (second >= 60) {
                minuteNotAlready = true;
                minute = second / 60;
                second = second % 60;
                if (minute >= 60) {
                    hourNotAlready = true;
                    hour = minute / 60;
                    minute = minute % 60;
                    if (hour > 24) {
                        dayNotAlready = true;
                        day = hour / 24;
                        hour = hour % 24;
                    }
                }
            }
        }
    }

    /**
     * 计算各个值的变动
     */
    private void startCount() {
        if (secondNotAlready) {
            if (second > 0) {
                second--;
                if (second == 0 && !minuteNotAlready) {
                    secondNotAlready = false;
                }
            } else {
                if (minuteNotAlready) {
                    if (minute > 0) {
                        minute--;
                        second = 59;
                        if (minute == 0 && !hourNotAlready) {
                            minuteNotAlready = false;
                        }

                    } else {
                        if (hourNotAlready) {
                            if (hour > 0) {
                                hour--;
                                minute = 59;
                                second = 59;
                                if (hour == 0 && !dayNotAlready) {
                                    hourNotAlready = false;
                                }

                            } else {
                                if (dayNotAlready) {
                                    day--;
                                    hour = 23;
                                    minute = 59;
                                    second = 59;
                                    if (day == 0) {
                                        dayNotAlready = false;
                                    }

                                }
                            }
                        }
                    }

                }
            }

        }
    }

    public void CountDownStart(long totalSeconds) {
        initData(totalSeconds);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (secondNotAlready) {
                    startCount();
                } else {
                    cancel();
                }

            }
        }, 0, 1000);
    }
    @Override
    public void onStart() {
        super.onStart();
        tv_mining_notice.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        tv_mining_notice.stopFlipping();
    }
}
