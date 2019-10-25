package com.btetop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.CoinInfo;
import com.btetop.utils.FormatUtil;
import com.btetop.utils.TypeFaceUtils;

import java.util.List;

public class HomeRecycleviewAdapter extends RecyclerView.Adapter<HomeRecycleviewAdapter.ViewHolder> {

    private final boolean mIsFirstHomeLatests;
    private final Context activity;
    private List<CoinInfo> list;
    private HomeRecycleviewItemClick homeRecycleviewItemClick;
    private int GreenanInt;
    private int RedanInt;
    private int GrayanInt;


    public HomeRecycleviewAdapter(Context activity, List<CoinInfo> list, boolean isFirstHomeLatests, HomeRecycleviewItemClick coinPairListen) {
        this.list = list;
        this.activity = activity;
        mIsFirstHomeLatests = isFirstHomeLatests;
        this.homeRecycleviewItemClick = coinPairListen;
        GreenanInt = Color.parseColor("#228B22");
        RedanInt = Color.parseColor("#FF4040");
        GrayanInt = Color.parseColor("#808790");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
        HomeRecycleviewAdapter.ViewHolder viewHolder = new HomeRecycleviewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String symbol = list.get(position).getSymbol();
        if (!TextUtils.isEmpty(symbol) && symbol != null) {
            holder.symbol.setText(symbol);
        }
        double price = list.get(position).getPrice();
        String strPrice = FormatUtil.doubleTrans1(price);
//        String money = FormatUtil.getMoneyType(strPrice);
        holder.price.setText(strPrice);
        TypeFaceUtils.setTVTypeFace(activity,holder.price);

//        if (mIsFirstHomeLatests) {
//            //第一次刷新直接赋值
//            holder.price.setText(money);
//        } else {
//            //以后每次刷新比较和上次的值 来改变颜色
//
//            holder.price.setText(money);
//
//            switch (symbol) {
//                case "BTC":
//                    comparePrice(holder, price, BTC_PRICE);
//                    BTC_PRICE = price;
//                    break;
//                case "ETH":
//                    comparePrice(holder, price, ETH_PRICE);
//                    ETH_PRICE = price;
//                    break;
//                case "BCH":
//                    comparePrice(holder, price, BCH_PRICE);
//                    BCH_PRICE = price;
//                    break;
//                case "EOS":
//                    comparePrice(holder, price, EOS_PRICE);
//                    EOS_PRICE = price;
//                    break;
//            }
//
//
//        }

        double change = list.get(position).getChange();
        if (change > 0.0) {
//            holder.change.setBackgroundResource(R.drawable.home_list_green_bg);
            holder.change.setTextColor(GreenanInt);
            holder.change.setText("+" + FormatUtil.doubleTrans1(change) + "%");
            holder.price.setText(strPrice);
            holder.price.setTextColor(GreenanInt);
        } else if (change < 0.0) {
//            holder.change.setBackgroundResource(R.drawable.home_list_orange_bg);
            holder.change.setTextColor(RedanInt);
            holder.change.setText(FormatUtil.doubleTrans1(change) + "%");
            holder.price.setText(strPrice);
            holder.price.setTextColor(RedanInt);
        } else if (change == 0.0) {
//            holder.change.setBackgroundResource(R.drawable.home_list_gray_bg);
            holder.change.setTextColor(GrayanInt);
            holder.change.setText(FormatUtil.doubleTrans1(change) + "%");
            holder.price.setText(strPrice);
            holder.price.setTextColor(GrayanInt);
        }
//        TypeFaceUtils.setTVTypeFace(activity,holder.change);
        holder.rl_home_recycler_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeRecycleviewItemClick.hangqingItemClick(position);
                holder.iv_ping.setVisibility(View.INVISIBLE);
            }
        });

        holder.iv_ping.setVisibility(list.get(position).getNotice() > 0 ? View.VISIBLE : View.INVISIBLE);
        if (list.size()-1 == position) {
            holder.view.setVisibility(View.GONE);
        }else {
            holder.view.setVisibility(View.VISIBLE);
        }
    }

//    public void comparePrice(ViewHolderNormal holder, double price, double lastPrice) {
//        if (price > lastPrice) {
//            holder.price.setTextColor(Color.parseColor("#228B22"));
//        } else if (price < lastPrice) {
//            holder.price.setTextColor(Color.parseColor("#FF4040"));
//        } else if (price == lastPrice) {
//            holder.price.setTextColor(Color.parseColor("#808790"));
//        }
//    }

    public interface HomeRecycleviewItemClick {
        void hangqingItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_home_recycler_item;
        TextView symbol;
        TextView change;
        TextView price;
        ImageView iv_ping;
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            rl_home_recycler_item = itemView.findViewById(R.id.rl_home_recycler_item);
            symbol = itemView.findViewById(R.id.home_list_symbol);
            price = itemView.findViewById(R.id.home_list_price);
            change = itemView.findViewById(R.id.home_list_change);
            iv_ping = itemView.findViewById(R.id.iv_ping);
            view = itemView.findViewById(R.id.view);
        }
    }
}
