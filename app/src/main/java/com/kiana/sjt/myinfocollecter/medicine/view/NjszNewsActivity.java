package com.kiana.sjt.myinfocollecter.medicine.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.medicine.model.NjszrlModel;
import com.kiana.sjt.myinfocollecter.medicine.vmodel.NjszNewsVModel;

import java.util.List;

/**
 * Created by taodi on 2018/4/24.
 */

public class NjszNewsActivity extends MainActivity {

    PullRefreshLayout pullRefreshLayout;

    RecyclerView recyclerView;

    NjszNewsAdapter adapter;

    NjszNewsVModel vModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_njsz_news);
        setToolbarTitle("南京市中");
        setBackNav();
        initToolbar();
        pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.pullview);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAfter();
    }

    public void initAfter() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vModel.questData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        vModel = new NjszNewsVModel(this);
    }

    /**
     * 设置内容
     * @param dataList
     */
    public void setRecyclerViewData(List<NjszrlModel.Newslist> dataList) {
        adapter = new NjszNewsAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        pullRefreshLayout.setRefreshing(false);
    }
}
