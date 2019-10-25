package com.btetop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.btetop.R;
import com.btetop.bean.DepthBean;
import com.btetop.bean.DetailDepthBean;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEADER_VIEW = 0;
    private final static int LEFT_VIEW = 1;
    private final static int RIGHT_VIEW = 2;
    private final static int FOOT_VIEW = 3;

    private int mRedColor;
    private int mBlueColor;

    private Context context;
    private List<DepthBean> mData;


    public void setData(List<DepthBean> mData) {
        this.mData = mData;
    }

    private int halfScreenWidth;

    //静态存储优化性能
    private View headerView,leftView,rightView,footView;

    public DepthAdapter(Context context) {
        this.context = context;
        mRedColor = ContextCompat.getColor(context, R.color.main_color_green);
        mBlueColor = ContextCompat.getColor(context, R.color.main_color_red);

        halfScreenWidth = (int) ((ScreenUtils.getScreenWidth()/2)*0.8);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            if(headerView == null)
                headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shendu_header, parent, false);
            return new ViewHolderHeader(headerView);
        } else if (viewType == LEFT_VIEW) {
            leftView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shendu_left_view, parent, false);
            return new ViewHolderLeft(leftView);
        } else if(viewType==RIGHT_VIEW){
            rightView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shendu_right_view, parent, false);
            return new ViewHolderRight(rightView);
        }else if(viewType==FOOT_VIEW) {
            footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shendu_bottom_view, parent, false);
            return new ViewHolderFoot(footView);
        }else {
            throw new RuntimeException("The type has to be HEADER_VIEW or LEFT_VIEW");
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        } else {
            return  itemType(position);
        }
    }

    private int itemType(int position) {
        String type = mData.get(position).getType();
        if (type.equals("ASK")) {
            return LEFT_VIEW;
        } else if (type.equals("BID")) {
            return RIGHT_VIEW;
        }else if(type.equals(DetailDepthBean.DEPTHFOOT)){
            return FOOT_VIEW;
        }
        return LEFT_VIEW;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER_VIEW:
                initHeaderLayout((ViewHolderHeader) holder, position);
                break;
            case LEFT_VIEW:
                initLeftLayout((ViewHolderLeft) holder, position);
                break;
            case RIGHT_VIEW:
                initRightLayout((ViewHolderRight)holder,position);
                break;
            case FOOT_VIEW:
                initFootLayout((ViewHolderFoot)holder,position);
                break;
            default:
                break;
        }

    }

    private void initFootLayout(ViewHolderFoot holder, int position) {
        if (mData==null || mData.size()==0) {
            return;
        }
        holder.title.setText(mData.get(position).getRemark());
    }

    private void initHeaderLayout(ViewHolderHeader holder, int pos) {

    }

    private void initLeftLayout(ViewHolderLeft holder, int pos) {

        if(mData==null||mData.size() == 0) return;
            double maxDepth = 0;
            for (DepthBean depth : mData) {
                maxDepth = Math.max(maxDepth, depth.getCount());
            }

        DepthBean depthBean = mData.get(pos);
        holder.price.setText(depthBean.getPrice()+"");

        DecimalFormat df4 = new DecimalFormat();
        df4.applyPattern("#.##");
        holder.volume.setText(df4.format(depthBean.getCount()));
        //动态设置设置宽高
        int heigt = SizeUtils.dp2px(40);
        float count=(float)depthBean.getCount();
        int width = maxDepth==0?0:(int) (halfScreenWidth * (count / maxDepth));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, heigt);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        holder.bg.setLayoutParams(layoutParams);

    }


    private void initRightLayout(ViewHolderRight holder, int pos) {

        if(mData==null||mData.size() == 0) return;
        double maxDepth = 0;
        for (DepthBean depth : mData) {
            maxDepth = Math.max(maxDepth, depth.getCount());

        }


        DepthBean depthBean = mData.get(pos);
        holder.price.setText(depthBean.getPrice()+"");

        DecimalFormat df4 = new DecimalFormat();
        df4.applyPattern("#.##");

        holder.volume.setText(df4.format(depthBean.getCount()));

        //动态设置设置宽高
        int heigt = SizeUtils.dp2px(40);
        float count=(float)depthBean.getCount();
        int width = maxDepth==0?0:(int) (halfScreenWidth * (count / maxDepth));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, heigt);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        holder.bg.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolderHeader extends RecyclerView.ViewHolder {
        public TextView item;
        public ViewHolderHeader(View itemView) {
            super(itemView);
        }
    }

    static class ViewHolderLeft extends RecyclerView.ViewHolder {

        @BindView(R.id.left_price)
        TextView price;
        @BindView(R.id.left_volume)
        TextView volume;
        @BindView(R.id.left_bg)
        View bg;


        public ViewHolderLeft(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolderFoot extends RecyclerView.ViewHolder {
        @BindView(R.id.shendu_bottom_title)
        TextView title;


        public ViewHolderFoot(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolderRight extends RecyclerView.ViewHolder {

        @BindView(R.id.right_price)
        TextView price;
        @BindView(R.id.right_volume)
        TextView volume;
        @BindView(R.id.right_bg)
        View bg;


        public ViewHolderRight(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
