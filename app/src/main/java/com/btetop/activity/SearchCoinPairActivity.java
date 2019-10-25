package com.btetop.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.SearchCoinPairAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.bean.AirBoardBean;
import com.btetop.bean.ArtBoardIdBean;
import com.btetop.bean.BaseBean;
import com.btetop.bean.SearchCoinBean;
import com.btetop.dialog.PositionTheCostMultiDialog;
import com.btetop.dialog.PositionTheCostOnlyDialog;
import com.btetop.net.BteTopService;
import com.btetop.service.ArtBoardService;
import com.btetop.utils.KeyBoardUtils;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.btetop.widget.SearchEdittext;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class SearchCoinPairActivity extends BaseActivity implements ArtBoardService.DataListener {


    @BindView(R.id.img_fan_hui)
    ImageView imgFanHui;
    @BindView(R.id.tran_title_detail)
    TextView tranTitleDetail;
    @BindView(R.id.tran_share_detail)
    ImageView tranShareDetail;
    @BindView(R.id.ed_search)
    SearchEdittext edSearch;
    @BindView(R.id.search_layout_container)
    LinearLayout searchLayoutContainer;
    @BindView(R.id.rv_coin_pair_search_list)
    RecyclerView rvCoinPairSearchList;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_no_data)
    ImageView ivNoData;
    private SearchCoinPairAdapter homeListAdapter;
    //查詢得到的数据
    private List<SearchCoinBean> mSearchData = new ArrayList<>();
    private List<AirBoardBean> mXuanfuData;
    private List<AirBoardBean> mMergeData = new ArrayList<>();


    @Override
    public int intiLayout() {
        return R.layout.activity_search_coin_pair;
    }

    @Override
    public void initView() {
        tranTitleDetail.setText("添加币对");
        edSearch.setSearchListener(new SearchEdittext.onSearchListener() {
            @Override
            public void onSearch(String content) {
//                ToastUtils.showShortToast(content);
                getSearchData(content);
            }
        });
    }

    @Override
    public void initData() {
        ArtBoardService.getInstance().addListener(this);
        ArtBoardService.getInstance().getData();
        showSoftKeyboard();
    }

    private void mergeData() {
        mMergeData.clear();
        if (mSearchData == null || mSearchData.size() == 0) {
            return;
        }
        for (SearchCoinBean zixuan : mSearchData) {

            AirBoardBean boardBean = new AirBoardBean(zixuan.getExchange(), zixuan.getBase(), zixuan.getQuote());
            for (AirBoardBean xuanfu : mXuanfuData) {
                if (zixuan.getExchange().equals(xuanfu.getExchange()) &&
                        zixuan.getBase().equals(xuanfu.getBaseAsset()) &&
                        zixuan.getQuote().equals(xuanfu.getQuoteAsset())) {
                    boardBean.setCheckFlag(true);
                    boardBean.setArtBoardId(xuanfu.getArtBoardId());
                }
            }
            mMergeData.add(boardBean);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCoinPairSearchList.setLayoutManager(layoutManager);
        homeListAdapter = new SearchCoinPairAdapter(mMergeData, new SearchCoinPairAdapter.CoinPairItemClick() {
            @Override
            public void ItemClick(int position) {
//                ToastUtils.showShortToast(position + "ItemClick");
                AirBoardBean airBoardBean = mMergeData.get(position);
                if (selectAddOrDel(airBoardBean)) {
                    showDialog(airBoardBean);
                } else {
                    delItem(airBoardBean);
                }
            }

            @Override
            public void ItemRadioBtnClick(int position) {
//                ToastUtils.showShortToast(position + "ItemRadioBtnClick");

            }
        });
        rvCoinPairSearchList.setAdapter(homeListAdapter);
    }


    /**
     * @param airBoardBean
     * @return true
     */
    private boolean selectAddOrDel(AirBoardBean airBoardBean) {
        return !airBoardBean.isCheckFlag();
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
                .getInstance(airBoardBean.getQuoteAsset())
                .showDialog(this, new PositionTheCostOnlyDialog.OnClickListener() {
            @Override
            public void onAddClick(String costPrice) {
                airBoardBean.setCostPrice(Double.parseDouble(costPrice));
                addItem(airBoardBean);
            }

            @Override
            public void onSkipClick() {
                addItem(airBoardBean);
            }
        });

    }

    public void addItem(AirBoardBean item) {
        BteTopService.setArtBoard(item)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<ArtBoardIdBean>>() {
                    @Override
                    public void call(BaseBean<ArtBoardIdBean> artBoardIdBeanBaseBean) {

                        if ("0000".equals(artBoardIdBeanBaseBean.getCode()) && artBoardIdBeanBaseBean.getData() != null) {
                            item.setCheckFlag(true);
                            item.setArtBoardId(artBoardIdBeanBaseBean.getData().getArtBoardId());
                            homeListAdapter.notifyDataSetChanged();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();

                    }
                });

    }

    public void delItem(AirBoardBean item) {
        BteTopService.delArtBoard(item.getArtBoardId())
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if ("0000".equals(baseBean.getCode())) {
                            ToastUtils.showShortToast(baseBean.getMessage());
                            item.setCheckFlag(false);
                            homeListAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    private void showHeyueDialog(AirBoardBean airBoardBean) {

        PositionTheCostMultiDialog.getInstance().showDialog(this, new PositionTheCostMultiDialog.onBtnClickListener() {
            @Override
            public void onAddClick(String editInfo, int group1type, int ratio) {
                airBoardBean.setCostPrice(Double.parseDouble(editInfo));
                airBoardBean.setDirection(group1type);
                airBoardBean.setRatio(ratio);
                addItem(airBoardBean);

            }

            @Override
            public void onSkipClick() {
                addItem(airBoardBean);
            }
        });

    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }

    @OnClick({R.id.tv_search, R.id.img_fan_hui})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                getSearchData(edSearch.getText().toString());
                tvSearch.setClickable(false);
                break;
            case R.id.img_fan_hui:
                finish();
                break;
        }
    }

    public void getSearchData(String content) {
        BteTopService.getSearchCoinPair(content)
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<List<SearchCoinBean>>>() {
                    @Override
                    public void call(BaseBean<List<SearchCoinBean>> listBaseBean) {
                        if (listBaseBean != null && listBaseBean.getData().size() > 0) {
                            for (int i = 0; i < listBaseBean.getData().size(); i++) {
                                mSearchData = listBaseBean.getData();
                            }
                            mergeData();
                            ivNoData.setVisibility(View.GONE);
                            rvCoinPairSearchList.setVisibility(View.VISIBLE);
                        } else {
                            ivNoData.setVisibility(View.VISIBLE);
                            rvCoinPairSearchList.setVisibility(View.GONE);
                        }
                        tvSearch.setClickable(true);
                        KeyBoardUtils.hiddenKeyBoard(edSearch,SearchCoinPairActivity.this);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        tvSearch.setClickable(true);
                    }
                });


    }

    @Override
    public void onData(List<AirBoardBean> data) {
        mXuanfuData = data;
        mergeData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArtBoardService.getInstance().unSetListener(this);
    }
}
