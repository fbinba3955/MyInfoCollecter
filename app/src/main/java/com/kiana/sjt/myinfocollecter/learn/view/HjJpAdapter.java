package com.kiana.sjt.myinfocollecter.learn.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.learn.model.HjJpModel;
import com.kiana.sjt.myinfocollecter.medicine.model.NjszrlModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * 沪江日语编辑推荐adapter
 * Created by taodi on 2018/4/24.
 */

public class HjJpAdapter extends RecyclerView.Adapter<HjHolder> {

    private List<HjJpModel.Newslist> dataList;

    private Context context;

    private OnItemClickListener onItemClickListener;

    public HjJpAdapter(Context context, List<HjJpModel.Newslist> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public HjHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_hjjp, parent, false);
        HjHolder holder = new HjHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HjHolder holder, int position) {
        holder.typeTv.setText(dataList.get(position).getType());
        holder.titleTv.setText(dataList.get(position).getTitle());
        if (null != onItemClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
