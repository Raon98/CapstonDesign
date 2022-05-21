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
import com.example.capstondesign.model.Board;
import com.example.capstondesign.view.BoardAdapter;
import com.example.capstondesign.view.SearchResultAdapter;
import com.example.capstondesign.model.SearchResultTask;
import com.example.capstondesign.model.SearchTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Search_result extends AppCompatActivity {
    public static ArrayList<Board> board = new ArrayList<>();
    public static BoardAdapter boardAdapter;
    String nick, title, text;
    ImageView button, back;
    EditText search;
    SearchResultTask searchResultTask;
    String search_result, result1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();

        String result = intent.getStringExtra("result");
        if (result.equals("[]")) {
            Toast.makeText(getApplicationContext(), "결과 없음", Toast.LENGTH_SHORT).show();
        } else {
            board.clear();
            searchResultTask = new SearchResultTask(result);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            boardAdapter = new BoardAdapter(board);

            recyclerView.setAdapter(boardAdapter);

            boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    nick = board.get(pos).getNick();
                    title = board.get(pos).getTitle();
                    text = board.get(pos).getTitle();
                    Intent intent = new Intent(getApplicationContext(), FreeBoard.class);
                    intent.putExtra("title", title);
                    intent.putExtra("text", text);
                    intent.putExtra("nick", nick);
                    intent.putExtra("location", "search");
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
                            Intent intent = new Intent(getApplicationContext(), Search_result.class);
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
