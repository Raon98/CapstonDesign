package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Phone_check extends AppCompatActivity {

    Button auth_check;
    private String verificationId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    EditText auth,phone_num;
    String phoneNum;
    TextView phone_check,re_check, delay_tv;
    public static String phone;
    public static Boolean check = false;
    private int mnMilliSecond = 1000;
    private int value;
    private int mnExitDelay = 60;


    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_check);

        AuthCodeTimer();

        Log.d("AUTH", mAuth.toString());

        phone_check = (TextView) findViewById(R.id.authClick);
        re_check = (TextView) findViewById(R.id.re_authClick);
        delay_tv = (TextView) findViewById(R.id.DelayTextView);
        auth = (EditText) findViewById(R.id.auth);
        phone_num = (EditText) findViewById(R.id.phone_num);
        auth_check = (Button) findViewById(R.id.auth_check);

        //휴대폰 인증버튼 클릭
        phone_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //휴대폰 인증번호 재전송
        re_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone_num.length() > 6) {
                    Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다. 60초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();
                    sendVerificationCode(phone_num.getText().toString());
                    if(timer != null) timer.cancel();
                    timer.start();
                } else {
                    Toast.makeText(getApplicationContext(), "휴대전화 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //인증하기 클릭
        auth_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = auth.getText().toString();
                if(code.isEmpty() || code.length()<6) {
                    Toast.makeText(getApplicationContext(),"인증번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                verifyCode(code);
            }
        });

    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {

            String code = credential.getSmsCode();
            if(code != null) {
                auth.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            // Invalid request
            // The SMS quota for the project has been exceeded
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String verificationid,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationid, token);
            verificationId = verificationid;

        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            phone = phone_num.getText().toString();
                            check = true;
                            finish();
                            Toast.makeText(getApplicationContext(), "인증성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    public void AuthCodeTimer() {
        int delay = mnExitDelay * mnMilliSecond;

        timer = new CountDownTimer(delay, 1000) {
            @Override
            public void onTick(long l) {
                long k =  l / 1000;
                delay_tv.setVisibility(View.VISIBLE);
                if(k >= 10) {
                    delay_tv.setText("00 : " + k);
                } else {
                    delay_tv.setText(("00 : 0" + k));
                }
            }

            @Override
            public void onFinish() {
                Log.d("FINISH", "FINISH");
                delay_tv.setText("");
                delay_tv.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "제한시간 종료", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
