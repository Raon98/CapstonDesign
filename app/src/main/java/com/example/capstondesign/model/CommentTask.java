package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.Fragment_board;
import com.example.capstondesign.controller.Fragment_chatting;
import com.example.capstondesign.controller.FreeBoard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CommentTask {
    public static ArrayList<Comment_Item> comment_items = FreeBoard.c_arr;
    Comment_Item comment_item;
    String nick, title, comment, co_nick, time;

    public CommentTask() {
        try {
            //Log.d("TTTTTTTTTT", downloadUrl());
            jsonParsing(downloadUrl(), comment_items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadUrl() throws IOException {
        String s = null;
        byte[] buffer = new byte[10000];
        InputStream iStream = null;
        try {
            URL url = new URL("http://13.124.75.92:8080/commentjson.jsp");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            iStream.read(buffer);
            s = new String(buffer);
        } catch (Exception e) {
            Log.d("Exception download", e.toString());
        } finally {
            if(iStream != null) {
                iStream.close();
            } else
                Log.d("iStreamNull", "iStreamNull");
        }
        return s;
    }

    //Json Parsing
    public void jsonParsing(String json, ArrayList<Comment_Item> board1)
    {
        try{
            JSONArray CommentArray = new JSONArray(json);

            for(int i=0; i<CommentArray.length(); i++)
            {

                JSONObject CommentObject = CommentArray.getJSONObject(i);
                nick = CommentObject.getString("nick");
                title = CommentObject.getString("title");
                comment = CommentObject.getString("comment");
                co_nick = CommentObject.getString("co_nick");
                time = CommentObject.getString("time");
                Log.d("NICK", nick);
                Log.d("TITLE", title);
                Log.d("JSONTIME", time);
                Log.d("BOARDTIME", FreeBoard.time);

                if(nick.equals(FreeBoard.nick) && title.equals(FreeBoard.title) && time.equals(FreeBoard.time)) {
                    comment_item = new Comment_Item(co_nick, comment, time);
                    comment_items.add(comment_item);
                }

            }
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }
}

