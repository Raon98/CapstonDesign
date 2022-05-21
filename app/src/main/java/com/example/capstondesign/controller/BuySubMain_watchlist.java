package com.example.capstondesign.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstondesign.R;
import com.example.capstondesign.model.AddNowCountTask;
import com.example.capstondesign.model.InWatchlistProfileCountTask;
import com.example.capstondesign.model.WatchlistCountjson;
import com.example.capstondesign.model.addWatchlistTask;
import com.example.capstondesign.view.BuySubAdapter;
import com.example.capstondesign.model.BuySubSlideritem;
import com.example.capstondesign.view.ChatAdapter;
import com.example.capstondesign.model.ChattingRoomTask;
import com.example.capstondesign.model.DelNowCountTask;
import com.example.capstondesign.model.DeleteGroupbuyingTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.view.WatchlistAdapter;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BuySubMain_watchlist extends AppCompatActivity {

    public static String numberGroupBuying;
    Button add, cancel, buyback, countadd, countdel;
    ImageView Myinfoimage;
    EditText chattingroom, othernick;
    String my_room_name,other_room_name, other_nick;
    String mynick = ChatAdapter.nick;
    public static String number, nickname, email, time;
    String strurl;
    InWatchlistProfileCountTask inWatchlistProfileCountTask;
    String real_nick;
    String message;
    Intent intent;
    int set, max_count;
    ChattingRoomTask chattingRoomTask = new ChattingRoomTask();
    Profile profile = LoginAcitivity.profile;
    WatchlistCountjson watchlistCountjson;
    addWatchlistTask addWatchlistTask;

    ImageView interest_btn;
    ViewPager2 pager2;
    int list[];
    TextView nick, text, area, title, price, headCount, nowCount;
    DotsIndicator dotsIndicator;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_submain);

        getNick();

        Myinfoimage = findViewById(R.id.Myinfoimage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bs_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        intent = getIntent();

        nick = findViewById(R.id.sub_name);
        title = findViewById(R.id.sub_title);
        area = findViewById(R.id.sub_place);
        text = findViewById(R.id.sub_Contents);
        price = findViewById(R.id.sub_price);
        headCount = findViewById(R.id.headCount);
        nowCount = findViewById(R.id.nowCount);
        countadd = findViewById(R.id.Countaddbtn);
        countdel = findViewById(R.id.Countdelbtn);

        nickname = intent.getStringExtra("nick");

        inWatchlistProfileCountTask = new InWatchlistProfileCountTask();

        try {
            //
            //String a = profileTask.substringBetween(result1, "number:", "/");
            Log.d("TEST", email);
            Log.d("TEST", number);
            if (number.equals("-1")) {
                strurl = "http://13.124.75.92:8080/king.png";
                Log.d("NUM0", strurl);
            } else {
                strurl = "http://13.124.75.92:8080/upload/" + email + number + ".jpg";
                Log.d("NUM", strurl);
            }
            profile.setPicture(strurl);
            Picasso.get().load(Uri.parse(strurl)).into(Myinfoimage);
        } catch (Exception e) {
            e.printStackTrace();
            profile.setPicture("http://13.124.75.92:8080/king.png");
            Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(Myinfoimage);
        }

        if(!real_nick.equals(intent.getStringExtra("nick"))) {
            countadd.setVisibility(View.INVISIBLE);
            countdel.setVisibility(View.INVISIBLE);
        }

        set = Integer.parseInt(intent.getStringExtra("nowcount"));
        max_count = Integer.parseInt(intent.getStringExtra("headcount"));

        countadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(set>=max_count) {
                    Toast.makeText(getApplicationContext(), "모집인원보다 많이 설정할 수 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    AddNowCountTask addNowCountTask = new AddNowCountTask();
                    Log.d("SET", String.valueOf(set));
                    addNowCountTask.execute(real_nick, intent.getStringExtra("title"), intent.getStringExtra("text"), String.valueOf(set));
                    set = set + 1;
                    nowCount.setText(String.valueOf(set));
                }
            }
        });

        countdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(set<=1) {
                    Toast.makeText(getApplicationContext(), "최소인원보다 적게 설정할 수 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    DelNowCountTask delNowCountTask = new DelNowCountTask();
                    Log.d("SET", String.valueOf(set));
                    delNowCountTask.execute(real_nick, intent.getStringExtra("title"), intent.getStringExtra("text"), String.valueOf(set));
                    set = set - 1;
                    nowCount.setText(String.valueOf(set));
                }
            }
        });

        interest_btn = findViewById(R.id.interest_btn);

        nick.setText(intent.getStringExtra("nick"));
        title.setText(intent.getStringExtra("title"));
        area.setText(intent.getStringExtra("area"));
        text.setText(intent.getStringExtra("text"));
        price.setText(intent.getStringExtra("price") + "원");
        headCount.setText(intent.getStringExtra("headcount"));
        nowCount.setText(intent.getStringExtra("nowcount"));

        dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        pager2 = findViewById(R.id.pager2);

        watchlistCountjson = new WatchlistCountjson();

        if(intent.getStringExtra("watchnick").contains(real_nick + ",")) {
            interest_btn.setImageResource(R.drawable.interest_aft);
        }

