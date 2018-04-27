package com.kiana.sjt.myinfocollecter.learn.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kiana.sjt.myinfocollecter.R;

/**
 * Created by taodi on 2018/4/24.
 */

public class HjHolder extends RecyclerView.ViewHolder {

    public TextView typeTv;

    public TextView titleTv;

    public HjHolder(View itemView) {
        super(itemView);
        titleTv = (TextView) itemView.findViewById(R.id.tv_hj_title);
        typeTv = (TextView) itemView.findViewById(R.id.tv_hj_type);
    }
}
