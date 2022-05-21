package com.example.capstondesign.model;

public class Groupbuying {

    String buy_image;
    String nick;
    String title;
    String text;
    String price;
    String headcount;
    String nowCount;
    String area;
    String watchnick;
    String time;

    public Groupbuying(String nick, String title, String text, String price, String headcount, String nowCount, String area, String watchnick, String buy_image, String time) {
        this.nick = nick;
        this.title = title;
        this.text = text;
        this.price = price;
        this.headcount = headcount;
        this.nowCount = nowCount;
        this.area = area;
        this.watchnick = watchnick;
        this.buy_image = buy_image;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getBuy_image() {
        return buy_image;
    }

    public void setBuy_image(String buy_image) {
        this.buy_image = buy_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHeadcount() {
        return headcount;
    }

    public void setHeadcount(String headcount) {
        this.headcount = headcount;
    }

    public String getNowCount() {
        return nowCount;
    }

    public void setNowCount(String nowCount) {
        this.nowCount = nowCount;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWatchnick() {
        return watchnick;
    }

    public void setWatchnick(String watchnick) {
        this.watchnick = watchnick;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


