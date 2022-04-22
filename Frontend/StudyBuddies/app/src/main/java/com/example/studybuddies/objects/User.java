package com.example.studybuddies.objects;

import com.example.studybuddies.utils.RequestsCentral;

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
     * Gets the location of the user object
     * @return username
     */
    public String getLocation() {
        return location;
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

}
