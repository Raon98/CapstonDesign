package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Board;
import com.example.capstondesign.model.BoardTask;
import com.example.capstondesign.model.ChangePasswordTask;
import com.example.capstondesign.model.NoticeTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.view.BoardAdapter;
import com.example.capstondesign.view.NoticeAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class in_notice extends AppCompatActivity {

    public static ArrayList<Board> board = new ArrayList<>();
    public static NoticeAdapter noticeAdapter;
    NoticeTask noticeTask;
    public String nick, title, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_notice);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //GALLERY(); // 허가
        board.clear();
        noticeTask = new NoticeTask();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        noticeAdapter = new NoticeAdapter(board);

        recyclerView.setAdapter(noticeAdapter);




        noticeAdapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                nick = NoticeAdapter.click_nickname;
                title = NoticeAdapter.click_title;
                text = NoticeAdapter.click_text;
                Intent intent = new Intent(getApplicationContext(), notice_inside.class);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });

    }

}

