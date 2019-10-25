package com.btetop.fragment;

import android.view.View;

import com.btetop.R;
import com.btetop.base.BaseFragment;

public class NestWebViewFragment extends BaseFragment {
    private String url;
    private String tagName;

    private CommonWebViewFragment webViewFragment;
    public NestWebViewFragment(){

    }
    public static NestWebViewFragment newInstance(String url,String tagName){
        NestWebViewFragment fragment=new NestWebViewFragment();
        fragment.url=url;
        fragment.tagName=tagName;
        return fragment;

    }
    public void reload(String url){

    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_nested_container;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() {
        this.webViewFragment =CommonWebViewFragment.newInstance(url,tagName,false);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, webViewFragment)
                .commit();
    }
    @Override
    public boolean statisticsFragment() {
        return false;
    }
}
