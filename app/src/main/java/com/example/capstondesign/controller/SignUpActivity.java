package com.example.capstondesign.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.EmailCheckTask;
import com.example.capstondesign.view.ChatAdapter;
import com.example.capstondesign.model.NickCheckTask;
import com.example.capstondesign.model.SignUpTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;


import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    EditText name, password, nickname, passwordCheck, email;
    RadioGroup gender;
    RadioButton radioButton;
    Button auth_check, email_check;
    String phoneNum, phone;
    private String verificationId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button sign_up, cancel, nick_check, phone_check;
    Boolean nick_click = false;
    Boolean gender_click = false;
    Context context;
    Activity activity;
    TextView re_check, delay_tv;
    EditText auth,phone_num;
    private int mnMilliSecond = 1000;
    private int value;
    private int mnExitDelay = 60;


    TextView phone_num_auth, auth_finish;

    Boolean check;
    Boolean email_click;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        AuthCodeTimer();

        check = false;
        email_click = false;

        phone_num_auth = (TextView) findViewById(R.id.phone_num_clear);
        auth_finish = (TextView) findViewById(R.id.auth_clear);


        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email_name);

        phone_check = (Button) findViewById(R.id.authClick);
        gender = (RadioGroup) findViewById(R.id.gender);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        nickname = (EditText) findViewById(R.id.nickname);

        nick_check = (Button) findViewById(R.id.nick_check);
        email_check = (Button) findViewById(R.id.email_check);
        phone_num = (EditText) findViewById(R.id.phone_num);

        re_check = (Button) findViewById(R.id.re_authClick);
        auth_check = (Button) findViewById(R.id.auth_check);
        auth = (EditText) findViewById(R.id.auth);
        delay_tv = (TextView) findViewById(R.id.DelayTextView);

        sign_up = (Button) findViewById(R.id.sign_up);
        cancel = (Button) findViewById(R.id.cancel);

        context = this;
        activity = SignUpActivity.this;


        phone_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone_num.length() > 6) {
                    Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다. 60초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();
                    phoneNum = phone_num.getText().toString();
                    Log.d("NUM", "+82"+phoneNum.substring(1));
                    sendVerificationCode("+82"+phoneNum.substring(1));
                    if(timer != null) timer.cancel();
                    timer.start();
                    phone_check.setVisibility(View.GONE);
                    re_check.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "휴대전화 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //휴대폰 인증번호 재전송
        re_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone_num.length() > 6) {
                    Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다. 60초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();
                    sendVerificationCode("+82"+phoneNum.substring(1));
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

        nick_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nick_check();
            }
        });

        email_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_check();
            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                gender_click = true;
                radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sign_up();
            }


        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginAcitivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                LoginAcitivity.login = 0;
                finish();
            }
        });

    }

    //이메일 중복확인 버튼 메소드
    void email_check() {
        String userEmail = email.getText().toString();
        if (userEmail.trim().length() > 0 && userEmail.contains("@")) {
            String result;
            EmailCheckTask task = new EmailCheckTask();
            try {
                result = task.execute(userEmail).get();
                if (result.contains("sameEmail")) {
                    Toast.makeText(getApplicationContext(), "중복된 이메일입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    email_click = true;
                    Toast.makeText(getApplicationContext(), "사용가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getApplicationContext(), "이메일을 정확히 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }



    void nick_check() {
        String userNickname = nickname.getText().toString();
        if (userNickname.trim().length() > 0) {
            String result;
            NickCheckTask task = new NickCheckTask();
            try {
                result = task.execute(userNickname).get();
                if (result.contains("sameNick")) {
                    Toast.makeText(getApplicationContext(), "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    nick_click = true;
                    Toast.makeText(getApplicationContext(), "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getApplicationContext(), "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }


    void sign_up() {
        Log.d("ABC", "ABC");
        String username = name.getText().toString();
        String useremail = email.getText().toString();
        String userNickname = nickname.getText().toString();
        String userPassword = password.getText().toString();
        String passwordcheck = passwordCheck.getText().toString();

        Log.d("BOOLEAN", String.valueOf(check));

        if (username.trim().length() > 0 && useremail.trim().length() > 0 && useremail.contains("@") && userPassword.trim().length() > 0
                && userNickname.trim().length() > 0 && userPassword.equals(passwordcheck) && gender_click && email_click && nick_click && check) {

            try {
                String result;
                SignUpTask signUpTask = new SignUpTask();
                String userNum = phone;
                String useremail_front = useremail.substring(0,useremail.indexOf("@"));
                String useremail_end = useremail.substring(useremail.indexOf("@")+1);
                result = signUpTask.execute(username, userNum, useremail_front, useremail_end, userNickname, userPassword, radioButton.getText().toString()).get();
                ChatAdapter.nick = userNickname;
                new SignUpTask.DuplicateCheck(result, context, activity);

            } catch (Exception ignored) {

            }

        } else if (!email_click) {
            Toast.makeText(getApplicationContext(), "이메일 중복 체크를 해주세요.", Toast.LENGTH_SHORT).show();
        } else if (!nick_click) {
            Toast.makeText(getApplicationContext(), "닉네임 중복 체크를 해주세요.", Toast.LENGTH_SHORT).show();
        } else if (!userPassword.equals(passwordcheck)) {
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        } else if (!check) {
            Toast.makeText(getApplicationContext(), "휴대폰 인증을 해야 합니다.", Toast.LENGTH_SHORT).show();
        }  else {
            Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
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

                phone = phone_num.getText().toString();
                check = true;

                timer.onFinish();

                //기존 휴대폰 인증 부분 사라지는 곳
                re_check.setVisibility(View.GONE);
                auth_check.setVisibility(View.GONE);
                auth.setVisibility(View.GONE);
                phone_num.setVisibility(View.GONE);
                delay_tv.setVisibility(View.GONE);

                //휴대폰 번호 텍스트 뷰로 생기는 곳
                phone_num_auth.setVisibility(View.VISIBLE);
                phone_num_auth.setText(phone);
                auth_finish.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "인증성공", Toast.LENGTH_SHORT).show();
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

                            timer.onFinish();

                            //기존 휴대폰 인증 부분 사라지는 곳
                            re_check.setVisibility(View.GONE);
                            auth_check.setVisibility(View.GONE);
                            auth.setVisibility(View.GONE);
                            phone_num.setVisibility(View.GONE);
                            delay_tv.setVisibility(View.GONE);

                            //휴대폰 번호 텍스트 뷰로 생기는 곳
                            phone_num_auth.setVisibility(View.VISIBLE);
                            phone_num_auth.setText(phone);
                            auth_finish.setVisibility(View.VISIBLE);

                            check = true;
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
                delay_tv.setVisibility(View.GONE);
                if(check = false) {
                    Toast.makeText(getApplicationContext(), "제한시간 종료", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }



}
