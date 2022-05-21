package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.Search_result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultTask {
    public static ArrayList<Board> searchlist = Search_result.board;
    Board board;
    String nick, title, text, time;

    public SearchResultTask(String result) {
            jsonParsing(result , searchlist);
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
                time = BoardObject.getString("count");
                Log.d("NICK", nick);
                Log.d("TITLE", title);
                Log.d("TEXT", text);

                board = new Board(nick, title, text, time);

                board1.add(board);

            }
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }
}

