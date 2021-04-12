package com.example.inbetweengame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class Popup extends Activity implements View.OnClickListener {

    public int pocket;
    public int bet;
    public Button bet10, bet25, bet50, bet100, bet500, betAll, btnClear, btnSet;
    public TextView txtBet;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.popup_bet);

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = displayMetrics.widthPixels;
//        int height = displayMetrics.heightPixels;
//        getWindow().setLayout((int)(width*.8),(int)(height*.9));



        bet10 = findViewById(R.id.bet10);
        bet25 = findViewById(R.id.bet25);
        bet50 = findViewById(R.id.bet50);
        bet100 = findViewById(R.id.bet100);
        bet500 = findViewById(R.id.bet500);
        betAll = findViewById(R.id.betAll);
        btnClear = findViewById(R.id.clear);
        btnSet = findViewById(R.id.set);
        txtBet = findViewById(R.id.txtBet);

        bet10.setOnClickListener(this);
        bet25.setOnClickListener(this);
        bet50.setOnClickListener(this);
        bet100.setOnClickListener(this);
        bet500.setOnClickListener(this);
        betAll.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnSet.setOnClickListener(this);


        bet = getIntent().getIntExtra("betValue",bet);
        pocket = getIntent().getIntExtra("pocketValue",pocket);
        txtBet.setText(String.valueOf(bet));



    }

    @Override
    public void onClick(View v) {
        TextView txtBet = findViewById(R.id.txtBet);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.button);
        mp.start();
        switch (v.getId())
        {
            case R.id.bet10:
                bet = bet+10;
                YoYo.with(Techniques.Pulse).duration(300).playOn(bet10);
                break;
            case R.id.bet25:
                bet = bet+25;
                YoYo.with(Techniques.Pulse).duration(300).playOn(bet25);
                break;
            case R.id.bet50:
                bet = bet+50;
                YoYo.with(Techniques.Pulse).duration(300).playOn(bet50);
                break;
            case R.id.bet100:
                bet = bet+100;
                YoYo.with(Techniques.Pulse).duration(300).playOn(bet100);
                break;
            case R.id.bet500:
                bet = bet+500;
                YoYo.with(Techniques.Pulse).duration(300).playOn(bet500);
                break;
            case R.id.betAll:
                bet = pocket;
                YoYo.with(Techniques.Pulse).duration(300).playOn(betAll);
                break;
            case R.id.clear:
                bet = 0;
                YoYo.with(Techniques.Pulse).duration(300).playOn(btnClear);
                break;
            case R.id.set:
                Intent intent = new Intent();
                intent.putExtra("betValue", bet);
                setResult(RESULT_OK, intent);
                finish();
                break;

        }
        if (bet > pocket )
        {
            bet = 0;
            Toast.makeText(getApplicationContext(), "Insufficient Money", Toast.LENGTH_SHORT).show();
        }
        txtBet.setText(String.valueOf(bet));
    }

}
