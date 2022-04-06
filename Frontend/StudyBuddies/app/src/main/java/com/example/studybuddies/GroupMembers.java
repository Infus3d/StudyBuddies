package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studybuddies.databinding.ActivityGroupMembersBinding;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;

public class GroupMembers extends DrawerBaseActivity {

    ActivityGroupMembersBinding activityGroupMembersBinding;
    JSONArray members;
    int groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGroupMembersBinding = ActivityGroupMembersBinding.inflate(getLayoutInflater());
        setContentView(activityGroupMembersBinding.getRoot());
        allocateActivityTitle("Members");

        Bundle extras = getIntent().getExtras();

        groupID = extras.getInt("groupID");





    }

    public void searchMembersForGroup(){

        final JSONArray[] allMembers = new JSONArray[1];

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) {
                allMembers[0] = response;
            }
        });


    }




}