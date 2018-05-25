package com.kiana.sjt.myinfocollecter.utils.music

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import com.kiana.sjt.myinfocollecter.music.model.SongsModel
import com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_KEY
import com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_PAUSE
import com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_PLAY
import com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_POSITION
import com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_REPLAY
import com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_SONGS
import com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_ACTIVITY_SERVICE_UPDATE_SONGS
import com.kiana.sjt.myinfocollecter.utils.music.MusicService.list.MUSIC_SERVICE
import java.io.IOException

/**
 * 音乐播放服务
 * Created by taodi on 2018/5/15.
 */
open class MusicService : Service(),
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener
{
    private val MUSIC_INTENT_KEY = "musics"
    private val MUSIC_INTENT_FLAG = 20001
    private val MAIN_MUSIC_INTENT_FLAG = 20017

    //播放列表
    private var musics:List<SongsModel.Datalist> = ArrayList()

    //MusicService来的Action
    private val MUSIC_ACTIVITY_SERVICE_ACTION = "activity.to.musicservice"

    private val MUSIC_SERVICE_REQUEST = 40001

    //MediaPlay
    private var mp: MediaPlayer = MediaPlayer()

    //当前播放的音乐
    private var musicposition = 0

    //当前播放歌曲的播放位置
    private var currentTime = 0

    //发送给Activity的intent
    private var mActivityIntent: Intent = Intent()

    object list {
        //服务到界面用
        @JvmField val MUSIC_ACTVITY = "service.to.activity"
        @JvmField val MUSIC_SERVICE_TO_ACTIVITY_KEY = "musictype"
        @JvmField val MUSIC_SERVICE_TO_ACTIVITY_MODEL = "model"
        @JvmField val MUSIC_SERVICE_TO_ACTIVITY_ISPLAY = "isplay"
        @JvmField val MUSIC_SERVICE_TO_ACTIVITY_NOWTIME = "nowtime"
        @JvmField val MUSIC_SERVICE_TO_ACTIVITY_COMPLETE_CHANGE_MUSIC = 1001

        //界面到服务用
        @JvmField val MUSIC_SERVICE = "activity.to.service"
        @JvmField val MUSIC_ACTIVITY_SERVICE_KEY = "musictype"
        @JvmField val MUSIC_ACTIVITY_SERVICE_POSITION = "musicposition"
        @JvmField val MUSIC_ACTIVITY_SERVICE_SONGS = "musicsongs"
        //播放
        @JvmField val MUSIC_ACTIVITY_SERVICE_PLAY = 1001
        //暂停
        @JvmField val MUSIC_ACTIVITY_SERVICE_PAUSE = 1002
        //继续播放
        @JvmField val MUSIC_ACTIVITY_SERVICE_REPLAY = 1003
        //更新歌曲列表
        @JvmField val MUSIC_ACTIVITY_SERVICE_UPDATE_SONGS = 999
    }


    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        mActivityIntent = Intent()

        //初始化mediaplayer
        mp = MediaPlayer()
        mp.setOnPreparedListener(this)
        mp.setOnCompletionListener(this)
        mp.setOnErrorListener(this)

        //注册广播
        var musicBroadcastReceiver:MusicBroadCast = MusicBroadCast()
        var intentFilter:IntentFilter = IntentFilter()
        intentFilter.addAction(MUSIC_SERVICE)
        registerReceiver(musicBroadcastReceiver, intentFilter)
    }

    //播放音乐
    open fun play(musicUrl:String) {
        mp.reset()
        currentTime = 0
        try {
            mp.setDataSource(applicationContext, Uri.parse(musicUrl))
            mp.prepareAsync()
        }
        catch (e:IOException) {
            e.printStackTrace()
        }
    }

    //暂停播放
    open fun pause() {
        if (mp.isPlaying) {
            currentTime = mp.currentPosition
            mp.pause()
        }
    }

    //继续播放
    open fun resume() {
        mp.start()
        if (currentTime > 0) {
            mp.seekTo(currentTime)
        }
    }

    //音乐停止
    open fun stop() {
        mp.stop()
        try {
            mp.prepare()
        }
        catch (e:IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 更新播放列表
     */
    open fun updateSongsList(songs:List<SongsModel.Datalist>) {
        this@MusicService.musics = songs
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != mp) {
            mp.stop()
            mp.release()
        }
    }

    override fun onPrepared(p0: MediaPlayer?) {
        resume()
    }

    override fun onCompletion(p0: MediaPlayer?) {
        currentTime = 0
        //继续播放下一首
        if(musicposition < musics.count() - 1) {
            musicposition++
        }
        play(musics[musicposition].music)
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 发送Model给MusicActivity
     */
    private fun sendModelToMusicActivity() {

    }

    //接收音乐界面回送的广播
    inner class MusicBroadCast : BroadcastReceiver() {

        private var flag = 0
        private var musictype = 0
        private var songs: SongsModel = SongsModel()

        override fun onReceive(context: Context?, intent: Intent?) {
            flag = intent!!.flags
            musictype = intent.getIntExtra(MUSIC_ACTIVITY_SERVICE_KEY, 0)
            musicposition = intent.getIntExtra(MUSIC_ACTIVITY_SERVICE_POSITION, -1)
            songs = intent.getSerializableExtra(MUSIC_ACTIVITY_SERVICE_SONGS) as SongsModel
            if (musictype > 0) {
                musicActivityService(musictype, musicposition, songs)
            }
        }

        /**
         * 来自MusciActivity的控制
         */
        private fun musicActivityService(musictype2:Int, position:Int, songs:SongsModel) {
            when(musictype2) {
                MUSIC_ACTIVITY_SERVICE_PLAY -> {
                    if (position < 0) return
                    this@MusicService.play(musics[position].music)
                }
                MUSIC_ACTIVITY_SERVICE_REPLAY -> {
                    this@MusicService.resume()
                }
                MUSIC_ACTIVITY_SERVICE_PAUSE -> {
                    this@MusicService.pause()
                }
                MUSIC_ACTIVITY_SERVICE_UPDATE_SONGS -> {
                    this@MusicService.updateSongsList(songs.datalist)
                }
            }
        }
    }
}