package com.btetop.dialog;

/**
 * Created by ban on 2018/4/25.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.widget.MyListView;

import java.util.List;

public class SpinnerPopWindow<T> extends PopupWindow {
    private final Context context;
    private LayoutInflater inflater;
    private MyListView mListView;
    private List<T> list;
    private MyAdapter mAdapter;
    private int selectedPosition = 0;


    public SpinnerPopWindow(Context context, List<T> list, AdapterView.OnItemClickListener clickListener) {
        super(context);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        init(clickListener);
    }

    private void init(AdapterView.OnItemClickListener clickListener) {
        View view = inflater.inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (MyListView) view.findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(clickListener);

        mListView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setDivider(null);
        //mListView.setSelector(context.getResources().getDrawable(R.drawable.home_coin_selector));

    }

    public void notityListView() {
        mAdapter.notifyDataSetChanged();
    }

    //这句是把listview的点击position,传递过来
    public void clearSelection(int position) {
        selectedPosition = position;
    }

    private class MyAdapter extends BaseAdapter {
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
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.home_coin_list_item, null);
                holder.tvName = (TextView) convertView.findViewById(R.id.coin_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(getItem(position).toString());

            //判断点击了哪个item,然后判断，让他的textview变色
            if (selectedPosition == position) {
                holder.tvName.setBackgroundColor(Color.parseColor("#5CACF3"));
            } else {
                holder.tvName.setBackgroundColor(Color.parseColor("#308CDD"));
            }
            return convertView;
        }
    }

    private class ViewHolder {
        private TextView tvName;
    }
}
