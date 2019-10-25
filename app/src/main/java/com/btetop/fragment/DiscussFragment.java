package com.btetop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.btetop.R;
import com.btetop.activity.ReplyDetailActivity;
import com.btetop.adapter.MyCommentAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CommentBean;
import com.btetop.config.UrlConfig;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

import static com.btetop.activity.ReplyDetailActivity.POST_ID_TAG;

public class DiscussFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.page_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.listview)
    ListView listView;

    private int index=0;
    private MyCommentAdapter adapter;
    private List<CommentBean.MyCommentBean> mdata=new ArrayList<>(UrlConfig.PAGE_SIZE);

    @Override
    protected int attachLayoutId() {
        return R.layout.discuss_fragment;
    }

    @Override
    protected void initView(View view) {

        adapter=new MyCommentAdapter(mdata,getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                index=0;
                mdata.clear();
                requestData();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                requestData();
            }
        });
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData(){
        BteTopService.getMyComment(index)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<CommentBean>>() {
                    @Override
                    public void call(BaseBean<CommentBean> commentBeanBaseBean) {

                        if (index==0) {
                            mdata.clear();
                        }
                        if (commentBeanBaseBean.getData().getMyComment().size()<UrlConfig.PAGE_SIZE) {

                            refreshLayout.setEnableLoadMore(false);
                        }
//                        mdata.addAll(myReleaseBeanOuterBaseBean.getData().getMyRelease());
                        mdata.addAll(commentBeanBaseBean.getData().getMyComment());
                        index++;
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(getActivity(),ReplyDetailActivity.class);
        Bundle b=new Bundle();
        b.putInt(POST_ID_TAG,mdata.get(position).getPostReplyId());
        intent.putExtras(b);
        getActivity().startActivity(intent);
    }

    public void refreshFragment(){
        index=0;
        requestData();
    }
}
