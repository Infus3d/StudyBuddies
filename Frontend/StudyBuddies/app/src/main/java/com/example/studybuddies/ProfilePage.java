package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studybuddies.databinding.ActivityProfilePageBinding;

public class ProfilePage extends DrawerBaseActivity {
    ActivityProfilePageBinding activityProfilePageBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfilePageBinding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(activityProfilePageBinding.getRoot());
        allocateActivityTitle("Profile");


    }
}