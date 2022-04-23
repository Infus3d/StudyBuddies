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
 * A class to contain an ArrayList of groups. Can be used
 * to get all groups or get all groups for a specific user.
 * @author Andy Bruder
 */
public class GroupList {


    private static final int SIMULATED_DELAY_MS = 250;

    /**
     * Stores a list of group objects
     */
    private ArrayList<Group> groupList;

    /**
     * Constructor to create a GroupList of all existing groups
     */
    public GroupList(OnFinishedArrayList o) {

        groupList = new ArrayList<Group>();

        RequestsCentral.getJSONArray(Const.GET_GROUPS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {

                for (int i = 0; i < response.length(); i++){

                    groupList.add(new Group((JSONObject) response.get(i)));

                }

                o.onFinishedArrayList(groupList);
            }
        });

    }

    /**
     * Creates a GroupList for groups containing the user
     * @param u User object to search groups for
     */
    public GroupList(OnFinishedArrayList o, User u) {

        groupList = new ArrayList<Group>();

        RequestsCentral.getJSONArray(Const.GET_GROUPS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {

                for (int i = 0; i < response.length(); i++){

                    Group current = new Group((JSONObject) response.get(i));

                    try {
                        Thread.sleep(SIMULATED_DELAY_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (current.memberPresent(u.getUsername())) {
                        groupList.add(current);
                    }
                }

                o.onFinishedArrayList(groupList);
            }
        });

    }

    /**
     * Returns the ArrayList of Groups
     * @return groupList
     */
    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    /**
     * Removes a group from the GroupList
     * @param g Group to be removed
     */
    public void removeGroup(Group g) {
        groupList.remove(g);
    }

    /**
     * Gets the public groups currently present in the GroupList
     * @return publicGroups - ArrayList containing all public groups from groupList
     */
    public ArrayList<Group> publicGroups() {
        ArrayList<Group> publicGroups = new ArrayList<Group>();
        for (Group g : groupList) {
            if (g.isPublic()) {
                publicGroups.add(g);
            }
        }
        return publicGroups;
    }

}
