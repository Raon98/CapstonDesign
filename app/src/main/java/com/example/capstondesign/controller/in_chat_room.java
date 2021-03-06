package com.example.capstondesign.controller;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.view.ChatAdapter;
import com.example.capstondesign.model.ChatData;
import com.example.capstondesign.model.LastMsgTask;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class in_chat_room extends AppCompatActivity {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference();
    ChatAdapter chatAdapter;
    List<ChatData> chatlist = new ArrayList<>();
    ChatData chatData;
    private EditText EditText_chat;
    Profile profile = LoginAcitivity.profile;
    private static final String TAG = "Fragment_chatting";
    ChildEventListener childEventListener;
    public static String key;
    String othername, name;
    Boolean check;
    LastMsgTask lastMsgTask;
    TextView chat_nick_name;


    @Override
    public void onPause() {
        Log.d(TAG, "remove");
        myRef.child("Chat").child(name).removeEventListener(childEventListener);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("chatNum", 3);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        chat_nick_name = findViewById(R.id.chat_nick_name);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        Log.d("NAME", name);
        Log.d("NAME1111", ChatAdapter.nick);

        chat_nick_name.setText(name);

        //??? ????????? ?????? ????????????
        othername = name;
        check = intent.getExtras().getBoolean("check");
        //???????????? ?????? ????????? Boolean ?????? ?????? myname?????? othername?????? ?????? ???
        //othername??? ?????? ?????????????????? ????????? ?????? othername?????? ?????????
        // mynick??? othernick??? ???????????? ????????? ??????????????? ????????? ??????
        if(!check) {
            name = ChatAdapter.nick + "&" +  name;
            Log.d("IF", name);
        } else {
            name = name + "&" + ChatAdapter.nick;
            Log.d("ELSE", name);
        }




        Button Button_send;
        Button_send = findViewById(R.id.send);
        EditText_chat = findViewById(R.id.EditText_chat);
        chatlist.clear();

        Log.d("DATABASE", String.valueOf(database.getReference()));

        //????????? ???????????? ??????
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                chatData = dataSnapshot.getValue(ChatData.class);
                chatAdapter.addChat(chatData);
                recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.child("Chat").child(name).addChildEventListener(childEventListener);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(chatlist);

        recyclerView.setAdapter(chatAdapter);

        //????????? ????????? ?????? ??????
        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    key = myRef.push().getKey();
                    String msg = EditText_chat.getText().toString();
                    ChatData chat = new ChatData(ChatAdapter.nick , msg, key, profile.getEmail(), profile.getName());
                    Log.d("SEND", ChatAdapter.nick + "msg" + msg);
                    //Realtime Database??? Chat ?????? name?????? key??? ?????? ??????
                    //key??? ????????? ??? ????????? ?????? ??????????????? ?????? ?????? ???????????? ??????
                    myRef.child("Chat").child(name).child(key).setValue(chat);
                    Log.d("NAME", name);
                    //????????? ???????????? ????????????????????? ???????????? ?????? Task
                    lastMsgTask = new LastMsgTask();
                    lastMsgTask.execute(msg, othername, ChatAdapter.nick, othername).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(ChatAdapter.chatData.size()>1) {
                    recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                }
                EditText_chat.setText(null);
            }
        });

        Button chat_exit;

        chat_exit = (Button)findViewById(R.id.chat_exit);
        chat_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("chatNum", 3);
                startActivity(intent);
                finish();
            }
        });
    }
}

