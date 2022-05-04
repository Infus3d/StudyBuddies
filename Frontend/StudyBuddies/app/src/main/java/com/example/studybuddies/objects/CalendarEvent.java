package com.example.studybuddies.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class CalendarEvent implements Comparable<CalendarEvent> {
    private String eventSource;
    private int id;
    private Member memberDetail;
    private User userDetail;
    private String message, time;

    public CalendarEvent(int eventUserId, String message, String time, String eventSource) {
        this.eventSource = eventSource;
        this.id = id;
        this.message = message;
        this.time = time;
    }

    public CalendarEvent(JSONObject jsonObject){
        try {
            id = jsonObject.getInt("id");
            message = jsonObject.getString("message");
            time = jsonObject.getString("time");
            if(jsonObject.has("userId")) {
                eventSource = "Personal Event";
                userDetail = new User(jsonObject.getJSONObject("userDetail"));
            }
            else {
                eventSource = jsonObject.getJSONObject("groupsDetail").getString("title") + " Event";
                memberDetail = new Member(jsonObject.getJSONObject("membersDetail"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Member getMemberDetail() {
        return memberDetail;
    }

    public void setMemberDetail(Member memberDetail) {
        this.memberDetail = memberDetail;
    }

    public User getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(User userDetail) {
        this.userDetail = userDetail;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
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

    @Override
    public int compareTo(CalendarEvent calendarEvent) {
        return this.time.compareTo(calendarEvent.getTime());
    }
}
