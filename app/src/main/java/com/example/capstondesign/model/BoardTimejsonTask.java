package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.FreeBoard;
import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.controller.add_Board;
import com.example.capstondesign.view.BoardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoardTimejsonTask {
    Profile profile = LoginAcitivity.profile;
    public String nick, title, number, time;


    public BoardTimejsonTask() {
        try {
            jsonParsing(downloadUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadUrl() throws IOException {
        String s = null;
        byte[] buffer = new byte[10000];
        InputStream iStream = null;
        try {
            URL url = new URL("http://13.124.75.92:8080/BoardCountjson.jsp");
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
    public void jsonParsing(String json)
    {

        try{
            JSONArray countArray = new JSONArray(json);

            for(int i=0; i<countArray.length(); i++)
            {
                JSONObject CountObject = countArray.getJSONObject(i);
                nick = CountObject.getString("nick");
                title = CountObject.getString("title");
                time = CountObject.getString("count"); // 1

                Log.d("countError", time);
                Log.d("nick", nick);
                Log.d("nick2", title);
                Log.d("CLiCKNICKNAME???", BoardAdapter.click_nickname);

                if(BoardAdapter.click_nickname.equals(nick) && BoardAdapter.click_title.equals(title) && BoardAdapter.click_time.equals(time)) {
//                    BuySubMain.numberGroupBuying = number;
                    FreeBoard.time = time; //count

                }
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

}