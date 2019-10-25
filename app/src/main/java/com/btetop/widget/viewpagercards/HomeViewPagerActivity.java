package com.btetop.widget.viewpagercards;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.btetop.R;

public class HomeViewPagerActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private Button mButton;
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_viewpager_main);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mButton = (Button) findViewById(R.id.cardTypeBtn);
        ((CheckBox) findViewById(R.id.checkBox)).setOnCheckedChangeListener(this);
        mButton.setOnClickListener(this);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(123, 23));
        mCardAdapter.addCardItem(new CardItem(123, 23));
        mCardAdapter.addCardItem(new CardItem(123, 23));
        mCardAdapter.addCardItem(new CardItem(123, 23));
        mCardAdapter.addCardItem(new CardItem(123, 23));
        mCardAdapter.addCardItem(new CardItem(123, 23));
//        mFragmentCardAdapter = new CardFragmentPagerAdapter( getSupportFragmentManager(),
//                dpToPixels(2, this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onClick(View view) {
        if (!mShowingFragments) {
            mButton.setText("Views");
            mViewPager.setAdapter(mFragmentCardAdapter);
            mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        } else {
            mButton.setText("Fragments");
            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
        }

        mShowingFragments = !mShowingFragments;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }
}
