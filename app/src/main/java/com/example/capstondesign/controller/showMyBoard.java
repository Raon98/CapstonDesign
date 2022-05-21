package com.example.capstondesign.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Board;
import com.example.capstondesign.model.BoardTimejsonTask;
import com.example.capstondesign.model.Groupbuying;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.ShowBoardTask;
import com.example.capstondesign.model.ShowGroupBuyingTask;
import com.example.capstondesign.model.ShowGroupBuyingjson;
import com.example.capstondesign.view.BoardAdapter;
import com.example.capstondesign.view.ShowBoardAdapter;
import com.example.capstondesign.view.ShowGroupBuyingAdapter;
import com.example.capstondesign.view.WatchlistAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class showMyBoard extends AppCompatActivity {
    public String mynick, nick, title, text, area, price, headCount, nowCount, watchnick;
    ImageView buysearch;
    public static Uri image;
    Profile profile = LoginAcitivity.profile;
    Button back;
    public static int position;

    public static ShowBoardAdapter showBoardAdapter;
    ShowBoardTask showBoardTask;
    public static ArrayList<Board> showboard = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_board);

        RecyclerView recyclerView = findViewById(R.id.rvshowboard);
        getNick();
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        back = findViewById(R.id.listbackshowboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //GALLERY(); // 허가
        showboard.clear();
        showBoardTask = new ShowBoardTask();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        showBoardAdapter = new ShowBoardAdapter(showboard);
        recyclerView.setAdapter(showBoardAdapter);

        showBoardAdapter.setOnItemClickListener(new ShowBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                nick = ShowBoardAdapter.click_nickname;
                title = ShowBoardAdapter.click_title;
                text = ShowBoardAdapter.click_text;
                getPosition(position);
                Intent intent = new Intent(getApplicationContext(), FreeBoard_showBoard.class);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });



        buysearch = (ImageView) findViewById(R.id.show_board_search);

        buysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getPosition(int position) {
        this.position = position;
    }

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            mynick = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}