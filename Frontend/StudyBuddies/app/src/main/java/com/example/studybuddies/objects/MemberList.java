package com.example.studybuddies.objects;

import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MemberList {

    /**
     * ArrayList to contain the member objects for the MemberList
     */
    private ArrayList<Member> memberList;

    /**
     * ArrayList to contain the user objects for the MemberList
     */
    private UserList userList;

    /**
     * Creates a MemberList with all members present in the database
     */
    public MemberList() {
        memberList = new ArrayList<Member>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject current = response.getJSONObject(i);
                    if (current.getInt("userId") >= 0
                            && current.getInt("groupId") >= 0
                            && current.get("membersDetail") != null
                            && current.get("groupDetail") !=  null) {
                        memberList.add(new Member(current));
                    }
                }

                userList = new UserList(memberList);
            }
        });
    }

    public MemberList(Group group) {
        memberList = new ArrayList<Member>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject current = response.getJSONObject(i);
                    if (current.getInt("groupId") == group.getId()) {
                        if (current.getInt("userId") >= 0
                                && current.getInt("groupId") >= 0
                                && current.get("membersDetail") != null
                                && current.get("groupDetail") !=  null) {
                            memberList.add(new Member(current));
                        }
                    }
                }

                userList = new UserList(memberList);
            }
        });

    }

    public MemberList(User user) {
        memberList = new ArrayList<Member>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject current = response.getJSONObject(i);
                    if (current.getInt("userId") == user.getId()) {
                        if (current.getInt("userId") >= 0
                                && current.getInt("groupId") >= 0
                                && current.get("membersDetail") != null
                                && current.get("groupDetail") !=  null) {
                            memberList.add(new Member(current));
                        }
                    }
                }

                userList = new UserList(memberList);
            }
        });
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }



}
