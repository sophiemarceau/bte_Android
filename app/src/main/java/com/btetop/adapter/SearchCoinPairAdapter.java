package com.btetop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.AirBoardBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchCoinPairAdapter extends RecyclerView.Adapter<SearchCoinPairAdapter.ViewHolder> {


    private List<AirBoardBean> list;
    private CoinPairItemClick coinPairItemClick;


    public SearchCoinPairAdapter(List<AirBoardBean> list, CoinPairItemClick coinPairListen) {
        this.list = list;
        this.coinPairItemClick = coinPairListen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_coin_pair, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.rbCoinPair.setSelected(list.get(position).isCheckFlag());
        AirBoardBean searchCoinBean = list.get(position);
        holder.tvCoinPairBase.setText(searchCoinBean.getBaseAsset() + "");
        holder.tvCoinPairQuto.setText("/" + searchCoinBean.getQuoteAsset());
        holder.tvCoinPairExchange.setText(searchCoinBean.getExchange() + "");

        holder.rlCoinPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinPairItemClick.ItemClick(position);
            }
        });
    }

    public interface CoinPairItemClick {
        void ItemClick(int position);
        void ItemRadioBtnClick(int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_coin_pair_base)
        TextView tvCoinPairBase;
        @BindView(R.id.tv_coin_pair_quto)
        TextView tvCoinPairQuto;
        @BindView(R.id.tv_coin_pair_exchange)
        TextView tvCoinPairExchange;
        @BindView(R.id.rb_coin_pair)
        ImageView rbCoinPair;
        @BindView(R.id.rl_coin_pair)
        RelativeLayout rlCoinPair;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
