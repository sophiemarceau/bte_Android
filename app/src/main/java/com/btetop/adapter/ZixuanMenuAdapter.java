package com.btetop.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.OptionalBean;
import com.btetop.utils.TypeFaceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZixuanMenuAdapter extends BaseAdapter {
    private static final String TAG = "ZixuanMenuAdapter";
    private static final int NORMALTYPE = 0;
    private static final int LASTTYPE = 1;
    private List<OptionalBean.ResultBean> mdata;
    private final LayoutInflater inflater;
    private Drawable redBg;
    private Drawable blueBg;


    public ZixuanMenuAdapter(List<OptionalBean.ResultBean> mdata, Activity context) {
        this.mdata = mdata;
        this.inflater = LayoutInflater.from(context);
        redBg = ContextCompat.getDrawable(context, R.drawable.bg_red_radio);
        blueBg = ContextCompat.getDrawable(context, R.drawable.bg_green_radio);
    }

    @Override
    public int getCount() {
        return mdata == null ? 0 : mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolderNormal viewHolderNormal=null;
        ViewHolderLast viewHolderLast=null;

        switch (type) {
            case NORMALTYPE:
                Log.e(TAG, "getView: normal");
                if (convertView != null && convertView.getTag(R.string.zixuan_normal_type)!=null) {
                    viewHolderNormal = (ViewHolderNormal) convertView.getTag(R.string.zixuan_normal_type);
                } else {
                    convertView = inflater.inflate(R.layout.adapter_zixuan_item, parent, false);
                    viewHolderNormal = new ViewHolderNormal(convertView);
                    convertView.setTag(R.string.zixuan_normal_type,viewHolderNormal);
                }
                break;
            case LASTTYPE:
                Log.e(TAG, "getView: last");
                if (convertView!=null&& convertView.getTag(R.string.zixuan_last_type)!=null) {
                    viewHolderLast=(ViewHolderLast)convertView.getTag(R.string.zixuan_last_type);
                }else {
                    convertView = inflater.inflate(R.layout.adapter_zixuan_last_item, parent, false);
                    viewHolderLast = new ViewHolderLast(convertView);
                    convertView.setTag(R.string.zixuan_last_type,viewHolderLast);
                }
                break;
        }

        switch (type) {
            case NORMALTYPE:
                renderNormalType(viewHolderNormal,position);
                break;
            case LASTTYPE:
                break;
        }

        return convertView;
    }


    private void renderNormalType(ViewHolderNormal viewHolderNormal, int position){
        OptionalBean.ResultBean bean = mdata.get(position);
        TypeFaceUtils.setTVTypeFace(null,viewHolderNormal.tvprice);
        viewHolderNormal.tvbase.setText(bean.getBase());
        viewHolderNormal.tvquote.setText(bean.getQuote());
        viewHolderNormal.tvexchange.setText(bean.getExchange());
        viewHolderNormal.tvchange.setBackground(bean.getFromatColorDrawable());
        viewHolderNormal.tvprice.setText(bean.getPrice());
        viewHolderNormal.tvcnyprice.setText(bean.getcnyPrice());
        viewHolderNormal.tvchange.setText(bean.getFormatChange());
        viewHolderNormal.tvturnover.setText(bean.getCnyAmount());

    }


    @Override
    public int getItemViewType(int position) {
        return mdata.get(position).isLast() ? LASTTYPE : NORMALTYPE;
    }

    static class ViewHolderNormal {
        @BindView(R.id.tvbase)
        TextView tvbase;
        @BindView(R.id.tvquote)
        TextView tvquote;
        @BindView(R.id.tvexchange)
        TextView tvexchange;
        @BindView(R.id.tvturnover)
        TextView tvturnover;
        @BindView(R.id.tvchange)
        TextView tvchange;
        @BindView(R.id.tvprice)
        TextView tvprice;
        @BindView(R.id.tvcnyprice)
        TextView tvcnyprice;

        ViewHolderNormal(View view) {
            ButterKnife.bind(this, view);
        }
    }


    static class ViewHolderLast {

        ViewHolderLast(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
