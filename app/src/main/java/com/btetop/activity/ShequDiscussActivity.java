package com.btetop.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.PostDetailAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.PostDetailBean;
import com.btetop.dialog.PostCommentDialog;
import com.btetop.dialog.ShareShequDialog;
import com.btetop.message.MessageUpdateShequ;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.RxUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.btetop.activity.ReplyDetailActivity.POST_ID_TAG;

public class ShequDiscussActivity extends BaseActivity implements PostCommentDialog.UserInputListener, View.OnClickListener {
    public static final String POST_INTRODUCE_TAG = "POST_INTRODUCE_TAG";
    public static final String POST_DATA_INDEX="post_data_index";

    private int dataIndex=0;

    private PostDetailAdapter adapter;

    @BindView(R.id.tran_title_detail)
    TextView title;
    @BindView(R.id.listview)
    ListView listView;

    private TextView headrShare, headerThumb, headerCommentCount;


    private int anInt;
    View header = null;
    private PostDetailBean mdata;
    private String introduce;

    @Override
    public int intiLayout() {
        return R.layout.activity_shequ_detail;
    }

    @Override
    public void initView() {
        title.setText("详情");

    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();
        anInt = extras.getInt(POST_ID_TAG);
        introduce = extras.getString(POST_INTRODUCE_TAG);
        dataIndex = extras.getInt(POST_DATA_INDEX);
        BteTopService.getPostDetail(anInt)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<PostDetailBean>>() {
                    @Override
                    public void call(BaseBean<PostDetailBean> postDetailBeanBaseBean) {
                        if (postDetailBeanBaseBean.getCode().equalsIgnoreCase("0000")) {
                            if (postDetailBeanBaseBean.getData() == null) {
                                return;
                            }
                            PostDetailBean.PostBean post = postDetailBeanBaseBean.getData().getPost();
//                            content.setText(post.getContent());
//                            commentCount.setText(post.getCommentCount()+"条评论");
                            if (postDetailBeanBaseBean.getData().getPostReply() == null) {
                                return;
                            }
                            mdata = postDetailBeanBaseBean.getData();
                            renderListView(postDetailBeanBaseBean.getData());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    private void renderListView(PostDetailBean postReply) {
        adapter = new PostDetailAdapter(postReply.getPostReply(), this);
        adapter.setListener(new PostDetailAdapter.OnClickReply2ReplyListener() {
            @Override
            public void onClickReplay(int id) {
                Intent intent=new Intent(ShequDiscussActivity.this,ReplyDetailActivity.class);
                Bundle b=new Bundle();
                b.putInt(POST_ID_TAG,id);
                intent.putExtras(b);
                ShequDiscussActivity.this.startActivity(intent);
            }
        });

        if (header == null) {
            header = getLayoutInflater().inflate(R.layout.shequ_detail_listview_header, null);
            TextView content = header.findViewById(R.id.content);
            content.setText(postReply.getPost().getContent());
            headerCommentCount = header.findViewById(R.id.commentCount);
            TextView nickNameh = header.findViewById(R.id.nickname);
            nickNameh.setText(postReply.getPost().getUserName()+"·"+postReply.getPost().getPostTime());
            headrShare = header.findViewById(R.id.header_share);
            headerThumb=header.findViewById(R.id.header_thumb);
            headrShare.setOnClickListener(this);
            headerThumb.setOnClickListener(this);
        }
        headrShare.setText(postReply.getPost().getShareCount()==0?"评论":""+postReply.getPost().getShareCount());
        headerThumb.setText(postReply.getPost().getLikeCount()==0?"点赞":""+postReply.getPost().getLikeCount());
        if (postReply.getPost().getHasLike()==1) {
            headerThumb.setSelected(true);
        }
        headerCommentCount.setText(postReply.getPost().getCommentCount() + "条评论");


        if (listView.getHeaderViewsCount() == 0) {
            listView.addHeaderView(header);
        }
        listView.setAdapter(adapter);
    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }

    @OnClick({R.id.img_fan_hui, R.id.postCommentll})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.img_fan_hui:
                finish();
                break;
            case R.id.postCommentll:
                showCommentDialog();
                break;
        }

    }

    private void showCommentDialog() {
        if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
            startActivity(new Intent(this, YZMLoginActivity.class));
        } else {
            PostCommentDialog dialog = new PostCommentDialog();
            dialog.show(this, this);
        }
    }

    @Override
    public void userInputContent(String s) {
        BteTopService.postCommentAdd(anInt, s)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        initData();
                        EventBus.getDefault().post(new MessageUpdateShequ(1,dataIndex));
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_share:
                if (mdata==null|| mdata.getPost()==null) {
                    return;
                }

                Glide.with(this).load(mdata.getPost().getIcon()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (resource==null || resource.isRecycled()) {
                            return;
                        }

                        ShareShequDialog shareShequDialog = ShareShequDialog.newInstance(resource, introduce,mdata.getPost().getUserName(),mdata.getPost().getContent());

                        shareShequDialog.show(getSupportFragmentManager(), "sharePicDialog");
                    }
                });

                BteTopService.addShare(mdata.getPost().getId())
                        .compose(RxUtil.mainAsync())
                        .subscribe(new Action1<BaseBean>() {
                            @Override
                            public void call(BaseBean baseBean) {
                                if (baseBean.getCode().equalsIgnoreCase("0000")) {
                                    initData();
                                    EventBus.getDefault().post(new MessageUpdateShequ(0,dataIndex));
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
                break;
            case R.id.header_thumb:
                if (mdata==null|| mdata.getPost()==null) {
                    return;
                }

                if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                    startActivity(new Intent(this, YZMLoginActivity.class));
                    return;
                }

                BteTopService.addThumb(mdata.getPost().getId())
                        .compose(RxUtil.mainAsync())
                        .subscribe(new Action1<BaseBean>() {
                            @Override
                            public void call(BaseBean baseBean) {
                                if (baseBean.getCode().equalsIgnoreCase("0000")) {
                                    initData();
                                    EventBus.getDefault().post(new MessageUpdateShequ(2,dataIndex));
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });

                break;
        }

    }
}
