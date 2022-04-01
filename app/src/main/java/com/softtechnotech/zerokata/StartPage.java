package com.softtechnotech.zerokata;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.softtechnotech.zerokata.addFunc;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class StartPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        if (addFunc.btn_music && !isMyServiceRunning(ServDsp.class)) {
            startService(new Intent(StartPage.this, ServDsp.class));
        }
        Button multiPlayer = findViewById(R.id.multiPlayer);
        multiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartPage.this, multiplayerlevels.class));
            }
        });
        Button singlePlayer = findViewById(R.id.singlePlayer);
        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartPage.this, singleplayerlevels.class));
            }
        });
        Button soundoff = findViewById(R.id.soundoff);
        soundoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addFunc.btn_music){
                    addFunc.btn_music = false;
                    stopService(new Intent(StartPage.this, ServDsp.class));
                }
                else{
                    addFunc.btn_music = true;
                    startService(new Intent(StartPage.this, ServDsp.class));
                }
            }
        });
        Button highscore = findViewById(R.id.highscore);
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartPage.this, highscores.class));
            }
        });
    }
    public void onPause(){
        super.onPause();
        if (addFunc.btn_music) {
            stopService(new Intent(this, ServDsp.class));
        }
    }
    public void onResume(){
        super.onResume();
        if (addFunc.btn_music && !isMyServiceRunning(ServDsp.class)) {
            startService(new Intent(StartPage.this, ServDsp.class));
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(new Intent(StartPage.this, ServDsp.class));
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
            if (serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
