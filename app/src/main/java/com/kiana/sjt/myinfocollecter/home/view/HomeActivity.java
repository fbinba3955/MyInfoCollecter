package com.kiana.sjt.myinfocollecter.home.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.kiana.sjt.myinfocollecter.Constants;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.RecyclerViewFragment;
import com.kiana.sjt.myinfocollecter.home.model.LoginModel;
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService;
import com.kiana.sjt.myinfocollecter.utils.UserUtil;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.im.ECFileMessageBody;
import com.yuntongxun.ecsdk.im.ECImageMessageBody;
import com.yuntongxun.ecsdk.im.ECLocationMessageBody;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;
import com.yuntongxun.ecsdk.im.ECVideoMessageBody;
import com.yuntongxun.ecsdk.im.ECVoiceMessageBody;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;

import org.w3c.dom.Text;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ezy.boost.update.UpdateManager;

import static com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAOACTION;
import static com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAOERRORMSG;
import static com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAOERRORTAG;
import static com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAORESULT;
import static com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAOSUCCESSTAG;
import static com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTVITY;

public class HomeActivity extends MainActivity {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    MaterialViewPager mViewPager;

    private LinearLayout userLayout;

    private TextView userNameTv;

    private TextView levelNameTv;

    private ImageView userHeadIv;

    private Button logoutBtn;

    private CircularProgressButton missionBtn;

