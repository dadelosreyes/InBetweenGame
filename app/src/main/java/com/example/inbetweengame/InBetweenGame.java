package com.example.inbetweengame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class InBetweenGame extends AppCompatActivity implements View.OnClickListener {


    public Button btnShow, btnFold, btnBet, btnPopup, btnHigher, btnLower;
    public TextView txtPocket;
    public ImageView imvFirst;
    public ImageView imvSecond;
    public ImageView imvPlayer;
    public int bet = 0;
    public int pocket = 100;
    public int first = 0;
    public int second = 0;
    public int player = 0;


// add credit feature



    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
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
        setContentView(R.layout.activity_in_between_game);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        txtPocket = findViewById(R.id.pocket);


        btnBet = findViewById(R.id.bet);
        btnFold = findViewById(R.id.fold);
        btnShow = findViewById(R.id.reveal);
        btnPopup = findViewById(R.id.bet_popup);
        btnHigher = findViewById(R.id.higher);
        btnLower = findViewById(R.id.lower);

        btnBet.setOnClickListener(this);
        btnFold.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnPopup.setOnClickListener(this);
        btnHigher.setOnClickListener(this);
        btnLower.setOnClickListener(this);

        btnBet.setEnabled(false);
        btnFold.setEnabled(false);

        txtPocket.setText(String.valueOf(pocket));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                bet = data.getIntExtra("betValue",bet);
                btnPopup.setText(String.valueOf(bet));
            }
        }

    }

    public static int RandomInt()
    {
        Random rand = new Random();
        return rand.nextInt(12);
    }

    public void getCard(ImageView imv,Integer number)
    {
        int[] Cards = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six,
                R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten, R.drawable.eleven, R.drawable.twelve,
                R.drawable.thirteen,R.drawable.back};
        imv.setImageResource(Cards[number]);
    }



    @Override
    public void onClick(View v)
    {
        imvFirst = findViewById(R.id.firstCard);
        imvSecond = findViewById(R.id.secondCard);
        imvPlayer = findViewById(R.id.playerCard);
        switch (v.getId())
        {
            case R.id.reveal:
                revealCard();
                break;
            case R.id.bet:
                Play();
                break;
            case R.id.fold:
                playSfx(4);
                btnBet.setEnabled(false);
                btnBet.setFocusable(false);
                btnShow.setEnabled(true);
                btnShow.setFocusable(false);
                btnFold.setEnabled(false);
                btnFold.setFocusable(false);
                btnPopup.setText("Click here to set your bet");
                YoYo.with(Techniques.Pulse).duration(200).playOn(btnFold);
                player = 0;
                first = 0;
                second = 0;
                bet = 0;
                getCard(imvFirst,13);
                getCard(imvPlayer,13);
                getCard(imvSecond,13);
                break;
            case R.id.bet_popup:
                playSfx(4);
                YoYo.with(Techniques.Pulse).duration(300).playOn(btnPopup);
                Intent intent = new Intent(InBetweenGame.this,Popup.class);
                intent.putExtra("betValue",bet);
                intent.putExtra("pocketValue",pocket);
                startActivityForResult(intent,0);
                break;
            case R.id.higher:
                highLow(1);
                break;
            case R.id.lower:
                highLow(0);
                break;
        }
        txtPocket.setText(String.valueOf(pocket));
        System.out.println("First: "+first);
        System.out.println("player: "+player);
        System.out.println("second: "+second);
    }

    void Play()
    {
        if (bet == 0)
        {
            playSfx(6);
            playAnimation(1);
        }
        else
        {
            playSfx(4);
            player = RandomInt();
            getCard(imvPlayer,player);

            btnBet.setEnabled(false);
            btnBet.setFocusable(false);
            btnShow.setEnabled(true);
            btnShow.setFocusable(false);
            btnFold.setEnabled(false);
            btnFold.setFocusable(false);
            YoYo.with(Techniques.Pulse).duration(300).playOn(btnBet);

            if ((first > player && second < player) || (second > player && first < player))//(player > first && player < second) || (player < first  && player < second)
            {
                winOrLose(1);
            }
            else
            {
                winOrLose(0);
            }
            if (pocket == 0)
            {
                playAnimation(2);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gameOver();
                        System.out.println("lost");
                    }
                },4000);
            }
            else
            {
                //txtPocket.setText(String.valueOf(pocket));
                btnBet.setEnabled(false);
                btnShow.setEnabled(false);
                btnFold.setEnabled(false);
                bet = 0;
                Handler animIn = new Handler();
                animIn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCard(imvFirst,13);
                        getCard(imvSecond,13);
                        getCard(imvPlayer,13);
                        btnShow.setEnabled(true);
                        System.out.println("eto nlabas");
                        btnPopup.setText("Click here to set your bet");
                        System.out.println("continue");
                    }
                }, 2000);
            }
        }


    }

    public void revealCard()
    {
        YoYo.with(Techniques.Pulse).duration(200).playOn(btnShow);
        if (bet == 0)
        {
            playSfx(6);
            playAnimation(1);
        }
        else
            {
                playSfx(4);
                if (pocket == 0)
                {
                    bet = 0;
                    //invalid animations
                    Toast.makeText(getApplicationContext(), "Insufficient Money", Toast.LENGTH_SHORT).show();
                }
                else if (bet > pocket)
                {
                    bet = 0;
                    //invalid animations
                    Toast.makeText(getApplicationContext(), "Your bet is larger than your money", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    btnShow.setEnabled(false);
                    btnBet.setEnabled(true);
                    btnFold.setEnabled(true);
                    first = RandomInt();
                    second = RandomInt();
//            first = 5;
//            second = 5;
                    while (first == 1 && second == 1 || second == first + 1 || first == second + 1 || first == 13 && second == 13)
                    {
                        first = RandomInt();
                        second = RandomInt();
                    }
                    if (first == second)
                    {
                        btnBet.setVisibility(View.INVISIBLE);
                        btnFold.setVisibility(View.INVISIBLE);
                        btnShow.setVisibility(View.INVISIBLE);
                        btnLower.setVisibility(View.VISIBLE);
                        btnHigher.setVisibility(View.VISIBLE);
                        getCard(imvFirst,first);
                        getCard(imvSecond,second);
                        Toast.makeText(getApplicationContext(), "HighLOW", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        getCard(imvFirst,first);
                        getCard(imvSecond,second);
                    }
                }
            }
    }

    public void gameOver()
    {
        playSfx(5);
        Intent intent = new Intent(getApplicationContext(),GameOver.class);
        startActivity(intent);

    }
    void highLow(int value)
    {
        playSfx(4);
        player = RandomInt();
        getCard(imvPlayer,player);
        if(value == 0)
        {

            if (player < first)
            {
               winOrLose(1);
            }
            else {
                winOrLose(0);
            }
        }
        else if (value == 1)
        {
            if (player > first)
            {
                winOrLose(1);
            }
            else {
                winOrLose(0);
            }
        }
        if (pocket == 0)
        {
            playAnimation(2);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gameOver();
                    System.out.println("lost");
                }
            },4000);
        }
        btnBet.setVisibility(View.VISIBLE);
        btnFold.setVisibility(View.VISIBLE);
        btnShow.setVisibility(View.VISIBLE);
        btnLower.setVisibility(View.INVISIBLE);
        btnHigher.setVisibility(View.INVISIBLE);
        btnBet.setEnabled(false);
        btnFold.setEnabled(false);
        Handler animIn = new Handler();
        animIn.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCard(imvFirst,13);
                getCard(imvSecond,13);
                getCard(imvPlayer,13);
                btnShow.setEnabled(true);
            }
        }, 3000);
    }
    void playSfx(int sfxCode)
    {
        MediaPlayer mp;
        if (sfxCode == 0){
            mp = MediaPlayer.create(this, R.raw.cardgive2);
        }
        else if (sfxCode == 1){
            mp = MediaPlayer.create(this, R.raw.win_coins);
        }
        else if (sfxCode == 2){
            int code = RandomInt();
            if(code % 2 == 0)
            {
                mp = MediaPlayer.create(this, R.raw.lose);
            }
            else {
                mp = MediaPlayer.create(this, R.raw.lose2);
            }
            mp.start();
        }
        else if (sfxCode == 3){
            mp = MediaPlayer.create(this, R.raw.cardgive2);
        }
        else if (sfxCode == 4){
            //buttons
            mp = MediaPlayer.create(this, R.raw.button);
        }
        else if (sfxCode == 5){

            if(bet < 1000)
            {
                mp = MediaPlayer.create(this, R.raw.gameover);
            }
            else {
                mp = MediaPlayer.create(this, R.raw.gameover2);
            }
            mp.start();
        }
        else if (sfxCode == 6)
        {
            mp = MediaPlayer.create(this, R.raw.invalid);
        }
        else
            {
                //bg
                mp=MediaPlayer.create(this, R.raw.bgmusic);
            }
        mp.start();


    }
    void playAnimation(int animationCode)
    {
        switch (animationCode)
        {
            case 0:
                //win
                YoYo.with(Techniques.Tada).duration(300).playOn(btnPopup);
                break;
            case 1:
                //lose
                YoYo.with(Techniques.Shake).duration(300).playOn(btnPopup);
                break;
            case 2:
                //GameOver
                int duration = 2000;
                YoYo.with(Techniques.SlideOutDown).duration(duration).playOn(btnPopup);
                YoYo.with(Techniques.SlideOutDown).duration(duration).playOn(btnBet);
                YoYo.with(Techniques.SlideOutDown).duration(duration).playOn(btnFold);
                YoYo.with(Techniques.SlideOutDown).duration(duration).playOn(btnShow);
                YoYo.with(Techniques.SlideOutDown).duration(duration).playOn(imvFirst);
                YoYo.with(Techniques.SlideOutDown).duration(duration).playOn(imvPlayer);
                YoYo.with(Techniques.SlideOutDown).duration(duration).playOn(imvSecond);
                YoYo.with(Techniques.SlideOutDown).duration(duration).playOn(txtPocket);
                break;
        }
    }
    void winOrLose(int condition)
    {
        if (condition == 0)
        {
            pocket-=bet;
            playSfx(2);
            Toast.makeText(getApplicationContext(), "You lost $"+String.valueOf(bet), Toast.LENGTH_SHORT).show();
            txtPocket.setText(String.valueOf(pocket));
            //btnPopup.setText(String.valueOf(pocket));
            System.out.println("lost");
        }
        else if (condition  == 1)
        {
            pocket+=bet;
            playSfx(1);
            playSfx(1);
            playAnimation(0);
            Toast.makeText(getApplicationContext(), "you won", Toast.LENGTH_SHORT).show();
            System.out.println("won");
        }
    }
}



//sfx
// lose animation: shake animator: done
//win animation: Tada animator : done
// main menu animation : bounce in
//card animation: flipOutY, alternative : Slide in Down
//gameover animation: bounceIn = textview, slide in up = button



