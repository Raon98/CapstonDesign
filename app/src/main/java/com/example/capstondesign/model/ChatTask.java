package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.Fragment_chatting;
import com.example.capstondesign.view.ChatAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChatTask {
    public static ArrayList<ChatRoomData> chatlist = Fragment_chatting.chatlist;
    ChatRoomData chatRoomData;
    String nickname, nickname2;

    public ChatTask() {
        try {
            jsonParsing(downloadUrl(), chatlist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadUrl() throws IOException {
        String s = null;
        byte[] buffer = new byte[10000];
        InputStream iStream = null;
        try {
            URL url = new URL("http://13.124.75.92:8080/chattingjson.jsp");
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
    public void jsonParsing(String json, ArrayList<ChatRoomData> chatlist)
    {
        int k = 0;

        try{
            JSONArray ChatArray = new JSONArray(json);

            for(int i=0; i<ChatArray.length(); i++)
            {
                chatRoomData = new ChatRoomData();
                JSONObject ChatObject = ChatArray.getJSONObject(i);
                nickname = ChatObject.getString("mynick");
                nickname2 = ChatObject.getString("othernick");

                if(nickname.equals(ChatAdapter.nick)) {

                    chatRoomData.setNickname(nickname2);
                    chatRoomData.setRoom_check(true);
                    chatRoomData.setMessage(ChatObject.getString("last_msg"));
                    Log.d("CHATDATA1", chatRoomData.getNickname());
                    if(nickname2 != null && !nickname2.equals("null")) {
                        if(chatlist.size() == 0) chatlist.add(chatRoomData);
                        else {
                            for (int j = 0; j < chatlist.size(); j++) {
                                Log.d("LISTNICK1", chatlist.get(j).getNickname());
                                Log.d("NICK1", nickname);
                                if (!chatlist.get(j).getNickname().equals(nickname2)){
                                    k++;
                                }
                            }
                            if(chatlist.size()==k) {
                                Log.d("KK", String.valueOf(k));
                                chatlist.add(chatRoomData);
                            }
                            k=0;
                        }
                    }

                } else if (nickname2.equals(ChatAdapter.nick)) {
                    chatRoomData.setNickname(nickname);
                    chatRoomData.setRoom_check(false);
                    chatRoomData.setMessage(ChatObject.getString("last_msg"));
                    Log.d("CHATDATA2", chatRoomData.getNickname());
                    if(nickname != null && !nickname.equals("null")) {
                        if(chatlist.size() == 0) chatlist.add(chatRoomData);
                        else {
                            for (int j = 0; j < chatlist.size(); j++) {
                                Log.d("LISTNICK2", chatlist.get(j).getNickname());
                                Log.d("NICK2", nickname);
                                if (!chatlist.get(j).getNickname().equals(nickname)) {
                                    k++;
                                }
                            }
                            if(chatlist.size()==k) {
                                Log.d("KK", String.valueOf(k));
                                chatlist.add(chatRoomData);
                            }
                            k=0;
                        }
                    }
                }
            }
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }
}

