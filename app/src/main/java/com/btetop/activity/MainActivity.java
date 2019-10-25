package com.btetop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.base.BaseFragment;
import com.btetop.base.EventBus.BindEventBus;
import com.btetop.bean.BaseBean;
import com.btetop.bean.HomeActiveBean;
import com.btetop.config.Constant;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.AdvertiseDialog;
import com.btetop.dialog.ShareDialog;
import com.btetop.fragment.CommonWebViewFragment;
import com.btetop.fragment.HomeFragment;
import com.btetop.fragment.MineFragment;
import com.btetop.fragment.SheQuFragment;
import com.btetop.fragment.ZixuanMenuFragment;
import com.btetop.message.RouteMessage;
import com.btetop.monitor.M;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.update.UpdateManager;
import com.btetop.utils.NotificationUtils;
import com.btetop.utils.RxUtil;
import com.example.zylaoshi.library.utils.LogUtil;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Stack;

import rx.functions.Action1;

@BindEventBus
public class MainActivity extends BaseActivity implements View.OnClickListener {

    //持续保持的一个Fragment
    private HashMap<String, BaseFragment> aliveFragmentMap = new HashMap<String, BaseFragment>();

    //该堆栈用于维护页面加入顺序，用于实现后退
    private Stack<BaseFragment> mFragmentStack = new Stack<>();

    private Fragment currentFragment = null;

    //底部Layout
    private RelativeLayout rlDiscover, rlHangqing, rlShequ, rlMine,rlZixuan;

    private ImageView imDiscover, imHangqing, imShendu, imMine,imZixuan;
    private TextView tvDiscover, tvHangqing, tvShendu, tvMime,tvZixuan;

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        //底部菜单
        rlDiscover = findViewById(R.id.menu_discover);
        rlHangqing = findViewById(R.id.menu_hang_qing);
        rlShequ = findViewById(R.id.menu_shequ);
        rlMine = findViewById(R.id.menu_mine);
        rlZixuan = findViewById(R.id.menu_zixuan);


        rlDiscover.setOnClickListener(this);
        rlHangqing.setOnClickListener(this);
        rlShequ.setOnClickListener(this);
        rlMine.setOnClickListener(this);
        rlZixuan.setOnClickListener(this);

        imDiscover = findViewById(R.id.menu_discover_image);
        imHangqing = findViewById(R.id.menu_hang_qing_image);
        imShendu = findViewById(R.id.menu_shequ_image);
        imMine = findViewById(R.id.menu_mine_image);
        imZixuan=findViewById(R.id.menu_zixuan_img);

