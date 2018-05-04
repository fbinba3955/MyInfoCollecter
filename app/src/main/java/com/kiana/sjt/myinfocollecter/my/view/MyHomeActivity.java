package com.kiana.sjt.myinfocollecter.my.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.learn.model.JpWebModel;
import com.kiana.sjt.myinfocollecter.learn.view.HjActivity;
import com.kiana.sjt.myinfocollecter.learn.view.LearnHomeActivity;
import com.kiana.sjt.myinfocollecter.learn.vmodel.LearnHomeVModel;
import com.kiana.sjt.myinfocollecter.my.vmodel.MyHomeListener;
import com.kiana.sjt.myinfocollecter.my.vmodel.MyHomeVModel;
import com.kiana.sjt.myinfocollecter.utils.viewpagercards.CardItem;
import com.kiana.sjt.myinfocollecter.utils.viewpagercards.CardPagerAdapter;
import com.kiana.sjt.myinfocollecter.utils.viewpagercards.ShadowTransformer;
import com.kiana.sjt.myinfocollecter.utils.webview.ContentWebView;

import java.util.List;

/**
 * Created by taodi on 2018/5/4.
 */

public class MyHomeActivity extends MainActivity implements MyHomeListener {

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;

    private ShadowTransformer mCardShadowTransformer;

    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_home);
        initView();
        initAfter();
    }

    private void initView() {
        setTranslateToolbar();
        setToolbarTitle("自娱自乐");
        initToolbar();
        setBackNav();
        iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageDrawable(getDrawable(R.drawable.image_my));
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapter();
    }

    private void initAfter() {
        MyHomeVModel vModel = new MyHomeVModel(this);
    }

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
                            Intent intent = new Intent(MyHomeActivity.this, ContentWebView.class);
                            intent.putExtra(ContentWebView.PARAM_URL, url);
                            intent.putExtra(ContentWebView.PARAM_TITLE, title);
                            startActivity(intent);
                        }
                    }));
        }
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

    }
}
