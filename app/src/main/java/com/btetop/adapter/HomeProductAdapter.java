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
import com.btetop.bean.ProductHomeBean;
import com.btetop.utils.FormatUtil;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ouyou on 2018/6/19.
 */

public class HomeProductAdapter extends BaseAdapter {
    private Context context;
    private List<ProductHomeBean.ProductHomeData> listDetails;

    public HomeProductAdapter(Context context, List<ProductHomeBean.ProductHomeData> listDetails) {
        this.context = context;
        this.listDetails = listDetails;
    }

    @Override
    public int getCount() {
        return listDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return listDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.home_product_item, null);
            holder.celueIcon = convertView.findViewById(R.id.home_fragment_ce_lue_img);
            holder.celueTitle = convertView.findViewById(R.id.home_fragment_ce_lue_title);
            holder.ceLueContent = convertView.findViewById(R.id.home_fragment_ce_lue_content);
            holder.celuerisk = convertView.findViewById(R.id.home_fragment_risk_img);
            holder.celueyield = convertView.findViewById(R.id.home_fragment_yield);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int riskValue = listDetails.get(position).getRiskValue();
        int riskLevel = listDetails.get(position).getRiskLevel();
        if (riskLevel == 2) {
            holder.celuerisk.setBackgroundResource(R.drawable.home_risk_orange_bg);
            holder.celuerisk.setTextColor(context.getResources().getColor(R.color.color_FF6B28));
        } else if (riskLevel == 3) {
            holder.celuerisk.setBackgroundResource(R.drawable.home_risk_red_bg);
            holder.celuerisk.setTextColor(context.getResources().getColor(R.color.color_FE413F));
        } else if (riskLevel == 1) {
            holder.celuerisk.setBackgroundResource(R.drawable.home_risk_green_bg);
            holder.celuerisk.setTextColor(context.getResources().getColor(R.color.color_A3D97D));
        }
        holder.celuerisk.setText(String.valueOf(riskValue));

        String type = listDetails.get(position).getType();
        if ("日内策略".equals(type)) {
            Glide.with(context).load(R.mipmap.celue_rinei).into(holder.celueIcon);
        } else if ("中期策略".equals(type)) {
            Glide.with(context).load(R.mipmap.celue_zhongqi).into(holder.celueIcon);
        } else if ("长期策略".equals(type)) {
            Glide.with(context).load(R.mipmap.celue_changqi).into(holder.celueIcon);
        }


        String name = listDetails.get(position).getName();
        if (!TextUtils.isEmpty(name)) {
            holder.celueTitle.setText(name);
        }
        String desc = listDetails.get(position).getDesc();
        if (!TextUtils.isEmpty(desc)) {
            holder.ceLueContent.setText(desc);
        }
        double rot = listDetails.get(position).getRor();
        if (rot > 0.0) {
            holder.celueyield.setTextColor(context.getResources().getColor(R.color.color_228B22));
            holder.celueyield.setText("+" + FormatUtil.doubleTrans1(rot) + "%");
        } else if (rot < 0.0) {
            holder.celueyield.setTextColor(context.getResources().getColor(R.color.color_FF4040));
            holder.celueyield.setText(FormatUtil.doubleTrans1(rot) + "%");
        } else if (rot == 0.0) {
            holder.celueyield.setTextColor(context.getResources().getColor(R.color.color_626A75));
            holder.celueyield.setText(FormatUtil.doubleTrans1(rot) + "%");
        }

        return convertView;
    }


    class ViewHolder {
        ImageView celueIcon;
        TextView celueTitle;
        TextView ceLueContent;
        TextView celuerisk;
        TextView celueyield;
    }
}
