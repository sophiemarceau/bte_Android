package com.btetop.bean;

import java.util.ArrayList;
import java.util.List;

public class MergeZixuanMenuData {
    private List<ZiXuanResultBean> searchResult;
    private List<OptionalBean.ResultBean> zixuanResult;


    private List<ZiXuanResultBean> mergeResult;

    public MergeZixuanMenuData(List<ZiXuanResultBean> searchResult, List<OptionalBean.ResultBean> zixuanResult) {
        this.searchResult = searchResult;
        this.zixuanResult = zixuanResult;
        MergeData();
    }

    public List<ZiXuanResultBean> getMergeResult() {
        return mergeResult;
    }

    private void MergeData(){
        if (searchResult==null) {
            return;
        }
        if (zixuanResult==null) {
            return;
        }
        if (mergeResult!=null) {
            mergeResult.clear();
        }
        mergeResult=new ArrayList<>(searchResult.size());
        for (ZiXuanResultBean searchItem : searchResult) {
            ZiXuanResultBean bean=new ZiXuanResultBean();
            bean.setExchange(searchItem.getExchange());
            bean.setQuote(searchItem.getQuote());
            bean.setQuoteCn(searchItem.getQuoteCn());
            bean.setBase(searchItem.getBase());
            for (OptionalBean.ResultBean zixuanItem : zixuanResult) {
                if (searchItem.getExchange().equalsIgnoreCase(zixuanItem.getExchange())
                        && searchItem.getQuote().equalsIgnoreCase(zixuanItem.getQuote())
                        && searchItem.getBase().equalsIgnoreCase(zixuanItem.getBase())) {
                    bean.setStatus(true);

                }

            }
            mergeResult.add(bean);
        }

    }
}
