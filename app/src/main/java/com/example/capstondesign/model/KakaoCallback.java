package com.example.capstondesign.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.capstondesign.controller.FastSignUpActivity;
import com.example.capstondesign.controller.Fragment_main;
import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.controller.SignUpActivity;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ApiErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class KakaoCallback {
    Profile profile = LoginAcitivity.profile;
    String name, gender, email;
    ISessionCallback sessionCallback;
    int login = LoginAcitivity.login;

    public ISessionCallback kakakoCallback(Context context, Activity activity) {
        new KakaoCallback(context, activity);
        return sessionCallback;
    }

    public KakaoCallback(Context context, Activity activity) {
        sessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        int result = errorResult.getErrorCode();

                        if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                            Toast.makeText(context, "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            activity.finish();
                        } else {
                            Toast.makeText(context, "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Toast.makeText(context, "세션이 닫혔습니다. 다시 시도해 주세요: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        if (result.getKakaoAccount().getProfile() != null) {
                            if (result.getKakaoAccount().getProfile().getNickname() != null) {
                                name = result.getKakaoAccount().getProfile().getNickname();
                                gender = result.getKakaoAccount().getGender().getValue();
                                email = result.getKakaoAccount().getEmail();


                                profile.setName(name);
                                profile.setGender(gender);
                                profile.setEmail(email);

                                CheckTask checkTask = new CheckTask();
                                if(gender.equals("male")) {
                                    gender = "남성";
                                } else if(gender.equals("female")) {
                                    gender = "여성";
                                }
                                login = 1;
                                LoginAcitivity.login = login;
                                try {
                                    String check;
                                    check = checkTask.execute(email).get();

                                    //회원가입 했는지 확인하는 부분
                                    new CheckTask.SignUpCheck(check, context, activity);

                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.d("Fail", "Fail");
                        }
                    }
                });

            }

            @Override
            public void onSessionOpenFailed(KakaoException e) {
                Toast.makeText(context, "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }


}
