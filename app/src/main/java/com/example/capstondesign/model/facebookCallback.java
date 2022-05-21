package com.example.capstondesign.model;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.capstondesign.controller.LoginAcitivity;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class facebookCallback implements FacebookCallback<LoginResult> {
    Profile profile = LoginAcitivity.profile;
    Context context;
    Activity activity;
    String name, email, gender;

    public facebookCallback(Activity activity1, Context context1) {
        activity = activity1;
        context = context1;
    }
    @Override
    public void onSuccess(LoginResult loginResult)
    {
        Log.e("Callback :: ", "onSuccess");
        requestMe(loginResult.getAccessToken());
    }

    @Override
    public void onCancel()
    {
        Log.e("Callback :: ", "onCancel");
    }

    @Override
    public void onError(FacebookException error)
    {
        Log.e("Callback :: ", "onError : " + error.getMessage());
    }

    // 사용자 정보 요청
    public void requestMe(AccessToken token)
    {
        GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        try {
                            email = object.getString("email");
                            profile.setEmail(email);

                            name = object.getString("name");
                            profile.setName(name);

                            gender = object.getString("gender");
                            if(gender.equals("male")) {
                                gender = "남성";
                            } else if(gender.equals("female")) {
                                gender = "여성";
                            }
                            LoginAcitivity.login = 3;
                            profile.setGender(gender);
                            CheckTask checkTask = new CheckTask();
                            String check;
                            check = checkTask.execute(email).get();

                            //회원가입 했는지 확인하는 부분
                            new CheckTask.SignUpCheck(check, context, activity);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }


}
