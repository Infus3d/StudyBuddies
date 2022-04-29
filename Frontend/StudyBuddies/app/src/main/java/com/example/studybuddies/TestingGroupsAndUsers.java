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

        Group.getGroups(andy, new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                try {
                    showGroups(a);
                    Log.i(TAG, a.toString());
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

        LinearLayout tempGroup = new LinearLayout(TestingGroupsAndUsers.this);
        tempGroup.setId(++layoutCounter);
        tempGroup.setOrientation(LinearLayout.VERTICAL);
        tempGroup.setHorizontalGravity(LinearLayout.HORIZONTAL);

        tempGroup.addView(groupID);
        tempGroup.addView(groupTitle);
        tempGroup.addView(isPublic);
        tempGroup.setClickable(true);
        tempGroup.setBackgroundColor(Color.LTGRAY);

        tempGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GroupPage.class);

                i.putExtra("groupID", g.getId());
                i.putExtra("groupTitle", g.getTitle());
                i.putExtra("isPublic", g.isPublic());

                startActivity(i);
            }
        });

        container.addView(tempGroup);
    }

}