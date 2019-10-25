package com.btetop.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.ProductListAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.bean.BaseBean;
import com.btetop.bean.ProductListBean;
import com.btetop.bean.ProductListBean.ProductListData.ProductListDetails;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.ShareDialog;
import com.btetop.message.RouteMessage;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.example.zylaoshi.library.utils.LogUtil;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import rx.functions.Action1;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ProductFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView mProductList;
    private int starNum = 0;
    private ProductListAdapter mListAdapter;
    private ArrayList<ProductListDetails> listDetails = new ArrayList<>();
    private RefreshLayout refreshLayout;
    private ProductListDetails detailsBean;
    private ImageView noData;


    private ImageView imgBack,imgShare;
    private TextView tvTitle;

    private static ProductFragment _instance = null;

    public static ProductFragment newInstance() {
        if(_instance == null)
            _instance = new ProductFragment();
        return  _instance;
    }


    @Override
    protected int attachLayoutId() {
        return R.layout.product_fragment_layout;
    }

    @Override
    protected void initView(View view) {
        mProductList = view.findViewById(R.id.product_listView);
        mProductList.setOnItemClickListener(this);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setNoMoreData(false);
                        starNum = 0;
                        initProductList(starNum, true);
                        refreshlayout.finishRefresh();
                    }
                }, 2000);

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        starNum++;
                        initProductList(starNum, false);
                        refreshlayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
        noData = view.findViewById(R.id.list_no_data);


        imgBack = view.findViewById(R.id.icon_back);


        imgShare = view.findViewById(R.id.icon_share);


        tvTitle = view.findViewById(R.id.tv_title);

        imgBack.setVisibility(View.GONE);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteMessage event = new RouteMessage(RouteMessage.MESSAGE_SHOW_HOME_PAGE);
                EventBusUtils.sendEvent(event);
            }
        });
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog.getInstance().showShareDialog(getActivity());
            }
        });
    }

    @Override
    protected void initData() throws NullPointerException {
        tvTitle.setText("策略服务");
        initProductList(starNum, true);
    }

    @Override
    public boolean statisticsFragment() {
        return true;
    }

    /**
     * 加载策略列表
     *
     * @param starNum
     */
    private void initProductList(int starNum, final boolean refresh) {

        BteTopService.getProductList(starNum).compose(RxUtil.<BaseBean<ProductListBean.ProductListData>>mainAsync())
                .subscribe(new Action1<BaseBean<ProductListBean.ProductListData>>() {
                    @Override
                    public void call(BaseBean<ProductListBean.ProductListData> listBean) {

                        mProductList.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                        if (listBean != null) {
                            String code = listBean.getCode();
                            String message = listBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    int nextPage = listBean.getData().getNextPage();
                                    ArrayList<ProductListDetails> list = listBean.getData().getDetails();
                                    if (refresh) {
                                        listDetails.clear();
                                    }
                                    listDetails.addAll(list);
                                    if (mListAdapter == null) {
                                        mListAdapter = new ProductListAdapter(getActivity(), listDetails);
                                        mProductList.setAdapter(mListAdapter);
                                    } else {
                                        mListAdapter.notifyDataSetChanged();
                                    }
                                    if (nextPage == -1 && list.size() == 0) {
                                        refreshLayout.setNoMoreData(true);
                                    }
                                } else {
                                    if (message != null) {
                                        ToastUtils.showShortToast(message);
                                    }
                                    mProductList.setVisibility(View.GONE);
                                    noData.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            mProductList.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        Log.e("ProductListBean", throwable.toString());
                        LogUtil.print("网络未连接");
                        mProductList.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        detailsBean = listDetails.get(position);
        String name = detailsBean.getName();
        double ror = detailsBean.getRor();
        String productId = String.valueOf(detailsBean.getId());
        String url = UrlConfig.CE_LUE_DETAILS_URL+productId;
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        RouteMessage event = new RouteMessage(RouteMessage.MESSAGE_SHOW_URL,bundle);
        EventBusUtils.sendEvent(event);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
