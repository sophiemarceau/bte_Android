package com.btetop.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.activity.CommonWebViewActivity;
import com.btetop.bean.MessageItemBean;
import com.btetop.utils.ToastUtils;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final Activity mActivity;
    private List<MessageItemBean.DataBean> mList;

    public MessageAdapter(Activity activity, List<MessageItemBean.DataBean> list) {
        mActivity = activity;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, null);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //将数据绑定到控件上
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageItemBean.DataBean bean = mList.get(position);
//        holder.imageView.setBackgroundResource(bean.itemImage);
        holder.title.setText(bean.getTitle());
        holder.time.setText(bean.getCreateTime());
        holder.content.setText(bean.getSummary());


        String redirectUrl = mList.get(position).getRedirectUrl();
        if (TextUtils.isEmpty(redirectUrl)) {
            holder.iv_message_url.setVisibility(View.GONE);
        } else {
            holder.iv_message_url.setVisibility(View.VISIBLE);
            holder.rl_item_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!android.text.TextUtils.isEmpty(redirectUrl)) {
                        Intent intent = new Intent(mActivity, CommonWebViewActivity.class);
                        intent.putExtra("url", redirectUrl);
                        intent.putExtra("showShare",false);
                        mActivity.startActivity(intent);
                    }

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    //下面两个方法提供给页面刷新和加载时调用
    public void add(List<MessageItemBean.DataBean> addMessageList) {
        //增加数据
        int position = mList.size();
        if (addMessageList.size() == 0) {
            ToastUtils.showShortToast("到最后了");
        } else if (position > 0) {
            mList.addAll(position, addMessageList);
            notifyItemInserted(position);
        }

//        notifyDataSetChanged();
    }

    public void refresh(List<MessageItemBean.DataBean> newList) {
        //刷新数据
        mList.removeAll(mList);
        mList.addAll(newList);
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_item_message;
        TextView title;
        TextView time;
        TextView content;
        ImageView iv_message_url;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_item_message = (RelativeLayout) itemView.findViewById(R.id.rl_item_message);
            title = (TextView) itemView.findViewById(R.id.tv_item_message_title);
            time = (TextView) itemView.findViewById(R.id.tv_item_message_time);
            content = (TextView) itemView.findViewById(R.id.tv_item_message_content);
            iv_message_url = (ImageView) itemView.findViewById(R.id.iv_message_url);
        }
    }
}

