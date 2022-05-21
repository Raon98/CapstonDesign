package com.example.capstondesign.model;

public class CardItem {     //아이템 객체생성

    private  String title;
    private  int Image;

    public CardItem(String title, int Image){
        this.title =title;
        this.Image = Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return Image;
    }

}
