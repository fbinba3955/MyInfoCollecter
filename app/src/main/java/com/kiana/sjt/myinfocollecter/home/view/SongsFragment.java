package com.kiana.sjt.myinfocollecter.home.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.kiana.sjt.myinfocollecter.utils.net.BaseNetStatusBean;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.CHANGE_PLAYMUSIC_POSITION;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_KEY;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_PAUSE;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_PLAY;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_POSITION;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_REPLAY;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_SONGS;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_UPDATE_SONGS;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTVITY;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_SERVICE;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_SERVICE_TO_ACTIVITY_CHANGE_PLAYMUSIC;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_SERVICE_TO_ACTIVITY_KEY;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.PAUSE_POSITION;

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

    MusicActvityBroadcastReciever reciever = new MusicActvityBroadcastReciever();

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
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(MUSIC_ACTVITY);
        getActivity().registerReceiver(reciever, filter);
    }

    @Override
    public void onDestroyView() {
        getActivity().unregisterReceiver(reciever);
        super.onDestroyView();
        unbinder.unbind();
    }

    private void requestSongsData() {
        NetWorkUtil.doGetData(getActivity(), CmdConstants.SONGS, null, new NetCallBack<BaseNetStatusBean<SongsModel>>() {

            @Override
            public void onSuccess(BaseNetStatusBean<SongsModel> bean) {
                songsModel = bean.getData();
                sendMusicBroadcast(MUSIC_ACTIVITY_SERVICE_UPDATE_SONGS, 0, songsModel);
                adapter = new SongsAdapter(getActivity(), bean.getData().getDatalist());
                adapter.setOnPlayClickListener(SongsFragment.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(ANError error) {
                ((MainActivity)getActivity()).tip(error.getErrorDetail());
            }

            @Override
            public void onInterError(String errCode, String errMsg) {
                ((MainActivity)getActivity()).tip(errMsg);
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
            switch (key) {
                //播放完毕一首歌曲，切换到下一首歌曲
                case MUSIC_SERVICE_TO_ACTIVITY_CHANGE_PLAYMUSIC:
                    int position = intent.getIntExtra(CHANGE_PLAYMUSIC_POSITION, -1);
                    if (position>=0) {
                        adapter.changePlayingMusic(position);
                    }
                    break;
                //暂停一首当前播放的歌曲
                case MUSIC_ACTIVITY_SERVICE_PAUSE:
                    int position2 = intent.getIntExtra(PAUSE_POSITION, -1);
                    if (position2>=0) {
                        adapter.pauseMusic(position2);
                    }
                    break;
                default:
            }
        }
    }
}
