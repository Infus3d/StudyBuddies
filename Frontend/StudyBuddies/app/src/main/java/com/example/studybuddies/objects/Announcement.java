package com.example.studybuddies.objects;

import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Announcement {

    private int announcementID;

    private int groupID;

    private int memberID;

    private String author;

    private String message;

    private String time;

    public Announcement(int announcementID, int groupID, int memberID, String author, String message, String time) {
        this.announcementID = announcementID;
        this.groupID = groupID;
        this.memberID = memberID;
        this.author = author;
        this.message = message;
        this.time = time;
    }

    public Announcement(JSONObject jsonObject) {
        try {
            announcementID = jsonObject.getInt("id");
            groupID = jsonObject.getInt("groupId");
            memberID = jsonObject.getInt("memberId");
            author = jsonObject.getJSONObject("membersDetail").getJSONObject("usersDetail").getString("username");
            message = jsonObject.getString("message");
            time = jsonObject.getString("time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getAnnouncementID() {
        return announcementID;
    }

    public int getGroupID() {
        return groupID;
    }

    public int getMemberID() {
        return memberID;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        Announcement announcement = (Announcement) o;
        if (this.getAnnouncementID() == announcement.getAnnouncementID() &&
            this.getGroupID() == announcement.getGroupID() &&
            this.getMemberID() == announcement.getMemberID() &&
            this.getAuthor().equals(announcement.getAuthor()) &&
            this.getMessage().equals(announcement.getMessage()) &&
            this.getTime().equals(announcement.getTime())
        ) {
            return true;
        }
        return false;
    }

    public static void getAnnouncements(OnFinishedArrayList onFinishedArrayList) {

        ArrayList<Announcement> list = new ArrayList<Announcement>();

        RequestsCentral.getJSONArray(Const.GET_ANNOUNCEMENTS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject j = response.getJSONObject(i);
                        list.add(new Announcement(j));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onFinishedArrayList.onFinishedArrayList(list);
            }
        });
    }

    public static void getAnnouncements(Group group, OnFinishedArrayList onFinishedArrayList) {

        ArrayList<Announcement> list = new ArrayList<Announcement>();

        RequestsCentral.getJSONArray(Const.GET_ANNOUNCEMENTS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject j = response.getJSONObject(i);
                        Group currentGroup = new Group(j.getJSONObject("membersDetail").getJSONObject("groupsDetail"));
                        if (currentGroup.equals(group)) {
                            list.add(new Announcement(j));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onFinishedArrayList.onFinishedArrayList(list);
            }
        });
    }
}
