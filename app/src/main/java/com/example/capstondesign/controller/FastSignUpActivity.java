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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.view.ChatAdapter;
import com.example.capstondesign.model.CheckTask;
import com.example.capstondesign.model.NickCheckTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.SignUpTask;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FastSignUpActivity extends AppCompatActivity {
    TextView name, email;
    EditText password, nickname, passwordCheck;
    RadioGroup gender;
    RadioButton radioButton;
    Context context;
    Activity activity;
    Button sign_up, sign_cancel, nick_check, phone_check;
    Button auth_check;

    Boolean nick_click = false;
    Profile profile = LoginAcitivity.profile;

    private String verificationId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    CheckTask.Logout logout;

    String phoneNum, phone;

    TextView re_check, delay_tv;
    EditText auth,phone_num;
    private int mnMilliSecond = 1000;
    private int value;
    private int mnExitDelay = 60;

    Boolean check;

    private CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fast_signup_page);

        AuthCodeTimer();

        check = false;

        context = this;
        activity = FastSignUpActivity.this;

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone_num = (EditText) findViewById(R.id.phone_num);
        gender = (RadioGroup) findViewById(R.id.gender);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        nickname = (EditText) findViewById(R.id.nickname);
        sign_up = (Button) findViewById(R.id.sign_up);
        sign_cancel = (Button) findViewById(R.id.cancel);
        nick_check = (Button) findViewById(R.id.nick_check);
        phone_check = (Button) findViewById(R.id.authClick);
        re_check = findViewById(R.id.re_authClick);
        delay_tv = findViewById(R.id.DelayTextView);
        auth_check = (Button) findViewById(R.id.auth_check);
        auth = findViewById(R.id.auth);

        //간편로그인으로 가져온 값들을 세팅해줌
        name.setText(profile.getName());
        email.setText(profile.getEmail());
        if(profile.getGender().equals("M")||profile.getGender().equals("male")) {
            gender.check(R.id.male);
            radioButton = (RadioButton) findViewById(R.id.male);
        } else {
            gender.check(R.id.female);
            radioButton = (RadioButton) findViewById(R.id.female);
        }

        phone_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone_num.length() > 6) {
                    Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다. 60초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();
                    phoneNum = phone_num.getText().toString();
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

        //재전송 클릭
        re_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone_num.length() > 6) {
                    Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다. 60초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();
                    phoneNum = phone_num.getText().toString();
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



        //취소를 누를 경우 간편 로그인을 로그아웃함.
        sign_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int login = LoginAcitivity.login;

                if(login == 2) {
                    //네이버 로그인시 login 값은 2
                    OAuthLogin mOAuthLoginModule;
                    mOAuthLoginModule = OAuthLogin.getInstance();
                    mOAuthLoginModule.init(
                            getApplicationContext()
                            ,getString(R.string.naver_client_id)
                            ,getString(R.string.naver_client_secret)
                            ,getString(R.string.naver_client_name)
                    );
                    mOAuthLoginModule.logout(getApplicationContext());
                    logout = new CheckTask.Logout(context, activity);
                    Toast.makeText(context , "회원가입 취소", Toast.LENGTH_SHORT).show();

                } else if(login == 1) {
                    //카카오 로그인시 login 값은 1
                    Toast.makeText(context , "회원가입 취소", Toast.LENGTH_SHORT).show();
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            logout = new CheckTask.Logout(context, activity);
                        }
                    });

                } else if(login==3) {
                    //페이스북 로그인시 login 값은 3
                    LoginManager.getInstance().logOut();
                    logout = new CheckTask.Logout(context, activity);
                    Toast.makeText(context , "회원가입 취소", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nick_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext() , radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String userEmail_front = email_front(email.getText().toString());
                String userEmail_end = email_end(email.getText().toString());
                String userNum = phone;
                String userNickname = nickname.getText().toString();
                String userPassword = password.getText().toString();
                String passwordcheck = passwordCheck.getText().toString();

                if(username.trim().length()>0 && userPassword.trim().length()>0
                        && userNickname.trim().length()>0 && userPassword.equals(passwordcheck) && nick_click) {
                    try {
                        String result;
                        SignUpTask task = new SignUpTask();
                        result = task.execute(username, userNum, userEmail_front,  userEmail_end, userNickname ,userPassword, radioButton.getText().toString()).get();
                        ChatAdapter.nick = userNickname;
                        new SignUpTask.DuplicateCheck(result, context, activity);

                    } catch (Exception e) {

                    }

                } else if (!userPassword.equals(passwordcheck)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (!nick_click) {
                    Toast.makeText(getApplicationContext(), "닉네임 중복 체크를 해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!check) {
                    Toast.makeText(getApplicationContext(), "휴대폰 인증을 해주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    String email_front(String email) {
        return email.substring(0 ,email.lastIndexOf("@"));
    }

    String email_end(String email) {
        return email.substring(email.lastIndexOf("@")+1);
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
