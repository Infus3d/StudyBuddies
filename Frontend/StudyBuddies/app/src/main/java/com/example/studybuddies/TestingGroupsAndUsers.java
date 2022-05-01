package com.example.studybuddies;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studybuddies.databinding.ActivityTestingGroupsAndUsersBinding;
import com.example.studybuddies.objects.Group;
import com.example.studybuddies.objects.User;
import com.example.studybuddies.utils.OnFinishedArrayList;

import org.json.JSONException;

import java.util.ArrayList;

public class TestingGroupsAndUsers extends DrawerBaseActivity {

    ActivityTestingGroupsAndUsersBinding activityTestingGroupsAndUsersBinding;

    private int layoutCounter, textViewCounter;
    private static final int SIMULATED_DELAY_MS = 250;

    private String TAG = TestingGroupsAndUsers.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTestingGroupsAndUsersBinding = ActivityTestingGroupsAndUsersBinding.inflate(getLayoutInflater());
        setContentView(activityTestingGroupsAndUsersBinding.getRoot());
        allocateActivityTitle("Testing Groups and Users");

        User andy = new User(11, "andy", "andy@gmail.com", "pass", "Ames, Iowa");

        /*
        Group.getGroups( new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                try {
                    showGroups(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/

        Group g = new Group(2, "COMS S 327", true);

        User.getUsers(g, new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                try {
                    showUsers(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void showGroups(ArrayList<Group> g) throws JSONException {

        LinearLayout container = findViewById(R.id.groupsScrollerLinearLayout);
        container.removeAllViews();

        layoutCounter = 0;
        textViewCounter = 0;

        for (Group group : g) {

            addGroupToLayout(container, group);

        }
    }

    public void addGroupToLayout(LinearLayout container, Group g) throws JSONException {

        TextView groupID = new TextView(TestingGroupsAndUsers.this);
        groupID.setText("ID: " + g.getId());
        groupID.setTextColor(Color.BLACK);
        groupID.setId(++textViewCounter);

        TextView groupTitle = new TextView(TestingGroupsAndUsers.this);
        groupTitle.setText("Title: " + g.getTitle());
        groupTitle.setTextColor(Color.BLACK);
        groupTitle.setId(++textViewCounter);

        TextView isPublic = new TextView(TestingGroupsAndUsers.this);
        isPublic.setText("isPublic: " + g.isPublic());
        isPublic.setTextColor(Color.BLACK);
        isPublic.setId(++textViewCounter);

        LinearLayout tempUser = new LinearLayout(TestingGroupsAndUsers.this);
        tempUser.setId(++layoutCounter);
        tempUser.setOrientation(LinearLayout.VERTICAL);
        tempUser.setHorizontalGravity(LinearLayout.HORIZONTAL);

        tempUser.addView(groupID);
        tempUser.addView(groupTitle);
        tempUser.addView(isPublic);
        tempUser.setClickable(true);
        tempUser.setBackgroundColor(Color.LTGRAY);

        tempUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GroupPage.class);

                i.putExtra("groupID", g.getId());
                i.putExtra("groupTitle", g.getTitle());
                i.putExtra("isPublic", g.isPublic());

                startActivity(i);
            }
        });

        container.addView(tempUser);
    }

    public void showUsers(ArrayList<User> users) throws JSONException {

        LinearLayout container = findViewById(R.id.groupsScrollerLinearLayout);
        container.removeAllViews();

        layoutCounter = 0;
        textViewCounter = 0;

        for (User user : users) {

            addUserToLayout(container, user);

        }
    }

    public void addUserToLayout(LinearLayout container, User user) throws JSONException {

        TextView userID = new TextView(TestingGroupsAndUsers.this);
        userID.setText("ID: " + user.getId());
        userID.setTextColor(Color.BLACK);
        userID.setId(++textViewCounter);

        TextView username = new TextView(TestingGroupsAndUsers.this);
        username.setText("username: " + user.getUsername());
        username.setTextColor(Color.BLACK);
        username.setId(++textViewCounter);

        TextView location = new TextView(TestingGroupsAndUsers.this);
        location.setText("location: " + user.getLocation());
        location.setTextColor(Color.BLACK);
        location.setId(++textViewCounter);

        TextView email = new TextView(TestingGroupsAndUsers.this);
        email.setText("email: " + user.getEmail());
        email.setTextColor(Color.BLACK);
        email.setId(++textViewCounter);

        LinearLayout tempUser = new LinearLayout(TestingGroupsAndUsers.this);
        tempUser.setId(++layoutCounter);
        tempUser.setOrientation(LinearLayout.VERTICAL);
        tempUser.setHorizontalGravity(LinearLayout.HORIZONTAL);

        tempUser.addView(userID);
        tempUser.addView(username);
        tempUser.addView(location);
        tempUser.addView(email);
        tempUser.setClickable(true);
        tempUser.setBackgroundColor(Color.LTGRAY);

        container.addView(tempUser);
    }

}