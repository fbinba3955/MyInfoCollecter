package com.kiana.sjt.myinfocollecter.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.kiana.sjt.myinfocollecter.MainFragment;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.TestRecyclerViewAdapter;
import com.kiana.sjt.myinfocollecter.others.view.WebSocketImActivity;

import java.util.ArrayList;
import java.util.List;

public class OthersFragment extends MainFragment implements OthersViewAdapter.OnItemClickListener{

    RecyclerView mRecyclerView;

    public static OthersFragment newInstance() {
        return new OthersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final List<Object> items = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            items.add("websocket");
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        OthersViewAdapter adapter = new OthersViewAdapter(items);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), WebSocketImActivity.class);
        startActivity(intent);
    }
}
