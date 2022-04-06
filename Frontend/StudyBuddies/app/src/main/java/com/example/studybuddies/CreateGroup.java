package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studybuddies.databinding.ActivityCreateGroupBinding;

public class CreateGroup extends DrawerBaseActivity {

    ActivityCreateGroupBinding activityCreateGroupBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateGroupBinding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Create a group");
        setContentView(activityCreateGroupBinding.getRoot());


    }
}