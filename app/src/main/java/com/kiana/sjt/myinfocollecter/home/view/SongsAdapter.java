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
    public void onBindViewHolder(ViewHolder holder, int position) {
        SongsModel.Datalist datalist = datalists.get(position);
        holder.songNameTv.setText(datalist.getName());
        ImageLoader.getInstance().displayImage(datalist.getCover(), holder.coverIv, getSongImageLoaderOption());
    }

    @Override
    public int getItemCount() {
        return datalists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView songNameTv;
        private ImageView coverIv;
        private ImageView playPauseIv;

        public ViewHolder(View itemView) {
            super(itemView);
            songNameTv = (TextView) itemView.findViewById(R.id.tv_song_name);
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
                .cacheInMemory(false)
                .build();
        return options;
    }
}
