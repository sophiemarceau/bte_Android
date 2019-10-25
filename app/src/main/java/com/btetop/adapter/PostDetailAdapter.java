package com.btetop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.PostDetailBean;
import com.btetop.utils.GlideCircleTransform;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailAdapter extends BaseAdapter {
    private List<PostDetailBean.PostReplyBean> mdata;
    private final LayoutInflater inflater;
    private Activity activity;
    private OnClickReply2ReplyListener listener;

    public PostDetailAdapter(List<PostDetailBean.PostReplyBean> mdata, Activity context) {
        this.mdata = mdata;
        this.activity=context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setListener(OnClickReply2ReplyListener listener) {
        this.listener = listener;
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
            convertView = inflater.inflate(R.layout.adapter_shequ_detail_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        PostDetailBean.PostReplyBean bean = mdata.get(position);

        Glide.with(activity)
                .load(bean.getIcon())
                .placeholder(R.mipmap.mine_top_logo)
                .transform(new GlideCircleTransform(activity))
                .into(viewHolder.img);
        viewHolder.nickName.setText(bean.getUserName());
        viewHolder.discussConntent.setText(bean.getContent());
        if (bean.getPostReplyItemList()==null || bean.getPostReplyItemList().size()==0) {
            viewHolder.reply2Reply.setVisibility(View.GONE);
        }else {
            viewHolder.reply2Reply.setVisibility(View.VISIBLE);
            viewHolder.reply2Reply.setText("查看全部"+bean.getPostReplyItemList().size()+"条回复 >");
            viewHolder.reply2Reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null) {
                        listener.onClickReplay(bean.getId());
                    }
                }
            });
        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.nickname)
        TextView nickName;
        @BindView(R.id.discussContent)
        TextView discussConntent;
        @BindView(R.id.reply2reply)
        TextView reply2Reply;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnClickReply2ReplyListener{
        void onClickReplay(int id);
    }

}
