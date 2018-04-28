package com.kiana.sjt.myinfocollecter.learn.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baoyz.widget.PullRefreshLayout;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.learn.model.HjJpModel;
import com.kiana.sjt.myinfocollecter.learn.vmodel.HjVModel;
import com.kiana.sjt.myinfocollecter.learn.vmodel.HjViewRefreshListener;
import com.kiana.sjt.myinfocollecter.utils.webview.ContentWebView;

import java.util.List;

/**
 * Created by taodi on 2018/4/27.
 */

public class HjActivity extends MainActivity implements HjViewRefreshListener{

    public PullRefreshLayout pullRefreshLayout;

    public RecyclerView recyclerView;

    HjJpAdapter adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hj);
        setDefaultToolbar(R.string.title_hj);
        init();
        initView();
        initAfter();
    }

    private void init() {}

    private void initView() {
        pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.pullview);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initAfter() {
        final HjVModel vModel = new HjVModel(this);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vModel.requestData();
            }
        });
    }


    @Override
    public void onRefreshListLinstener(final List<HjJpModel.Newslist> dataList) {
        adapter = new HjJpAdapter(HjActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new HjJpAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(HjActivity.this, ContentWebView.class);
                intent.putExtra(ContentWebView.PARAM_URL, dataList.get(position).getUrl());
                intent.putExtra(ContentWebView.PARAM_TITLE, dataList.get(position).getTitle());
                startActivity(intent);
            }
        });
        hideLoadingDialog();
        pullRefreshLayout.setRefreshing(false);
    }

}
