package com.example.capstondesign.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.view.ChatAdapter;
import com.example.capstondesign.view.ChatRoomAdapter;
import com.example.capstondesign.model.ChatRoomData;
import com.example.capstondesign.model.ChatTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Fragment_chatting extends Fragment {
    public static ChatRoomAdapter chatRoomAdapter;
    public static ArrayList<ChatRoomData> chatlist = new ArrayList<>();
    static int clicked_item;
    public static String name;
    Profile profile = LoginAcitivity.profile;
    Boolean check;
    ChatTask chatTask;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_chatting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment_second.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_chatting newInstance(String param1, String param2) {
        Fragment_chatting fragment = new Fragment_chatting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getNick();
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.fragment_chatting, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);

        chatlist.clear();
        chatTask = new ChatTask();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        chatRoomAdapter = new ChatRoomAdapter(chatlist);

        recyclerView.setAdapter(chatRoomAdapter);



        //채팅방을 클릭했을 경우의 이벤트
        chatRoomAdapter.setOnItemClickListener(new ChatRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                clicked_item = pos;
                //내가 누른 채팅방의 이름과 mynick, othernick의 구별을 하는 boolean값
                name = chatRoomAdapter.getItem(pos).getNickname();
                check = chatRoomAdapter.getItem(pos).getRoom_check();
                Intent intent = new Intent(getContext(), in_chat_room.class);
                intent.putExtra("name", name);
                intent.putExtra("check", check);
                startActivity(intent);
                
            }

            final String[] items = {"채팅방 이름 설정", "나가기"};
            @Override
            public void onItemLongClick(View v, int pos) {
                setAlertDialog(items, getContext());
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    public void setAlertDialog(String[] items, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("채팅방 설정");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Toast.makeText(context, "채팅방 이름 설정 클릭", Toast.LENGTH_SHORT).show();
                } else if (which == 1) {
                    Toast.makeText(context, "나가기 클릭", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            ChatAdapter.nick = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
