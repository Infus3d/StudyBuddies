package com.example.studybuddies.objects;

import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A class to represent a group object
 * @author Andy Bruder
 */
public class Group {

    /**
     * int value representing the group ID
     */
    private int id;

    /**
     * String value to represent the name of the group
     */
    private String title;

    /**
     * boolean value to determine if the group is visible to all users
     */
    private boolean isPublic;

    /**
     * ArrayList containing user objects for all users who are a member of the group
     */
    private ArrayList<User> members;

    /**
     * Creates a new group from the information contained in the database
     * @param id The group ID
     * @param title The name of the group
     * @param isPublic The public visibility for the group
     */
    public Group(int id, String title, boolean isPublic){

        this.id = id;
        this.title = title;
        this.isPublic = isPublic;

        members = new ArrayList<User>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {

                    JSONObject j = response.getJSONObject(i);
                    if (j.getInt("groupId") == id) {
                        members.add(new User((JSONObject) j.get("membersDetail")));
                    }
                }
            }
        });

    }

    /**
     * Creates a new group object from a JSONObject representing a group
     * @param j JSONObject representing group
     */
    public Group(JSONObject j){

        try {
            this.id = j.getInt("id");
            this.title = j.getString("title");
            this.isPublic = j.getBoolean("isPublic");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        members = new ArrayList<User>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {

                    JSONObject j = response.getJSONObject(i);
                    if (j.getInt("groupId") == id) {
                        members.add(new User((JSONObject) j.get("membersDetail")));
                    }
                }
            }
        });

    }

    /**
     * Gets the group ID of the object as an int
      * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the group object as a String
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the public visibility status of the group as a boolean
     * @return isPublic
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * Returns the ArrayList<User> that contains all of the group's members
     * @return members
     */
    public ArrayList<User> getMembers() {
        return members;
    }

    /**
     * Searches a group for a given user by their username
     * @param username
     * @return boolean value representing if a member is part of the group
     */
    public boolean memberPresent(String username) {
        for (User u : members) {
            if (u.getUsername() == username) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a JSONObject to be able to send to the database
     * @return JSONObject j
     */
    public JSONObject toJSONObject() {

        JSONObject j = new JSONObject();

        try {
            j.put("id", id);
            j.put("title", title);
            j.put("isPublic", isPublic);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return j;
    }

}
