package com.kiana.sjt.myinfocollecter.home.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.error.ANError;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.kiana.sjt.myinfocollecter.CmdConstants;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.MainFragment;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.music.model.SongsModel;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by taodi on 2018/5/12.
 */

public class SongsFragment extends MainFragment{

    private Unbinder unbinder;

    @BindView(R.id.plr_songs)
    RecyclerView recyclerView;

    SongsAdapter adapter;

    public static SongsFragment newInstance() {
        return new SongsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_songs, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);

        //Use this now
        recyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        requestSongsData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void requestSongsData() {
        NetWorkUtil.doGetNullData(getActivity(), new MainVModel().makeMusicUrl(CmdConstants.SONGS), new NetCallBack<SongsModel>() {

            @Override
            public void onSuccess(SongsModel bean) {
                adapter = new SongsAdapter(getActivity(), bean.getDatalist());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(ANError error) {
                ((MainActivity)getActivity()).tip(error.getErrorDetail());
            }
        });
    }
}
