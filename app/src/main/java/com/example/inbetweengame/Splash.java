package com.example.inbetweengame;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        Handler animIn = new Handler();
        animIn.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.title);
                textView.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp).duration(1000).playOn(textView);
            }
        }, 500);

        Handler animOut = new Handler();
        animOut.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}