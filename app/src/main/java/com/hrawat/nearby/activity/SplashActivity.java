package com.hrawat.nearby.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hrawat.nearby.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        final Animation animationZoom = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoomin);
        imageView.startAnimation(animationZoom);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                animationZoom.cancel();
                finish();
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        }, 5000);
    }
}
