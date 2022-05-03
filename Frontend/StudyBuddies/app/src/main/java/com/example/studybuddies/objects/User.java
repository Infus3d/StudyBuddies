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
 * A class to represent an individual user, containing all pieces of information on a user.
 * @author Andy Bruder
 */
public class User {

    /**
     * int to represent the id for a user
     */
    private int id;

    /**
     * String to represent the username of a user
     */
    private String username;

    /**
     * String to represent the email of a user
     */
    private String email;

    /**
     * String to represent the password of a user
     */
    private String password;

    /**
     * String to represent the location of a user
     */
    private String location;

    /**
     * ArrayList to contain the Group objects for groups that this user is a part of
     */
    private ArrayList<Group> userGroups;

    /**
     * Creates a new user object using the parameters from the database
     * @param id The user ID for the new user
     * @param username The username for the new user
     * @param email The email for the new user
     * @param password The password for the new user
     * @param location The location for the new user
     */
    public User(int id, String username, String email, String password, String location) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.location = location;

    }

    /**
     * Creates a new user object using a JSONObject
     * @param j the JSONObject to use to create the User
     */
    public User(JSONObject j) {
        try {
            this.id = j.getInt("id");
            this.username = j.getString("username");
            this.email = j.getString("email");
            this.password = j.getString("password");
            this.location = j.getString("location");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User(String username, String email, String password, String location) {
        id = -1;
        this.username = username;
        this.email = email;
        this.password = password;
        this.location = location;
    }

    /**
     * Gets the ID of the user object
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the username of the user object
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the email of the user object
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password of the user object
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the location of the user object
     * @return username
     */
    public String getLocation() {
        return location;
    }


    /**
     * Sets the ID of the user object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the username of the user object
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the email of the user object
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password of the user object
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the location of the user object
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Updates and returns the user's current groups
     * @return userGroups
     */
    public ArrayList<Group> getUserGroups() {
        return userGroups;
    }

    public ArrayList<Group> removeUserGroup(int id) {
        for (Group g : userGroups) {
            if (g.getId() == id) {
                userGroups.remove(g);
            }
        }
        return  userGroups;
    }

    /**
     * Checks the password of the user object to be used for authentication
     * @return boolean representing if the password was correct
     */
    public boolean correctPassword(String password) {
        return password == this.password;
    }

    /**
     * Creates a JSONObject to be able to send to the database
     * @return JSONObject j
     */
    public JSONObject toJSONObject() {

        JSONObject j = new JSONObject();

        try {
            j.put("id", id);
            j.put("username", username);
            j.put("email", email);
            j.put("password", password);
            j.put("location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return j;
    }

    public JSONObject toJSONForServer() {

        JSONObject j = new JSONObject();

        try {
            j.put("username", username);
            j.put("email", email);
            j.put("password", password);
            j.put("location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return j;

    }

    @Override
    public boolean equals(Object o) {

        User user = (User) o;
        
        if (this.getId() == user.getId() &&
            this.getUsername().equals(user.getUsername()) &&
            this.getEmail().equals(user.getEmail()) &&
            this.getPassword().equals(user.getPassword()) &&
            this.getLocation().equals(user.getLocation())) {
            return true;
        }
        return false;
    }

    /**
     * Returns a list of all users in the database as User objects
     */
    public static void getUsers(OnFinishedArrayList onFinishedArrayList) {

        ArrayList<User> list = new ArrayList<User>();

        RequestsCentral.getJSONArray(Const.GET_USERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject j = response.getJSONObject(i);
                    list.add(new User(j));
                }
                onFinishedArrayList.onFinishedArrayList(list);
            }
        });
    }

    /**
     * Returns a list of all users in the database as User objects for a particular group
     */
    public static void getUsers(Group group, OnFinishedArrayList onFinishedArrayList) {

        ArrayList<User> list = new ArrayList<User>();

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject member = response.getJSONObject(i);
                    Group currentGroup = new Group(member.getJSONObject("groupsDetail"));
                    if (currentGroup.equals(group)) {
                        list.add(new User(member.getJSONObject("usersDetail")));
                    }
                }
                onFinishedArrayList.onFinishedArrayList(list);
            }
        });

    }
}
