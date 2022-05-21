package com.example.capstondesign.controller;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.capstondesign.R;
import com.example.capstondesign.model.DeleteBoardTask;
import com.example.capstondesign.model.DeleteGroupbuyingTask;
import com.example.capstondesign.model.ProfileCountTask;
import com.example.capstondesign.model.ProfileCountjsonTask;
import com.example.capstondesign.view.BoardAdapter;
import com.example.capstondesign.model.CommentTask;
import com.example.capstondesign.view.Comment_Adapter;
import com.example.capstondesign.model.Comment_Item;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.addCommentTask;
import com.squareup.picasso.Picasso;
import com.example.capstondesign.model.BoardTimejsonTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class  FreeBoard extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    public static String title, nick, time;
    public static String number;
    Profile profile = LoginAcitivity.profile;
    String nickname, text, strurl;
    ListView comment_list;
    EditText comment_edit;
    Comment_Adapter ca;
    CommentTask commentTask;
    ImageView imageView;

    BoardTimejsonTask boardTimejsonTask;
    ProfileTask profileTask;
    ProfileCountTask profileCountTask;
    ProfileCountjsonTask profileCountjsonTask;

    int i;

    public static ArrayList<Comment_Item> c_arr = new ArrayList<>();
    View footer;
    Fragment_board ma;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        c_arr.clear();
        getNick();

        intent = getIntent();

        Toolbar toolbar2 = findViewById(R.id.bd_toolbar);
        setSupportActionBar(toolbar2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        profileCountTask = new ProfileCountTask();
        profileCountjsonTask = new ProfileCountjsonTask();
        profileTask = new ProfileTask();


        title = getIntent().getStringExtra("title");
        text = getIntent().getStringExtra("text");
        nick = getIntent().getStringExtra("nick");

        boardTimejsonTask = new BoardTimejsonTask();

        TextView content_text = findViewById(R.id.content_text);
        content_text.setText(text);

        TextView title_text = findViewById(R.id.title_text);
        title_text.setText(title);

        ImageView imgView = findViewById(R.id.imageHeader);

        try {
            i = getResponseCode("http://13.124.75.92:8080/uploadBoard/" + title.hashCode() + time + ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(i == 404) {
            imgView.setVisibility(View.GONE);
        } else {
            try {
                Log.d("URL","http://13.124.75.92:8080/uploadBoard/" + title.hashCode() + time + ".jpg" );
                Picasso.get().load(Uri.parse("http://13.124.75.92:8080/uploadBoard/" + title.hashCode() + time + ".jpg")).into(imgView);
            } catch (Exception e) {
                Log.d("NOPICTURE", "NOPICTURE");
            }
        }



        back = (Button)findViewById(R.id.inboard_exit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
                intent.putExtra("boardNum", 1);
                startActivity(intent);
                finish();
            }
        });



        commentTask = new CommentTask();

        //setResult(RESULT_OK, intent);
        try {
            init();
            //Log.d("AAAAA", String.valueOf(image));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }



    public void init() throws ExecutionException, InterruptedException {
        comment_list = findViewById(R.id.comment_list);
        footer = getLayoutInflater().inflate(R.layout.footer, null, false);
        comment_list.addFooterView(footer);
        setList(); // listview 세팅
        setFooter(); // footer세팅

    }


    private void setFooter()  {
        imageView = footer.findViewById(R.id.imageView1);
        comment_edit = (EditText)footer.findViewById(R.id.comment_edit);

        Log.d("NAME!@#@#", profile.getName());

        try {
            profileTask.execute(profile.getName(), profile.getEmail()).get();
            //
            try {
                //
                //String a = profileTask.substringBetween(result1, "number:", "/");

                Log.d("TEST", number);
                if (number.equals("-1")) {
                    strurl = "http://13.124.75.92:8080/king.png";
                    Log.d("NUM0", strurl);
                } else {
                    strurl = "http://13.124.75.92:8080/upload/" + profile.getEmail() + number + ".jpg";
                    Log.d("NUM", strurl);
                }
                profile.setPicture(strurl);
                Picasso.get().load(Uri.parse(strurl)).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
                profile.setPicture("http://13.124.75.92:8080/king.png");
                Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(imageView);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Button commentInput_Btn = (Button)footer.findViewById(R.id.input_btn);

        commentInput_Btn.setOnClickListener(this);
    }

    private void setList() {
        ca = new Comment_Adapter(getApplicationContext(), this, ma, c_arr);
        comment_list.setAdapter(ca);
        comment_list.setSelection(c_arr.size()-1);
        comment_list.setDivider(null);
        comment_list.setSelectionFromTop(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(intent.getStringExtra("nick").equals(nickname)) {
            menuInflater.inflate(R.menu.buy_menu, menu);
        } else {
            Log.d("NICK", nickname);
            menuInflater.inflate(R.menu.buy_menu_user, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.achome:
                startActivity(Fragment_main.class);
                break;
            case R.id.acsearch:
                startActivity(Search.class);
                break;
            case R.id.action_home:
                finish();
                break;
            case R.id.acDelete:
                DeleteBoardTask deleteBoardTask = new DeleteBoardTask();
                deleteBoardTask.execute(intent.getStringExtra("nick"),
                        intent.getStringExtra("title"), intent.getStringExtra("text"), time);
                Toast.makeText(getApplicationContext(), "게시글 삭제", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), Fragment_main.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.putExtra("boardNum", 1);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_btn:
                long now = System.currentTimeMillis();
                String temp = comment_edit.getText().toString();
                if(temp.equals("")) {
                    Toast.makeText(getApplicationContext(), "빈칸입니다.", Toast.LENGTH_LONG).show();
                } else {
                    addCommentTask addCommentTask = new addCommentTask();
                    addCommentTask.execute(title, nick, temp, nickname, time);
                    Comment_Item ci = new Comment_Item(nickname, temp, time);
                    c_arr.add(ci);
                    resetAdapter();
                    comment_edit.setText("");
                }
                break;
        }
    }


    public void resetAdapter() {
        ca.notifyDataSetChanged();
    }

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            nickname = profileTask.substringBetween(result, "nickname:", "/");
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

    public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
        URL u = new URL (urlString);
        HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
        huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
        huc.connect () ;
        int code = huc.getResponseCode() ;
        Log.d("GETCODE", String.valueOf(code));
        return code;
    }

}