package com.example.studybuddies.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class CalendarEvent implements Comparable<CalendarEvent> {
    private String eventSource;
    private int id;
    private Member memberDetail;
    private User userDetail;
    private String message, time;
    private boolean editAndDelete, personal;

    public CalendarEvent(int eventUserId, String message, String time, String eventSource) {
        this.eventSource = eventSource;
        this.id = id;
        this.message = message;
        this.time = time;
    }

    public CalendarEvent(JSONObject jsonObject, boolean editAndDelete){
        try {
            this.editAndDelete = editAndDelete;
            id = jsonObject.getInt("id");
            message = jsonObject.getString("message");
            time = jsonObject.getString("time");
            if(jsonObject.has("userId")) {
                eventSource = "Personal Event";
                userDetail = new User(jsonObject.getJSONObject("userDetail"));
                memberDetail = null;
                personal = true;
            }
            else {
                eventSource = jsonObject.getJSONObject("groupsDetail").getString("title") + " Event";
                memberDetail = new Member(jsonObject.getJSONObject("membersDetail"));
                userDetail = null;
                personal = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isPersonal() {
        return personal;
    }

    public boolean isEditAndDelete() {
        return editAndDelete;
    }

    public void setEditAndDelete(boolean editAndDelete) {
        this.editAndDelete = editAndDelete;
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

    /**
     * Returns the JSONObject of the current Calendar Event to make it easier
     * for server requests
     * @return JSONObject version of this calendar event
     */
    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("message", this.message);
            jsonObject.put("time", this.time);
            if(isPersonal()) jsonObject.put("userId", this.userDetail.getId());
            else{
                jsonObject.put("eventGroupId", this.memberDetail.getGroupId());
                jsonObject.put("eventMemberId", this.memberDetail.getId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public int compareTo(CalendarEvent calendarEvent) {
        return this.time.compareTo(calendarEvent.getTime());
    }
}
