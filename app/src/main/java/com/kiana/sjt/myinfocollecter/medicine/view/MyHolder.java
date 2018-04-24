package com.kiana.sjt.myinfocollecter.medicine.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kiana.sjt.myinfocollecter.R;

/**
 * Created by taodi on 2018/4/24.
 */

public class MyHolder extends RecyclerView.ViewHolder {

    public TextView titleTv;

    public TextView dateTv;

    public MyHolder(View itemView) {
        super(itemView);
        titleTv = (TextView) itemView.findViewById(R.id.tv_news_title);
        dateTv = (TextView) itemView.findViewById(R.id.tv_news_date);
    }
}
