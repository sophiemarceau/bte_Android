package com.btetop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.ShequListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShequListAdapter extends BaseAdapter {
    private List<ShequListBean.PostlistBean> mdata;
    private final LayoutInflater inflater;
    private BottomClickListener listener;


    public ShequListAdapter(List<ShequListBean.PostlistBean> mdata, Activity activity) {
        this.mdata = mdata;
        this.inflater = LayoutInflater.from(activity);
    }

    public void setListener(BottomClickListener listener) {
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
            convertView = inflater.inflate(R.layout.adapter_sheque_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ShequListBean.PostlistBean bean = mdata.get(position);
        viewHolder.content.setText(bean.getContent());
        viewHolder.share.setText(bean.getShareCount());
        viewHolder.discuss.setText(bean.getCommentCount());
        viewHolder.thumb.setText(bean.getLikeCount());

        viewHolder.share.setText(bean.getShareCount());
        viewHolder.shareContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onShareClick(position);
                    bean.addShareCount();
                    viewHolder.share.setText(bean.getShareCount());
                }

            }
        });
        viewHolder.discussContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDiscussClick(position);
                }
            }
        });
        if (bean.getHasLike()) {
            viewHolder.thumb.setSelected(true);

        } else {
            viewHolder.thumb.setSelected(false);
            viewHolder.thumbContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && !bean.getHasLike()) {
                        listener.onThumbClick(position);
                        bean.addLikeCount();
                        viewHolder.thumb.setText(bean.getLikeCount());
                        viewHolder.thumb.setSelected(true);
                        bean.addHasLike();
                    }
                }
            });
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.share)
        TextView share;
        @BindView(R.id.share_container)
        RelativeLayout shareContainer;
        @BindView(R.id.discuss)
        TextView discuss;
        @BindView(R.id.discussContainer)
        RelativeLayout discussContainer;
        @BindView(R.id.thumb)
        TextView thumb;
        @BindView(R.id.thumbContainer)
        RelativeLayout thumbContainer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface BottomClickListener {
        void onShareClick(int position);

        void onDiscussClick(int position);

        void onThumbClick(int position);

    }
}
