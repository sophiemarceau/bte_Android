package com.btetop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.GridItem;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class GridItemPopupAdapter extends BaseAdapter {

    private Context context;
    private List<GridItem> data = new ArrayList<GridItem>();

    public GridItemPopupAdapter(Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_gv_time_index, null);
            viewHolder.tv1 = (TextView) view.findViewById(R.id.item_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (data != null && data.size() > 0) {
            viewHolder.tv1.setText(data.get(i).getTitle());
            if (data.get(i).isChecked()){
                viewHolder.tv1.setBackgroundResource(R.drawable.goods_attr_selected_shape);
                viewHolder.tv1.setTextColor(Color.WHITE);
            } else {
                viewHolder.tv1.setBackgroundResource(R.drawable.goods_attr_unselected_shape);
                viewHolder.tv1.setTextColor(Color.WHITE);
            }
        }
        return view;
    }

    static class ViewHolder {
        public TextView tv1;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(List<GridItem> tempData) {
        if (tempData == null || tempData.size() == 0) {
            return;
        }
        data.clear();
        data.addAll(tempData);
        notifyDataSetChanged();
    }
}
