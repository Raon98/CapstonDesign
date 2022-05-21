package com.example.capstondesign.model;

public class ChatData {
    private String message;
    private String nickname;
    private String key;
    private String email;
    private String name;

    public ChatData() { }

    public ChatData(String nickname, String message, String key, String email, String name) {
        this.message = message;
        this.nickname = nickname;
        this.key = key;
        this.email = email;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
