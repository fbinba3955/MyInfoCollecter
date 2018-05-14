package com.kiana.sjt.myinfocollecter.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.kiana.sjt.myinfocollecter.Constants;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.RecyclerViewFragment;
import com.kiana.sjt.myinfocollecter.utils.UserUtil;

import org.w3c.dom.Text;

public class HomeActivity extends MainActivity {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    MaterialViewPager mViewPager;

    private LinearLayout userLayout;

    private TextView userNameTv;

    private TextView levelNameTv;

    private ImageView userHeadIv;

    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userLayout = (LinearLayout) findViewById(R.id.layout_user);
        userLayout.setOnClickListener(onClickListener);

        userNameTv = (TextView) findViewById(R.id.tv_user_name);

        levelNameTv = (TextView) findViewById(R.id.tv_level_name);

        userHeadIv = (ImageView) findViewById(R.id.iv_user_head);

        logoutBtn = (Button) findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(onClickListener);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        final Toolbar toolbar = mViewPager.getToolbar();
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return InfoCollecterFragment.newInstance();
                    //    return RecyclerViewFragment.newInstance();
                    //case 1:
                    //    return RecyclerViewFragment.newInstance();
                    case 2:
                        return SongsFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "信息收集";
                    case 1:
                        return "书籍";
                    case 2:
                        return "音乐";
                    case 3:
                        return "其他";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.colorPrimary,
                                Constants.serverImgUrl + "img_head_nav1.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green_teal,
                                Constants.serverImgUrl + "img_head_nav2.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                Constants.serverImgUrl + "img_head_nav3.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                Constants.serverImgUrl + "img_head_nav4.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //刷新登录状态
        if (UserUtil.isLogin()) {
            userNameTv.setText(UserUtil.getUserInfo().getNickname());
            levelNameTv.setText(UserUtil.getUserInfo().getLevelname());
            logoutBtn.setVisibility(View.VISIBLE);
        }
        else {
            userNameTv.setText(getResources().getString(R.string.login));
            levelNameTv.setText("");
            logoutBtn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.layout_user) {
                //判断是否已经登录
                if (!UserUtil.isLogin()) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
            else if (view.getId() == R.id.btn_logout) {
                UserUtil.setLogout();
                tip("已退出当前用户");
                onResume();
            }
        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }
}
