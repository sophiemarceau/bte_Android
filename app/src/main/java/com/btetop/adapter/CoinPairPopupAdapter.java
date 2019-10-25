package com.btetop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.CoinPairBean;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class CoinPairPopupAdapter extends BaseAdapter {

    private Context context;
    private List<CoinPairBean> data =new ArrayList<>();

    public CoinPairPopupAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.spinner_item_coinpair_dropdown, null);
            viewHolder.tvExchange = (TextView) view.findViewById(R.id.tv_exchange);
            viewHolder.tvPairName=view.findViewById(R.id.tv_pair_name);
            viewHolder.llContainer=view.findViewById(R.id.pairContainer);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (data != null && data.size() > 0) {
            viewHolder.tvExchange.setText(data.get(i).getExchange());
            viewHolder.tvPairName.setText(data.get(i).getName());
            if (data.get(i).isChecked()){
                viewHolder.llContainer.setBackgroundResource(R.drawable.goods_attr_selected_shape);
            } else {
                viewHolder.llContainer.setBackgroundResource(R.drawable.goods_attr_unselected_shape);
            }
        }
        return view;
    }

    static class ViewHolder {
        public TextView tvExchange;
        public TextView tvPairName;
        public LinearLayout llContainer;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(List<CoinPairBean> tempData) {
        if (tempData == null || tempData.size() == 0) {
            return;
        }
        data.clear();
        data.addAll(tempData);
        notifyDataSetChanged();
    }
}
