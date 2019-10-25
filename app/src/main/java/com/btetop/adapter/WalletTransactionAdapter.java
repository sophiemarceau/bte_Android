package com.btetop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.WalletTransaction;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletTransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEADER_VIEW = 0;
    private final static int CONTENT_VIEW = 1;
    private final static int FOOT_VIEW = 2;
    private int mRedColor;
    private int mBlueColor;

    private Context context;
    private List<WalletTransaction.WalletTractionDetail> mdata;

    public WalletTransactionAdapter(Context context) {
        this.context=context;
        mRedColor= ContextCompat.getColor(context, R.color.main_info_color_red);
        mBlueColor= ContextCompat.getColor(context, R.color.main_info_color_green);
    }

    public void setData(List<WalletTransaction.WalletTractionDetail> data){
        mdata = data;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallettransaction_header, parent, false);
            return new ViewHolderHeader(view);
        } else if (viewType == CONTENT_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallettransaction, parent, false);
            return new ViewHolderContent(view);
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
        WalletTransaction.WalletTractionDetail item = mdata.get(pos);
        if (item.getAmount().intValue() < 0 ) {//红色
            holder.tvAmount.setTextColor(mRedColor);
        }else {
            holder.tvAmount.setTextColor(mBlueColor);
        }
//
        holder.tvTime1.setText(item.getTime1String());
        holder.tvTime2.setText(item.getTime2String());
        holder.tvType.setText(item.getType());

        DecimalFormat df4 = new DecimalFormat();
        df4.applyPattern("#.##");

        holder.tvAmount.setText(df4.format(item.getAmount()));


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
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.tvAmount)
        TextView tvAmount;



        public ViewHolderContent(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
