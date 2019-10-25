package com.btetop.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.GridItem;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<GridItem> {

    private final int gridPosition;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();


    public GridViewAdapter(Context context, int resource, ArrayList<GridItem> objects, int gridPosition) {
        super(context, resource, objects);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.mGridData = objects;
        this.gridPosition = gridPosition;

    }

    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }
    private int clickTemp = -1;
    //标识选择的Item

    public void setSelection(int position) {
        clickTemp = position;
    }

    public void setCallBack(int position, String title){
       // GridViewCallBackUtils.doCallBackMethod(position,title);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_text);
//            holder.imageView = (ImageView) convertView.findViewById(R.id.imgview_item);
            convertView.setTag(holder);
//            holder.textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    GridViewCallBackUtils.doCallBackMethod(position,mGridData.get(position).getTitle());
//                }
//            });
//            setCallBack(position,mGridData.get(position).getTitle());

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GridItem item = mGridData.get(position);
        holder.textView.setText(item.getTitle());
//        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        if (clickTemp == position) {
            holder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_65B0F3));
        } else {
            holder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_308CDD));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView textView;
    }

}
