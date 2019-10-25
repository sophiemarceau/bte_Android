package com.btetop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.btetop.R;
import com.btetop.activity.CommonWebViewActivity;
import com.btetop.activity.KLineActivity;
import com.btetop.activity.MainActivity;
import com.btetop.activity.MiningActivity;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.application.BteTopApplication;
import com.btetop.base.BaseFragment;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.bean.CoinInfo;
import com.btetop.config.Constant;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.ShareDialog;
import com.btetop.dialog.ShareMiningPicDialog;
import com.btetop.dialog.SharePicDialog;
import com.btetop.message.RouteMessage;
import com.btetop.monitor.M;
import com.btetop.service.UserService;
import com.btetop.utils.ToastUtils;
import com.example.zylaoshi.library.utils.DeviceUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by ouyou on 2018/6/19.
 */

public class CommonWebViewFragment extends BaseFragment {


    private String currentUserToken = "";
    private String oldMeasureHeight = "";

    private static boolean onResumeFresh=false;

    public WebView getWebView() {
        return webView;
    }

    private WebView webView;


    private String userAgent;

    private RelativeLayout rlHeader;

    private ImageView imgBack;
    private ImageView imgShare;
    private TextView tvTitle;
    private boolean onPageFinished;

    private String goBackTarget = null;


    public String getGoBackTarget() {
        return goBackTarget;
    }

    public void setGoBackTarget(String goBackTarget) {
        this.goBackTarget = goBackTarget;
    }


    private String url;

    private boolean isShowShare, isShowBack, isShowTitle, isShowHeader;


    private View.OnClickListener backBtnOnClickListener = null;

    private LooperHandler handler = new LooperHandler(this);


    public void loadUrl(String url) {


        this.url = url;

        if (tvTitle != null) tvTitle.setVisibility(View.INVISIBLE);

        if (webView != null) webView.loadUrl(url);


    }


    public static CommonWebViewFragment newInstance(String url, String tagName) {
        return newInstance(url, tagName, true, true, true, true);
    }

    public static CommonWebViewFragment newInstance(String url, String tagName, boolean showHeader) {
        if (showHeader == false) {
            return newInstance(url, tagName, false, false, false, false);
        }
        return newInstance(url, tagName, true, true, true, true);
    }

    public static CommonWebViewFragment newInstance(String url, String tagName, boolean showHeader, boolean showTitle, boolean showShare, boolean showBack) {
        CommonWebViewFragment fragment = new CommonWebViewFragment();
        fragment.setFragmentNameTag(tagName);
        fragment.url = url;
        fragment.isShowTitle = showTitle;
        fragment.isShowShare = showShare;
        fragment.isShowBack = showBack;
        fragment.isShowHeader = showHeader;

        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_webview;
    }


    private View.OnTouchListener touchListener = null;

    public View.OnTouchListener getTouchListener() {
        return touchListener;
    }

