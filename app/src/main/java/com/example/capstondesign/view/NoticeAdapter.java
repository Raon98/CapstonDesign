package com.example.capstondesign.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Board;

import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {
    static NoticeAdapter.OnItemClickListener mListener = null;
    public String nick;
    public Board board;


    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(NoticeAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView nick, text, title;
        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nick = (TextView) itemView.findViewById(R.id.nick);
            title = (TextView) itemView.findViewById(R.id.title);
            text = (TextView) itemView.findViewById(R.id.text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            click_nickname = boardList.get(pos).getNick();
                            click_title = boardList.get(pos).getTitle();
                            click_text = boardList.get(pos).getText();
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
    }


    public static List<Board> boardList;
    public NoticeAdapter(List<Board> items) { boardList = items; }
    public static String click_nickname, click_title, click_text;

    @NonNull
    @Override
    public NoticeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.board_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);
        board = boardList.get(position);

        holder.nick.setText(boardList.get(position).getNick());
        holder.title.setText(boardList.get(position).getTitle());
        holder.text.setText(boardList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }





}

