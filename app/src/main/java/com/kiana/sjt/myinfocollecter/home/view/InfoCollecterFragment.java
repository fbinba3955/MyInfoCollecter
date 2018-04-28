package com.kiana.sjt.myinfocollecter.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.kiana.sjt.myinfocollecter.Constants;
import com.kiana.sjt.myinfocollecter.MainFragment;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.learn.view.LearnHomeActivity;
import com.kiana.sjt.myinfocollecter.medicine.view.MedicineHomeActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by taodi on 2018/4/27.
 */

public class InfoCollecterFragment extends MainFragment{

    LinearLayout layout;

    NestedScrollView scrollView;

    ImageView img_medicine;

    CardView cardView_medicine;

    ImageView img_learn;

    CardView cardView_learn;

    public static InfoCollecterFragment newInstance() {
        return new InfoCollecterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_collecter, container, false);
        layout = (LinearLayout) v.findViewById(R.id.layout);
        scrollView = (NestedScrollView) v.findViewById(R.id.scrollView);
        img_medicine = (ImageView) v.findViewById(R.id.img_medicine);
        cardView_medicine = (CardView) v.findViewById(R.id.card_medicine);
        cardView_medicine.setOnClickListener(onClickListener);
        img_learn = (ImageView) v.findViewById(R.id.img_learn);
        cardView_learn = (CardView) v.findViewById(R.id.card_learn);
        cardView_learn.setOnClickListener(onClickListener);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerHelper.registerScrollView(getActivity(), scrollView);
        ImageLoader.getInstance().displayImage(Constants.serverImgUrl + "img_medicine_enter.jpg", img_medicine, getImageLoaderOption());
        ImageLoader.getInstance().displayImage(Constants.serverImgUrl + "img_learn_enter.jpg", img_learn, getImageLoaderOption());
    }

    View.OnClickListener onClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.card_medicine) {
                Intent intent = new Intent(getActivity(), MedicineHomeActivity.class);
                startActivity(intent);
            }
            else if (view.getId() == R.id.card_learn) {
                Intent intent = new Intent(getActivity(), LearnHomeActivity.class);
                startActivity(intent);
            }
        }
    };
}
