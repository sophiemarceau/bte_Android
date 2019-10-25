package com.btetop.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.CoinInfo;
import com.btetop.utils.FormatUtil;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class HomeListAdapter extends BaseAdapter {
    private Context context;
    private List<CoinInfo> list;

    public HomeListAdapter(Context context, List<CoinInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item, null);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.home_list_icon);
            holder.symbol = convertView.findViewById(R.id.home_list_symbol);
            holder.price = convertView.findViewById(R.id.home_list_price);
            holder.change = convertView.findViewById(R.id.home_list_change);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String icon = list.get(position).getIcon();
        if (!TextUtils.isEmpty(icon) && icon != null) {
            Glide.with(context).load(icon)
                    .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(holder.icon);
        }
        String symbol = list.get(position).getSymbol();
        if (!TextUtils.isEmpty(symbol) && symbol != null) {
            holder.symbol.setText(symbol);
        }
        double price = list.get(position).getPrice();
        String strPrice = FormatUtil.doubleTrans1(price);
        String money = FormatUtil.getMoneyType(strPrice);
        holder.price.setText(money);

        double change = list.get(position).getChange();
        if (change > 0.0) {
            holder.change.setBackgroundResource(R.drawable.home_list_green_bg);
            holder.change.setText("+" + FormatUtil.doubleTrans1(change) + "%");
        } else if (change < 0.0) {
            holder.change.setBackgroundResource(R.drawable.home_list_orange_bg);
            holder.change.setText(FormatUtil.doubleTrans1(change) + "%");
        } else if (change == 0.0) {
            holder.change.setBackgroundResource(R.drawable.home_list_gray_bg);
            holder.change.setText(FormatUtil.doubleTrans1(change) + "%");
        }
        convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.home_list_selector));
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView symbol;
        TextView change;
        TextView price;
    }
}
