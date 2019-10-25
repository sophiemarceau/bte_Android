package com.btetop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.btetop.R;
import com.btetop.bean.DetailDadanBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailDepthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEADER_VIEW = 0;//近7日大单卖出：2345个 近7日大单买入:12个
    private final static int HEADER_VIEW_1 = 1;//数量 大单卖出 价格 大单买入 数量
    private final static int HEADER_VIEW_1_CONTENT = 2;
    private final static int HEADER_VIEW_1_FOOT = 6;
    private final static int REMARK_VIEW = 3;//*超级深度为huobi、binance、okex、bitfinex的btc/usdt交易对的深度合并
    private final static int HEADER_VIEW_2 = 4;//时间 交易所 类型 价格 数量
    private final static int HEADER_VIEW_2_CONTENT = 5;


    private int mRedColor;
    private int mBlueColor;
    private int mGreyColor;

    private Context context;
    private List<DetailDadanBean.MergeDetailDadanBean> mData;

    private int halfScreenWidth;

    //静态存储优化性能
    private View headerView, headerView1, headerView1Content, remarkView, headerView2, headerView2Content,headerView2Foot;

    public DetailDepthAdapter(Context context, List<DetailDadanBean.MergeDetailDadanBean> itemlist) {
        this.context = context;
        mRedColor = ContextCompat.getColor(context, R.color.main_color_green);
        mBlueColor = ContextCompat.getColor(context, R.color.main_color_red);
        mGreyColor = ContextCompat.getColor(context, R.color.color_all_866);


        halfScreenWidth = (int) ((ScreenUtils.getScreenWidth() / 2) * 0.8);

        mData = itemlist;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            if (headerView == null)
                headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_dadan_header, parent, false);
            return new ViewHolderHeader(headerView);
        } else if (viewType == HEADER_VIEW_1) {
            if (headerView1 == null)
                headerView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_dadan_header1, parent, false);
            return new ViewHolderHeader1(headerView1);
        } else if (viewType == HEADER_VIEW_1_CONTENT) {
            headerView1Content = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_dadan_header1_content, parent, false);
            return new ViewHolderHeader1Content(headerView1Content);
        } else if (viewType == REMARK_VIEW) {
            remarkView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_dadan_remark, parent, false);
            return new ViewHolderRemark(remarkView);
        } else if (viewType == HEADER_VIEW_2) {
            headerView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_dadan_header2, parent, false);
            return new ViewHolderHeader2(headerView2);
        } else if (viewType == HEADER_VIEW_2_CONTENT) {
            headerView2Content = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_dadan_header2_content, parent, false);
            return new ViewHolderHeader2Content(headerView2Content);
        } else if(viewType==HEADER_VIEW_1_FOOT){
            headerView2Foot = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_dadan_header1_foot, parent, false);
            return new ViewHolderHeader1Foot(headerView2Foot);
        }else{
            throw new RuntimeException("The type has to be HEADER_VIEW or HEADER_VIEW_1");
        }

    }

    @Override
    public int getItemViewType(int position) {

        String type = mData.get(position).getRenderStyle();
        switch (type) {
            case DetailDadanBean.RENDER_HEADER1:
                return HEADER_VIEW;
            case DetailDadanBean.RENDER_HEADER2:
                return HEADER_VIEW_1;
            case DetailDadanBean.RENDER_HEADER2_DETAIL:
                return HEADER_VIEW_1_CONTENT;
            case DetailDadanBean.RENDER_REMARK:
                return REMARK_VIEW;
            case DetailDadanBean.RENDER_HEADER3:
                return HEADER_VIEW_2;
            case DetailDadanBean.RENDER_HEADER3_DETAIL:
                return HEADER_VIEW_2_CONTENT;
            case DetailDadanBean.RENDER_HEADER2_DETAIL_FOOT:
                return HEADER_VIEW_1_FOOT;
            default:
                throw new RuntimeException("The type has to be HEADER_VIEW or HEADER_VIEW_1");

        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER_VIEW:
                initHeaderLayout((ViewHolderHeader) holder, position);
                break;
            case HEADER_VIEW_1:
                initHeader1Layout((ViewHolderHeader1) holder, position);
                break;
            case HEADER_VIEW_1_CONTENT:
                initHeader1ContentLayout((ViewHolderHeader1Content) holder, position);
//                initRightLayout((ViewHolderRight) holder, position);
                break;
            case REMARK_VIEW:
                initRemarkLayout((ViewHolderRemark)holder,position);
//                initFootLayout((ViewHolderFoot) holder, position);
                break;
            case HEADER_VIEW_2:
//                initLeftLayout((ViewHolderLeft) holder, position);
                break;
            case HEADER_VIEW_2_CONTENT:
                initHeader2ContentLayout((ViewHolderHeader2Content)holder, position);
                break;
            case HEADER_VIEW_1_FOOT:
                initHeader1FootLayout((ViewHolderHeader1Foot)holder,position);
                break;
            default:
                break;
        }

    }

    private void initHeader1FootLayout(ViewHolderHeader1Foot holder, int position) {
        holder.price11.setText(mData.get(position).getHeader1ContentPrice());

    }


    private void initHeader2ContentLayout(ViewHolderHeader2Content holder, int position) {
        DetailDadanBean.MergeDetailDadanBean bean = mData.get(position);
        holder.time1.setText(bean.getTime1String());
        holder.time2.setText(bean.getTime2String());
        holder.exchange.setText(bean.getExchange());
        String directionDesc = bean.getDirectionName();
        int buyTypeColor=mBlueColor;
        if ("sell".equals(directionDesc)) {
            buyTypeColor=mRedColor;
        }else if ("buy".equals(directionDesc)){
            buyTypeColor=mBlueColor;
        }
        holder.buyOrSell.setTextColor(buyTypeColor);
        holder.buyOrSell.setText(bean.getDirectionDesc());

        holder.price.setText(bean.getHeader2ContentPrice());
//        holder.count.setText(bean.getCount());
        holder.countPrice.setText(bean.getHeader2ContentCountPrice());


    }

    private void initRemarkLayout(ViewHolderRemark holder, int position) {
        holder.tvRemakr.setText(mData.get(position).getMarker());
    }

    private void initHeader1ContentLayout(ViewHolderHeader1Content holder, int position) {
        DetailDadanBean.MergeDetailDadanBean bean = mData.get(position);


        //region left
        holder.leftCount.setText(""+bean.getHeader2DetailAskAmountString());
        //动态设置设置宽高
        int  heigt= SizeUtils.dp2px(12);
        int leftWidth= (int) (halfScreenWidth*(bean.getHeader2DetailAskAmount()/bean.getHeader2DetailMax()));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(leftWidth, heigt);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        holder.leftProgress.setLayoutParams(layoutParams);

        //endregion

        holder.price.setText(""+bean.getHeader1ContentPrice());

        //region right
        holder.rightCount.setText(""+bean.getHeader2DetailBidAmountString());

        int rightWidth= (int) (halfScreenWidth*(bean.getHeader2DetailBidAmount()/bean.getHeader2DetailMax()));
        RelativeLayout.LayoutParams rightLayoutParams = new RelativeLayout.LayoutParams(rightWidth, heigt);
        rightLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        holder.rightProgress.setLayoutParams(rightLayoutParams);
        //endregion

    }

    private void initHeader1Layout(ViewHolderHeader1 holder, int position) {

    }


    private void initHeaderLayout(ViewHolderHeader holder, int pos) {
        DetailDadanBean.MergeDetailDadanBean bean = mData.get(pos);

        SpannableStringBuilder spannableStringBuilderAsk = new SpanUtils().append("近7日大单卖出：").setFontSize(12, true).setForegroundColor(mGreyColor)
                .append("$"+bean.getHeader1AskString()).setFontSize(12, true).setForegroundColor(mRedColor)
                .create();
        holder.leftTitle.setText(spannableStringBuilderAsk);

        SpannableStringBuilder spannableStringBuilderBid = new SpanUtils().append("近7日大单买入：").setFontSize(12, true).setForegroundColor(mGreyColor)
                .append("$"+bean.getHeader1BidString()).setFontSize(12, true).setForegroundColor(mBlueColor)
                .create();

        holder.rightTitle.setText(spannableStringBuilderBid);

        //progress
        int screenWidth = ScreenUtils.getScreenWidth();
        int bgProgressWidth = (int) (screenWidth * 0.9);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bgProgressWidth, SizeUtils.dp2px(6));
        layoutParams.gravity= Gravity.CENTER;
        holder.rlProgressAsk.setLayoutParams(layoutParams);

        int topProgressWidth = (int) (bgProgressWidth * bean.getHeaer1Bid() / (bean.getHeader1Ask() + bean.getHeaer1Bid()));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(topProgressWidth, SizeUtils.dp2px(6));
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        holder.viewProgressBid.setLayoutParams(params);


    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolderHeader extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_left_title)
        TextView leftTitle;
        @BindView(R.id.tv_right_title)
        TextView rightTitle;
        @BindView(R.id.progress_ask)
        RelativeLayout rlProgressAsk;
        @BindView(R.id.progress_bid)
        View viewProgressBid;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class ViewHolderHeader1 extends RecyclerView.ViewHolder {
        public ViewHolderHeader1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class ViewHolderHeader1Content extends RecyclerView.ViewHolder {
        @BindView(R.id.leftCount)
        TextView leftCount;
        @BindView(R.id.left_progress)
        View leftProgress;
        @BindView(R.id.rightCount)
        TextView rightCount;
        @BindView(R.id.right_progress)
        View rightProgress;
        @BindView(R.id.price)
        TextView price;

        public ViewHolderHeader1Content(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

     static class ViewHolderHeader1Foot extends RecyclerView.ViewHolder {
        @BindView(R.id.price_hi)
        TextView price11;

        public ViewHolderHeader1Foot(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class ViewHolderRemark extends RecyclerView.ViewHolder {
        @BindView(R.id.remark)
        TextView tvRemakr;

        public ViewHolderRemark(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolderHeader2 extends RecyclerView.ViewHolder {
        public ViewHolderHeader2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolderHeader2Content extends RecyclerView.ViewHolder {
        @BindView(R.id.time1)
        TextView time1;
        @BindView(R.id.time2)
        TextView time2;
        @BindView(R.id.exchange)
        TextView exchange;
        @BindView(R.id.buyoOrSell)
        TextView buyOrSell;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.countPrice)
        TextView countPrice;

        public ViewHolderHeader2Content(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
