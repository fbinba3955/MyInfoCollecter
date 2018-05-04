package com.kiana.sjt.myinfocollecter.learn.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.learn.model.JpWebModel;
import com.kiana.sjt.myinfocollecter.learn.vmodel.LearnHomeListener;
import com.kiana.sjt.myinfocollecter.learn.vmodel.LearnHomeVModel;
import com.kiana.sjt.myinfocollecter.utils.viewpagercards.CardItem;
import com.kiana.sjt.myinfocollecter.utils.viewpagercards.CardPagerAdapter;
import com.kiana.sjt.myinfocollecter.utils.viewpagercards.ShadowTransformer;
import com.kiana.sjt.myinfocollecter.utils.webview.ContentWebView;

import java.util.List;

/**
 * 学习-首页
 * Created by taodi on 2018/4/27.
 */

public class LearnHomeActivity extends MainActivity implements LearnHomeListener{

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;

    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_home);
        initView();
        initAfter();
    }

    private void initView() {
        setTranslateToolbar();
        setToolbarTitle("学习");
        initToolbar();
        setBackNav();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(getString(R.string.hj), getString(R.string.hj_detail), getString(R.string.look), listener));
//        mCardAdapter.addCardItem(new CardItem(R.string.cardother, R.string.cardother_des, R.string.look, listener));
//        mCardAdapter.addCardItem(new CardItem(R.string.cardother, R.string.cardother_des, R.string.look, listener));
//        mCardAdapter.addCardItem(new CardItem(R.string.cardother, R.string.cardother_des, R.string.look, listener));
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    private void initAfter() {
        LearnHomeVModel vModel = new LearnHomeVModel(this);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LearnHomeActivity.this, HjActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onRefreshCards(final List<JpWebModel> dataList) {
        for (int i=0;i<dataList.size();i++) {
            final int finalI = i;
            mCardAdapter.addCardItem(new CardItem(dataList.get(i).getTitle(),
                    dataList.get(i).getDescription(),
                    getString(R.string.look),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String title = dataList.get(finalI).getTitle();
                            String url = dataList.get(finalI).getUrl();
                            Intent intent = new Intent(LearnHomeActivity.this, ContentWebView.class);
                            intent.putExtra(ContentWebView.PARAM_URL, url);
                            intent.putExtra(ContentWebView.PARAM_TITLE, title);
                            startActivity(intent);
                        }
                    }));
        }
        mViewPager.setAdapter(mCardAdapter);
    }
}
