package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.BuySubMain;
import com.example.capstondesign.controller.BuySubMain_watchlist;
import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.view.GroupBuyingAdapter;
import com.example.capstondesign.controller.add_GroupBuying;
import com.example.capstondesign.controller.Fragment_Groupbuy;
import com.example.capstondesign.view.WatchlistAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WatchlistCountjson {
    Profile profile = LoginAcitivity.profile;
    String nick, title, email_end, number, time;
    String fileLength;
    public static int position = 0;

    public WatchlistCountjson() {
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
            URL url = new URL("http://13.124.75.92:8080/GroupBuyingCountjson.jsp");
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
//            String titleArray[] = new String[countArray.length()];//1
            int titleNum = 0;
            for(int i=0; i<countArray.length(); i++)
            {
                JSONObject CountObject = countArray.getJSONObject(i);
                nick = CountObject.getString("nick");
                title = CountObject.getString("title");
                Log.d("positionTask", Integer.toString(position));
                //email_end = CountObject.getString("email_end");
                number = CountObject.getString("number");
                time = CountObject.getString("count"); // 1


                if(WatchlistAdapter.click_nickname.equals(nick) && WatchlistAdapter.click_title.equals(title) && WatchlistAdapter.click_time.equals(time)) {
                    BuySubMain_watchlist.numberGroupBuying = number;
                    Log.d("numberEroor", number);
                    BuySubMain_watchlist.time = time; //count

                }
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

}
