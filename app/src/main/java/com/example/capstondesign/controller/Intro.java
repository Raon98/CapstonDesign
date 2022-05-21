package com.example.capstondesign.controller;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.capstondesign.R;
import com.facebook.login.Login;

import java.security.MessageDigest;

public class Intro extends AppCompatActivity {

    private LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        animationView = findViewById(R.id.loading);

        animationView.setVisibility(animationView.VISIBLE);
        animationView.playAnimation();
        Handler handler = new Handler(); //객체생성
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Intro.this, Fragment_main.class);
                startActivity(intent);
                finish();
            }

        },2400);
    }

}
