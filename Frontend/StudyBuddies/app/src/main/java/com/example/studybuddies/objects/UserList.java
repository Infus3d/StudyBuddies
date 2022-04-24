package com.example.studybuddies.objects;

import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A class to contain an ArrayList of users. Can be used to
 * create the list of all users or based on a specific group.
 * @author Andy Bruder
 */
public class UserList {

    /**
     * An ArrayList to contain the User objects
     */
    ArrayList<User> userList;

    /**
     * Creates a UserList containing all users in the database
     */
    public UserList() {

        userList = new ArrayList<User>();

        RequestsCentral.getJSONArray(Const.GET_USERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {

                for (int i = 0; i < response.length(); i++) {
                    userList.add(new User(response.getJSONObject(i)));
                }
            }
        });

    }

    /**
     * Creates a new UserList from an existing group object
     * @param g Group to create list from
     */
    public UserList(Group g) {
        userList = new ArrayList<User>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {

                    JSONObject j = response.getJSONObject(i);
                    if (j.getInt("groupId") == g.getId() && !j.get("userId").equals(null) && !j.get("membersDetail").equals(null)) {
                        userList.add(new User(j.getJSONObject("membersDetail")));
                    }
                }
            }
        });

    }


    public UserList(ArrayList<Member> list) {
        userList = new ArrayList<User>();

        for(Member m : list) {
            RequestsCentral.getJSONObject(Const.GET_MEMBERS + Integer.valueOf(m.getUserId()), new OnSuccessfulObject() {
                @Override
                public void onSuccess(JSONObject response) {
                    userList.add(new User(response));
                }
            });
        }

    }


    public boolean searchList(String username) {

        for (User u : userList) {
            if (u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Searches the userList for a specific user
     * @param u User object to search for
     * @return boolean value representing if the user is present
     */
    public boolean searchList(User u) {
        return userList.contains(u);
    }

    public void add(User u) {
        userList.add(u);
    }



}