//        String positionNum = intent.getStringExtra("count");
//        Log.d("positionNum", positionNum);

        interest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWatchlistTask = new addWatchlistTask();
                try {
                    String result = addWatchlistTask.execute(real_nick, title.getText().toString(), text.getText().toString() , price.getText().toString() , area.getText().toString(), nick.getText().toString(), time).get();
                    Log.d("결과", result);
                    if(result.contains("추가")) {
                        interest_btn.setImageResource(R.drawable.interest_aft);
                        Log.d("추가", result);
                    } else if(result.contains("삭제")){
                        interest_btn.setImageResource(R.drawable.interest_prv);
                        //하트 흰색
                        Log.d("삭제", result);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        List<BuySubSlideritem> itemList = new ArrayList<>();
        Log.d("numberGroupbyuing", numberGroupBuying);
        for(int i = 0; i < Integer.parseInt(numberGroupBuying); i++) { // MySQL 길이
            if(i == 0) itemList.add(new BuySubSlideritem("http://13.124.75.92:8080/upload/" + WatchlistAdapter.click_title.hashCode() + time + ".jpg"));
            else itemList.add(new BuySubSlideritem("http://13.124.75.92:8080/upload/" + WatchlistAdapter.click_title.hashCode() + time +  i + ".jpg"));
        }

        pager2.setAdapter(new BuySubAdapter(itemList,pager2));
        dotsIndicator.setViewPager2(pager2);

        buyback = (Button)findViewById(R.id.sub_buy_back);
        buyback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button chat = (Button)findViewById(R.id.sub_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other_room_name = mynick;
                other_nick = intent.getStringExtra("nick");

                //내 채팅방의 이름은 상대방의 닉네임
                my_room_name = other_nick;

                //채팅방의 값을 데이터베이스에 저장하는 Task
                chattingRoomTask.execute(mynick, other_nick, my_room_name, other_room_name, message);

                Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
                intent.putExtra("chatNum", 3);
                startActivity(intent);
                finish();

            }
        });

        // 관심목록 추가 해야함

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(intent.getStringExtra("nick").equals(real_nick)) {
            menuInflater.inflate(R.menu.buy_menu, menu);
        } else {
            Log.d("NICK", real_nick);
            menuInflater.inflate(R.menu.buy_menu_user, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        chattingRoomTask.execute(mynick, other_nick, my_room_name, other_room_name, message);

        switch (item.getItemId()) {
            case R.id.achome:
                startActivity(Fragment_main.class);
                break;
            case R.id.acsearch:
                startActivity(Search.class);
                break;

            case R.id.acDelete:
                DeleteGroupbuyingTask deleteGroupbuyingTask = new DeleteGroupbuyingTask();
                deleteGroupbuyingTask.execute(intent.getStringExtra("nick"),
                        intent.getStringExtra("title"), intent.getStringExtra("text"));
                Toast.makeText(getApplicationContext(), "게시글 삭제", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
                intent.putExtra("groupbuyingNum", 2);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            real_nick = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    void startActivity(Class c) {
        Intent intent1 = new Intent(getApplicationContext(), c);
        startActivity(intent1);
    }
}
