package com.btetop.widget.viewpagercards;

import android.os.Build;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.btetop.R;
import com.btetop.base.BaseFragment;
import com.example.zylaoshi.library.utils.DeviceUtil;
import com.example.zylaoshi.library.utils.LogUtil;


public class CardFragment extends BaseFragment {

    private static final String TAG = "CardFragment";
    private String wv_url;
    private CardView mCardView;
    private WebView wb_card_pager;
    private ImageView noData;
    private String userAgent;
    private RelativeLayout rl_card_pager;
    private int rl_card_pager_height;


    public static CardFragment newInstance(String url) {
        CardFragment fragment = new CardFragment();
        fragment.wv_url = url;
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_card_pager;
    }

    @Override
    protected void initView(View view) {
        mCardView = view.findViewById(R.id.cardView);
        rl_card_pager = view.findViewById(R.id.rl_card_pager);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        wb_card_pager = view.findViewById(R.id.wb_card_pager);
        noData = view.findViewById(R.id.lu_dog_webview_img);
        wb_card_pager.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wb_card_pager.getSettings().setUseWideViewPort(true);
        wb_card_pager.getSettings().setLoadWithOverviewMode(true);
        wb_card_pager.getSettings().setAllowFileAccess(true);
        wb_card_pager.getSettings().setSupportZoom(true);
        wb_card_pager.getSettings().setAppCacheEnabled(true);
        wb_card_pager.getSettings().setDomStorageEnabled(true);
        wb_card_pager.getSettings().setDatabaseEnabled(true);
        wb_card_pager.getSettings().supportMultipleWindows();
        wb_card_pager.getSettings().setJavaScriptEnabled(true);
        wb_card_pager.getSettings().setBlockNetworkImage(false);
        wb_card_pager.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb_card_pager.getSettings().setBuiltInZoomControls(false);
        wb_card_pager.getSettings().setDefaultTextEncodingName("UTF-8");
        wb_card_pager.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wb_card_pager.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        userAgent = wb_card_pager.getSettings().getUserAgentString();
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript

        LogUtil.print("userAgent------->" + userAgent + "bteAPP/" + DeviceUtil.getVersion(getActivity()));
        wb_card_pager.getSettings().setUserAgentString(userAgent + "bteAPP/" + DeviceUtil.getVersion(getActivity()));
        wb_card_pager.setWebChromeClient(new DiscoveryWebChromeClient());
        if (!TextUtils.isEmpty(wv_url)) {
            wb_card_pager.loadUrl(wv_url);
        }



//        mScrollView.setLayoutParams(linearParams);
        int screenWidth = ScreenUtils.getScreenWidth();
        int height = (int) (screenWidth * 0.4);

        wb_card_pager.getLayoutParams().width = screenWidth;
        wb_card_pager.getLayoutParams().height = height;
    }


    class DiscoveryWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    @Override
    protected void initData() throws NullPointerException {

    }

    @Override
    public boolean statisticsFragment() {
        return false;
    }

    public CardView getCardView() {
        return mCardView;
    }
}
