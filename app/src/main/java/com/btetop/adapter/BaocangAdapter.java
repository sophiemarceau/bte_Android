package com.btetop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.BurnedBean;
import com.btetop.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaocangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEADER_VIEW = 0;
    private final static int CONTENT_VIEW = 1;
    private final static int FOOT_VIEW = 2;
    private int mRedColor;
    private int mBlueColor;

    private Context context;
    private List<BurnedBean> mdata;

    public BaocangAdapter(Context context, List<BurnedBean> itemlist) {
        this.context=context;
        mRedColor= ContextCompat.getColor(context, R.color.main_color_green);
        mBlueColor= ContextCompat.getColor(context, R.color.main_color_red);

        mdata = itemlist;
    }


    public void add(BurnedBean item) {
        mdata.add(1, item);
        notifyItemInserted(1);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baocang_item_header, parent, false);
            return new ViewHolderHeader(view);
        } else if (viewType == CONTENT_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baocang_item, parent, false);
            return new ViewHolderContent(view);
        } else if (viewType == FOOT_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bg_bottom_logo, parent, false);
            return new ViewHolderFoot(view);
        } else {
            throw new RuntimeException("The type has to be HEADER_VIEW or CONTENT_VIEW");
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        }  else {
            return CONTENT_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER_VIEW:
                initHeaderLayout((ViewHolderHeader) holder, position);
                break;
            case CONTENT_VIEW:
                initContentLayout((ViewHolderContent) holder, position);
                break;
            case FOOT_VIEW:
                break;
            default:
                break;
        }

    }

    private void initHeaderLayout(ViewHolderHeader holder, int pos) {

    }

    private void initContentLayout(ViewHolderContent holder, int pos) {
        BurnedBean item = mdata.get(pos);
        if (item.getDirection().equals("sell")) {//红色
            holder.tvDirect.setTextColor(mRedColor);
        }else {
            holder.tvDirect.setTextColor(mBlueColor);
        }
//
        holder.tvTime1.setText(item.getTimeString());
        holder.tvTime2.setText(item.getTimeString1());
        holder.tvDirect.setText(item.getDirectionDesc());
        holder.tvPrice.setText(item.getPrice());
        holder.tvCount.setText(item.getCount()+"");
//        holder.tvCount.setText(item.getCount()+"");
        holder.tvCount.setText(CommonUtils.priceConvert(item.getCount()) + "张");


    }


    @Override
    public int getItemCount() {
        return mdata == null ? 0 : mdata.size();
    }

    // Static inner class to initialize the views of rows
    static class ViewHolderHeader extends RecyclerView.ViewHolder {
        public TextView item;

        public ViewHolderHeader(View itemView) {
            super(itemView);

//            item = (TextView) itemView.findViewById(R.id.row_item);
        }
    }

    static class ViewHolderFoot extends RecyclerView.ViewHolder {

        public ViewHolderFoot(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
    static class ViewHolderContent extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTime1)
        TextView tvTime1;
        @BindView(R.id.tvTime2)
        TextView tvTime2;
        @BindView(R.id.tvStatusDesc)
        TextView tvDirect;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.tvStatus)
        TextView tvStatus;

        public ViewHolderContent(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
