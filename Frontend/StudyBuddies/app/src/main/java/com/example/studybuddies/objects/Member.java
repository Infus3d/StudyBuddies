package com.example.studybuddies.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Member {

    private int id;

    private int groupId;

    private int userId;

    private int permission;

    public Member(int id, int groupId, int userId, int permission) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.permission = permission;
    }

    public Member(JSONObject j) {
        try {
            this.id = j.getInt("id");
            this.groupId = j.getInt("groupId");
            this.userId = j.getInt("userId");
            this.permission = j.getInt("permission");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getUserId() {
        return userId;
    }

    public int getPermission() {
        return permission;
    }

}
