package com.example.capstondesign.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.ChangePasswordTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;

import java.util.concurrent.ExecutionException;

public class in_change_password extends AppCompatActivity {

    EditText password, change_password, change_password_re;
    Button change, cancel;
    ChangePasswordTask changePasswordTask;
    String str_pwd, str_change_pwd, str_change_pwd_re, nickname;
    Profile profile = LoginAcitivity.profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_change_password);

        getNick();

        password = findViewById(R.id.password);
        change_password = findViewById(R.id.change_password);
        change_password_re = findViewById(R.id.change_password_re);

        change = findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_pwd = password.getText().toString();
                str_change_pwd = change_password.getText().toString();
                str_change_pwd_re = change_password_re.getText().toString();

                if (str_pwd != null && str_change_pwd != null) {

                    Log.d("NICK", nickname);
                    Log.d("TEST", str_pwd);
                    Log.d("TEST", str_change_pwd);
                    Log.d("TEST2", str_change_pwd_re);

                    try {
                        if (str_change_pwd.equals(str_change_pwd_re)) {
                            changePasswordTask = new ChangePasswordTask();
                            String result = changePasswordTask.execute(nickname, str_pwd, str_change_pwd).get();
                            if (result.contains("비밀번호 변경완료")) {
                                Toast.makeText(getApplicationContext(), "비밀번호 변경완료", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (result.contains("비밀번호 불일치")) {
                                Toast.makeText(getApplicationContext(), "현재 비밀번호를 잘못 입력하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "입력한 비밀번호가 서로 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            nickname = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

