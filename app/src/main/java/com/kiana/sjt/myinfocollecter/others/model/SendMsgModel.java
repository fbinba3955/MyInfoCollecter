package com.kiana.sjt.myinfocollecter.others.model;

import android.os.Bundle;
import android.os.Message;

import com.kiana.sjt.myinfocollecter.others.view.WebSocketImActivity;
import com.kiana.sjt.myinfocollecter.utils.JsonUtil;

/**
 * 信息对象
 */
public class SendMsgModel {

    //创建连接时的初始化
    public static final String INIT = "init";
    //发送文本消息
    public static final String SEND_TEXT_MSG = "send_text";

    //消息类型
    private String type;

    //用户名
    private String userName;

    //消息内容
    private String content;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Message buildMessage() {
        String contentJson = JsonUtil.fromObjectToJsonString(this);
        Bundle bundle = new Bundle();
        bundle.putString("msg", contentJson);
        Message msg = Message.obtain();
        msg.what = WebSocketImActivity.H_RECIEVE_MSG;
        msg.setData(bundle);
        return msg;
    }

    @Override
    public String toString() {
        String contentJson = JsonUtil.fromObjectToJsonString(this);
        return contentJson;
    }
}
