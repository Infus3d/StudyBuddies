package com.example.studybuddies;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studybuddies.databinding.ActivityGroupPostsBinding;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.OnSuccessfulInteger;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class GroupPosts extends DrawerBaseActivity {

    private int memberID;
    ActivityGroupPostsBinding activityGroupPostsBinding;

    private String groupName;
    private int groupId;

    private int id;
    private String username_s;
    private String email_s;
    private String password_s;
    private String location_s;

    SharedPreferences sharedPreferences;

    private int layoutCounter, textViewCounter;


    private Button createPost;
    private String postMessage;
    private EditText messageEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGroupPostsBinding = ActivityGroupPostsBinding.inflate(getLayoutInflater());
        setContentView(activityGroupPostsBinding.getRoot());

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        groupId = extras.getInt("groupId");
        groupName = extras.getString("title");

        allocateActivityTitle("Posts for " + groupName);

        sharedPreferences = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(LoginScreen.ID_KEY, 0);
        username_s = sharedPreferences.getString(LoginScreen.USERNAME_KEY, null);
        email_s = sharedPreferences.getString(LoginScreen.EMAIL_KEY, null);
        password_s = sharedPreferences.getString(LoginScreen.PASSWORD_KEY, null);
        location_s = sharedPreferences.getString(LoginScreen.LOCATION_KEY, null);

        getPosts();
        getMemberID(new OnSuccessfulInteger() {
            @Override
            public void onSuccess(int i) {
                memberID = i;
            }
        });
        getTime();

        createPost = findViewById(R.id.create_post);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creates the alert dialog that will pop up to
                // prompt for a message and EditText for message
                AlertDialog.Builder alert = new AlertDialog.Builder(GroupPosts.this);
                final EditText editTextMessage = new EditText(GroupPosts.this);

                // sets the elements to be present in the alert dialog
                alert.setTitle("Create a post for " + groupName);
                alert.setView(editTextMessage);
                LinearLayout layout = new LinearLayout(GroupPosts.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(editTextMessage);
                alert.setView(layout);

                alert.setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        messageEnter = editTextMessage;
                        makePost();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                alert.show();

            }
        });

    }

    /**
     * Gets the current time in a String format. 24hr time version
     * @return time as a String
     */
    public String getTime() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
    }

    /**
     * Gets the correct memberID that ties the current user to the current group
     * @param onSuccessfulInteger
     */
    public void getMemberID(OnSuccessfulInteger onSuccessfulInteger) {
        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject member = response.getJSONObject(i);
                    int gID = member.getInt("groupId");
                    int uID = member.getInt("userId");
                    if (groupId == gID && id == uID) {
                        onSuccessfulInteger.onSuccess(member.getInt("id"));
                        break;
                    }
                }
            }
        });
    }

    /**
     * Creates the JSONObject to be sent to the database and displays the updated post list on the response
     */
    public void makePost() {

        JSONObject j = new JSONObject();
        try {
            j.put("time", getTime());
            j.put("message", messageEnter.getText().toString());
            j.put("groupId", groupId);
            j.put("memberId", memberID);

            RequestsCentral.postJSONObject(Const.CREATE_NEW_ANNOUNCEMENT, j, new OnSuccessfulObject() {
                @Override
                public void onSuccess(JSONObject response) {
                    getPosts();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to be called to find all posts present in the database for the group currently being viewed
     */
    public void getPosts() {

        RequestsCentral.getJSONArray(Const.GET_ANNOUNCEMENTS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {

                JSONArray postsForGroup = new JSONArray();

                for (int i = response.length() - 1; i >= 0; i--) {
                    if (response.getJSONObject(i).getInt("groupId") == groupId) {
                        postsForGroup.put(response.getJSONObject(i));
                    }
                }

                showPosts(postsForGroup);
            }
        });
    }

    /**
     * Preps all posts from the JSONArray to be added to the layout and display groups
     * @param posts
     * @throws JSONException
     */
    public void showPosts(JSONArray posts) throws JSONException {

        LinearLayout container = findViewById(R.id.group_posts_scroller_linear_layout);
        container.removeAllViews();

        layoutCounter = 0;
        textViewCounter = 0;

        for (int i=0; i< posts.length(); i++) {

            JSONObject currentGroup = null;

            try {
                currentGroup = posts.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();

            }
            addGroupToLayout(container, currentGroup);

        }
    }

    /**
     * Adds a group to the layout to display them on the dashboard
     * @param container Layout to hold all of the groups on a dashboard
     * @param obj Group to add to the layout
     * @throws JSONException
     */
    public void addGroupToLayout(LinearLayout container, JSONObject obj) throws JSONException {

        TextView groupID = new TextView(GroupPosts.this);
        groupID.setText("ID: " + obj.getInt("id"));
        groupID.setTextColor(Color.BLACK);
        groupID.setId(++textViewCounter);

        TextView author = new TextView(GroupPosts.this);
        author.setText("Author: " + obj.getJSONObject("membersDetail").getJSONObject("usersDetail").getString("username"));
        author.setTextColor(Color.BLACK);
        author.setId(++textViewCounter);

        TextView message = new TextView(GroupPosts.this);
        message.setText("Message: " + obj.getString("message"));
        message.setTextColor(Color.BLACK);
        message.setId(++textViewCounter);

        TextView time = new TextView(GroupPosts.this);
        time.setText("Time: " + obj.getString("time"));
        time.setTextColor(Color.BLACK);
        time.setId(++textViewCounter);

        LinearLayout tempGroup = new LinearLayout(GroupPosts.this);
        tempGroup.setId(++layoutCounter);
        tempGroup.setOrientation(LinearLayout.VERTICAL);
        tempGroup.setHorizontalGravity(LinearLayout.HORIZONTAL);

        tempGroup.addView(groupID);
        tempGroup.addView(author);
        tempGroup.addView(message);
        tempGroup.addView(time);
        tempGroup.setClickable(true);
        tempGroup.setBackgroundColor(Color.LTGRAY);

        tempGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        container.addView(tempGroup);
    }

}