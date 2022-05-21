package com.example.capstondesign.model;

import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import com.example.capstondesign.R;
import com.example.capstondesign.controller.Fragment_Groupbuy;
import com.example.capstondesign.controller.Fragment_board;
import com.example.capstondesign.controller.Fragment_chatting;
import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.controller.in_watchlist;
import com.example.capstondesign.controller.add_GroupBuying;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GroupBuyingTask {
    public static ArrayList<Groupbuying> groupbuyinglist = Fragment_Groupbuy.groupbuying;

    Groupbuying groupbuying;

    Profile profile = LoginAcitivity.profile;
    String nick, price, title, text, headCount, nowCount, area, watchnick, mynick, time;

    public GroupBuyingTask() {
        try {
            jsonParsing(downloadUrl(), groupbuyinglist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadUrl() throws IOException {
        String s = null;
        byte[] buffer = new byte[10000];
        InputStream iStream = null;
        try {
            URL url = new URL("http://13.124.75.92:8080/groupbuyingjson.jsp");
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
    public void jsonParsing(String json, ArrayList<Groupbuying> board1)
    {
        getNick();
        try{
            JSONArray GroupbuyingArray = new JSONArray(json);

            for(int i=0; i<GroupbuyingArray.length(); i++)
            {

                JSONObject GroupbuyingObject = GroupbuyingArray.getJSONObject(i);

                nick = GroupbuyingObject.getString("nick");
                title = GroupbuyingObject.getString("title");
                price = GroupbuyingObject.getString("price");
                headCount = GroupbuyingObject.getString("headcount");
                nowCount = GroupbuyingObject.getString("nowCount");
                area = GroupbuyingObject.getString("area");
                text = GroupbuyingObject.getString("text");
                watchnick = GroupbuyingObject.getString("watchnick");
                time = GroupbuyingObject.getString("count");
                Log.d("PRICE", price);
                Log.d("TEXT", text);
                Log.d("WATCHNICK", watchnick);

                groupbuying = new Groupbuying(nick, title, text, price, headCount, nowCount, area, watchnick, title.hashCode() + time + ".jpg", time);

                board1.add(groupbuying);

            }
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            mynick = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

