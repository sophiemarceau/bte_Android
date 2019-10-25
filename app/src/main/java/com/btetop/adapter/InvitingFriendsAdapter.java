package com.btetop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.InviteFriendsBean.InviteFriendsData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/11.
 */

public class InvitingFriendsAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<InviteFriendsData> list;

    public InvitingFriendsAdapter(Context mContext, ArrayList<InviteFriendsData> list) {
        this.mContext = mContext;
        this.list = list;
    }

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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inviting_friend_list_item, null);
            holder = new ViewHolder();
            holder.tel = convertView.findViewById(R.id.friends_tel);
            holder.date = convertView.findViewById(R.id.friends_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String friendTel = list.get(position).getTel();
        if (!"".equals(friendTel) && null != friendTel) {
            holder.tel.setText(friendTel);
        }
        String friendDate = list.get(position).getDate();
        if (!"".equals(friendDate) && null != friendDate) {
            holder.date.setText(friendDate);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tel;
        TextView date;
    }
}
