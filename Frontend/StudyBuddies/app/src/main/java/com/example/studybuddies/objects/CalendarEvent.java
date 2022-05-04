package com.example.studybuddies.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class CalendarEvent {
    private String eventSource;
    private int eventUserId, id;
    private String message, time;

    public CalendarEvent(int id, int eventUserId, String message, String time, String eventSource) {
        this.eventSource = eventSource;
        this.eventUserId = eventUserId;
        this.id = id;
        this.message = message;
        this.time = time;
    }

    public CalendarEvent(JSONObject jsonObject){
        try {
            id = jsonObject.getInt("id");
            eventUserId = jsonObject.getInt("eventUserId");
            message = jsonObject.getString("message");
            time = jsonObject.getString("time");
            eventSource = "Personal Event";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public int getEventUserId() {
        return eventUserId;
    }

    public void setEventUserId(int eventUserId) {
        this.eventUserId = eventUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
