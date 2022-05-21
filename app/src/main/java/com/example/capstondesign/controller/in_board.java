package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;

public class in_board extends AppCompatActivity {

    Button inboard_groupbuying, inboard_board, exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_board_select);

        exit = findViewById(R.id.exit_in_board);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inboard_groupbuying = findViewById(R.id.inboard_groupbuying);
        inboard_groupbuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroupBuyingClickHandler();
            }
        });

        inboard_board = findViewById(R.id.inboard_board);
        inboard_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoardClickHandler();
            }
        });

    }

    private void showBoardClickHandler() {
        Intent intent = new Intent(getApplicationContext(), showMyBoard.class);
        startActivity(intent);
    }

    private void showGroupBuyingClickHandler() {
        Intent intent = new Intent(getApplicationContext(), showMyGroupBuying.class);
        startActivity(intent);
    }
}
