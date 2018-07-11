package com.kiana.sjt.myinfocollecter.others.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.others.model.SendMsgModel;
import com.kiana.sjt.myinfocollecter.utils.JsonUtil;
import com.kiana.sjt.myinfocollecter.utils.UserUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketImActivity extends MainActivity{

    private Button sendBtn,connectBtn;
    private TextView recieveTv;
    private EditText sendContentEt;

    private WebSocket mWebSocket;

    public final static int H_SEND_ENABLED_TRUE = 101;
    public final static int H_SEND_ENABLED_FALSE = 102;
    public final static int H_CONNECT_ENABLED_FALSE = 103;
    public final static int H_CONNECT_ENABLED_true = 104;
    public final static int H_RECIEVE_MSG = 105;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websocket_im);
        sendBtn = (Button) findViewById(R.id.btn_send);
        sendBtn.setEnabled(false);
        connectBtn = (Button) findViewById(R.id.btn_connect);
        recieveTv = (TextView) findViewById(R.id.tv_receive_msg);
        sendContentEt = (EditText) findViewById(R.id.et_send_content);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = sendContentEt.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    SendMsgModel model = new SendMsgModel();
                    model.setType(SendMsgModel.SEND_TEXT_MSG);
                    model.setUserName(UserUtil.getAppUserName());
                    model.setContent(content);
                    mWebSocket.send(model.toString());
                }
            }
        });
    }

    private void connect() {

        EchoWebSocketListener listener = new EchoWebSocketListener();
        Request request = new Request.Builder()
                .url("ws://echo.websocket.org")
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    private Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case H_CONNECT_ENABLED_FALSE:
                    connectBtn.setEnabled(false);
                    break;
                case H_CONNECT_ENABLED_true:
                    connectBtn.setEnabled(true);
                    break;
                case H_SEND_ENABLED_TRUE:
                    sendBtn.setEnabled(true);
                    break;
                case H_SEND_ENABLED_FALSE:
                    sendBtn.setEnabled(false);
                    break;
                case H_RECIEVE_MSG:
                    String content = msg.getData().getString("msg");
                    recieveTv.append(content);
                    recieveTv.append("\n");
                    break;
            }
        }
    };

    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {

            mWebSocket = webSocket;
            uiHandler.sendEmptyMessage(H_CONNECT_ENABLED_FALSE);
            uiHandler.sendEmptyMessage(H_SEND_ENABLED_TRUE);
            webSocket.send(getInitMsg().toString());
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            LogUtils.d("onMessage: " + text);
            Bundle bundle = new Bundle();
            bundle.putString("msg", text);
            Message msg = Message.obtain();
            msg.what = H_RECIEVE_MSG;
            msg.setData(bundle);
            uiHandler.sendMessage(msg);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            LogUtils.d("onMessage byteString: " + bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            LogUtils.d("onClosing: " + code + "/" + reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            LogUtils.d("onClosed: " + code + "/" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            LogUtils.d("onFailure: " + t.getMessage());
        }
    }

    private SendMsgModel getInitMsg() {
        SendMsgModel model = new SendMsgModel();
        model.setType(SendMsgModel.INIT);
        model.setUserName(UserUtil.getAppUserName());
        return model;
    }

    @Override
    protected void onDestroy() {
        //before destroy the act, close the socket
        mWebSocket.close(1000, "bye");
        super.onDestroy();
    }
}
