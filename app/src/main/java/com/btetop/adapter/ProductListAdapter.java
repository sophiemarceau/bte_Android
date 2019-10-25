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
import com.btetop.bean.ProductListBean.ProductListData.ProductListDetails;
import com.btetop.utils.FormatUtil;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ProductListAdapter extends BaseAdapter {
    private Context context;
    private List<ProductListDetails> listDetails;

    public ProductListAdapter(Context context, List<ProductListDetails> listDetails) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list_item, null);
            holder = new ViewHolder();
            holder.mCeLueImg = convertView.findViewById(R.id.product_fragment_ce_lue_img);
            holder.mRisk = convertView.findViewById(R.id.product_fragment_risk_img);
            holder.mCeLueTitle = convertView.findViewById(R.id.product_fragment_ce_lue_title);
            holder.mCelueContent = convertView.findViewById(R.id.product_fragment_ce_lue_content);
            holder.mCelueRot = convertView.findViewById(R.id.product_fragment_yield);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int riskValue = listDetails.get(position).getRiskValue();
        int riskLevel = listDetails.get(position).getRiskLevel();
        if (riskLevel == 2) {
            holder.mRisk.setBackgroundResource(R.drawable.home_risk_orange_bg);
            holder.mRisk.setTextColor(context.getResources().getColor(R.color.color_FF6B28));
        } else if (riskLevel == 3) {
            holder.mRisk.setBackgroundResource(R.drawable.home_risk_red_bg);
            holder.mRisk.setTextColor(context.getResources().getColor(R.color.color_FE413F));
        } else if (riskLevel == 1) {
            holder.mRisk.setBackgroundResource(R.drawable.home_risk_green_bg);
            holder.mRisk.setTextColor(context.getResources().getColor(R.color.color_A3D97D));
        }
        holder.mRisk.setText(String.valueOf(riskValue));

        String type = listDetails.get(position).getType();
        if ("日内策略".equals(type)) {
            Glide.with(context).load(R.mipmap.celue_rinei).into(holder.mCeLueImg);
        } else if ("中期策略".equals(type)) {
            Glide.with(context).load(R.mipmap.celue_zhongqi).into(holder.mCeLueImg);
        } else if ("长期策略".equals(type)) {
            Glide.with(context).load(R.mipmap.celue_changqi).into(holder.mCeLueImg);
        }
        String name = listDetails.get(position).getName();
        if (!TextUtils.isEmpty(name)) {
            holder.mCeLueTitle.setText(name);
        }
        String desc = listDetails.get(position).getDesc();
        if (!TextUtils.isEmpty(desc)) {
            holder.mCelueContent.setText(desc);
        }
        double rot = listDetails.get(position).getRor();
        if (rot > 0.0) {
            holder.mCelueRot.setTextColor(context.getResources().getColor(R.color.color_228B22));
            holder.mCelueRot.setText("+" + FormatUtil.doubleTrans1(rot) + "%");
        } else if (rot < 0.0) {
            holder.mCelueRot.setTextColor(context.getResources().getColor(R.color.color_FF4040));
            holder.mCelueRot.setText(FormatUtil.doubleTrans1(rot) + "%");
        } else if (rot == 0.0) {
            holder.mCelueRot.setTextColor(context.getResources().getColor(R.color.color_626A75));
            holder.mCelueRot.setText(FormatUtil.doubleTrans1(rot) + "%");
        }
        return convertView;
    }

    class ViewHolder {
        private TextView mRisk;
        private ImageView mCeLueImg;
        private TextView mCeLueTitle;
        private TextView mCelueContent;
        private TextView mCelueRot;
    }
}
