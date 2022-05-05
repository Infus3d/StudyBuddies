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

    private User user;

    private Group group;

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
            this.user = new User(j.getJSONObject("usersDetail"));
            this.group = new Group(j.getJSONObject("groupsDetail"));
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

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
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

    public static void getMembers(int groupId, OnFinishedArrayList onFinishedArrayList) {

        ArrayList<Member> list = new ArrayList<Member>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject member = response.getJSONObject(i);
                    Group currentGroup = new Group(member.getJSONObject("groupsDetail"));
                    if (currentGroup.getId() == groupId) {
                        list.add(new Member(member));
                    }
                }
                onFinishedArrayList.onFinishedArrayList(list);
            }
        });

    }

    /**
     * Allows to get all of the memberships of the specified user
     * @param userId The specified user to get the memberships of
     * @param onFinishedArrayList callback function to execute upon success
     */
    public static void getMemberships(int userId, OnFinishedArrayList onFinishedArrayList) {
        ArrayList<Member> list = new ArrayList<Member>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);
                    User currentUser = new User(jsonObject.getJSONObject("usersDetail"));
                    if (currentUser.getId() == userId) {
                        list.add(new Member(jsonObject));
                    }
                }
                onFinishedArrayList.onFinishedArrayList(list);
            }
        });

    }
}
