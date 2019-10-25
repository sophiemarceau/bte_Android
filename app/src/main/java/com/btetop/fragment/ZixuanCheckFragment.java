package com.btetop.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.ZixuanCheckAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.AirBoardBean;
import com.btetop.bean.ArtBoardIdBean;
import com.btetop.bean.BaseBean;
import com.btetop.bean.OptionalBean;
import com.btetop.dialog.PositionTheCostMultiDialog;
import com.btetop.dialog.PositionTheCostOnlyDialog;
import com.btetop.net.BteTopService;
import com.btetop.service.ArtBoardService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class ZixuanCheckFragment extends BaseFragment implements ArtBoardService.DataListener, AdapterView.OnItemClickListener {
    private static final String TAG = "ZixuanCheckFragment";
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.empty_layout)
    TextView emptyLayout;
    private List<OptionalBean.ResultBean> mZixuanData;
    private List<AirBoardBean> mXuanfuData;
    private List<AirBoardBean> mMergeData = new ArrayList<>();

    private ZixuanCheckAdapter Adapter;




    public static ZixuanCheckFragment newInstance() {
        ZixuanCheckFragment fragment = new ZixuanCheckFragment();
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_zixuan;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        getZixuanData();
        ArtBoardService.getInstance().addListener(this);
        ArtBoardService.getInstance().getData();
    }

    private void getZixuanData() {
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
                            mZixuanData = optionalBeanBaseBean.getData().getResult();
                            mergeData();
                        } else {
                            emptyLayout.setVisibility(View.VISIBLE);

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    protected boolean statisticsFragment() {
        return false;
    }

    @Override
    public void onData(List<AirBoardBean> data) {
        mXuanfuData = data;
        mergeData();

    }

    private void mergeData() {
        mMergeData.clear();
        if (mZixuanData == null || mZixuanData.size() == 0) {
            return;
        }
        for (OptionalBean.ResultBean zixuan : mZixuanData) {

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

        Adapter = new ZixuanCheckAdapter(mMergeData, getActivity());
        listView.setAdapter(Adapter);
        listView.setOnItemClickListener(this);
        ArtBoardService.getInstance().unSetListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AirBoardBean airBoardBean = mMergeData.get(position);
        if (selectAddOrDel(airBoardBean)) {
            showDialog(airBoardBean);
        }else {
            requestDel(airBoardBean);
        }
    }

    /**
     *
     * @param airBoardBean
     * @return true
     */
    private boolean selectAddOrDel(AirBoardBean airBoardBean){
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
                .showDialog(getActivity(), new PositionTheCostOnlyDialog.OnClickListener() {
            @Override
            public void onAddClick(String costPrice) {
                airBoardBean.setCostPrice(Double.parseDouble(costPrice));
                requestAdd(airBoardBean);
            }

            @Override
            public void onSkipClick() {
                requestAdd(airBoardBean);
            }
        });

    }

    private void showHeyueDialog(AirBoardBean airBoardBean) {


        PositionTheCostMultiDialog.getInstance().showDialog(getActivity(), new PositionTheCostMultiDialog.onBtnClickListener() {
            @Override
            public void onAddClick(String editInfo, int group1type, int ratio) {
                airBoardBean.setCostPrice(Double.parseDouble(editInfo));
                airBoardBean.setDirection(group1type);
                airBoardBean.setRatio(ratio);
                requestAdd(airBoardBean);

            }

            @Override
            public void onSkipClick() {
                requestAdd(airBoardBean);
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

                        if ("0000".equals(artBoardIdBeanBaseBean.getCode())&& artBoardIdBeanBaseBean.getData()!=null) {
                            ToastUtils.showShortToast(artBoardIdBeanBaseBean.getMessage());
                            item.setCheckFlag(true);
                            item.setArtBoardId(artBoardIdBeanBaseBean.getData().getArtBoardId());
                            Adapter.notifyDataSetChanged();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    public void requestDel(AirBoardBean item) {
        BteTopService.delArtBoard(item.getArtBoardId())
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if ("0000".equals(baseBean.getCode())) {
                            ToastUtils.showShortToast(baseBean.getMessage());
                            item.setCheckFlag(false);
                            Adapter.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ArtBoardService.getInstance().unSetListener(this);
    }
}
