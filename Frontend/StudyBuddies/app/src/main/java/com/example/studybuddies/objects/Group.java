package com.example.studybuddies.objects;

import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
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
     * Creates a new group from the information contained in the database
     * @param id The group ID
     * @param title The name of the group
     * @param isPublic The public visibility for the group
     */
    public Group(int id, String title, boolean isPublic){

        this.id = id;
        this.title = title;
        this.isPublic = isPublic;

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

    @Override
    public boolean equals(Object o) {
        Group group = (Group) o;
        if (this.getId() == group.getId() &&
            this.getTitle().equals(group.getTitle()) &&
            this.isPublic == group.isPublic
        ) {
            return true;
        }
        return false;
    }

    public static void getGroups(OnFinishedArrayList onFinishedArrayList) {

        ArrayList<Group> list = new ArrayList<Group>();

        RequestsCentral.getJSONArray(Const.GET_GROUPS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject j = response.getJSONObject(i);
                        list.add(new Group(j));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onFinishedArrayList.onFinishedArrayList(list);
            }
        });
    }

    public static void getGroups(User user, OnFinishedArrayList onFinishedArrayList) {

        ArrayList<Group> list = new ArrayList<Group>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject j = response.getJSONObject(i);
                        if (!j.getJSONObject("usersDetail").equals(null)) {
                            if (new User(j.getJSONObject("usersDetail")).equals(user)) {
                                list.add(new Group(j.getJSONObject("groupsDetail")));
                            }
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
