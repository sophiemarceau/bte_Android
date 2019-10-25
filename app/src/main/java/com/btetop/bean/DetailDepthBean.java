package com.btetop.bean;

import java.util.ArrayList;
import java.util.List;

public class DetailDepthBean {
    public static final String DEPTHFOOT="detail_depth_foot";

    /**
     * depth : [{"price":6386.6,"depth":0.0126,"type":"ASK","count":31960},{"price":6376.95,"depth":0.0111,"type":"ASK","count":27746},{"price":6366.82,"depth":0.0095,"type":"ASK","count":23370},{"price":6352.2,"depth":0.0072,"type":"ASK","count":7677},{"price":6341.84,"depth":0.0055,"type":"ASK","count":5828},{"price":6330.21,"depth":0.0037,"type":"ASK","count":3945},{"price":6318.41,"depth":0.0018,"type":"ASK","count":2262},{"price":6311.58,"depth":7.0E-4,"type":"ASK","count":1869},{"price":6310,"depth":5.0E-4,"type":"ASK","count":1699},{"price":6308.51,"depth":2.0E-4,"type":"ASK","count":1387},{"price":6303.14,"depth":-4.0E-4,"type":"BID","count":664},{"price":6298.43,"depth":-0.0011,"type":"BID","count":1434},{"price":6292.96,"depth":-0.002,"type":"BID","count":2447},{"price":6284.85,"depth":-0.0033,"type":"BID","count":3140},{"price":6270.64,"depth":-0.0055,"type":"BID","count":6915},{"price":6258.24,"depth":-0.0075,"type":"BID","count":8153},{"price":6235,"depth":-0.0112,"type":"BID","count":11574},{"price":6214.36,"depth":-0.0145,"type":"BID","count":29296},{"price":6193,"depth":-0.0178,"type":"BID","count":41071},{"price":6174.98,"depth":-0.0207,"type":"BID","count":46216}]
     * remark : *超级深度为huobi、binance、okex、bitfinex的btc/usdt交易对的深度合并
     */

    private String remark;
    private List<DepthBean> depth;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DepthBean> getDepth() {
        return depth;
    }

    public void setDepth(List<DepthBean> depth) {
        this.depth = depth;
    }

    public List<DepthBean> getAllItem() {
        ArrayList<DepthBean> beans = new ArrayList<>(depth.size());
        beans.addAll(depth);

        DepthBean bean = new DepthBean();
        bean.setType(DEPTHFOOT);
        bean.setRemark(remark);
        beans.add(bean);
        return beans;

    }


}
