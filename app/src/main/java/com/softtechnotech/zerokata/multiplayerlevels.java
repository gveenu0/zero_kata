package com.softtechnotech.zerokata;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class multiplayerlevels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayerlevels);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Button normal = findViewById(R.id.normal);

        Button easy = findViewById(R.id.easy);

        Button hard = findViewById(R.id.hard);

        Button mainmenu = findViewById(R.id.mainMenu);
        if (addFunc.btn_music) {
            startService(new Intent(multiplayerlevels.this, ServDsp.class));
        }

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(multiplayerlevels.this, multi_player.class));
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(multiplayerlevels.this, multi_player_easy.class));
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(multiplayerlevels.this, multi_player_hard.class));
            }
        });

        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(multiplayerlevels.this, StartPage.class));
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
            startService(new Intent(multiplayerlevels.this, ServDsp.class));
        }
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
