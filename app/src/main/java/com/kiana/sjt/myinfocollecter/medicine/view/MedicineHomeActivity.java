package com.kiana.sjt.myinfocollecter.medicine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.learn.view.HjActivity;
import com.kiana.sjt.myinfocollecter.utils.viewpagercards.CardItem;
import com.kiana.sjt.myinfocollecter.utils.viewpagercards.CardPagerAdapter;
import com.kiana.sjt.myinfocollecter.utils.viewpagercards.ShadowTransformer;

/**
 * 医药类型信息主页
 * Created by taodi on 2018/4/24.
 */

public class MedicineHomeActivity extends MainActivity{

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;

    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_home);
        initView();
    }

    private void initView() {
        setTranslateToolbar();
        setToolbarTitle("医药信息");
        initToolbar();
        setBackNav();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.njszrl, R.string.njszrl_des, R.string.look, listener));
        mCardAdapter.addCardItem(new CardItem(R.string.cardother, R.string.cardother_des, R.string.look, listener));
        mCardAdapter.addCardItem(new CardItem(R.string.cardother, R.string.cardother_des, R.string.look, listener));
        mCardAdapter.addCardItem(new CardItem(R.string.cardother, R.string.cardother_des, R.string.look, listener));
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MedicineHomeActivity.this, NjszNewsActivity.class);
            startActivity(intent);
        }
    };
}
