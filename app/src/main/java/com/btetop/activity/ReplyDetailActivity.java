package com.btetop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.PostReplyDetailAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.ReplyListBean;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.PostCommentDialog;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.RxUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class ReplyDetailActivity extends BaseActivity implements PostCommentDialog.UserInputListener, AdapterView.OnItemClickListener {
    public static final String POST_ID_TAG="postidTag";
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.page_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tvBottomHint)
    TextView bottomHintTv;
    View header=null;


    private PostReplyDetailAdapter adapter;
    private List<ReplyListBean.PostReplyItemlistBean> mdata=new ArrayList<>(UrlConfig.PAGE_SIZE);

    private int index=0;
    private ImageView headerImg;
    private TextView nickName;
    private TextView content;
    private int postId;
    private int postReplyId;
    private int postReplyItemId;
    private PostCommentDialog dialog;

    @Override
    public int intiLayout() {
        return R.layout.activity_repaly_detail;
    }

    @Override
    public void initView() {

        dialog = new PostCommentDialog();

    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            postId = extras.getInt(POST_ID_TAG);
        }

        adapter=new PostReplyDetailAdapter(mdata,this);
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
        requestData();
    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }

    private void requestData(){
        BteTopService.getReplyListBean(postId, index)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<ReplyListBean>>() {
                    @Override
                    public void call(BaseBean<ReplyListBean> replyListBeanBaseBean) {
                        if (!replyListBeanBaseBean.getCode().equalsIgnoreCase("0000") || replyListBeanBaseBean.getData()==null) {
                            return;
                        }
                        if (index==0) {
                            mdata.clear();
                        }

                        if (replyListBeanBaseBean.getData().getPostReplyItemlist().size()< UrlConfig.PAGE_SIZE) {
                            refreshLayout.setEnableLoadMore(false);
                        }
                        mdata.addAll(replyListBeanBaseBean.getData().getPostReplyItemlist());

                        if (header==null) {
                            header = getLayoutInflater().inflate(R.layout.reply_detail_header, null);
                            headerImg = header.findViewById(R.id.img);
                            nickName = header.findViewById(R.id.nickname);
                            content = header.findViewById(R.id.discussContent);
                            renderHeader(replyListBeanBaseBean);
                            listView.addHeaderView(header);
                        }else {
                            renderHeader(replyListBeanBaseBean);
                        }

                        index++;
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @OnClick({R.id.img_fan_hui,R.id.postCommentll})
    public void setOnClick(View view){
        switch (view.getId()) {
            case R.id.img_fan_hui:
                finish();
                break;
            case R.id.postCommentll:
                showCommentDialog();
                break;
        }

    }

    private void showCommentDialog(){
        if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
            startActivity(new Intent(this, YZMLoginActivity.class));
        }else {
            PostCommentDialog dialog=new PostCommentDialog();
            dialog.show(this,this);
        }
    }

    private void renderHeader(BaseBean<ReplyListBean> replyListBeanBaseBean) {
        ReplyListBean.PostReplyBean postReply = replyListBeanBaseBean.getData().getPostReply();
        nickName.setText(postReply.getUserName());
        content.setText(postReply.getContent());
    }

    @Override
    public void userInputContent(String s) {
        BteTopService.postReplyAdd(postId,s,postReplyItemId)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        index=0;
                        requestData();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    @Override
    public void onHiddenDialog() {
        hiddenKeyBoard();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String sendUserName="";
        if (position==0) {
            postReplyItemId=0;
        }else {
            ReplyListBean.PostReplyItemlistBean postReplyItemlistBean = mdata.get(position-1);
            sendUserName = postReplyItemlistBean.getSendUserName();
            postReplyItemId = postReplyItemlistBean.getId();
        }


        if (position==0) {
            dialog.show(this,this);
        }else {
            dialog.showHint(this,this,"回复"+sendUserName);
        }
    }
}
