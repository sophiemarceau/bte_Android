package com.btetop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.UserCurrentLotBean.UserStrategyData.UserStrategyDateDetails.UserStrategySmallDetails;
import com.btetop.utils.FormatUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/25.
 */

public class MyMineStrategyAdapter extends BaseAdapter {
    private Context context;
    private String flag;
    private String type;
    private ArrayList<UserStrategySmallDetails> detailsList;
    private double ror;

    public MyMineStrategyAdapter(Context context, String flag,
                                 ArrayList<UserStrategySmallDetails> detailsList,
                                 String type, double ror) {
        this.context = context;
        this.flag = flag;
        this.detailsList = detailsList;
        this.type = type;
        this.ror = ror;
    }

    @Override
    public int getCount() {
        return detailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if ("current".equals(flag)) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.mine_details_list_item, null);
                holder = new ViewHolder();
                holder.tvAmount = convertView.findViewById(R.id.tv_investment_amount);
                holder.tvAmountType = convertView.findViewById(R.id.tv_investment_amount_type);
                holder.tvUnits = convertView.findViewById(R.id.tv_floating_units);
                holder.tvUnitsType = convertView.findViewById(R.id.tv_whole_title);
                holder.tvNetValue = convertView.findViewById(R.id.tv_whole_value);
                holder.tvStatus = convertView.findViewById(R.id.tv_status);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //单位||币种
            holder.tvAmountType.setText("本金" + " (" + type + ")");
            holder.tvUnitsType.setText("当前额" + " (" + type + ")");
            //跟随本金
            holder.tvAmount.setText(FormatUtil.doubleTrans1(detailsList.get(position).getPrincipal()));
            //当前额
            String netValues = FormatUtil.doubleTrans1(detailsList.get(position).getAmount());
            if (netValues.equals("0")) {
                holder.tvNetValue.setText("--");
            } else {
                holder.tvNetValue.setText(netValues);
            }
            //收益
            String untis = FormatUtil.doubleTrans1(detailsList.get(position).getRor());
            if (untis.equals("0")) {
                holder.tvUnits.setText("--");
            } else {
                holder.tvUnits.setText(untis + "%");
            }

            //跟随状态
            holder.tvStatus.setText(detailsList.get(position).getStatus());

        } else if ("settle".equals(flag)) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.mine_details_settle_list_item, null);
                holder = new ViewHolder();
                holder.tvSettleUnits = convertView.findViewById(R.id.tv_settle_units);
                holder.tvSettleUnitsType = convertView.findViewById(R.id.tv_settle_units_type);
                holder.tvSettleNetValue = convertView.findViewById(R.id.tv_settle_value);
                holder.tvSettleNetValueType = convertView.findViewById(R.id.tv_settle_value_type);
                holder.tvSettleAmount = convertView.findViewById(R.id.tv_settle_amount);
                holder.tvSettleStatus = convertView.findViewById(R.id.tv_settle_status);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //单位||币种
            holder.tvSettleUnitsType.setText("本金" + " (" + type + ")");
            holder.tvSettleNetValueType.setText("赎回额" + " (" + type + ")");
            //赎回本金
            holder.tvSettleUnits.setText(FormatUtil.doubleTrans1(detailsList.get(position).getPrincipal()));
            //赎回额
            String netValues = FormatUtil.doubleTrans1(detailsList.get(position).getAmount());
            if (netValues.equals("0")) {
                holder.tvSettleNetValue.setText("--");
            } else {
                holder.tvSettleNetValue.setText(netValues);
            }
            //赎回收益
            String amount = FormatUtil.doubleTrans1(detailsList.get(position).getRor());
            if (amount.equals("0")) {
                holder.tvSettleAmount.setText("--");
            } else {
                holder.tvSettleAmount.setText(amount + "%");
            }
            //跟随状态
            holder.tvSettleStatus.setText(detailsList.get(position).getStatus());

        }

        return convertView;
    }

    class ViewHolder {
        TextView tvAmount;//数量
        TextView tvAmountType;//数量币种
        TextView tvUnits;//份额
        TextView tvUnitsType;//份额币种
        TextView tvNetValue;//净值
        TextView tvStatus;//状态

        TextView tvSettleAmount;//数量
        TextView tvSettleUnitsType;//份额币种
        TextView tvSettleUnits;//份额
        TextView tvSettleNetValue;//净值
        TextView tvSettleNetValueType;//净值
        TextView tvSettleStatus;//状态

    }
}
