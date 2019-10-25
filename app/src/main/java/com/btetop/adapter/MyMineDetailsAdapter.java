package com.btetop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.UserCurrentLotBean.UserStrategyData;
import com.btetop.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */

public class MyMineDetailsAdapter extends BaseAdapter {
    private Context context;
    private List<UserStrategyData.UserStrategyDateDetails> dataList;
    private String falg;
    private MyMineStrategyAdapter strategyAdapter;
    private double ror;

    public MyMineDetailsAdapter(Context context, List<UserStrategyData.UserStrategyDateDetails> dataList, String falg) {
        this.context = context;
        this.dataList = dataList;
        this.falg = falg;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.mine_list_item, null);
            holder.batchName = convertView.findViewById(R.id.batchName);
            holder.itemListView = convertView.findViewById(R.id.details_item_listView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ror = dataList.get(position).getRor();
        holder.batchName.setText(dataList.get(position).getProductBatchName());
        String type = dataList.get(position).getAssetType();
        final ArrayList<UserStrategyData.UserStrategyDateDetails.UserStrategySmallDetails> smallDetailsList = dataList.get(position).getDetails();
        if (null != smallDetailsList && dataList.size() != 0) {
            holder.itemListView.setVisibility(View.VISIBLE);
            strategyAdapter = new MyMineStrategyAdapter(context, falg, smallDetailsList, type, ror);
            holder.itemListView.setAdapter(strategyAdapter);
        } else {
            holder.itemListView.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView batchName;
        MyListView itemListView;
    }
}
