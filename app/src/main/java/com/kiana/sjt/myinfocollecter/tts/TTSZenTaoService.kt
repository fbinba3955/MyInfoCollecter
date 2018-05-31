package com.kiana.sjt.myinfocollecter.tts

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import com.androidnetworking.error.ANError
import com.blankj.utilcode.util.LogUtils
import com.kiana.sjt.myinfocollecter.Constants
import com.kiana.sjt.myinfocollecter.utils.net.BaseResponseModel
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBackForString
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil

open class TTSZenTaoService : Service() {

    var shijianting = ""
    var niuxiang = ""
    var cuiliqing = ""
    var hangweiqing = ""
    var yinjiachun = ""
    var qianglusheng = ""

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        doPostZTLogin("shijianting", "sjt3953395")
        return super.onStartCommand(intent, flags, startId)
    }

    open fun doPostZTLogin(username:String, password:String) {
        var params = HashMap<String, String>()
        params.put("account", username)
        params.put("password", password)
        NetWorkUtil.doPostData(this,
                "http://172.18.0.69/zentao/user-login.json",
                params,
                object : NetCallBack<ZenTaoLogin>() {

            override fun onSuccess(bean: ZenTaoLogin?) {
                if ("success".equals(bean!!.status)) {
                    //获取禅道任务内容
                    doGetZTMessionList("shijianting")
                }
            }

            override fun onError(error: ANError?) {

            }
        })

    }

    /**
     * 从禅道获取任务列表 按人
     */
    open fun doGetZTMessionList(name:String) {
        NetWorkUtil.doGetNullDataForString(this,
                "http://172.18.0.69/zentao/user-task-"+name+".html",
                object : NetCallBackForString(){
            override fun onSuccess(response: String?) {
                if ("shijianting".equals(name)) {
                    shijianting = response!!
                    doGetZTMessionList("niuxiang")
                }
                else if("niuxiang".equals(name)) {
                    niuxiang = response!!
                    doGetZTMessionList("cuiliqing")
                }
                else if("cuiliqing".equals(name)) {
                    cuiliqing = response!!
                    doGetZTMessionList("hangweiqing")
                }
                else if("hangweiqing".equals(name)) {
                    hangweiqing = response!!
                    doGetZTMessionList("yinjiachun")
                }
                else if("yinjiachun".equals(name)) {
                    yinjiachun = response!!
                    doGetZTMessionList("qianglusheng")
                }
                else if("qianglusheng".equals(name)) {
                    qianglusheng = response!!
                    doPostZTMissionToServer()
                }
            }

            override fun onError(error: ANError?) {
            }

        })
    }

    /**
     * 把任务列表送给服务器
     */
    open fun doPostZTMissionToServer() {
        var params = HashMap<String, String>()
        if (!TextUtils.isEmpty(shijianting)) {
            params.put("shijianting", shijianting);
        }
        if (!TextUtils.isEmpty(niuxiang)) {
            params.put("niuxiang", niuxiang);
        }
        if (!TextUtils.isEmpty(cuiliqing)) {
            params.put("cuiliqing", cuiliqing);
        }
        if (!TextUtils.isEmpty(hangweiqing)) {
            params.put("hangweiqing", hangweiqing);
        }
        if (!TextUtils.isEmpty(yinjiachun)) {
            params.put("yinjiachun", yinjiachun);
        }
        if (!TextUtils.isEmpty(qianglusheng)) {
            params.put("qianglusheng", qianglusheng);
        }
        NetWorkUtil.doPostData(this, Constants.serverUrl+"tts/zentaomission.php", params,
                object : NetCallBack<BaseResponseModel>(){
                    override fun onSuccess(bean: BaseResponseModel?) {
                    }

                    override fun onError(error: ANError?) {
                        LogUtils.json(error!!.errorDetail)
                    }

                })
    }
}