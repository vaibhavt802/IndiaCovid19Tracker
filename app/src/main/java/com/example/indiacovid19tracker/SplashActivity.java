package com.example.indiacovid19tracker;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    ImageView splashImage;
    TextView tvIndias, tvCovid, tvTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImage = findViewById(R.id.ivcorona);
        tvIndias = findViewById(R.id.tvindias);
        tvCovid = findViewById(R.id.tvcovid);
        tvTracker = findViewById(R.id.tvtracker);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splashanim);
        splashImage.startAnimation(animation);
        Animation bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottomanim);
        tvIndias.startAnimation(bottomAnim);
        tvCovid.startAnimation(bottomAnim);
        tvTracker.startAnimation(bottomAnim);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
