package com.example.studybuddies.objects;

import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    @Override
    public boolean equals(Object o) {
        Member member = (Member) o;
        return this.getId() == member.getId() &&
                this.getUserId() == member.getUserId() &&
                this.getGroupId() == member.getGroupId() &&
                this.getPermission() == member.getPermission();
    }

    /**
     * Returns a list of all members in the database as Member objects
     */
    public static void getMembers(OnFinishedArrayList onFinishedArrayList) {

        ArrayList<Member> list = new ArrayList<Member>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    list.add(new Member(response.getJSONObject(i)));
                }
                onFinishedArrayList.onFinishedArrayList(list);
            }
        });
    }

    /**
     * Returns a list of all members in the database as Member objects for a particular group
     */
    public static void getMembers(Group group, OnFinishedArrayList onFinishedArrayList) {

        ArrayList<Member> list = new ArrayList<Member>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject member = response.getJSONObject(i);
                    Group currentGroup = new Group(member.getJSONObject("groupsDetail"));
                    if (currentGroup.equals(group)) {
                        list.add(new Member(member));
                    }
                }
                onFinishedArrayList.onFinishedArrayList(list);
            }
        });

    }
}
