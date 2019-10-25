package com.btetop.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.activity.PostShequActivity;
import com.btetop.activity.SheQuMessageCenterActivity;
import com.btetop.activity.ShequDiscussActivity;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.adapter.ShequListAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.base.EventBus.BindEventBus;
import com.btetop.bean.BaseBean;
import com.btetop.bean.ShequListBean;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.ShareShequDialog;
import com.btetop.message.MessageUpdateShequ;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.RxUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.btetop.activity.PostShequActivity.HINT_TAG;
import static com.btetop.activity.ReplyDetailActivity.POST_ID_TAG;
import static com.btetop.activity.ShequDiscussActivity.POST_DATA_INDEX;
import static com.btetop.activity.ShequDiscussActivity.POST_INTRODUCE_TAG;

@BindEventBus
public class SheQuFragment extends BaseFragment implements ShequListAdapter.BottomClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "SheQuFragment";
    @BindView(R.id.page_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.title)
    TextView title;


    private  int index = 0;
    private List<ShequListBean.PostlistBean> mdata=new ArrayList<>(UrlConfig.PAGE_SIZE);

    private ShequListAdapter adapter;
    private String introducer="";


    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_shequ;
    }

    @Override
    protected void initView(View view) {
        title.setText("社区");
        adapter=new ShequListAdapter(mdata,getActivity());
        adapter.setListener(this);
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

    @Override
    protected boolean statisticsFragment() {
        return false;
    }


    @OnClick({R.id.title,R.id.add,R.id.layout_message_center})
    public void setOnClick(View v) {
        switch (v.getId()) {
            case R.id.title:

                break;
            case R.id.add:
                if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                    getActivity().startActivity(new Intent(getActivity(), YZMLoginActivity.class));
                    return;
                }
                Intent intent=new Intent(getActivity(), PostShequActivity.class);
                intent.putExtra(HINT_TAG,introducer);
                startActivity(intent);
                break;
            case R.id.layout_message_center:
                if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                    startActivity(new Intent(getActivity(), YZMLoginActivity.class));
                }else {
                    Intent intent1=new Intent(getActivity(), SheQuMessageCenterActivity.class);
                    intent1.putExtra(HINT_TAG,introducer);
                    startActivity(intent1);
                }
                break;
        }


    }

    private void requestData(){
        BteTopService.getShequList(index)
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<ShequListBean>>() {
                    @Override
                    public void call(BaseBean<ShequListBean> shequListBeanBaseBean) {
                        if (shequListBeanBaseBean.getCode().equalsIgnoreCase("0000")
                                && shequListBeanBaseBean.getData() != null) {
                            introducer = shequListBeanBaseBean.getData().getIntroducer();
                            if (index==0) {
                                mdata.clear();
                            }
                            if (shequListBeanBaseBean.getData().getPostlist().size()< UrlConfig.PAGE_SIZE) {
                                refreshLayout.setEnableLoadMore(false);
                            }
                            mdata.addAll(shequListBeanBaseBean.getData().getPostlist());
                            index++;
                            adapter.notifyDataSetChanged();
                            refreshLayout.finishLoadMore();
                            refreshLayout.finishRefresh();
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void onShareClick(int position) {
        if (mdata==null || mdata.size()<position) {
            return;
        }

        Glide.with(getActivity()).load(mdata.get(position).getIcon()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource==null || resource.isRecycled()) {
                    return;
                }

                ShareShequDialog shareShequDialog = ShareShequDialog.newInstance(resource, introducer, mdata.get(position).getUserName(), mdata.get(position).getContent());

                shareShequDialog.show(getActivity().getSupportFragmentManager(), "sharePicDialog");
            }
        });


        BteTopService.addShare(mdata.get(position).getId())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    @Override
    public void onDiscussClick(int position) {
        Intent intent=new Intent(getActivity(), ShequDiscussActivity.class);
        intent.putExtra(POST_ID_TAG,mdata.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onThumbClick(int position) {

        if (mdata==null || mdata.size()<position) {
            return;
        }

        if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
            getActivity().startActivity(new Intent(getContext(), YZMLoginActivity.class));
            return;
        }

        BteTopService.addThumb(mdata.get(position).getId())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), ShequDiscussActivity.class);
        intent.putExtra(POST_ID_TAG,mdata.get(position).getId());
        intent.putExtra(POST_INTRODUCE_TAG,introducer);
        intent.putExtra(POST_DATA_INDEX,position);
        startActivity(intent);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateThumbOrShare(MessageUpdateShequ msg){

        if (mdata==null || mdata.size()<msg.dataPosition) {
            return;
        }
        //0 分享 1 评论 2 点赞
        ShequListBean.PostlistBean postlistBean = mdata.get(msg.dataPosition);
        switch (msg.type) {
            case 0:
                postlistBean.addShareCount();
                break;
            case 1:
                postlistBean.addCommentCount();
                break;
            case 2:
                postlistBean.addLikeCount();
                postlistBean.addHasLike();
                break;
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        }else {
            listView.smoothScrollToPositionFromTop(0,0);
            index=0;
            requestData();
        }
    }
}
