package com.kiana.sjt.myinfocollecter;

import android.support.v4.app.Fragment;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by taodi on 2018/4/27.
 */

public class MainFragment extends Fragment{

    public static DisplayImageOptions getImageLoaderOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_loading_default)
                .showImageForEmptyUri(R.drawable.img_loading_default)
                .showImageOnFail(R.drawable.img_loading_default)
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .build();
        return options;
    }
}
