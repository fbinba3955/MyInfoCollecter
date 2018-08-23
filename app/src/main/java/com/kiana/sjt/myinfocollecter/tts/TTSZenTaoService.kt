package com.kiana.sjt.myinfocollecter.tts

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import com.androidnetworking.error.ANError
import com.blankj.utilcode.util.LogUtils
import com.kiana.sjt.myinfocollecter.Constants
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTANINTERERROR
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTANNETERROR
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTANSUCCESSMSG
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAOACTION
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAOERRORMSG
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAOERRORTAG
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAORESULT
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService.list.ZENTAOSUCCESSTAG
import com.kiana.sjt.myinfocollecter.utils.net.*

open class TTSZenTaoService : Service() {

    var shijianting = ""
    var niuxiang = ""
    var cuiliqing = ""
    var hangweiqing = ""
    var yinjiachun = ""
    var qianglusheng = ""

    object list {
        @JvmField val ZENTAOACTION = "zt.service.to.activity"
        @JvmField val ZENTAORESULT = "ztresult"
        @JvmField val ZENTAOERRORMSG = "errormsg"
        @JvmField val ZENTAOERRORTAG = "error"
        @JvmField val ZENTAOSUCCESSTAG = "success"

        @JvmField val ZENTANSUCCESSMSG = "禅道任务更新成功"
        @JvmField val ZENTANNETERROR = "无法连接到禅道服务器"
        @JvmField val ZENTANINTERERROR = "服务错误"
    }

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
        NetWorkUtil.doGetData(this,
                Constants.zentaoUrl + "zentao/user-login.json",
                params,
                object : NetCallBack<BaseNetStatusBean<ZenTaoLogin>>() {

            override fun onSuccess(bean: BaseNetStatusBean<ZenTaoLogin>?) {
                if ("success".equals(bean!!.data.status)) {
                    //获取禅道任务内容
                    doGetZTMessionList("shijianting")
                }
            }

            override fun onInterError(errCode: String?, errMsg: String?) {
                LogUtils.json(errMsg)
                sendZenTaoResult(ZENTAOERRORTAG, ZENTANNETERROR)
            }

            override fun onError(error: ANError?) {
                LogUtils.json(error!!.errorDetail)
                sendZenTaoResult(ZENTAOERRORTAG, ZENTANNETERROR)
            }
        })

    }

    /**
     * 从禅道获取任务列表 按人
     */
    open fun doGetZTMessionList(name:String) {
        NetWorkUtil.doGetNullDataForString(this,
                Constants.zentaoUrl + "zentao/user-task-"+name+".html",
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
                sendZenTaoResult(ZENTAOERRORTAG, ZENTANNETERROR)
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
                object : NetCallBack<BaseNetStatusBean<BaseResponseModel>>(){

                    override fun onInterError(errCode: String?, errMsg: String?) {
                        LogUtils.json(errMsg)
                        sendZenTaoResult(ZENTAOERRORTAG, ZENTANINTERERROR)
                    }

                    override fun onSuccess(bean: BaseNetStatusBean<BaseResponseModel>?) {
                        if ("0".equals(bean!!.data.resultCode)) {
                            sendZenTaoResult(ZENTAOSUCCESSTAG, ZENTANSUCCESSMSG)
                        }
                    }

                    override fun onError(error: ANError?) {
                        LogUtils.json(error!!.errorDetail)
                        sendZenTaoResult(ZENTAOERRORTAG, ZENTANINTERERROR)
                    }

                })
    }

    /**
     * 发送禅道结果
     */
    open fun sendZenTaoResult(tag:String, msg:String) {
        var intent = Intent()
        intent.action = ZENTAOACTION
        intent.putExtra(ZENTAORESULT, tag)
        intent.putExtra(ZENTAOERRORMSG, msg)
        sendBroadcast(intent)
    }
}