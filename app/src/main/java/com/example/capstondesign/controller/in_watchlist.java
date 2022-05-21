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
import com.example.capstondesign.model.Groupbuying;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.WatchlistCountjson;
import com.example.capstondesign.view.WatchlistAdapter;
import com.example.capstondesign.model.WatchlistTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class in_watchlist extends AppCompatActivity {

    public String mynick, nick, title, text, area, price, headCount, nowCount, watchnick;
    ImageView buysearch;
    ImageView buynotify;
    public static Uri image;
    Profile profile = LoginAcitivity.profile;
    Button back;
    public static int position;

    public static WatchlistAdapter watchlistAdapter;
    WatchlistTask watchlistTask;
    public static ArrayList<Groupbuying> watchlist = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_watchlist);

        RecyclerView recyclerView = findViewById(R.id.rv);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getNick();


        back = (Button)findViewById(R.id.listback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
                intent.putExtra("profileNum", 4);
                startActivity(intent);
                finish();
            }
        });

        //GALLERY(); // 허가
        watchlist.clear();
        watchlistTask = new WatchlistTask();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        watchlistAdapter = new WatchlistAdapter(watchlist);


        recyclerView.setAdapter(watchlistAdapter);


        watchlistAdapter.setOnItemClickListener(new WatchlistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                position = pos;
                WatchlistCountjson.position = pos + 1;
                nick = watchlist.get(pos).getNick();
                title = watchlist.get(pos).getTitle();
                text = watchlist.get(pos).getText();
                area = watchlist.get(pos).getArea();
                price = watchlist.get(pos).getPrice();
                headCount = watchlist.get(pos).getHeadcount();
                nowCount = watchlist.get(pos).getNowCount();
                watchnick = watchlist.get(pos).getWatchnick();
                Log.d("onItemClick", Integer.toString(pos));

                getPosition(position);
                Intent intent = new Intent(getApplicationContext(), BuySubMain_watchlist.class);
                intent.putExtra("price", price);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("nick", nick);
                intent.putExtra("area", area);
                intent.putExtra("headcount", headCount);
                intent.putExtra("nowcount", nowCount);
                intent.putExtra("watchnick", watchnick);
                //intent.putExtra("count", pos);
                startActivity(intent);
            }
        });



        //관심목록 클릭
        watchlistAdapter.setOnInterestClickListener(new WatchlistAdapter.OnInterestClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                Toast.makeText(getApplicationContext(), "관심목록 버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });


        buysearch = (ImageView) findViewById(R.id.buysearch);

        buysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Search.class);
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