        tvDiscover = findViewById(R.id.menu_discover_text);
        tvHangqing = findViewById(R.id.menu_hang_qing_text);
        tvShendu = findViewById(R.id.menu_shequ_text);
        tvMime = findViewById(R.id.menu_mine_text);
        tvZixuan=findViewById(R.id.menu_zixuan_text);

    }

    @Override
    public void initData() {
        UpdateManager.getInstance().checkUpdate(this);
        showHomeFragment();
        showHomeAdvertise();
        //检查通知是否打开
        NotificationUtils.showNotificationDialog(this);
        //用户资料更新完毕后更新按钮
        //xuanfukuang shezhi shuju

    }


    @Override
    public boolean statisticsActivity() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        //远程读取用户数据
        try {
            UserService.loadUserInfo();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取首页广告活动
     */
    private void showHomeAdvertise() {

        BteTopService.getHomeAdvertise()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<HomeActiveBean.HomeActiveData>>() {
                    @Override
                    public void call(BaseBean<HomeActiveBean.HomeActiveData> activeBean) {
                        if (null != activeBean && !"".equals(activeBean)) {
                            String code = activeBean.getCode();
                            if (null != code && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    String activeUrl = activeBean.getData().getImage();
                                    if (null != activeUrl && !"".equals(activeUrl)) {
                                        AdvertiseDialog.getInstance().showActiveDialog(MainActivity.this,
                                                activeBean);
                                    }
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.print("wrong in home");
                        throwable.printStackTrace();
                    }
                });

    }

    //重置所有文本的选中状态
    private void resetMenu() {
        rlDiscover.setSelected(false);
        imDiscover.setSelected(false);
        tvDiscover.setSelected(false);

        rlHangqing.setSelected(false);
        imHangqing.setSelected(false);
        tvHangqing.setSelected(false);

        rlShequ.setSelected(false);
        imShendu.setSelected(false);
        tvShendu.setSelected(false);

        rlMine.setSelected(false);
        imMine.setSelected(false);
        tvMime.setSelected(false);

        rlZixuan.setSelected(false);
        imZixuan.setSelected(false);
        tvZixuan.setSelected(false);

    }

    private void setDiscoverSelscted() {
        resetMenu();
        rlDiscover.setSelected(true);
        imDiscover.setSelected(true);
        tvDiscover.setSelected(true);
    }

    private void setHangqingSelscted() {
        resetMenu();
        rlHangqing.setSelected(true);
        imHangqing.setSelected(true);
        tvHangqing.setSelected(true);
    }

    private void setShequSelscted() {
        resetMenu();
        rlShequ.setSelected(true);
        imShendu.setSelected(true);
        tvShendu.setSelected(true);
    }

    private void setMineSelscted() {
        resetMenu();
        rlMine.setSelected(true);
        imMine.setSelected(true);
        tvMime.setSelected(true);
    }

    private void setZixuanSelected(){
        resetMenu();
        rlZixuan.setSelected(true);
        imZixuan.setSelected(true);
        tvZixuan.setSelected(true);
    }


    private void setCurrentFragment(BaseFragment currentFragment) {
        this.currentFragment = currentFragment;
        //switch menu

        if (StringUtils.equalsIgnoreCase(currentFragment.getFragmentNameTag(), "homeFragment")) {

            setDiscoverSelscted();
            return;
        }

        if (StringUtils.equalsIgnoreCase(currentFragment.getFragmentNameTag(), "marketFragment")) {
            setHangqingSelscted();
            return;
        }

        if (StringUtils.equalsIgnoreCase(currentFragment.getFragmentNameTag(), "shequFragment")) {
            setShequSelscted();
            return;
        }

        if (StringUtils.equalsIgnoreCase(currentFragment.getFragmentNameTag(), "mineFragment")) {
            setMineSelscted();
            return;
        }

        if (StringUtils.equalsIgnoreCase(currentFragment.getFragmentNameTag(), "zixuanFragment")) {
            setZixuanSelected();
            return;
        }

    }

    private Fragment getCurrentFragment() {
        return currentFragment;
    }

    private BaseFragment findAliveFragmentByTag(String tag) {
        return aliveFragmentMap.get(tag);
    }




    private void showPopBackFragment() {
        //将栈顶的fragment推出
        mFragmentStack.pop();
        //将下一个再推出后加入
        BaseFragment fragment = mFragmentStack.pop();
        if (fragment != null) addAndShowFragment(fragment);
    }

    private void showWebViewActivity(String url) {
        Intent intent = new Intent(this, CommonWebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);

    }

    private void addAndShowFragment(BaseFragment fragment) {

        if (fragment != null) {
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //首先将所有原有fragment隐藏
            for (BaseFragment bf : aliveFragmentMap.values()) {
                transaction.hide(bf);
            }
            if (!fragment.isAdded()) transaction.add(R.id.fragment_container, fragment);
            transaction.show(fragment);
            transaction.addToBackStack(fragment.getTag());
            transaction.commitAllowingStateLoss();
            aliveFragmentMap.put(fragment.getFragmentNameTag(), fragment);
            mFragmentStack.push(fragment);
            setCurrentFragment(fragment);
        }
    }


    private void showLuDogFragment() {
        Intent intent = new Intent(this, CommonWebViewActivity.class);
        intent.putExtra("url", UrlConfig.LU_DOG_RUL);
        startActivity(intent);
    }


    private void showBoDogFragment() {
        Intent intent = new Intent(this, CommonWebViewActivity.class);
        intent.putExtra("url", UrlConfig.BO_GOU_H5_URL);
        startActivity(intent);
    }

    private void showMarketDataFragment() {
        BaseFragment fragment = findAliveFragmentByTag("marketFragment");
        if (fragment == null) {
            CommonWebViewFragment webViewFragment = CommonWebViewFragment.newInstance(UrlConfig.HANG_QING_URL, "marketFragment",true,true,false,false);
            fragment = webViewFragment;

//            MarketFragment marketFragment = MarketFragment.newInstance();
//            marketFragment.setFragmentNameTag("marketFragment");
//            fragment = marketFragment;
        }
        addAndShowFragment(fragment);
    }

    private void showHomeFragment() {
        BaseFragment fragment = findAliveFragmentByTag("homeFragment");
        if (fragment == null) {
            HomeFragment homeFragment = HomeFragment.instance();
            homeFragment.setFragmentNameTag("homeFragment");
            fragment = homeFragment;
        }
        addAndShowFragment(fragment);

    }

    private void showResearchDogFragment() {
        Intent intent = new Intent(this, CommonWebViewActivity.class);
        intent.putExtra("url", UrlConfig.RESEARCH_DOG_URL);
        startActivity(intent);
    }


    private void showDingPanDogFragment() {
        Intent intent = new Intent(this, CommonWebViewActivity.class);
        intent.putExtra("url", UrlConfig.DINGPAN_HOME_URL);
        startActivity(intent);
    }


    private void showHeyueDogActivity() {
        Intent intent = new Intent(this, HeyueActivity.class);
        startActivity(intent);
    }

    private void showSheQuFragment() {
        BaseFragment fragment = findAliveFragmentByTag("shequFragment");
        if (fragment == null) {
            fragment= new SheQuFragment();
            fragment.setFragmentNameTag("shequFragment");
            //将这个webview的回退动作强制设置为返回首页
            //webViewFragment.setGoBackTarget(RouteMessage.MESSAGE_SHOW_HOME_PAGE);
        }
        addAndShowFragment(fragment);
    }

    private void showMineFragment() {
        BaseFragment fragment = findAliveFragmentByTag("mineFragment");
        if (fragment == null) {
            MineFragment mineFragment = new MineFragment();
            mineFragment.setFragmentNameTag("mineFragment");
            fragment = mineFragment;
        }
        addAndShowFragment(fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }


    //fix me later
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(RouteMessage messageEvent) {
        Log.e(TAG, "Event: "+messageEvent.getMessage() );
        String message = messageEvent.getMessage();
        Bundle bundle = messageEvent.getBundle();
        switch (message) {
            case RouteMessage.MESSAGE_POP_BACK_PAGE:
                showPopBackFragment();
                break;
            case RouteMessage.MESSAGE_SHOW_HOME_PAGE:
                showHomeFragment();
                break;
            case RouteMessage.MESSAGE_SHOW_MY_PAGE:
                showMineFragment();
                break;

            case RouteMessage.MESSAGE_SHOW_CE_LUE_PAGE:
                showSheQuFragment();
                break;
            case RouteMessage.MESSAGE_SHOW_MARKET_DATA:
                showMarketDataFragment();
                break;
            case RouteMessage.MESSAGE_SHOW_HEYUE_DOG_PAGE:
                showHeyueDogActivity();
                break;
            case RouteMessage.MESSAGE_SHOW_BO_DOG_PAGE:
                showBoDogFragment();
                break;
            case RouteMessage.MESSAGE_SHOW_LU_DOG_PAGE:
                showLuDogFragment();
                break;
            case RouteMessage.MESSAGE_SHOW_RESEARCH_DOG_PAGE:
                showResearchDogFragment();
                break;

            case RouteMessage.MESSAGE_SHOW_DINGPAN_DOG_PAGE:
                showDingPanDogFragment();
                break;
            case RouteMessage.MESSAGE_SHOW_URL:
                if (bundle != null) {
                    String url = bundle.getString("url");
                    showWebViewActivity(url);
                }
                break;
            case RouteMessage.MESSAGE_SHOW_GET_SCORE:
                showWebViewActivity(UrlConfig.HOME_GET_COIN_URL);
                break;
            case RouteMessage.MESSAGE_LOGIN_SUCCESS:
                break;
            case RouteMessage.MESSAGE_LOGIN_OUT_SUCCESS:
                break;

            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Fragment fragment = getCurrentFragment();
            if (fragment instanceof CommonWebViewFragment) {
                ((CommonWebViewFragment) fragment).goBack();
            } else if (!(fragment instanceof HomeFragment)) {
                showPopBackFragment();
            } else {
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }



    /*
     * 修改为检查通知是否打开
     * */


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_discover:
                showHomeFragment();
                break;
            case R.id.menu_shequ:
                showSheQuFragment();
                break;
            case R.id.menu_hang_qing:
                showMarketDataFragment();
                M.monitor().onEvent(mContext, Constant.HOME_HANG_QING);
                break;
            case R.id.menu_mine:
                showMineFragment();
                break;
            case R.id.share:
                ShareDialog.getInstance().showShareDialog(this);
                M.monitor().onEvent(mContext, Constant.HOME_RIGHT_SHARE);
                break;
            case R.id.menu_zixuan:
                showZiXuanFragment();
                break;
        }
    }

    private void showZiXuanFragment() {
        BaseFragment fragment = findAliveFragmentByTag("zixuanFragment");
        if (fragment == null) {
            ZixuanMenuFragment zixuanMenuFragment = new ZixuanMenuFragment();
            zixuanMenuFragment.setFragmentNameTag("zixuanFragment");
            fragment = zixuanMenuFragment;
        }
        addAndShowFragment(fragment);
    }
}
