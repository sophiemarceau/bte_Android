package com.btetop.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.btetop.R;
import com.btetop.adapter.AirBoardAdapter;
import com.btetop.application.BteTopApplication;
import com.btetop.base.BaseActivity;
import com.btetop.bean.AirBoardBean;
import com.btetop.bean.ArtBoardIdBean;
import com.btetop.bean.BaseBean;
import com.btetop.dialog.PositionTheCostMultiDialog;
import com.btetop.dialog.PositionTheCostOnlyDialog;
import com.btetop.net.BteTopService;
import com.btetop.service.ArtBoardService;
import com.btetop.utils.RxUtil;
import com.btetop.widget.floatprice.FloatPriceService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;
import rx.functions.Action1;

import static com.btetop.utils.CheckPermisson.checkDrawOverPermisson;

public class OverDrawActivity extends BaseActivity implements SwitchView.OnStateChangedListener, ArtBoardService.DataListener, AirBoardAdapter.listViewClick {
    @BindView(R.id.switch_button)
    SwitchView switchView;

    @BindView(R.id.tran_title_detail)
    TextView title;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.ll_empty)
    RelativeLayout emptyLayout;
    @BindView(R.id.ll_data_view)
    RelativeLayout dataView;

    public static FloatPriceService mService;
    private AirBoardAdapter adapter;

    private ArrayList<AirBoardBean> mdata = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(mContext)) {
                    closeDrawOverView();
                    ToastUtils.showShort("授权失败");
                } else {
                    ToastUtils.showShort("授权成功");
                    openDrawaOverView();
                }
            }
        }
    };

    @Override
    public int intiLayout() {
        return R.layout.activity_over_draw;
    }

    @Override
    public void initView() {
        switchView.setOnStateChangedListener(this);
        title.setText("悬浮窗设置");
        if (FloatPriceService.isStarted) {
            switchView.setOpened(true);
        }
    }

    private void openPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//需要申请权限
            if (checkDrawOverPermisson()) {
                openDrawaOverView();
            } else {
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 1);
            }

        } else {//6.0直接打开
            openDrawaOverView();

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        ArtBoardService instance = ArtBoardService.getInstance();
        instance.addListener(this);
        instance.getOnLineData();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    private void openDrawaOverView() {
        switchView.setOpened(true);
        if (FloatPriceService.isStarted) {
            return;
        }

        Intent intent = new Intent(BteTopApplication.getInstance(), FloatPriceService.class);
//        intent.putStringArrayListExtra(AirBoardBean.SerializableTag,mdata);
        intent.putExtra(AirBoardBean.SerializableTag, (Serializable) mdata);
        BteTopApplication.getInstance().startService(intent);
        BteTopApplication.getInstance().bindService(intent, conn, Context.BIND_AUTO_CREATE);

    }

    private void closeDrawOverView() {
        switchView.setOpened(false);
        if (mService != null) {
            mService = null;
            BteTopApplication.getInstance().unbindService(conn);
        }
        BteTopApplication.getInstance().stopService(new Intent(BteTopApplication.getInstance(), FloatPriceService.class));
    }


    @Override
    public void initData() {
    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }

    @OnClick({R.id.img_fan_hui, R.id.ll_empty,R.id.tv_add})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.img_fan_hui:
                this.finish();
                break;
            case R.id.right_tv:
                break;
            case R.id.ll_empty:
                go2AddActivity();
                break;
            case R.id.tv_add:
                go2AddActivity();
                break;
        }

    }

    @Override
    public void toggleToOn(SwitchView view) {
        openPermission();
        requestOnOff();
    }

    @Override
    public void toggleToOff(SwitchView view) {
        closeDrawOverView();
        requestOnOff();
    }

    public static ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FloatPriceService.ConnectFloatPriceServiceBinder binder = (FloatPriceService.ConnectFloatPriceServiceBinder) service;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };


    @Override
    public void onData(List<AirBoardBean> data) {
        if (data.size() == 0) {
            this.mdata.clear();
            dataView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            dataView.setVisibility(View.VISIBLE);
            this.mdata = (ArrayList<AirBoardBean>) data;
            adapter = new AirBoardAdapter(mdata, this);
            adapter.setListener(this);
            listView.setAdapter(adapter);
        }

        ArtBoardService.getInstance().unSetListener(this);
    }

    @Override
    public void rightClick(int position) {
        int artBoardId = mdata.get(position).getArtBoardId();
        deleteItem(artBoardId, position);
    }

    private void deleteItem(int artBoardId, int position) {
        BteTopService.delArtBoard(artBoardId)
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if ("0000".equals(baseBean.getCode())) {
                            ToastUtils.showShort(baseBean.getMessage());
                            //删除字节自己列表的
                            mdata.remove(position);
                            adapter.notifyDataSetChanged();
                            //更新service中的
                            delService(position);

                            if (mdata.size() == 0) {
                                dataView.setVisibility(View.GONE);
                                emptyLayout.setVisibility(View.VISIBLE);
                            }else {
                                emptyLayout.setVisibility(View.GONE);
                                dataView.setVisibility(View.VISIBLE);
                            }


                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    private void delService(int position) {
        if (mService != null) {
            mService.delItem(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void centerClick(int position) {
        showDialog(mdata.get(position));
    }

    private void showDialog(AirBoardBean airBoardBean) {
        if (airBoardBean.getQuoteAsset().equalsIgnoreCase("QUARTER")) {//合约
            showHeyueDialog(airBoardBean);
        } else {
            showNormalDialog(airBoardBean);
        }
    }

    private void showNormalDialog(AirBoardBean airBoardBean) {

        PositionTheCostOnlyDialog
                .getEditInstance(airBoardBean.getCostPrice() + "", "取消", airBoardBean.getQuoteAsset())
                .showDialog(mContext, new PositionTheCostOnlyDialog.OnClickListener() {
                    @Override
                    public void onAddClick(String costPrice) {
                        airBoardBean.setCostPrice(Double.parseDouble(costPrice));
                        requestSetCostPrice(airBoardBean, Double.parseDouble(costPrice));
                    }

                    @Override
                    public void onSkipClick() {

                    }
                });
    }


    private void showHeyueDialog(AirBoardBean airBoardBean) {

        PositionTheCostMultiDialog
                .getEditInstance(airBoardBean.getCostPrice() + "", "取消", airBoardBean.getDirection(), airBoardBean.getRatio())
                .showDialog(this, new PositionTheCostMultiDialog.onBtnClickListener() {
                    @Override
                    public void onAddClick(String editInfo, int group1type, int ratio) {
                        airBoardBean.setCostPrice(Double.parseDouble(editInfo));
                        airBoardBean.setDirection(group1type);
                        airBoardBean.setRatio(ratio);
                        requestAdd(airBoardBean);

                    }

                    @Override
                    public void onSkipClick() {
                    }
                });
    }

    /**
     * 设置成本价
     */
    private void requestSetCostPrice(AirBoardBean item, double costPrice) {
        BteTopService.setArtBoard(item)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<ArtBoardIdBean>>() {
                    @Override
                    public void call(BaseBean<ArtBoardIdBean> artBoardIdBeanBaseBean) {
                        if ("0000".equalsIgnoreCase(artBoardIdBeanBaseBean.getCode())) {
                            item.setCostPrice(costPrice);
                            adapter.notifyDataSetChanged();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void go2AddActivity() {
        ArtBoardService.getInstance().setData(mdata);
        mContext.startActivity(new Intent(mContext, AddCoinPairActivity.class));
    }

    private void requestOnOff() {
        int onOff = 0;
        if (switchView.isOpened()) {
            onOff = 1;
        }
        BteTopService.setOnoff(onOff)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });


    }

    public void requestAdd(AirBoardBean item) {
        BteTopService.setArtBoard(item)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<ArtBoardIdBean>>() {
                    @Override
                    public void call(BaseBean<ArtBoardIdBean> artBoardIdBeanBaseBean) {

                        if ("0000".equals(artBoardIdBeanBaseBean.getCode())) {
                            ToastUtils.showShort(artBoardIdBeanBaseBean.getMessage());
                            adapter.notifyDataSetChanged();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();

                    }
                });

    }


}
