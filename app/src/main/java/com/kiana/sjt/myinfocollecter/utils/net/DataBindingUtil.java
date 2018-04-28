package com.kiana.sjt.myinfocollecter.utils.net;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.kiana.sjt.myinfocollecter.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * databinding处理类
 * Created by taodi on 2018/4/23.
 */

public class DataBindingUtil {

    /**
     * 欢迎页加载图片
     * @param imageView
     * @param url
     */
    @BindingAdapter("image_load_welcome")
    public static void imageLoaderForWelcome(ImageView imageView, String url) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, imageView, getImageLoaderOptionForWelcome());
    }

    /**
     * 欢迎页加载图片的配置项
     * @return
     */
    public static DisplayImageOptions getImageLoaderOptionForWelcome() {
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
