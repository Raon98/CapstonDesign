package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.Search_result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchEmailResultTask {
    public String email_front, email_end, name;

    public SearchEmailResultTask(String result) {
        jsonParsing(result);
    }

    //Json Parsing
    public void jsonParsing(String json)
    {
        try{
            JSONArray BoardArray = new JSONArray(json);

            for(int i=0; i<BoardArray.length(); i++)
            {

                JSONObject BoardObject = BoardArray.getJSONObject(i);
                email_front = BoardObject.getString("email_front");
                email_end = BoardObject.getString("email_end");
                name = BoardObject.getString("name");
                Log.d("email_front", email_front);
                Log.d("email_end", email_end);
                Log.d("name", name);

            }
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }
}

