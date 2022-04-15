package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.LinkAddress;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Bundle;

import com.example.studybuddies.databinding.ActivityGroupMembersBinding;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This activity shows the members of the group that this activity was accessed from.
 * Extends DrawBaseActivity in order to get access to the menu.
 * @author Omar Muhammetkulyyev
 */
public class GroupMembers extends DrawerBaseActivity {
    private ActivityGroupMembersBinding activityGroupMembersBinding;
    private Intent incomingIntent;
    private JSONArray members;
    private int groupID;

    /**
     * On creation of the view this method embeds itself in the activity frame of the super class
     * as well as initializing all of the views relevant to this page.
     * @param savedInstanceState required Bundle of instances to initialize the activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGroupMembersBinding = ActivityGroupMembersBinding.inflate(getLayoutInflater());
        setContentView(activityGroupMembersBinding.getRoot());
        allocateActivityTitle("Group Members");

        incomingIntent = getIntent();
        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                ArrayList<JSONObject> alist = new ArrayList<JSONObject>();
                for(int i=response.length()-1; i>=0; i--){
                    JSONObject cur = response.getJSONObject(i);
//                    uncomment this line when synced with group home page
//                    if(cur.getString("groupId") != null && cur.getString("groupId").equals(incomingIntent.getStringExtra("groupId")))
                        alist.add(cur);
                }
                showAllMembers(alist);
            }
        });
    }

    /**
     * Helper method that shows all of the members in this group
     * @param alist List of members as JSON objects to display
     */
    private void showAllMembers(ArrayList<JSONObject> alist){
        LinearLayout container = findViewById(R.id.scrollViewLinearContainer_group_members);
        container.removeAllViews();

        for(int i=alist.size()-1; i>=0; i--){
            try{
                addMember(container, alist.get(i));
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper method that shows all of the members in this group
     * @param jsonArray An array of members as JSON objects to display
     */
    private void showAllMembers(JSONArray jsonArray){
        LinearLayout container = findViewById(R.id.scrollViewLinearContainer_group_members);
        container.removeAllViews();

        for(int i=jsonArray.length()-1; i>=0; i--){
            try {
                addMember(container, jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper method that creates an empty linear layout view for a specific member and adds that
     * member to the container view
     * @param container The ultimate view to contain all members
     * @param obj   JSONObject representing current member to add
     * @throws JSONException
     */
    private void addMember(LinearLayout container, JSONObject obj) throws JSONException {
        LinearLayout memberContainer = new LinearLayout(this);

        Button makeOwnerButton = new Button(this);
        Button kickButton = new Button(this);
        makeOwnerButton.setText("Make Owner");
        kickButton.setText("Kick");

        TextView memberName = new TextView(this);
        memberName.setText("Username: " + obj.getJSONObject("membersDetail").getString("username"));
        memberName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView memberAccess = new TextView(this);
        memberAccess.setText("Permission: " + (obj.getInt("permission") == 1 ? "read only" : (obj.getInt("permission") == 2 ? "read and write" : "read and write and edit")));
        memberAccess.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        memberContainer.setOrientation(LinearLayout.VERTICAL);
        memberContainer.addView(memberName);
        memberContainer.addView(memberAccess);
        memberContainer.addView(makeOwnerButton);
        memberContainer.addView(kickButton);
        memberContainer.setVisibility(View.VISIBLE);

        container.addView(memberContainer);
    }
}