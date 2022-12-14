package com.example.studybuddies.objects;

import org.json.JSONObject;

public class ChatMessage {

    private int id;

    private int userId;

    private String time;

    private String author;

    private int groupId;

    private String message;

    public ChatMessage(int id, int userId, String time, String author, int groupId, String message) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.author = author;
        this.groupId = groupId;
        this.message = message;
    }

    public ChatMessage(int userId, String time, String author, int groupId, String message) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.author = author;
        this.groupId = groupId;
        this.message = message;
    }

    public ChatMessage(JSONObject jsonObject) {

    }

    public ChatMessage(String outgoing, String author){
        this.message = outgoing;
        this.author = author;
    }

    public ChatMessage(String fromSocket, int groupId) {
        this.author = fromSocket.substring(0, fromSocket.indexOf(": "));
        this.message = fromSocket.substring(fromSocket.indexOf(": ") + 2);
        this.groupId = groupId;
        this.time = "";
        this.userId = -1;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTime() {
        return time;
    }

    public String getAuthor() {
        return author;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getMessage() {
        return message;
    }


}
