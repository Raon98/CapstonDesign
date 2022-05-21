package com.example.capstondesign.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.WithdrawTask;

public class notice_inside extends AppCompatActivity {

    TextView title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_inside);

        getIntent();

        title = findViewById(R.id.title_text);

        content = findViewById(R.id.content_text);

        Log.d("TITLE", getIntent().getStringExtra("title"));
        Log.d("TEXT", getIntent().getStringExtra("text"));

        title.setText(getIntent().getStringExtra("title"));
        content.setText(getIntent().getStringExtra("text"));

    }
}