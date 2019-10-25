package com.btetop.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.btetop.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class HXRegistAdapter extends RecyclerView.Adapter<HXRegistAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mBookList;

    private CoinPairListen mCoinPairListen;

    public HXRegistAdapter(Context context, List<String> list, CoinPairListen coinPairListen) {
        this.mContext = context;
        this.mBookList = list;
        this.mCoinPairListen = coinPairListen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hx_regist_head_icon, parent, false);
        HXRegistAdapter.ViewHolder viewHolder = new HXRegistAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        switch (position) {
//            case 0:
//                Glide.with(hxRegistDialogActivity).load(R.mipmap.inviting_friends_wx).into(holder.iv_head);
//                break;
//            case 1:
//                Glide.with(hxRegistDialogActivity).load(R.mipmap.inviting_friends_logo).into(holder.iv_head);
//                break;
//            case 2:
//                Glide.with(hxRegistDialogActivity).load(R.mipmap.inviting_friends_wb).into(holder.iv_head);
//                break;
//            case 3:
//                Glide.with(hxRegistDialogActivity).load(R.mipmap.inviting_friends_logo).into(holder.iv_head);
//                break;
//            case 4:
//                Glide.with(hxRegistDialogActivity).load(R.mipmap.inviting_friends_pyq).into(holder.iv_head);
//                break;
//            case 5:
//                Glide.with(hxRegistDialogActivity).load(R.mipmap.inviting_friends_logo).into(holder.iv_head);
//                break;
//            case 6:
//                Glide.with(hxRegistDialogActivity).load(R.mipmap.inviting_friends_wb).into(holder.iv_head);
//                break;
//            case 7:
//                Glide.with(hxRegistDialogActivity).load(R.mipmap.inviting_friends_logo).into(holder.iv_head);
//                break;
//        }

//        Glide.with(hxRegistDialogActivity).load(R.mipmap.inviting_friends_logo).into(holder.iv_head);
        Glide.with(mContext).load(mBookList.get(position).toString()).placeholder(R.mipmap.ic_launcher).into(holder.iv_head);
        holder.rl_item_coin_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCoinPairListen.getCoinPairInfo(position);
            }
        });
    }

    public interface CoinPairListen {
        void getCoinPairInfo(int position);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_head;
        RelativeLayout rl_item_coin_info;

        ViewHolder(View itemView) {
            super(itemView);
            iv_head = itemView.findViewById(R.id.iv_head);
            rl_item_coin_info = itemView.findViewById(R.id.rl_item_coin_info);
        }
    }
}
