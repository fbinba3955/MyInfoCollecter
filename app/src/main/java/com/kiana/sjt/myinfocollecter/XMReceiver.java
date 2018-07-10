package com.kiana.sjt.myinfocollecter;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * 小米推送接收器
 */
public class XMReceiver extends PushMessageReceiver{

    public String tag = "push";

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        LogUtils.json(tag, "onNotificationMessageClicked is called. " + miPushMessage.toString());
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        LogUtils.json(tag, "onNotificationMessageArrived is called. " + miPushMessage.toString());
    }

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        LogUtils.json(tag, "onReceivePassThroughMessage is called. "+ miPushMessage.toString());
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        LogUtils.json(tag, "onCommandResult is called. " + miPushCommandMessage.toString());
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        LogUtils.json(tag, "onReceiveRegisterResult is called. " + miPushCommandMessage.toString());
    }
}
