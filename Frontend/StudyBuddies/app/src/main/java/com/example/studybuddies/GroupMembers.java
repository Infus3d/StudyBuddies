package com.example.studybuddies;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.example.studybuddies.adapters.GroupMembersRecyclerViewAdapter;
import com.example.studybuddies.databinding.ActivityGroupMembersBinding;
import com.example.studybuddies.objects.Member;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
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
    private static final String TAG = "GroupMembers";
    private SharedPreferences sharedPreferences;
    private Member userMembership;
    private int groupID;
    private int userID;

    private RecyclerView groupMembersRecView;
    private GroupMembersRecyclerViewAdapter adapter;


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
        Bundle extras = incomingIntent.getExtras();
        groupID = extras.getInt("groupId");

        sharedPreferences = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt(LoginScreen.ID_KEY, 0);

        groupMembersRecView = findViewById(R.id.groupMembersRecyclerView);
        adapter = new GroupMembersRecyclerViewAdapter(this, this.groupID);
        groupMembersRecView.setAdapter(adapter);
        groupMembersRecView.setLayoutManager(new LinearLayoutManager(this));

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                Log.d(TAG, "Maybe this time? " + response.length());
                for(int i=0; i<response.length(); i++){
                    JSONObject jsonObject = response.getJSONObject(i);
                    if(jsonObject.getInt("groupId") == groupID && jsonObject.getInt("userId") == userID){
                        userMembership = new Member(jsonObject);
                        refreshRecView();
                        break;
                    }
                }
            }
        });
    }

    private void refreshRecView(){
        ArrayList<Member> members = new ArrayList<>();
        Member.getMembers(groupID, new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                ArrayList<Member> responseMembers = (ArrayList<Member>) a;
                for(int i=0; i<responseMembers.size(); i++){
                    responseMembers.get(i).setEditable((userMembership.getPermission() > 1 && responseMembers.get(i).getUserId() != userID) ? true : false);
                    members.add(new Member(responseMembers.get(i)));
                }
                adapter.setMembers(members);
            }
        });
    }

//    /**
//     * Helper method that shows all of the members in this group
//     * @param members List of members as JSON objects to display
//     */
//    private void showAllMembers(ArrayList<Member> members){
//        LinearLayout container = findViewById(R.id.scrollViewLinearContainer_group_members);
//        container.removeAllViews();
//
//        for(Member member : members){
//            addMember(container, member);
//        }
//    }
//
//    /**
//     * Helper method that creates an empty linear layout view for a specific member and adds that
//     * member to the container view
//     * @param container The ultimate view to contain all members
//     * @param member   Member representing current member to add
//     * @throws JSONException
//     */
//    private void addMember(LinearLayout container, Member member) {
//        LinearLayout memberContainer = new LinearLayout(this);
//
//        Button makeOwnerButton = new Button(this);
//        Button kickButton = new Button(this);
//        makeOwnerButton.setText("Make Owner");
//        kickButton.setText("Kick");
//
//        TextView memberName = new TextView(this);
//        memberName.setText("Username: " + member.getUser().getUsername());
//        memberName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//
//        TextView memberAccess = new TextView(this);
//        memberAccess.setText("Permission: " + (member.getPermission() == 1 ? "read only" : (member.getPermission() == 2 ? "read and write" : "read and write and edit")));
//        memberAccess.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//
//        memberContainer.setOrientation(LinearLayout.VERTICAL);
//        memberContainer.addView(memberName);
//        memberContainer.addView(memberAccess);
//        memberContainer.addView(makeOwnerButton);
//        memberContainer.addView(kickButton);
//        memberContainer.setVisibility(View.VISIBLE);
//
//        container.addView(memberContainer);
//    }
}