    public void setTouchListener(View.OnTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    @Override
    protected void initView(View view) {


        rlHeader = view.findViewById(R.id.header_layout);
        if (isShowHeader == false) {
            rlHeader.setVisibility(View.GONE);
        }


        webView = view.findViewById(R.id.wv_webView);

        if (touchListener != null) webView.setOnTouchListener(touchListener);
        webView.setBackgroundColor(Color.TRANSPARENT);

        imgBack = view.findViewById(R.id.icon_back);
        if (isShowBack == false) imgBack.setVisibility(View.INVISIBLE);


        imgShare = view.findViewById(R.id.icon_share);
        if (isShowShare == false) imgShare.setVisibility(View.INVISIBLE);


        tvTitle = view.findViewById(R.id.tv_title);
        if (isShowTitle == false) tvTitle.setVisibility(View.INVISIBLE);


        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.requestFocus();
        webView.requestFocusFromTouch();
        webView.getSettings().setNeedInitialFocus(false);
        //webView.getSettings().setSupportZoom(true);

        //不需要缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(false);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

        // webView.getSettings().supportMultipleWindows();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);

        //滚动条不显示
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);

        webView.getSettings().setBuiltInZoomControls(false);

        //增加一个JS函数接口
        webView.addJavascriptInterface(new WebViewInterface(), "bteApp");
        //设置回调接口
        webView.setWebViewClient(webViewClient);

        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        userAgent = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(userAgent + "bteAPP/" + DeviceUtil.getVersion(getActivity()));


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nativeShare();
                M.monitor().onEvent(getContext(), Constant.POINT_RIGHT_SHARE);
            }
        });


    }

    @Override
    protected void initData() throws NullPointerException {
        currentUserToken = UserService.getCurrentUserToken();
        loadUrl(url);
    }

    @Override
    public boolean statisticsFragment() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();

    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            onPageFinished = true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    };

    public void stopLoadingWebView() {
        if (!onPageFinished) {
            webView.stopLoading();
        }
    }


    public void setBackButtonListener(View.OnClickListener listener) {
        backBtnOnClickListener = listener;
    }

    public void goBack() {
        //如果有设置goBackTarget，则点击返回跳转至goBackTarget
        if (!TextUtils.isEmpty(goBackTarget)) {
            if (getActivity() instanceof MainActivity) {
                RouteMessage event = new RouteMessage(RouteMessage.MESSAGE_SHOW_HOME_PAGE);
                EventBusUtils.sendEvent(event);
                return;
            }
        }


        if (webView.canGoBack()) {
            webView.goBack();// goBack()表示返回WebView的上一页面
            return;
        }
        if (backBtnOnClickListener != null) {
            backBtnOnClickListener.onClick(imgBack);
            return;
        }

        if (getActivity() instanceof MainActivity) {
            RouteMessage event = new RouteMessage(RouteMessage.MESSAGE_SHOW_HOME_PAGE);
            EventBusUtils.sendEvent(event);
        } else {
            getActivity().finish();
        }
        return;
    }


    private Handler textHandler = new Handler();


    final class WebViewInterface {
        @JavascriptInterface
        public void getTitle(final String title) {
            textHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(title) && isShowTitle == true) {
                        tvTitle.setText(title);
                        tvTitle.setVisibility(View.VISIBLE);
                    } else
                        tvTitle.setVisibility(View.INVISIBLE);
                }
            });
        }

        public void getHeight(final String measureHeight) {

            if (oldMeasureHeight.equals(measureHeight)) {
                return;
            }
            oldMeasureHeight = measureHeight;
            webView.post(new Runnable() {
                @Override
                public void run() {

                    int i = SizeUtils.dp2px(Integer.parseInt(measureHeight));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, i);
                    webView.setLayoutParams(layoutParams);
                }
            });
        }


        //title content  url
        @JavascriptInterface
        public void getShareInfo(String title, String content, String url) {

            //分享标题
            String shareTitle = "";
            //分享内容
            String shareContent = "";
            //分享URL
            String shareURL = "";

            try {
                if (!"undfined".equalsIgnoreCase(title))
                    shareTitle = title;

                if (!"undfined".equalsIgnoreCase(content))
                    shareContent = content;

                if (!"undfined".equalsIgnoreCase(url))
                    shareURL = url;
            } catch (Exception e) {
                e.printStackTrace();
            }
            ShareDialog.getInstance().showShareDialog(getActivity(), shareURL, shareTitle, shareContent);
        }

        //title content  url
        @JavascriptInterface
        public void getShareInfoNoInvite(String title, String content, String url) {

            //分享标题
            String shareTitle = "";
            //分享内容
            String shareContent = "";
            //分享URL
            String shareURL = "";

            try {
                if (!"undfined".equalsIgnoreCase(title))
                    shareTitle = title;

                if (!"undfined".equalsIgnoreCase(content))
                    shareContent = content;

                if (!"undfined".equalsIgnoreCase(url))
                    shareURL = url;
            } catch (Exception e) {
                e.printStackTrace();
            }
            ShareDialog.getInstance().showShareDialog(getActivity(), shareURL, shareTitle, shareContent);
        }


        @JavascriptInterface
        public String getUserToken() {
            return UserService.getCurrentUserToken();
        }


        @JavascriptInterface
        public String invoke(String action, String param) {

            switch (action) {
                case "login":
                    invokeLogin();
                    break;
                case "jumpToKline":
                    jumpToKline(param);
                    break;
                case "share":
                    jsShare();
                    M.monitor().onEvent(getContext(), Constant.POINT_SHARE);
                    break;
                case "invite":
                    invite();
                    M.monitor().onEvent(getContext(), Constant.POINT_INVITATION);
                    break;
                case "logout":
                    logout();
                    break;
                case "goback":
                    goBack();
                    break;
                case "ActualHeight":
                    getHeight(param);
                    break;
                case "inviteBoostShare":
                    nativeShare();
                    break;
                case "chainSearchClick":
                    openNewActivty(param);
                    break;
                case "addAseets":
                    openNewActivty(param);
                    break;
                case "backAssets":
                    finishAndRefresh();
                    break;
                case "openMining":
                    openMiningActivty();
                    break;
                case "shareMining":
                    miningShare();
                    break;
                case "goBack":
                    //用户调研 填写完成问卷后，直接退出。
                    loadUrl(UrlConfig.MINING_INDEX_URL + "mission");
                    break;
                case "bindWechat":
                    wxLogin();
                    break;

            }

            return "0";
        }

    }
    public void wxLogin() {
        if (!BteTopApplication.mWxApi.isWXAppInstalled()) {
            ToastUtils.showShortToast("您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        BteTopApplication.mWxApi.sendReq(req);
    }
    private void openNewActivty(String param) {
        Intent intent = new Intent(getActivity(), CommonWebViewActivity.class);
        intent.putExtra("url", param);
        startActivity(intent);
    }
    private void openMiningActivty() {
        M.monitor().onEvent(getContext(), Constant.MINING_START);
        Intent intent = new Intent(getActivity(), MiningActivity.class);
        startActivity(intent);
    }

    private void finishAndRefresh(){
        onResumeFresh=true;
        getActivity().finish();

    }

    //退出
    private void logout() {
    }

    //邀请好友

    private void invite() {
        SharePicDialog sharePicDialog = SharePicDialog.newInstance(getContext());
        sharePicDialog.setTargetFragment(CommonWebViewFragment.this, 0);
        sharePicDialog.show(getFragmentManager(), "sharePicDialog");
    }

    //原生调用，文案通过js得到
    private void nativeShare() {
        getShareInfo();
    }

    //js直接调用，分享文案是默认
    private void jsShare() {
        ShareDialog.getInstance().showShareDialog(getActivity());
    }

    private void miningShare(){
        ShareMiningPicDialog shareMiningPicDialog = ShareMiningPicDialog.newInstance(getActivity(), new ShareMiningPicDialog.doShare() {
            @Override
            public void doShare() {

            }
        });
        shareMiningPicDialog.show(getFragmentManager(),"CommonWebViewFragment");
    }

    private void invokeLogin() {
        Intent intent = new Intent(getActivity(), YZMLoginActivity.class);
        startActivity(intent);
    }

    private void jumpToKline(String param) {

        CoinInfo coinInfo = new CoinInfo();
        try {
            JSONObject jsonObject = new JSONObject(param);

            String symbol = jsonObject.getString("base");
            String exchange = jsonObject.getString("exchange");
            String quote = jsonObject.getString("quote");

            coinInfo.setSymbol(symbol);
            coinInfo.setExchange(exchange);
            coinInfo.setQuote(quote);

            Intent intent = new Intent(getActivity(), KLineActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("coinInfo", coinInfo);
            intent.putExtras(bundle);
            startActivity(intent);

        } catch (Exception e) {
        }

    }


    private void getShareInfo() {
        if (webView != null) {
            //title content  url
            String share = "javascript: try{window.bteApp.getShareInfo(document.getElementsByTagName('share-title')[0].innerHTML," +
                    "document.getElementsByTagName('share-content')[0].innerHTML," +
                    "document.getElementsByTagName('share-url')[0].innerHTML);} catch(error){window.bteApp.getShareInfo('','','')}";

            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl(share);
                }
            });
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //用户信息发生变化时需要重新刷新页面
        if (!(StringUtils.equalsIgnoreCase(UserService.getCurrentUserToken(), currentUserToken))) {
            //更新用户状态
            currentUserToken = UserService.getCurrentUserToken();
            if (webView != null) {
                webView.loadUrl(url);
            }
        }
        startLooper();
    }

    @Override
    public void onPause() {
        super.onPause();
        endLooper();

    }


    private void startLooper() {
        endLooper();
        handler.sendEmptyMessageDelayed(0, UrlConfig.REFRESH_TIMES);
    }

    private void endLooper() {
        handler.removeMessages(0);
    }

    @Override
    protected void onHidden() {
        super.onHidden();
        endLooper();
    }

    @Override
    protected void onShow() {
        super.onShow();
        //用户信息发生变化时需要重新刷新页面
        if (!(StringUtils.equalsIgnoreCase(UserService.getCurrentUserToken(), currentUserToken))) {
            //更新用户状态
            currentUserToken = UserService.getCurrentUserToken();
            if (webView != null) {
                webView.loadUrl(url);
            }
        }
        startLooper();
    }

    private void getWebViewTitle() {
        if (webView != null) {
            webView.loadUrl("javascript: try{window.bteApp.getTitle(document.getElementsByTagName('title')[0].innerHTML);} catch(error){}");
        }
    }


    public static class LooperHandler extends Handler {
        private WeakReference<CommonWebViewFragment> fragmentWeakReference;

        public LooperHandler(CommonWebViewFragment fragment) {
            this.fragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CommonWebViewFragment commonWebViewFragment = fragmentWeakReference.get();
            if (commonWebViewFragment != null) {
                commonWebViewFragment.getWebViewTitle();
                commonWebViewFragment.startLooper();
            }
        }

    }

    /**
     * 刷新url，具体应用 在币种详情里，选择完币对，需要刷新其他页面
     *
     * @param url
     */
    public void reload(String url) {
        CommonWebViewFragment.this.url = url;
        if (webView != null) {
            webView.reload();
        }

    }


    @Override
    public void onViewPagerHidden() {
        super.onViewPagerHidden();
    }

    @Override
    public void onViewPagerShow() {
        super.onViewPagerShow();
        if (onResumeFresh) {
            if (webView!=null) {
                webView.reload();
                onResumeFresh=false;
            }
        }
    }


}

