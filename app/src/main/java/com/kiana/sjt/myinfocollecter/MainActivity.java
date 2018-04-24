package com.kiana.sjt.myinfocollecter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

/**
 * 基本的activity类
 * Created by taodi on 2018/4/23.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setNoTitle() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setVisibility(View.GONE);
        }
    }

    public void setTranslateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    public void setToolbarTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setTitle(title);
        }
    }

    public void setBackNav() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setNavigationIcon(getDrawable(R.drawable.icon_back));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setTitleTextColor(getColor(R.color.white));
            setSupportActionBar(toolbar);
        }
    }


}
