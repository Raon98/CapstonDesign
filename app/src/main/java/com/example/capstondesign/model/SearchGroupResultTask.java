package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.Search_Groupbuy;
import com.example.capstondesign.controller.Search_Groupbuy_result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchGroupResultTask {
    public static ArrayList<Groupbuying> searchlist = Search_Groupbuy_result.group;
    Groupbuying groupbuying;
    String nick, title, text, area, headcount, nowCount, price, watchnick, count, time;

    public SearchGroupResultTask(String result) {
        jsonParsing(result , searchlist);
    }

    //Json Parsing
    public void jsonParsing(String json, ArrayList<Groupbuying> board1)
    {
        try{
            JSONArray GroupbuyingArray = new JSONArray(json);

            for(int i=0; i<GroupbuyingArray.length(); i++)
            {

                JSONObject GroupbuyingObject = GroupbuyingArray.getJSONObject(i);
                nick = GroupbuyingObject.getString("nick");
                title = GroupbuyingObject.getString("title");
                text = GroupbuyingObject.getString("text");
                area = GroupbuyingObject.getString("area");
                headcount = GroupbuyingObject.getString("headcount");
                nowCount = GroupbuyingObject.getString("nowCount");
                price = GroupbuyingObject.getString("price");
                watchnick = GroupbuyingObject.getString("watchnick");
                time = GroupbuyingObject.getString("count");

                Log.d("NICK", nick);
                Log.d("TITLE", title);
                Log.d("TEXT", text);

                groupbuying = new Groupbuying(nick, title, text, price, headcount, nowCount, area, watchnick, title.hashCode() + time + ".jpg", time);

                board1.add(groupbuying);

            }
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }
}

