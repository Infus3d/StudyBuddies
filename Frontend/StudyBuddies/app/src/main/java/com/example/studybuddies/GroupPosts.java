package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studybuddies.databinding.ActivityGroupPostsBinding;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupPosts extends DrawerBaseActivity {

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

    }

    public void getPosts() {

        RequestsCentral.getJSONArray(Const.GET_POSTS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {

                JSONArray postsForGroup = new JSONArray();

                for (int i = 0; i < response.length(); i++) {
                    if (response.getJSONObject(i).getInt("groupId") == groupId) {
                        postsForGroup.put(response.getJSONObject(i));
                    }
                }

                showPosts(postsForGroup);
            }
        });
    }

    public void showPosts(JSONArray groups) throws JSONException {

        LinearLayout container = findViewById(R.id.group_posts_scroller_linear_layout);
        container.removeAllViews();

        layoutCounter = 0;
        textViewCounter = 0;

        for (int i=0; i<groups.length(); i++) {

            JSONObject currentGroup = null;

            try {
                currentGroup = groups.getJSONObject(i);
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
        author.setText("Author: " + obj.getJSONObject("membersDetail").getString("username"));
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