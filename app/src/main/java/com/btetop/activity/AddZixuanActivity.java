package com.btetop.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.ZixuanChenckMenuAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.MergeZixuanMenuData;
import com.btetop.bean.OptionalBean;
import com.btetop.bean.ZiXuanResultBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class AddZixuanActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_search_coin_pair)
    EditText query;
    @BindView(R.id.recommendLayout)
    LinearLayout recommendLayout;
    @BindView(R.id.listview)
    ListView listView;
    private ZixuanChenckMenuAdapter adapter;
    private List<ZiXuanResultBean> mergeResult;

    private List<ZiXuanResultBean> requestRemoteData=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    public int intiLayout() {
        return R.layout.activity_add_zixuan;
    }

    @Override
    public void initView() {
        searchData();

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }

    private void searchData() {
        RxTextView.textChanges(query)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        boolean showClearBtn = query.getText().length() > 0;
                        if (showClearBtn) {
                        }else {
                            noData();
                        }
                        return showClearBtn;
                    }
                })
                .debounce(150, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        mergerData(charSequence.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
        ;

    }

    private void mergerData(String query) {
        Observable
                .zip(BteTopService.getZixuanResult(query), BteTopService.getOptionList()
                , new Func2<BaseBean<List<ZiXuanResultBean>>, BaseBean<OptionalBean>, MergeZixuanMenuData>() {
                    @Override
                    public MergeZixuanMenuData call(BaseBean<List<ZiXuanResultBean>> listBaseBean, BaseBean<OptionalBean> optionalBeanBaseBean) {
                        return new MergeZixuanMenuData(listBaseBean.getData(), optionalBeanBaseBean.getData().getResult());
                    }
                })
                .compose(RxUtil.mainAsync())
                .filter(new Func1<MergeZixuanMenuData, Boolean>() {
                    @Override
                    public Boolean call(MergeZixuanMenuData mergeZixuanMenuData) {
                        if (mergeZixuanMenuData.getMergeResult()==null || mergeZixuanMenuData.getMergeResult().size()==0) {
                            noData();
                            return false;
                        }
                        haveData();

                        return true;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MergeZixuanMenuData>() {
                    @Override
                    public void call(MergeZixuanMenuData mergeZixuanMenuData) {
                        mergeResult = mergeZixuanMenuData.getMergeResult();
                        adapter=new ZixuanChenckMenuAdapter(mergeResult,mContext);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(AddZixuanActivity.this);


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    private void noData(){
        listView.setVisibility(View.GONE);
        recommendLayout.setVisibility(View.VISIBLE);
    }

    private void haveData(){
        listView.setVisibility(View.VISIBLE);
        recommendLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ZiXuanResultBean bean = mergeResult.get(position);
        bean.setStatus(!bean.isStatus());
        adapter.notifyDataSetChanged();

        boolean contains = requestRemoteData.contains(bean);
        if (contains) {
            requestRemoteData.remove(bean);
        }
        requestRemoteData.add(bean);
    }

    @OnClick({R.id.queryBtc,R.id.queryEth,R.id.queryBch,R.id.queryEos})
    public void setOnClick(TextView view){
        query.setText(view.getText().toString());
    }

    @OnClick(R.id.complete)
    public void setOnClickComplete(){
        dealRequestRemoteData();
        //request

    }

    @OnClick(R.id.img_fan_hui)
    public void setOnClickBack(){
        this.finish();
    }

    private void dealRequestRemoteData() {
        BteTopService.setOptional(requestRemoteData)
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        AddZixuanActivity.this.finish();

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }
}
