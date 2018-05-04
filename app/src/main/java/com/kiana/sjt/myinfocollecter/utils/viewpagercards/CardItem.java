package com.kiana.sjt.myinfocollecter.utils.viewpagercards;


import android.view.View;

public class CardItem {

    private String mTextResource;
    private String mTitleResource;
    private String mButtonContent;
    private View.OnClickListener onButtonClickListener;

    public CardItem(String title, String text, String btnContent, View.OnClickListener listener) {
        mTitleResource = title;
        mTextResource = text;
        mButtonContent = btnContent;
        onButtonClickListener = listener;
    }

    public CardItem() {}

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public String getmButtonContent() {
        return mButtonContent;
    }

    public View.OnClickListener getOnButtonClickListener() {
        return onButtonClickListener;
    }

    public void setmTextResource(String mTextResource) {
        this.mTextResource = mTextResource;
    }

    public void setmTitleResource(String mTitleResource) {
        this.mTitleResource = mTitleResource;
    }

    public void setmButtonContent(String mButtonContent) {
        this.mButtonContent = mButtonContent;
    }

    public void setOnButtonClickListener(View.OnClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}
