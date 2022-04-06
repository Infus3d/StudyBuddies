package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studybuddies.databinding.ActivityGroupMembersBinding;
import com.example.studybuddies.databinding.ActivityPublicGroupsBinding;

public class GroupMembers extends DrawerBaseActivity {
    ActivityGroupMembersBinding activityGroupMembersBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGroupMembersBinding = ActivityGroupMembersBinding.inflate(getLayoutInflater());
        setContentView(activityGroupMembersBinding.getRoot());
        allocateActivityTitle("Group Members");
    }
}