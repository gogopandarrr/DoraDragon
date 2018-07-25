package com.q1.doradragon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp= MediaPlayer.create(this, R.raw.dragon_flight);
        mp.setLooping(true);
    }

    @Override
    protected void onPause() {
        if(mp!=null && mp.isPlaying()){
            mp.pause();


        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(mp!=null){

            mp.stop();
            mp.release();
            mp=null;

        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {

        if(G.isMusic){

            mp.setVolume(0.5f, 0.5f);
        }else {
            mp.setVolume(0, 0);
        }
        mp.start();
        super.onResume();

    }

    public void clickStart (View v){

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

        mp= MediaPlayer.create(this, R.raw.dragon_flight); //쓰레드랑 같다.
        mp.setLooping(true);
        mp.setVolume(0.5f, 0.5f);
        mp.start();

    }

    public void clickExit (View v){

        finish();

    }
}
