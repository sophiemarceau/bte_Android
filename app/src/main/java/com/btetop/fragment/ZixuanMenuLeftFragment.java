package com.btetop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.btetop.R;
import com.btetop.activity.AddZixuanActivity;
import com.btetop.activity.KLineActivity;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.adapter.ZixuanMenuAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinInfo;
import com.btetop.bean.OptionalBean;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.RxUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class ZixuanMenuLeftFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "ZixuanMenuLeftFragment";
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.page_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;

    private ZixuanMenuAdapter adapter;
    private List<OptionalBean.ResultBean> mZixuanData;

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_zixuan_left;
    }

    @Override
    protected void initView(View view) {

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    @Override
    protected void initData() {
       BteTopService.getOptionList()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<OptionalBean>>() {
                    @Override
                    public void call(BaseBean<OptionalBean> optionalBeanBaseBean) {
                        if ("0000".equals(optionalBeanBaseBean.getCode()) && optionalBeanBaseBean.getData() != null &&
                                optionalBeanBaseBean.getData().getResult() != null &&
                                optionalBeanBaseBean.getData().getResult() != null &&
                                optionalBeanBaseBean.getData().getResult().size() > 0) {
                            renderListView(optionalBeanBaseBean.getData().getResult());
                        } else {
                            renderEmptyView();
                        }
                        refreshLayout.finishRefresh();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }


    private void renderListView(List<OptionalBean.ResultBean> list) {
        listView.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
        mZixuanData=list;
        OptionalBean.ResultBean resultBean = new OptionalBean.ResultBean();
        resultBean.setLast(true);
        mZixuanData.add(mZixuanData.size(), resultBean);
        adapter=new ZixuanMenuAdapter(mZixuanData,getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void renderEmptyView() {
        listView.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
    }


    @Override
    protected boolean statisticsFragment() {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mZixuanData==null || mZixuanData.size()==0) {
            return;
        }

        if (position==mZixuanData.size()-1) {
            Intent intent=new Intent(getActivity(),AddZixuanActivity.class);
            getActivity().startActivity(intent);
        }else {
            OptionalBean.ResultBean resultBean = mZixuanData.get(position);
            CoinInfo coinInfo = new CoinInfo();
                coinInfo.setSymbol(resultBean.getBase());
            coinInfo.setExchange(resultBean.getExchange());
            coinInfo.setQuote(resultBean.getQuote());

            Intent intent = new Intent(getActivity(), KLineActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("coinInfo", coinInfo);
            intent.putExtras(bundle);
            startActivity(intent);
        }


    }

    @OnClick({R.id.empty_layout})
    public void setOnClick(View v){
        switch (v.getId()) {
            case R.id.empty_layout:
                if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                    getActivity().startActivity(new Intent(getContext(), YZMLoginActivity.class));
                }else {
                    Intent intent=new Intent(getActivity(),AddZixuanActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onViewPagerHidden() {
        super.onViewPagerHidden();
        Log.e(TAG, "onViewPagerHidden: " );
    }

    @Override
    public void onViewPagerShow() {
        super.onViewPagerShow();
        Log.e(TAG, "onViewPagerShow: " );
        initData();
    }
}
