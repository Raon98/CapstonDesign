package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.Fragment_board;
import com.example.capstondesign.controller.Fragment_chatting;
import com.example.capstondesign.controller.in_notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NoticeTask {
    public static ArrayList<Board> noticelist = in_notice.board;
    Board board;
    String nick, title, text;

    public NoticeTask() {
        try {
            //Log.d("TTTTTTTTTT", downloadUrl());
            jsonParsing(downloadUrl(), noticelist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadUrl() throws IOException {
        String s = null;
        byte[] buffer = new byte[10000];
        InputStream iStream = null;
        try {
            URL url = new URL("http://13.124.75.92:8080/noticejson.jsp");
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
    public void jsonParsing(String json, ArrayList<Board> board1)
    {
        try{
            JSONArray BoardArray = new JSONArray(json);

            for(int i=0; i<BoardArray.length(); i++)
            {

                JSONObject BoardObject = BoardArray.getJSONObject(i);
                nick = BoardObject.getString("nick");
                title = BoardObject.getString("title");
                text = BoardObject.getString("text");

                board = new Board(nick, title, text);

                board1.add(board);

            }
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }
}

