package com.btetop.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.Course;
import com.btetop.config.Constant;
import com.btetop.utils.SPUtils;
import com.btetop.utils.TypeFaceUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeGouRecycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Activity mContext;
    private final GouItemClick mGouItemClick;
    private List<Course> dogsBean;

    private final static int NORMAL = 0;
    private final static int OTHER = 1;


    public HomeGouRecycleviewAdapter(Activity mContext, List<Course> list, GouItemClick gouItemClick) {
        this.mContext = mContext;
        this.dogsBean = list;
        mGouItemClick = gouItemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_gou_item, parent, false);
            return new HomeGouRecycleviewAdapter.NormalViewHolder(view);
        } else if (viewType == OTHER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_gou_other_item, parent, false);
            return new HomeGouRecycleviewAdapter.OtherHolder(view);
        } else {
            throw new RuntimeException("The type has to be HEADER_VIEW or CONTENT_VIEW");
        }

    }

    @Override
    public int getItemViewType(int position) {

        return NORMAL;
//        if (position == 6) {
//            return OTHER;
//        } else {
//            return NORMAL;
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case NORMAL:
                initNormal((NormalViewHolder) holder, position);
                break;
            case OTHER:
                initOther((OtherHolder) holder, position);
                break;
            default:
                break;
        }

    }


    public interface GouItemClick {
        void GouItemClick(String position);
    }


    @Override
    public int getItemCount() {
        return dogsBean == null ? 0 : dogsBean.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_to_dog)
        RelativeLayout rl_to_dog;
        @BindView(R.id.iv_gou_icon)
        ImageView iv_gou_icon;
        @BindView(R.id.tv_gou_title)
        TextView tv_gou_title;
        @BindView(R.id.tv_gou_desc)
        TextView tv_gou_desc;
        @BindView(R.id.tv_gou_info)
        TextView tv_gou_info;
        @BindView(R.id.tv_gou_btn)
        TextView tv_gou_btn;
        @BindView(R.id.iv_gou_point)
        ImageView iv_gou_point;
        @BindView(R.id.iv_lz_new)
        ImageView iv_lz_new;



        NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class OtherHolder extends RecyclerView.ViewHolder {


        OtherHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    private void initOther(OtherHolder holder, int position) {


    }

    public void initNormal(final NormalViewHolder holder, final int position) {

        Course course = dogsBean.get(position);
        final String type = course.getType();
        holder.iv_gou_point.setVisibility(course.getNotice() > 0 ? View.VISIBLE : View.GONE);

        switch (type) {
            case Constant.EVENT_LZ_DOG:
                ImageView iv_gou_icon = holder.iv_gou_icon;
                Glide.with(mContext).load(R.mipmap.luzhaung).into(iv_gou_icon);
                holder.tv_gou_desc.setText("人工智能预判拉盘、出货");
                String info = "本周开撸<font color='#308CDD'><b><tt>" + course.getCount() + "</tt></b></font>次"
                        + " 收益<font color='#308CDD'><b><tt>" + course.getIncome() + "%</tt></b></font>";
                holder.tv_gou_title.setText("撸庄狗");
                TypeFaceUtils.setTVTypeFace(mContext, holder.tv_gou_title);
                holder.tv_gou_info.setText(Html.fromHtml(info));
                holder.tv_gou_btn.setText("去撸庄");
                if (SPUtils.get("iv_lz_new","0").equals("0")) {
                    holder.iv_lz_new.setVisibility(View.VISIBLE);
                }
                holder.rl_to_dog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGouItemClick.GouItemClick(type);
                        holder.iv_gou_point.setVisibility(View.GONE);
                        SPUtils.put("iv_lz_new", "2");
                        holder.iv_lz_new.setVisibility(View.GONE);
                    }
                });
                break;

            case Constant.EVENT_BAND_DOG:
                Glide.with(mContext).load(R.mipmap.boduan).into(holder.iv_gou_icon);
                holder.tv_gou_desc.setText("主流币种实时买点/卖点智能提示");
                String info1 = "<font color='#308CDD'><b><tt>" + course.getUserCount() + "</tt></b></font>人正在使用";
                holder.tv_gou_title.setText("波段狗");
                TypeFaceUtils.setTVTypeFace(mContext, holder.tv_gou_title);
                holder.tv_gou_info.setText(Html.fromHtml(info1));
                holder.tv_gou_btn.setText("做波段");
                holder.iv_lz_new.setVisibility(View.GONE);
                holder.rl_to_dog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGouItemClick.GouItemClick(type);
                        holder.iv_gou_point.setVisibility(View.GONE);
                    }
                });
                break;

            case Constant.EVENT_RESEARCH_DOG:
                Glide.with(mContext).load(R.mipmap.yanjiu).into(holder.iv_gou_icon);
                holder.tv_gou_desc.setText("汇聚区块链领域最新研究报告");
                String info3 = "<font color='#308CDD'><b><tt>" + course.getAgencyCount() + "</tt></b></font>家机构"
                        + " <font color='#308CDD'><b><tt>" + course.getCount() + "</tt></b></font>篇报告";
                holder.tv_gou_title.setText("研究狗");
                TypeFaceUtils.setTVTypeFace(mContext, holder.tv_gou_title);
                holder.tv_gou_info.setText(Html.fromHtml(info3));
                holder.tv_gou_btn.setText("读报告");
                holder.iv_lz_new.setVisibility(View.GONE);
                holder.rl_to_dog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGouItemClick.GouItemClick(type);
                        holder.iv_gou_point.setVisibility(View.GONE);
                    }
                });
                break;
            case Constant.EVENT_STRATEGY_DOG:
                Glide.with(mContext).load(R.mipmap.ding_pan_gou).into(holder.iv_gou_icon);
                holder.tv_gou_desc.setText("多种智能指标为您实时盯盘");
                String info4 = "已累计盯盘<font color='#308CDD'><b><tt>" + course.getUserCount() + "</tt></b></font>次";
                holder.tv_gou_title.setText("盯盘狗");
                TypeFaceUtils.setTVTypeFace(mContext, holder.tv_gou_title);
                holder.tv_gou_info.setText(Html.fromHtml(info4));
                holder.tv_gou_btn.setText("去盯盘");
                holder.iv_lz_new.setVisibility(View.GONE);
                holder.rl_to_dog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGouItemClick.GouItemClick(type);
                        holder.iv_gou_point.setVisibility(View.GONE);
                    }
                });
                break;
            case Constant.EVENT_FUTURE_DOG:
                Glide.with(mContext).load(R.mipmap.heyue).into(holder.iv_gou_icon);
                holder.tv_gou_desc.setText("深度挖掘交易数据，技术派必备");
                String info2 = "<font color='#308CDD'><b><tt>" + course.getUserCount() + "</tt></b></font>人正在使用";
                SPUtils.put("futureDogUsers", course.getUserCount() + "");
                holder.iv_gou_point.setVisibility(View.VISIBLE);
                holder.tv_gou_title.setText("合约狗");
                TypeFaceUtils.setTVTypeFace(mContext, holder.tv_gou_title);
                holder.tv_gou_info.setText(Html.fromHtml(info2));
                holder.tv_gou_btn.setText("做合约");
                holder.iv_lz_new.setVisibility(View.GONE);
                holder.rl_to_dog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGouItemClick.GouItemClick(type);
                    }
                });
                break;

            case Constant.EVENT_LIAN_CHACHA:
                Glide.with(mContext).load(R.mipmap.iv_gou_lianchacha).into(holder.iv_gou_icon);
                holder.tv_gou_desc.setText("跟踪区块链行业信息");
                String  lcc= "已关联项目<font color='#308CDD'><b><tt>" + course.getCount() + "</tt></b></font>个";
                SPUtils.put("futureDogUsers", course.getUserCount() + "");
                holder.iv_gou_point.setVisibility(View.VISIBLE);
                holder.tv_gou_title.setText("链查查");
                TypeFaceUtils.setTVTypeFace(mContext, holder.tv_gou_title);
                holder.tv_gou_info.setText(Html.fromHtml(lcc));
                holder.iv_lz_new.setVisibility(View.GONE);
                holder.rl_to_dog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGouItemClick.GouItemClick(type);
                    }
                });
                holder.iv_gou_point.setVisibility(View.GONE);
                break;
        }
    }

}
