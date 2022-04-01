package com.softtechnotech.zerokata;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ServDsp extends Service {
    MediaPlayer mp;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate(){
        mp = MediaPlayer.create(this,R.raw.mybackground);
        mp.setLooping(true);
    }
    public void onDestroy(){
        mp.stop();
    }
    public void onStart(Intent intent,int startid){
        mp.start();
    }
}
