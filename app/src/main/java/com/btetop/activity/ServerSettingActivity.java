package com.btetop.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.V2DogsBean;
import com.btetop.net.BteTopService;
import com.btetop.service.DogsService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.btetop.widget.PopDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;
import rx.functions.Action1;

public class ServerSettingActivity extends BaseActivity {
    @BindView(R.id.switch_button_heyue)
    SwitchView switchButtonHeyue;
    @BindView(R.id.switch_button_luzhuang)
    SwitchView switchButtonLuzhuang;
    @BindView(R.id.switch_button_boduan)
    SwitchView switchButtonBoduan;

    @BindView(R.id.img_fan_hui)
    ImageView img_fan_hui;
    @BindView(R.id.tran_title_detail)
    TextView tran_title_detail;
    PopDialogFragment popDialogFragment;

    @Override
    public int intiLayout() {
        return R.layout.activity_server_setting;
    }


    @Override
    public void initView() {
        tran_title_detail.setText("服务设置");

        //开 对应 关
        switchButtonLuzhuang.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                boolean opened = !view.isOpened();
                String lzDog_point = DogsService.getLzDog_point();
                String lzString = "开启撸庄狗每天将消耗" + lzDog_point + "积分,是否确认开启?";
                showPopOpenDialog(lzString, "lz", view, opened);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                boolean opened = !view.isOpened();
                String closeInfo = "是否确认关闭撸庄狗服务？";
                showCloseDialog(closeInfo, "lz", view, opened);
            }
        });
        switchButtonHeyue.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                boolean opened = !view.isOpened();
                String futureDog_point = DogsService.getFutureDog_point();
                String heyueString = "开启合约狗每天将消耗" + futureDog_point + "积分,是否确认开启?";
                showPopOpenDialog(heyueString, "heyue", view, opened);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                boolean opened = !view.isOpened();
                String closeInfo = "是否确认关闭合约狗服务？";
                showCloseDialog(closeInfo, "heyue", view, opened);
            }
        });

        switchButtonBoduan.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                boolean opened = !view.isOpened();
                String bandDog_point = DogsService.getBandDog_point();
                String boduanString = "开启波段狗每天将消耗" + bandDog_point + "积分,是否确认开启?";
                showPopOpenDialog(boduanString, "boduan", view, opened);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                boolean opened = !view.isOpened();
                String closeInfo = "是否确认关闭波段狗服务？";
                showCloseDialog(closeInfo, "boduan", view, opened);
            }
        });
    }

    @Override
    public void initData() {
        getDogsInfo();
    }

    public void SwitchLuzhuang(SwitchView view, boolean isChecked) {
        String status = "0";
        if (isChecked) {
            status = "1";
        } else {
            status = "0";
        }

        BteTopService.switchLuzhuang(status).compose(bindToLifecycle()).compose(RxUtil.mainAsync()).subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if ("0000".equals(baseBean.getCode())) {
                    view.toggleSwitch(isChecked);
                } else {
                    ToastUtils.showShortToast(baseBean.getMessage() + "");
                    view.toggleSwitch(!isChecked);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                view.toggleSwitch(!isChecked);
            }
        });
    }

    public void SwitchHeyue(SwitchView view, boolean isChecked) {
        String status = "0";
        if (isChecked) {
            status = "1";
        } else {
            status = "0";
        }

        BteTopService.switchFuture(status).compose(bindToLifecycle()).compose(RxUtil.mainAsync()).subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if ("0000".equals(baseBean.getCode())) {
                    ToastUtils.showShortToast(baseBean.getMessage());
                    view.toggleSwitch(isChecked);
                } else {
                    ToastUtils.showShortToast(baseBean.getMessage() + "");
                    view.toggleSwitch(!isChecked);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                view.toggleSwitch(!isChecked);
            }
        });
    }

    public void SwitchBoduan(SwitchView view, boolean isChecked) {
        String status = "0";
        if (isChecked) {
            status = "1";
        } else {
            status = "0";
        }

        BteTopService.switchBoduan(status).compose(bindToLifecycle()).compose(RxUtil.mainAsync()).subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if ("0000".equals(baseBean.getCode())) {
                    ToastUtils.showShortToast(baseBean.getMessage());
                    view.toggleSwitch(isChecked);
                } else {
                    ToastUtils.showShortToast(baseBean.getMessage() + "");
                    view.toggleSwitch(!isChecked);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                view.toggleSwitch(!isChecked);
            }
        });
    }

    private void getDogsInfo() {

        BteTopService.getV2Dogs().compose(bindToLifecycle()).compose(RxUtil.mainAsync()).subscribe(new Action1<BaseBean<List<V2DogsBean>>>() {
            @Override
            public void call(BaseBean<List<V2DogsBean>> listBaseBean) {
                if (("0000").equals(listBaseBean.getCode())) {
                    for (int i = 0; i < listBaseBean.getData().size(); i++) {
                        V2DogsBean v2DogsBean = listBaseBean.getData().get(i);
                        switch (v2DogsBean.getName()) {
                            case "撸庄狗":
                                if (v2DogsBean.getStatus() == 1) {
                                    switchButtonLuzhuang.toggleSwitch(true);
                                } else {
                                    switchButtonLuzhuang.toggleSwitch(false);
                                }
                                break;
                            case "波段狗":
                                if (v2DogsBean.getStatus() == 1) {
                                    switchButtonBoduan.toggleSwitch(true);
                                } else {
                                    switchButtonBoduan.toggleSwitch(false);
                                }
                                break;
                            case "合约狗":
                                if (v2DogsBean.getStatus() == 1) {
                                    switchButtonHeyue.toggleSwitch(true);
                                } else {
                                    switchButtonHeyue.toggleSwitch(false);
                                }
                                break;
                        }
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    @OnClick({R.id.img_fan_hui})
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.img_fan_hui:
                finish();
                break;

        }
    }

    public void showCloseDialog(String closeInfo, String type, SwitchView view, boolean opened) {
        popDialogFragment = PopDialogFragment.newInstance(
                closeInfo, "取消", "确定", new PopDialogFragment.PopDialogListener() {
                    @Override
                    public void leftClick() {
                        popDialogFragment.dismiss();
                        view.setOpened(!opened);
                    }

                    @Override
                    public void rightClick() {
                        popDialogFragment.dismiss();
                        switch (type) {
                            case "lz":
                                SwitchLuzhuang(view, opened);
                                break;
                            case "heyue":
                                SwitchHeyue(view, opened);
                                break;
                            case "boduan":
                                SwitchBoduan(view, opened);
                                break;
                        }
                    }

                    @Override
                    public void cancleClick() {
                        popDialogFragment.dismiss();
                        view.setOpened(!opened);
                    }
                });
        popDialogFragment.setStyle(popDialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
        popDialogFragment.show(getSupportFragmentManager(), "showCloseDialog");
    }

    public void showPopOpenDialog(String lzString, String type, SwitchView view, boolean opened) {

        popDialogFragment = PopDialogFragment.newInstance(
                lzString, "取消", "确定", new PopDialogFragment.PopDialogListener() {
                    @Override
                    public void leftClick() {
                        popDialogFragment.dismiss();
                        view.setOpened(!opened);
                    }

                    @Override
                    public void rightClick() {
                        popDialogFragment.dismiss();
                        switch (type) {
                            case "lz":
                                SwitchLuzhuang(view, opened);
                                break;
                            case "heyue":
                                SwitchHeyue(view, opened);
                                break;
                            case "boduan":
                                SwitchBoduan(view, opened);
                                break;
                        }
                    }

                    @Override
                    public void cancleClick() {
                        popDialogFragment.dismiss();
                        view.setOpened(!opened);
                    }
                });
        popDialogFragment.setStyle(popDialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
        popDialogFragment.show(getSupportFragmentManager(), "showPopOpenDialog");
    }
}
