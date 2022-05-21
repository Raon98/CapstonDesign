package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.GroupBuyingTimejsonTask;
import com.example.capstondesign.model.Groupbuying;
import com.example.capstondesign.model.SearchGroupResultTask;
import com.example.capstondesign.model.SearchTask;
import com.example.capstondesign.view.GroupBuyingAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Search_Groupbuy_result extends AppCompatActivity {
    public static ArrayList<Groupbuying> group = new ArrayList<>();
    public static GroupBuyingAdapter groupBuyingAdapter;
    String nick, title, text, price, headcount, nowCount, area, watchnick;
    ImageView button, back;
    EditText search;
    SearchGroupResultTask searchGroupResultTask;
    String search_result, result1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_groupbuying_result);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();

        String result = intent.getStringExtra("result");
        if (result.equals("[]")) {
            Toast.makeText(getApplicationContext(), "결과 없음", Toast.LENGTH_SHORT).show();
        } else {
            group.clear();
            searchGroupResultTask = new SearchGroupResultTask(result);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            groupBuyingAdapter = new GroupBuyingAdapter(group);

            recyclerView.setAdapter(groupBuyingAdapter);

            groupBuyingAdapter.setOnItemClickListener(new GroupBuyingAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    GroupBuyingTimejsonTask.position = pos + 1;
                    nick = group.get(pos).getNick();
                    title = group.get(pos).getTitle();
                    text = group.get(pos).getText();
                    area = group.get(pos).getArea();
                    price = group.get(pos).getPrice();
                    headcount = group.get(pos).getHeadcount();
                    nowCount = group.get(pos).getNowCount();
                    watchnick = group.get(pos).getWatchnick();
                    Log.d("onItemClick", Integer.toString(pos));

                    Intent intent = new Intent(getApplicationContext(), BuySubMain.class);
                    intent.putExtra("price", price);
                    intent.putExtra("title", title);
                    intent.putExtra("text", text);
                    intent.putExtra("nick", nick);
                    intent.putExtra("area", area);
                    intent.putExtra("headcount", headcount);
                    intent.putExtra("nowcount", nowCount);
                    intent.putExtra("watchnick", watchnick);
                    //intent.putExtra("count", pos);
                    startActivity(intent);
                }
            });


            search = (EditText) findViewById(R.id.search);
            button = (ImageView) findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchTask searchTask = new SearchTask();
                    search_result = search.getText().toString();
                    try {
                        result1 = searchTask.execute(search_result).get();
                        if (result1.contains("[]")) {
                            Toast.makeText(getApplicationContext(), "결과 없음", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), Search_Groupbuy_result.class);
                            int idx = result1.indexOf("[");
                            String re_result = result1.substring(idx);
                            Log.d("RESULT", re_result);
                            intent.putExtra("result", re_result);
                            startActivity(intent);
                            finish();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            back = (ImageView) findViewById(R.id.backButton);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


        }
    }
}
