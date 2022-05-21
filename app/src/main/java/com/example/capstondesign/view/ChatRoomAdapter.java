package com.example.capstondesign.view;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.model.ChatProfileCountjson;
import com.example.capstondesign.model.ChatRoomData;
import com.example.capstondesign.model.ChatRoomProfileCountjson;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.SearchEmailResultTask;
import com.example.capstondesign.model.SearchEmailTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MyViewHolder> {
    public static String nick, name, email;
    static ChatRoomAdapter.OnItemClickListener mListener = null;
    public static ArrayList<ChatRoomData> items = null;
    public static String number;
    Profile profile = LoginAcitivity.profile;
    String strurl;
    ChatRoomProfileCountjson chatRoomProfileCountjson;


    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
        void onItemLongClick(View v, int pos);
    }

    public void setOnItemClickListener(ChatRoomAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
    public void setOnItemLongClickListener(ChatRoomAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ChatRoomData getItem(int position) {return items.get(position);}

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView TextView_msg;
        public ImageView otherchatprofile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.room_name);
            TextView_msg = itemView.findViewById(R.id.msg_last);
            otherchatprofile = itemView.findViewById(R.id.otherImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemLongClick(v, pos);
                        }
                    }
                    return false;
                }
            });

        }

        @SuppressLint("SetTextI18n")
        public void setItem(ChatRoomData item) {
            name.setText(item.getNickname());
            TextView_msg.setText(item.getMessage());
        }
    }


    public ChatRoomAdapter(ArrayList<ChatRoomData> item) { items = item; }


    @NonNull
    @Override
    public ChatRoomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatting_room_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);

        ChatRoomData chat = items.get(position);
        if(chat.getMessage().equals("null")) {
            chat.setMessage("메시지 없음");
        }
        SearchEmailTask searchEmailTask = new SearchEmailTask();

        try {
            String result = searchEmailTask.execute(chat.getNickname()).get();
            int idx = result.indexOf("[");
            String re_result = result.substring(idx);
            SearchEmailResultTask searchEmailResultTask = new SearchEmailResultTask(re_result);
            name = searchEmailResultTask.name;
            email = searchEmailResultTask.email_front + "@" + searchEmailResultTask.email_end;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("CHATADAPTER", name);
        chatRoomProfileCountjson = new ChatRoomProfileCountjson();
        try {
            //
            //String a = profileTask.substringBetween(result1, "number:", "/");

            Log.d("TEST", number);
            if (number.equals("-1")) {
                strurl = "http://13.124.75.92:8080/king.png";
                Log.d("NUM0", strurl);
            } else {
                strurl = "http://13.124.75.92:8080/upload/" + email + number + ".jpg";
                Log.d("NUM", strurl);
            }
            Picasso.get().load(Uri.parse(strurl)).into(holder.otherchatprofile);
        } catch (Exception e) {
            e.printStackTrace();
            Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(holder.otherchatprofile);
        }

        holder.setItem(chat);

        //Picasso.get().load(Uri.parse(chat.getProfilePic())).into(holder.myImage);
        //Picasso.get().load(Uri.parse(chat.getProfilePic())).into(holder.otherImage);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public ChatRoomData getChat(int position) {
        return items != null ? items.get(position) : null;
    }

    public void addChat(ChatRoomData chat) {
        items.add(chat);
        notifyItemInserted(items.size()-1);
    }



}
