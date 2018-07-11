package com.kiana.sjt.myinfocollecter.home.view;

import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiana.sjt.myinfocollecter.R;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class OthersViewAdapter extends RecyclerView.Adapter<OthersViewAdapter.ViewHolder> {

    List<Object> contents;

    OnItemClickListener onItemClickListener;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public OthersViewAdapter(List<Object> contents) {
        this.contents = contents;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_others, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.nameTv.setText(contents.get(position).toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener) {
                    onItemClickListener.onClick(position);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconIv;
        private TextView nameTv;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            iconIv = (ImageView) itemView.findViewById(R.id.iv_icon);
            nameTv = (TextView) itemView.findViewById(R.id.tv_name);
            cardView = (CardView) itemView.findViewById(R.id.card_others);
        }

    }

    public interface OnItemClickListener {

        public void onClick(int position);
    }
}