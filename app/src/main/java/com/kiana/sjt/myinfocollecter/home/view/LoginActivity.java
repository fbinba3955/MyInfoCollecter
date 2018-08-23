package com.kiana.sjt.myinfocollecter.home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.home.vmodel.LoginVModel;
import com.tts.android.aopmonitor.annotation.BindMonitor;

/**
 * 登录界面
 * Created by taodi on 2018/5/8.
 */

public class LoginActivity extends MainActivity{

    private EditText userNameEt;
    private EditText passWordEt;
    private Button loginBtn;

    LoginVModel loginVModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initView();
        initAfter();
    }

    private void initView() {
        userNameEt = (EditText) findViewById(R.id.et_username);
        passWordEt = (EditText) findViewById(R.id.et_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(onClickListener);
    }

    private void init() {

    }

    private void initAfter() {
        loginVModel = new LoginVModel(this);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @BindMonitor
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_login) {
                String username = userNameEt.getText().toString();
                String password = passWordEt.getText().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    loginVModel.doRequestData(username, password);
                }
                else {
                    tip(getString(R.string.login_fail_msg));
                }
            }
        }
    };
}
