package com.kiana.sjt.myinfocollecter.utils.viewpagercards;


import android.view.View;

public class CardItem {

    private int mTextResource;
    private int mTitleResource;
    private int mButtonContent;
    private View.OnClickListener onButtonClickListener;

    public CardItem(int title, int text, int btnContent, View.OnClickListener listener) {
        mTitleResource = title;
        mTextResource = text;
        mButtonContent = btnContent;
        onButtonClickListener = listener;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }

    public int getmButtonContent() {
        return mButtonContent;
    }

    public View.OnClickListener getOnButtonClickListener() {
        return onButtonClickListener;
    }
}
