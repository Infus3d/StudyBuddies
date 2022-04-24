package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.studybuddies.databinding.ActivityGroupPostsBinding;

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
}