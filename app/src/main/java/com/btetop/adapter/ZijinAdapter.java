package com.btetop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.ZiJinBean;
import com.btetop.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZijinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEADER_VIEW = 0;
    private final static int CONTENT_VIEW = 1;
    private int mRedColor;
    private int mBlueColor;
    private List<ZiJinBean.TradeBean> mdata;

    public ZijinAdapter(Context context) {
        mRedColor = ContextCompat.getColor(context, R.color.zijin_decreasing_color);
        mBlueColor = ContextCompat.getColor(context, R.color.zijin_increasing_color);
    }

    public void setData(List<ZiJinBean.TradeBean> flowBeans){
        this.mdata = flowBeans;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zijin_item_header, parent, false);
            return new ViewHolderHeader(view);
        } else if (viewType == CONTENT_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zijin_item_content, parent, false);
            return new ViewHolderContent(view);
        } else {
            throw new RuntimeException("The type has to be HEADER_VIEW or CONTENT_VIEW");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER_VIEW:
                initHeaderLayout((ViewHolderHeader) holder, position);
                break;
            case CONTENT_VIEW:
//                initHeaderLayout((ViewHolderHeader) holder, position);
                initContentLayout((ViewHolderContent) holder, position);
                break;
            default:
                break;
        }

    }


    private void initHeaderLayout(ViewHolderHeader holder, int pos) {

    }

    private void initContentLayout(ViewHolderContent holder, int position) {
        ZiJinBean.TradeBean timeBean = mdata.get(position);
        holder.tvTime.setText(timeBean.getName());

        holder.tvBuy.setText(CommonUtils.formatChina(timeBean.getBuy()));
        holder.tvSell.setText(CommonUtils.formatChina(timeBean.getSell()));

        holder.tvNet.setText(CommonUtils.formatChina(timeBean.getNet()));
        if (timeBean.getNet()>0) {
            holder.tvNet.setTextColor(mBlueColor);
        }else {
            holder.tvNet.setTextColor(mRedColor);
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
    public int getItemCount() {
        return mdata == null ? 0 : mdata.size();
    }

    static class ViewHolderHeader extends RecyclerView.ViewHolder {

        public ViewHolderHeader(View itemView) {
            super(itemView);

//            item = (TextView) itemView.findViewById(R.id.row_item);
        }
    }

    static class ViewHolderContent extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        TextView tvTime;
        @BindView(R.id.buy)
        TextView tvBuy;
        @BindView(R.id.sell)
        TextView tvSell;
        @BindView(R.id.netVolume)
        TextView tvNet;


        public ViewHolderContent(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
