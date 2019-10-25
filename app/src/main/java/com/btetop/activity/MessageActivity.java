package com.btetop.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.MessageAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.MessageItemBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import rx.functions.Action1;

public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private List<MessageItemBean> list;
    private MessageAdapter myAdapter;
    private RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    private List<MessageItemBean.DataBean> mMessageList;
    private ImageView img_fan_hui;
    private TextView tran_title_detail;

    private int index = 0;
    private int page = 10;


    @Override
    public int intiLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        img_fan_hui = findViewById(R.id.img_fan_hui);
        tran_title_detail = findViewById(R.id.tran_title_detail);
        tran_title_detail.setText("消息中心");
        img_fan_hui.setOnClickListener(this);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        initMessageData(index, page, "init");
        setPullRefresher();
    }

    private void initMessageData(int index, int page, String init) {
        BteTopService.getMessageList(index, page)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<MessageItemBean>>() {
                    @Override
                    public void call(BaseBean<MessageItemBean> listBaseBean) {
                        if (listBaseBean != null &&
                                listBaseBean.getData() != null) {
                            if ("0000".equals(listBaseBean.getCode())) {
                                mMessageList = listBaseBean.getData().getData();
                                if (index == 0 && "init".equals(init)) {
                                    initAdapterData(mMessageList);
                                } else if (index == 0 && "refresh".equals(init)) {
                                    myAdapter.refresh(mMessageList);
                                } else if (index > 0 && "add".equals(init)) {
                                    myAdapter.add(mMessageList);
                                }
                            }
                        }
                        if ("-1".equals(listBaseBean.getCode())) {
                            startActivity(new Intent(MessageActivity.this, YZMLoginActivity.class));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void initAdapterData(List<MessageItemBean.DataBean> listBaseBean) {
        myAdapter = new MessageAdapter(this,listBaseBean);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//纵向线性布局
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
    }

    private void setPullRefresher() {
        //设置 Header 为 MaterialHeader
        refreshLayout.setRefreshHeader(new MaterialHeader(this));
        //设置 Footer 为 经典样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initMessageData(0, page, "refresh");
                refreshlayout.finishRefresh(1000/*,false*/);
                //不传时间则立即停止刷新    传入false表示刷新失败
                index = 0;
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                index = ++index;
                initMessageData(index, page, "add");
                //在这里执行下拉加载时的具体操作(网络请求、更新UI等)
                refreshlayout.finishLoadmore(1000/*,false*/);//不传时间则立即停止刷新    传入false表示加载失败
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fan_hui:
                finish();
                break;
        }
    }
}
