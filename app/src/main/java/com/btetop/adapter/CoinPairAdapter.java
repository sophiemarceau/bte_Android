package com.btetop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.CoinPairBean;

import java.util.List;

public class CoinPairAdapter extends RecyclerView.Adapter<CoinPairAdapter.ViewHolder> {

    private List<CoinPairBean> mBookList;

    private CoinPairListen mCoinPairListen;

    public CoinPairAdapter(List<CoinPairBean> list, CoinPairListen coinPairListen) {
        this.mBookList = list;
        this.mCoinPairListen = coinPairListen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_pair, parent, false);
        CoinPairAdapter.ViewHolder viewHolder = new CoinPairAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String quoteAsset = mBookList.get(position).getQuoteAsset();
        final String baseAsset = mBookList.get(position).getBaseAsset();
        final String exchange = mBookList.get(position).getExchange();
        final String name=mBookList.get(position).getName();
        holder.coinPair.setText(name);
        holder.address.setText(exchange);
        holder.rl_item_coin_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCoinPairListen.getCoinPairInfo(baseAsset, quoteAsset, exchange,name);
            }
        });
    }

    public interface CoinPairListen {
        void getCoinPairInfo(String baseAsset, String quoteAsset, String exchange,String name);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView coinPair;
        TextView address;
        RelativeLayout rl_item_coin_info;

        ViewHolder(View itemView) {
            super(itemView);
            coinPair = itemView.findViewById(R.id.tv_coin_pair);
            address = itemView.findViewById(R.id.tv_coin_pair_address);
            rl_item_coin_info = itemView.findViewById(R.id.rl_item_coin_info);
        }
    }
}
