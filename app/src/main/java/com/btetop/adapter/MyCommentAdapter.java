package com.btetop.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.bean.CommentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCommentAdapter extends BaseAdapter {
    private List<CommentBean.MyCommentBean> mdata;
    private final LayoutInflater inflater;

    private final int mYelloColor;
    private final int mNormalColor;


    public MyCommentAdapter(List<CommentBean.MyCommentBean> mdata, Activity activity) {
        this.mdata = mdata;
        this.inflater = LayoutInflater.from(activity);
        this.mYelloColor=ContextCompat.getColor(BteTopApplication.getContext(),R.color.shequ_yello_color);
        this.mNormalColor=ContextCompat.getColor(BteTopApplication.getContext(),R.color.shequ_normal_color);
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
            convertView = inflater.inflate(R.layout.adapter_my_comment, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        CommentBean.MyCommentBean bean = mdata.get(position);
        viewHolder.sendUserName.setText(bean.getSendUserName());
        if (bean.getRead()==1) {
            viewHolder.content.setTextColor(mNormalColor);
        }else {
            //黄色
            viewHolder.content.setTextColor(mYelloColor);
        }
        viewHolder.content.setText(bean.getContent());
        viewHolder.reply.setText(bean.getLastReplyContent());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.sendUserName)
        TextView sendUserName;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.reply)
        TextView reply;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
