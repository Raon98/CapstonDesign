package com.example.capstondesign.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.InGroupBuyProfileCountTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;

import java.util.concurrent.ExecutionException;

public class in_setting extends AppCompatActivity {

    Button change_password, withdraw;
    String password;
    Profile profile = LoginAcitivity.profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_setting);

        change_password = findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeClickHandler();
            }
        });

        withdraw = findViewById(R.id.withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithdrawClickHandler();
            }
        });

    }

    public void ChangeClickHandler() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("비밀번호 변경").setMessage("비밀번호를 변경하시겠습니까??");

        builder.setPositiveButton("변경하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), in_change_password.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void WithdrawClickHandler() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("회원탈퇴").setMessage("비밀번호를 입력해주세요.");

        final EditText et = new EditText(in_setting.this);

        builder.setView(et);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPassword();
                if(et.getText().toString().equals(password)) {
                    Intent intent = new Intent(getApplicationContext(), in_withdraw.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호를 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void getPassword() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            password = profileTask.substringBetween(result, "password:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}