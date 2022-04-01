package com.softtechnotech.zerokata;

import android.util.Log;
import android.widget.ImageView;



class ImagesAndScores {
    int score;
    ImageView im;
    String name;
    String TAG="ImagesANDScores";
    ImagesAndScores(int score, ImageView im, String name) {
        this.score = score;
//        Log.d(TAG,"Score: "+score+"Box : "+name);
        this.im = im;
        this.name=name;
    }
}
