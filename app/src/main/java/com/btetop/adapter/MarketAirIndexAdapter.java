package com.btetop.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.MarketCoinPairBean;
import com.btetop.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketAirIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final ItemClick mItemClick;


    private List<MarketCoinPairBean.ListBean> coinPairBeans;

    private final static int NORMAL = 1;
    private final static int HEADER_VIEW = 0;

    public MarketAirIndexAdapter(Activity mContext, List<MarketCoinPairBean.ListBean> list, ItemClick itemClick) {
        this.mContext = mContext;
        this.coinPairBeans = list;
        this.mItemClick = itemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_head, parent, false);
            return new HeadViewHolder(view);
        } else if (viewType == NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_heat_index, parent, false);
            return new NormalViewHolder(view);
        } else {
            throw new RuntimeException("The type has to be HEADER_VIEW or CONTENT_VIEW");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        } else {
            return NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case NORMAL:
                initNormal((NormalViewHolder) holder, position);
                break;
            case HEADER_VIEW:
                initHeaderLayout((HeadViewHolder) holder, position);
                break;
            default:
                break;
        }

    }


    public interface ItemClick {
        void ItemClick(String position);
    }


    @Override
    public int getItemCount() {
        return coinPairBeans.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item_market_coin_index_name)
        TextView ivItemMarketCoinIndexName;
        @BindView(R.id.iv_item_market_coin_index_deal_count)
        TextView ivItemMarketCoinIndexDealCount;
        @BindView(R.id.iv_item_market_coin_heat_index)
        TextView ivItemMarketCoinHeatIndex;
        @BindView(R.id.iv_item_market_coin_price)
        TextView ivItemMarketCoinPrice;
        @BindView(R.id.iv_item_market_coin_change)
        TextView ivItemMarketCoinChange;

        NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_market_head_info)
        TextView tvItemMarketHeadInfo;
        @BindView(R.id.tv_item_market_head_price)
        TextView tvItemMarketHeadPrice;
        @BindView(R.id.tv_item_market_head_change)
        TextView tvItemMarketHeadChange;

        HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void initNormal(final NormalViewHolder holder, final int position) {
        holder.ivItemMarketCoinIndexName.setText(coinPairBeans.get(position).getBase() + "");
        holder.ivItemMarketCoinIndexDealCount.setText(CommonUtils.priceConvert(coinPairBeans.get(position).getAmount()) + "");
        holder.ivItemMarketCoinChange.setText(CommonUtils.formathundred(coinPairBeans.get(position).getChange()) + "%");
        holder.ivItemMarketCoinPrice.setText(CommonUtils.formathundred(coinPairBeans.get(position).getPrice()) + "");
        holder.ivItemMarketCoinHeatIndex.setText(CommonUtils.formathundred(coinPairBeans.get(position).getAirIndex()) + "");
    }

    private void initHeaderLayout(HeadViewHolder holder, int pos) {

        holder.tvItemMarketHeadInfo.setText("币种/成交额");
        holder.tvItemMarketHeadPrice.setText("价格");
        holder.tvItemMarketHeadChange.setText("空气指数");
    }
}
