package com.q1.doradragon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameoverActivity extends AppCompatActivity {


    ImageView iv;
    TextView tvChampion;
    TextView tvYourScore;
    boolean isChamp=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        iv= findViewById(R.id.iv);
        tvChampion= findViewById(R.id.tv_champion);
        tvYourScore= findViewById(R.id.tv_yourScore);


        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("Data");

        int score= data.getInt("Score",0);
        int coin= data.getInt("Coin", 0);

        int yourscore = score + coin*10;
        String s = String.format("%07d", yourscore);
        tvYourScore.setText(s);
        if (yourscore > G.champion){

            G.champion= yourscore;
            isChamp=true;

        }
        s = String.format("%07d", G.champion);
        tvChampion.setText(s);

        //챔피언 이미지가 있는가?
        if(G.championImg!=null){
            Uri uri = Uri.parse(G.championImg);
            iv.setImageURI(uri);


        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }
    void saveData(){

        SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();

        editor.putInt("Gem",G.gem);
        editor.putInt("Champion", G.champion);
        editor.putBoolean("Music", G.isMusic);
        editor.putBoolean("Vibtrate", G.isVibrate);
        editor.putBoolean("Sound", G.isSound);

        editor.putString("ChampionImg", G.championImg);

        editor.commit();
    }

    public void clickRetry(View v){

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void  clickExit(View v){
        finish();

    }
    public void clickImg(View v){
        if(!isChamp) return;
        //디바이스의 사진을 선택하도록..

        //Gallery앱 이나 사진앱을 실행!
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);



    }

// startActivityForResult()로 실행한 액티비티가 종료되면 자동으로 실행되는 메소드


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){

                    Uri uri = data.getData();
                    if(uri != null){
                        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT);
                        G.championImg=uri.toString();

                        iv.setImageURI(uri);


                    }else{

                        Toast.makeText(this,"null", Toast.LENGTH_SHORT);
                        Bundle bundle = data.getExtras();
                        Bitmap bm = (Bitmap)bundle.get("data");

                        iv.setImageBitmap(bm);


                    }

                }
                break;
        }

    }
}
