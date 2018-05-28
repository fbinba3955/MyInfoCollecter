package com.kiana.sjt.myinfocollecter.home.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_KEY;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_PAUSE;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_PLAY;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_POSITION;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_REPLAY;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_SONGS;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_UPDATE_SONGS;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_SERVICE;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_SERVICE_TO_ACTIVITY_KEY;

/**
 * Created by taodi on 2018/5/12.
 */

public class SongsFragment extends MainFragment implements SongsAdapter.OnPlayClickListener{

    private Unbinder unbinder;

    @BindView(R.id.plr_songs)
    RecyclerView recyclerView;

    SongsAdapter adapter;

    /**
     * 当前播放位置
     */
    private int currentPosition = 0;

    private SongsModel songsModel;

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
                songsModel = bean;
                sendMusicBroadcast(MUSIC_ACTIVITY_SERVICE_UPDATE_SONGS, 0, songsModel);
                adapter = new SongsAdapter(getActivity(), bean.getDatalist());
                adapter.setOnPlayClickListener(SongsFragment.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(ANError error) {
                ((MainActivity)getActivity()).tip(error.getErrorDetail());
            }
        });
    }

    @Override
    public void onPlayClick(int position, boolean isPlay) {
        if (currentPosition == position) {
            if (isPlay) {
                sendMusicBroadcast(MUSIC_ACTIVITY_SERVICE_REPLAY, position, songsModel);
            }else {
                sendMusicBroadcast(MUSIC_ACTIVITY_SERVICE_PAUSE, position, songsModel);
            }
        }
        else {
            currentPosition = position;
            sendMusicBroadcast(MUSIC_ACTIVITY_SERVICE_PLAY, position, songsModel);
        }
    }

    /**
     * 发送广播给音乐服务
     * @param musicType
     * @param position
     * @param songs
     */
    private void sendMusicBroadcast(int musicType, int position, SongsModel songs) {
        Intent intent = new Intent();
        intent.setAction(MUSIC_SERVICE);
        intent.putExtra(MUSIC_ACTIVITY_SERVICE_KEY, musicType);
        intent.putExtra(MUSIC_ACTIVITY_SERVICE_POSITION, position);
        intent.putExtra(MUSIC_ACTIVITY_SERVICE_SONGS, songs);
        getActivity().sendBroadcast(intent);
    }

    public class MusicActvityBroadcastReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int key = intent.getIntExtra(MUSIC_SERVICE_TO_ACTIVITY_KEY, 0);
        }
    }
}
