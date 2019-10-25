package com.btetop.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.btetop.R;
import com.btetop.activity.ShequDiscussActivity;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.adapter.ReleaseAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.MyReleaseBeanOuter;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.ShareShequDialog;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

import static com.btetop.activity.ReplyDetailActivity.POST_ID_TAG;
import static com.btetop.activity.ShequDiscussActivity.POST_DATA_INDEX;
import static com.btetop.activity.ShequDiscussActivity.POST_INTRODUCE_TAG;

public class PostFragment extends BaseFragment implements ReleaseAdapter.BottomClickListener, AdapterView.OnItemClickListener {
    @BindView(R.id.page_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.listview)
    ListView listView;

    private  int index = 0;
    private ReleaseAdapter adapter;
    private List<MyReleaseBeanOuter.MyReleaseBean> mdata=new ArrayList<>(UrlConfig.PAGE_SIZE);

    private String hintString="";


    public static  PostFragment getInstance(String hintString){
        PostFragment fragment=new PostFragment();
        fragment.hintString=hintString;
        return fragment;
    }

    public PostFragment(){

    }
    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_post;
    }

    @Override
    protected void initView(View view) {

        adapter=new ReleaseAdapter(mdata,getActivity());
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


    private void requestData() {
        BteTopService.getRelease(index)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<MyReleaseBeanOuter>>() {
                    @Override
                    public void call(BaseBean<MyReleaseBeanOuter> myReleaseBeanOuterBaseBean) {

                        if (index==0) {
                            mdata.clear();
                        }
                        if (myReleaseBeanOuterBaseBean.getData().getMyRelease().size()< UrlConfig.PAGE_SIZE) {
                            refreshLayout.setEnableLoadMore(false);
                        }
                        mdata.addAll(myReleaseBeanOuterBaseBean.getData().getMyRelease());
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

                ShareShequDialog shareShequDialog = ShareShequDialog.newInstance(resource, hintString, mdata.get(position).getUserName(), mdata.get(position).getContent());

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
        intent.putExtra(POST_INTRODUCE_TAG,hintString);
        intent.putExtra(POST_DATA_INDEX,position);
        startActivity(intent);
    }

    public void refreshFragment(){
        index=0;
        requestData();
    }
}
