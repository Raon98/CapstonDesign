package com.example.capstondesign.view;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.controller.Fragment_profile;
import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.model.ChatData;
import com.example.capstondesign.model.ChatProfileCountjson;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileCountjsonTask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    public static String nick, name, email;
    private static final String TAG = "ChatAdapter";
    public ChatData chat;
    String strurl;
    public static String number;
    Profile profile = LoginAcitivity.profile;
    ProfileCountjsonTask profileCountjsonTask;
    ChatProfileCountjson chatProfileCountjson;


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView TextView_mynickname, TextView_othernickname;
        public TextView TextView_mymsg, TextView_othermsg;
        public CardView myprofileImage, otherprofileImage;
        public ImageView myImage, otherImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView_mynickname = itemView.findViewById(R.id.TextView_mynickname);
            TextView_othernickname = itemView.findViewById(R.id.TextView_othernickname);
            TextView_mymsg = itemView.findViewById(R.id.TextView_mymsg);
            TextView_othermsg = itemView.findViewById(R.id.TextView_othermsg);
            myprofileImage = itemView.findViewById(R.id.mychatprofile);
            otherprofileImage = itemView.findViewById(R.id.otherchatprofile);
            myImage = itemView.findViewById(R.id.myImage);
            otherImage = itemView.findViewById(R.id.otherImage);

        }
    }

    public static List<ChatData> chatData;
    public ChatAdapter(List<ChatData> items) { chatData = items; }


    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);

        chat = chatData.get(position);


        holder.TextView_othernickname.setText(chat.getNickname());
        //DTO
        //Picasso.get().load(Uri.parse(chat.getProfilePic())).into(holder.myImage);
        //Picasso.get().load(Uri.parse(chat.getProfilePic())).into(holder.otherImage);


        if(chat.getNickname()!=null && chat.getNickname().equals(nick)) {
            profileCountjsonTask = new ProfileCountjsonTask();
            try {
                Log.d("TEST", Fragment_profile.number);
                if (Fragment_profile.number.equals("-1")) {
                    strurl = "http://13.124.75.92:8080/king.png";
                    Log.d("NUM0", strurl);
                } else {
                    strurl = "http://13.124.75.92:8080/upload/" + profile.getEmail() + Fragment_profile.number + ".jpg";
                    Log.d("NUM", strurl);
                }
                Picasso.get().load(Uri.parse(strurl)).into(holder.myImage);
            } catch (Exception e) {
                e.printStackTrace();
                Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(holder.myImage);
            }

            holder.myprofileImage.setVisibility(View.VISIBLE);
            holder.otherprofileImage.setVisibility(View.GONE);
            holder.TextView_mymsg.setVisibility(View.VISIBLE);
            holder.TextView_mymsg.setText(chat.getMessage());
            holder.TextView_mynickname.setVisibility(View.VISIBLE);
            holder.TextView_mynickname.setText(chat.getNickname());
        } else {
            name = chat.getName();
            email = chat.getEmail();
            Log.d("CHATADAPTER", name);
            chatProfileCountjson = new ChatProfileCountjson();
            try {
                //
                //String a = profileTask.substringBetween(result1, "number:", "/");

                Log.d("TEST", number);
                if (number.equals("-1")) {
                    strurl = "http://13.124.75.92:8080/king.png";
                    Log.d("NUM0", strurl);
                } else {
                    strurl = "http://13.124.75.92:8080/upload/" + chat.getEmail() + number + ".jpg";
                    Log.d("NUM", strurl);
                }
                Picasso.get().load(Uri.parse(strurl)).into(holder.otherImage);
            } catch (Exception e) {
                e.printStackTrace();
                Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(holder.otherImage);
            }


            holder.myprofileImage.setVisibility(View.GONE);
            holder.otherprofileImage.setVisibility(View.VISIBLE);
            holder.TextView_othermsg.setVisibility(View.VISIBLE);
            holder.TextView_othermsg.setText(chat.getMessage());
            holder.TextView_othernickname.setVisibility(View.VISIBLE);
            holder.TextView_othernickname.setText(chat.getNickname());
        }

    }

    @Override
    public int getItemCount() {
        return chatData.size();
    }


    public ChatData getChat(int position) {
        return chatData != null ? chatData.get(position) : null;
    }

    public void addChat(ChatData chat) {
        chatData.add(chat);
        notifyItemInserted(chatData.size()-1);
    }



}
