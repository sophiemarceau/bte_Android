package com.btetop.widget.floatprice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.AirBoardBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PriceAdapter extends BaseAdapter {

    private static final int threeItem = 0;
    private static final int towItem = 1;

    private List<AirBoardBean> mData;
    private Context context;
    private final LayoutInflater inflater;

    public PriceAdapter(List<AirBoardBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
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
        ViewHolderThree viewHolderThree = null;
        ViewHolderTwo viewHolderTwo = null;

        int type = getItemViewType(position);

        switch (type) {
            case threeItem:
                    convertView = inflater.inflate(R.layout.adapter_price_item_three, parent, false);
                    viewHolderThree = new ViewHolderThree(convertView);
//                    convertView.setTag(viewHolderThree);

                break;
            case towItem:
                    convertView = inflater.inflate(R.layout.adapter_price_item_two, parent, false);
                    viewHolderTwo = new ViewHolderTwo(convertView);
//                    convertView.setTag(viewHolderTwo);

                break;
        }



        AirBoardBean airBoardBean = mData.get(position);
        switch (type) {
            case threeItem:
                renderThreeType(viewHolderThree, airBoardBean);
                break;
            case towItem:
                renderTwoType(viewHolderTwo, airBoardBean);
                break;
        }
        return convertView;
    }

    private void renderTwoType(ViewHolderTwo viewholder, AirBoardBean airBoardBean) {

        int fromatColor = airBoardBean.getFromatColor();

        viewholder.price.setText(airBoardBean.getPrice());
        viewholder.price.setTextColor(fromatColor);
        viewholder.symbol.setText(airBoardBean.getBaseAsset() + "-" + airBoardBean.getExchange());
    }

    private void renderThreeType(ViewHolderThree viewholder, AirBoardBean airBoardBean) {
        int fromatColor = airBoardBean.getFromatColor();

        viewholder.price.setText(airBoardBean.getPrice());
        viewholder.price.setTextColor(fromatColor);
        viewholder.revenue.setTextColor(fromatColor);
        viewholder.revenue.setText(airBoardBean.getRevenueString());
        viewholder.symbol.setText(airBoardBean.getBaseAsset() + "-" + airBoardBean.getExchange());
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getRevenue() == null) {
            return towItem;
        } else {
            return threeItem;
        }
    }

    static class ViewHolderThree {
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.symbol)
        TextView symbol;
        @BindView(R.id.revenue)
        TextView revenue;

        public ViewHolderThree(View view) {
            ButterKnife.bind(this, view);
        }
    }


    static class ViewHolderTwo {
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.symbol)
        TextView symbol;

        public ViewHolderTwo(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
