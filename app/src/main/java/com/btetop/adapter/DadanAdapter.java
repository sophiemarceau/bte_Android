package com.btetop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.AbnormityBean;
import com.btetop.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DadanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEADER_VIEW = 0;
    private final static int CONTENT_VIEW = 1;
    private final static int FOOT_VIEW = 2;
    private int redTextColor;
    private int blueTextColor;
    private int bgWhiteColor = Color.rgb(255, 255, 255);
    private int bgGrayColor;
    private int bgBlueColor;
    private int bgRedColor;

    private Context context;
    private List<AbnormityBean> mdata;




    public void setdata(List<AbnormityBean> data) {
        this.mdata = data;
    }

    public DadanAdapter(Context context) {
        this.context = context;
        redTextColor = ContextCompat.getColor(context, R.color.main_info_color_red);
        blueTextColor = ContextCompat.getColor(context, R.color.main_info_color_green);

        bgWhiteColor = ContextCompat.getColor(context, R.color.white);
        bgGrayColor = ContextCompat.getColor(context, R.color.color_626A75_10);
        bgBlueColor = ContextCompat.getColor(context, R.color.color_b22_10);
        bgRedColor = ContextCompat.getColor(context, R.color.color_10_4040);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dadan_item_header, parent, false);
            return new ViewHolderHeader(view);
        } else if (viewType == CONTENT_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dadan_item, parent, false);
            return new ViewHolderContent(view);
        } else if (viewType==FOOT_VIEW){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bg_bottom_logo, parent, false);
            return new ViewHolderFoot(view);
        }else {
            throw new RuntimeException("The type has to be HEADER_VIEW or CONTENT_VIEW");
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        } else {
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
        AbnormityBean item = mdata.get(pos);

//        if(("成交").equals(item.getStatusDesc())){
//
//            holder.tvTime1.setPaintFlags(holder.tvTime1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//            holder.tvDirect.setPaintFlags(holder.tvTime.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
//            holder.tvType.setPaintFlags(holder.tvTime.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//            holder.tvPrice.setPaintFlags(holder.tvTime.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
//            holder.tvCount.setPaintFlags(holder.tvTime.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//
//
//            holder.tvDirect.setTextColor(bgWhiteColor);
//            holder.tvType.setTextColor(bgWhiteColor);
//            holder.tvTime.setTextColor(bgWhiteColor);
//            holder.tvCount.setTextColor(bgWhiteColor);
//            holder.tvPrice.setTextColor(bgWhiteColor);
//            if(item.getDirection() == 1)
//                holder.ll_dadan.setBackgroundColor(blueTextColor);
//            else
//                holder.ll_dadan.setBackgroundColor(redTextColor);
//        }
//        else {
//
//
//            if (item.getDirection() == 1) {//绿
//                holder.tvTime.setTextColor(blueTextColor);
//                holder.tvDirect.setTextColor(blueTextColor);
//                holder.tvType.setTextColor(blueTextColor);
//                holder.tvCount.setTextColor(blueTextColor);
//                holder.tvPrice.setTextColor(blueTextColor);
//
//            } else if (item.getDirection() == 2) {//红
//                holder.tvTime.setTextColor(redTextColor);
//                holder.tvDirect.setTextColor(redTextColor);
//                holder.tvType.setTextColor(redTextColor);
//                holder.tvCount.setTextColor(redTextColor);
//                holder.tvPrice.setTextColor(redTextColor);
//            }
//            if ("撤单".equals(item.getStatusDesc())) {
//                holder.ll_dadan.setBackgroundColor(bgGrayColor);
//                holder.tvTime.setPaintFlags(holder.tvTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                holder.tvDirect.setPaintFlags(holder.tvTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                holder.tvType.setPaintFlags(holder.tvTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                holder.tvPrice.setPaintFlags(holder.tvTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                holder.tvCount.setPaintFlags(holder.tvTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//            } else {
//                holder.ll_dadan.setBackgroundColor(bgBlueColor);
//                holder.tvTime.setPaintFlags(holder.tvTime.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                holder.tvDirect.setPaintFlags(holder.tvTime.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
//                holder.tvType.setPaintFlags(holder.tvTime.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                holder.tvPrice.setPaintFlags(holder.tvTime.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
//                holder.tvCount.setPaintFlags(holder.tvTime.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//            }
//        }


//        if ("多单".equals(item.getDirectionDesc()) && "成交".equals(item.getStatusDesc())) {
//            holder.ll_dadan.setBackgroundColor(bgBlueColor);
//        } else if ("空单".equals(item.getDirectionDesc()) && "".equals(item.getStatusDesc())) {
//            holder.ll_dadan.setBackgroundColor(bgGrayColor);
//        }else {
//            holder.ll_dadan.setBackgroundColor(bgWhiteColor);
//        }

        //文字颜色
        if (item.getDirection()==1) {
            holder.tvDirect.setTextColor(blueTextColor);
            holder.tvType.setTextColor(blueTextColor);
        }else {
            holder.tvDirect.setTextColor(redTextColor);
            holder.tvType.setTextColor(redTextColor);
        }

        //北京颜色
        if ("撤单".equals(item.getStatusDesc())) {
            holder.ll_dadan.setBackgroundColor(bgGrayColor);
        }else {
            if ("成交".equals(item.getStatusDesc())){
                holder.ll_dadan.setBackgroundColor(bgBlueColor);
                if (item.getDirection()==1) {
                    holder.ll_dadan.setBackgroundColor(bgBlueColor);
                }else {
                    holder.ll_dadan.setBackgroundColor(bgRedColor);
                }
            }else {
                holder.ll_dadan.setBackgroundColor(bgWhiteColor);
            }
        }



        holder.tvTime1.setText(item.getTime1String());
        holder.tvTime2.setText(item.getTime2String());
        holder.tvDirect.setText(item.getDirectionDesc());
        holder.tvType.setText(item.getStatusDesc());
        holder.tvPrice.setText(item.getPrice());
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

        }
    }

    static class ViewHolderFoot extends RecyclerView.ViewHolder{

        public ViewHolderFoot(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    static class ViewHolderContent extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTime1)
        TextView tvTime1;
        @BindView(R.id.tvTime2)
        TextView tvTime2;
        @BindView(R.id.tvDirectionDesc)
        TextView tvDirect;
        @BindView(R.id.tvStatusDesc)
        TextView tvType;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvCount)
        TextView tvCount;

        @BindView(R.id.ll_dadan)
        LinearLayout ll_dadan;

        public ViewHolderContent(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
