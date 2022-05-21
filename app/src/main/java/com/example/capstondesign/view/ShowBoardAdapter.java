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

public class ShowBoardAdapter extends RecyclerView.Adapter<ShowBoardAdapter.MyViewHolder> {
    static ShowBoardAdapter.OnItemClickListener mListener = null;
    public static String nick;
    public static Board board;


    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(ShowBoardAdapter.OnItemClickListener listener) {
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
                            click_time = boardList.get(pos).getTime();
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
    }


    public static List<Board> boardList;
    public ShowBoardAdapter(List<Board> items) { boardList = items; }
    public static String click_nickname, click_title, click_text, click_time;

    @NonNull
    @Override
    public ShowBoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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


    public Board getChat(int position) {
        return boardList != null ? boardList.get(position) : null;
    }

    public void addBoard(Board board1) {
        boardList.add(board1);
        notifyItemInserted(boardList.size()-1);
    }



}