    private ZenTaoBroadCastReciever reciever = new ZenTaoBroadCastReciever();

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
        missionBtn = (CircularProgressButton) findViewById(R.id.btn_get_missions);
        missionBtn.setOnClickListener(onClickListener);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        final Toolbar toolbar = mViewPager.getToolbar();
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(ZENTAOACTION);
        registerReceiver(reciever, filter);

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
        } else {
            userNameTv.setText(getResources().getString(R.string.login));
            levelNameTv.setText("");
            logoutBtn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(reciever);
        super.onDestroy();
        missionBtn.dispose();
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
        initRTIm();
        updateApp();
    }

    /**
     * 初始化im
     */
    protected void initRTIm() {

        //设置自定义登录参数
        LoginModel.User user = UserUtil.getUserInfo();
        final ECInitParams params = ECInitParams.createParams();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            tip("没有READ_PHONE_STATE权限");
            return;
        }
        params.setUserid(PhoneUtils.getDeviceId());
        params.setAppKey("8a216da8646e949a016486c904930b8c");
        params.setToken("746713b1871f4b783c3888e14fa8942d");
        params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
        params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);

        //初始化im功能
        if (!ECDevice.isInitialized()) {
            ECDevice.initial(getApplicationContext(), new ECDevice.InitListener() {
                @Override
                public void onInitialized() {
                    LogUtils.dTag("IM", "初始化SDK成功");
                    initIMCallBack(params);
                }

                @Override
                public void onError(Exception e) {
                    LogUtils.dTag("IM", "初始化SDK失败" + e.getMessage());
                }
            });
        }


    }

    private void initIMCallBack(ECInitParams params) {
        //登录回调
        ECDevice.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
            @Override
            public void onConnect() {

            }

            @Override
            public void onDisconnect(ECError ecError) {

            }

            @Override
            public void onConnectState(ECDevice.ECConnectState ecConnectState, ECError ecError) {
                if (ecConnectState == ECDevice.ECConnectState.CONNECT_FAILED) {
                    if (ecError.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
                        LogUtils.dTag("IM", "==账号异地登录");
                    }
                    else {
                        LogUtils.dTag("IM", "==其他登录失败，错误码"+ecError.errorCode);
                    }
                }
                else if (ecConnectState == ECDevice.ECConnectState.CONNECT_SUCCESS) {
                    LogUtils.dTag("IM", "==登录成功");
                }
            }
        });

        //IM接收消息回调
        ECDevice.setOnChatReceiveListener(new OnChatReceiveListener() {
            @Override
            public void OnReceivedMessage(ECMessage msg) {
                LogUtils.dTag("IM", "==收到新消息");
                if (msg == null) {
                    return;
                }
                ECMessage.Type type = msg.getType();
                if (type == ECMessage.Type.TXT) {
                    ECTextMessageBody textMessageBody = (ECTextMessageBody) msg.getBody();
                    tip(textMessageBody.getMessage());
                }
                else {
                    String thumbnailFileUrl = null;
                    String remoteUrl = null;
                    if (type == ECMessage.Type.FILE) {
                        // 在这里处理附件消息
                        ECFileMessageBody fileMsgBody = (ECFileMessageBody) msg.getBody();
                        // 获得下载地址
                        remoteUrl = fileMsgBody.getRemoteUrl();
                    } else if (type == ECMessage.Type.IMAGE) {
                        // 在这里处理图片消息
                        ECImageMessageBody imageMsgBody = (ECImageMessageBody) msg.getBody();
                        // 获得缩略图地址
                        thumbnailFileUrl = imageMsgBody.getThumbnailFileUrl();
                        // 获得原图地址
                        remoteUrl = imageMsgBody.getRemoteUrl();
                    } else if (type == ECMessage.Type.VOICE) {
                        // 在这里处理语音消息
                        ECVoiceMessageBody voiceMsgBody = (ECVoiceMessageBody) msg.getBody();
                        // 获得下载地址
                        remoteUrl = voiceMsgBody.getRemoteUrl();
                    } else if (type == ECMessage.Type.VIDEO) {
                        // 在这里处理视频消息
                        ECVideoMessageBody videoMessageBody = (ECVideoMessageBody) msg.getBody();
                        // 获得下载地址
                        remoteUrl = videoMessageBody.getRemoteUrl();
                    } else if (type == ECMessage.Type.LOCATION) {
                        // 在这里处理地理位置消息
                        ECLocationMessageBody locationMessageBody = (ECLocationMessageBody) msg.getBody();
                        // 获得下载地址
                        locationMessageBody.getLatitude(); // 纬度信息
                        locationMessageBody.getLongitude();// 经度信息
                    } else {
                        LogUtils.eTag("ECSDK_Demo" , "Can't handle msgType=" + type.name()
                                + " , then ignore.");
                        // 后续还会支持（自定义等消息类型）
                    }
                    if(TextUtils.isEmpty(remoteUrl)) {
                        return ;
                    }
                    if(!TextUtils.isEmpty(thumbnailFileUrl)) {
                        // 先下载缩略图
                    } else {
                        // 下载附件
                    }
                }
            }

            @Override
            public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {

            }

            @Override
            public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage ecGroupNoticeMessage) {
                LogUtils.dTag("IM", "==收到群组通知消息（有人加入、退出……）");
            }

            @Override
            public void onOfflineMessageCount(int i) {

            }

            @Override
            public int onGetOfflineMessage() {
                return 0;
            }

            @Override
            public void onReceiveOfflineMessage(List<ECMessage> list) {

            }

            @Override
            public void onReceiveOfflineMessageCompletion() {

            }

            @Override
            public void onServicePersonVersion(int i) {

            }

            @Override
            public void onReceiveDeskMessage(ECMessage ecMessage) {

            }

            @Override
            public void onSoftVersion(String s, int i) {

            }
        });

        //执行登录操作
        if (params.validate()) {
            ECDevice.login(params);
        }
    }

    /**
     * 获取更新状态
     */
    private void updateApp() {
        UpdateManager.setDebuggable(true);
        UpdateManager.setWifiOnly(false);
        UpdateManager.setUrl(Constants.serverUrl+"update.php", "yyb");
        UpdateManager.check(this);
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
            else if (view.getId() == R.id.btn_get_missions) {
                //登录成功后启动，启动禅道服务
                Intent intent2 = new Intent(HomeActivity.this, TTSZenTaoService.class);
                HomeActivity.this.startService(intent2);
                missionBtn.startAnimation();
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

    public class ZenTaoBroadCastReciever extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (ZENTAOACTION.equals(intent.getAction())) {
                //成功
                if (ZENTAOSUCCESSTAG.equals(intent.getStringExtra(ZENTAORESULT))) {
                    tip(intent.getStringExtra(ZENTAOERRORMSG));
                }
                else if (ZENTAOERRORTAG.equals(intent.getStringExtra(ZENTAORESULT))) {
                    tip(intent.getStringExtra(ZENTAOERRORMSG));
                }
                missionBtn.revertAnimation();
            }
        }
    }
}
