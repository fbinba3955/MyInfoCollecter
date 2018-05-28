package com.kiana.sjt.myinfocollecter.home.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiana.sjt.myinfocollecter.Constants;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.music.model.SongsModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 歌曲目录
 * Created by taodi on 2018/5/12.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder>{

    private Context context;
    private List<SongsModel.Datalist> datalists;
    private OnPlayClickListener listener;

    public SongsAdapter(Context context, List<SongsModel.Datalist> datalists) {
        this.context = context;
        this.datalists = datalists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_song, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SongsModel.Datalist datalist = datalists.get(position);
        if (datalist.getName().split("-").length >= 2) {
            holder.songNameTv.setText(datalist.getName().split("-")[1]);
            holder.songSingerTv.setText(datalist.getName().split("-")[0]);
            holder.songSingerTv.setVisibility(View.VISIBLE);
        }
        else {
            holder.songNameTv.setText(datalist.getName());
            holder.songSingerTv.setVisibility(View.GONE);
        }

        holder.playPauseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!datalist.isPlaying()) {
                    for (int i=0;i<datalists.size();i++) {
                        if (datalists.get(i).isPlaying()) {
                            datalists.get(i).setPlaying(false);
                            notifyItemChanged(i, "sss");
                        }
                    }
                }
                datalist.toggle();
                notifyItemChanged(position, "sss");
                listener.onPlayClick(position, datalist.isPlaying());
            }
        });
        if (datalist.isPlaying()) {
            holder.playPauseIv.setImageResource(R.drawable.ic_pause_black_24dp);
        }
        else {
            holder.playPauseIv.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
        ImageLoader.getInstance().displayImage(datalist.getCover(), holder.coverIv, getSongImageLoaderOption());
    }

    /**
     * 切换音乐播放状态
     * @param position
     * @param isPlay
     */
    public void changePlayMusic(int position, boolean isPlay) {
        if (isPlay) {
            for (SongsModel.Datalist bean:datalists
                    ) {
                bean.setPlaying(false);
            }
            datalists.get(position).toggle();
        }
        else {
            datalists.get(position).toggle();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datalists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView songNameTv;
        private TextView songSingerTv;
        private ImageView coverIv;
        private ImageView playPauseIv;

        public ViewHolder(View itemView) {
            super(itemView);
            songNameTv = (TextView) itemView.findViewById(R.id.tv_song_name);
            songSingerTv = (TextView) itemView.findViewById(R.id.tv_song_singer);
            coverIv = (ImageView) itemView.findViewById(R.id.iv_cover);
            playPauseIv = (ImageView) itemView.findViewById(R.id.iv_play_pause);
        }
    }

    public static DisplayImageOptions getSongImageLoaderOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_default_song)
                .showImageForEmptyUri(R.drawable.icon_default_song)
                .showImageOnFail(R.drawable.icon_default_song)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        return options;
    }

    public void setOnPlayClickListener(OnPlayClickListener listener) {
        this.listener = listener;
    }

    public interface OnPlayClickListener {
        public void onPlayClick(int position, boolean isPlay);
    }
}
