package com.example.capstondesign.view;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.model.Profile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.capstondesign.R;
import com.example.capstondesign.model.BuySubSlideritem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BuySubAdapter extends RecyclerView.Adapter<BuySubAdapter.BuySubViewHolder> {

    Profile profile = LoginAcitivity.profile;

    public BuySubAdapter(List<BuySubSlideritem> itemList, ViewPager2 pager2) {
        this.itemList = itemList;
        this.pager2 = pager2;
    }

    private List<BuySubSlideritem> itemList;
    private ViewPager2 pager2;

    @NonNull
    @Override
    public BuySubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BuySubViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_sub_slide_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull BuySubViewHolder holder, int position) {
        holder.setImage(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class BuySubViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public BuySubViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pagerimage);
        }
        void setImage(BuySubSlideritem buySubSlideritem) {
//            imageView.setImageURI(buySubSlideritem.getImage());
            Picasso.get().load(Uri.parse(buySubSlideritem.getImage())).into(imageView);
            Log.d("picasoo", Uri.parse(buySubSlideritem.getImage()).toString());
        }
    }
}
