package com.btetop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.ReplyListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostReplyDetailAdapter extends BaseAdapter {
    private List<ReplyListBean.PostReplyItemlistBean> mdata;
    private final LayoutInflater inflater;
    private Activity activity;

    public PostReplyDetailAdapter(List<ReplyListBean.PostReplyItemlistBean> mdata, Activity context) {
        this.mdata = mdata;
        this.activity=context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mdata==null?0:mdata.size();
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
            convertView = inflater.inflate(R.layout.adapter_shequ_reply_detail_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ReplyListBean.PostReplyItemlistBean bean = mdata.get(position);

//        Glide.with(activity)
//                .load(bean.getIcon())
//                .placeholder(R.mipmap.mine_top_logo)
//                .transform(new GlideCircleTransform(activity))
//                .into(viewHolder.img);
        viewHolder.nickName.setText(bean.getSendUserName()+"发送给"+bean.getReceiveUserName());
        viewHolder.discussConntent.setText(bean.getContent());

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.nickname)
        TextView nickName;
        @BindView(R.id.discussContent)
        TextView discussConntent;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
