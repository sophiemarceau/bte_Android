package com.btetop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.HomeMarketNewsBean.HomeMarketNewsData;
import com.btetop.config.Constant;
import com.btetop.monitor.M;
import com.btetop.widget.ExpandableTextView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<HomeMarketNewsData> list;
    private SparseBooleanArray mConvertTextCollapsedStatus = new SparseBooleanArray();

    public HomeNewsAdapter(Context mContext, List<HomeMarketNewsData> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_news_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        int size = list.size();
        String title = list.get(position % size).getTitle();
        holder.tv_title.setText(title);
        String date = list.get(position % size).getDate();
        holder.tv_date.setText(date);
        String newsContent = list.get(position % size).getContent();
        holder.expandableTextView.setConvertText(mConvertTextCollapsedStatus, position % size, newsContent,holder.tv_title);

        //标题的收缩
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextUtils.TruncateAt ellipsize = holder.tv_title.getEllipsize();
                if (ellipsize != null
                        && !TextUtils.isEmpty(ellipsize.name())
                        && ellipsize.name().equals("END")) {
                    holder.tv_title.setEllipsize(null);
                    holder.tv_title.setMaxLines(3);
                } else {
                    holder.tv_title.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                    holder.tv_title.setMaxLines(1);
                }

                M.monitor().onEvent(mContext, Constant.HOME_MARKET_NEWS);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_date;
        private ExpandableTextView expandableTextView;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.news_title);
            tv_date = itemView.findViewById(R.id.news_date);
            expandableTextView = itemView.findViewById(R.id.news_expand_text);
        }
    }
}
