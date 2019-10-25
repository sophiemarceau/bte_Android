package com.btetop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.AirBoardBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirBoardAdapter extends BaseAdapter {
    private List<AirBoardBean> mdata;
    private Activity context;
    private final LayoutInflater inflater;
    private listViewClick listener;

    public AirBoardAdapter(List<AirBoardBean> mdata, Activity context) {
        this.mdata = mdata;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setListener(listViewClick listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return mdata == null ? 0 : mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.adapter_over_draw, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        AirBoardBean itemBean = mdata.get(position);
        viewHolder.base.setText(itemBean.getBaseAsset());
        viewHolder.quote.setText("/"+itemBean.getQuoteAsset());
        viewHolder.exchage.setText(itemBean.getExchange());
        if (itemBean.getCostPrice() == 0.0) {
            viewHolder.center.setText("添加");
        } else {
            viewHolder.center.setText("" + itemBean.getCostPrice());
        }
        setOnClick(viewHolder, position);
        return convertView;
    }

    private void setOnClick(ViewHolder viewHolder, int position) {
        viewHolder.center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null) {
                    listener.centerClick(position);
                }

            }
        });

        viewHolder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.rightClick(position);
            }
        });

    }

    static class ViewHolder {
        @BindView(R.id.base_tv)
        TextView base;
        @BindView(R.id.quote_tv)
        TextView quote;
        @BindView(R.id.exchange_tv)
        TextView exchage;
        @BindView(R.id.center_tv)
        TextView center;
        @BindView(R.id.image_container_ll)
        LinearLayout llDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface listViewClick {

        void rightClick(int position);

        void centerClick(int position);
    }
}
