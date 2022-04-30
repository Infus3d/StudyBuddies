package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.studybuddies.databinding.ActivityViewPostBinding;

public class ViewPost extends DrawerBaseActivity {

    ActivityViewPostBinding activityViewPostBinding;

    private int currentPermission;
    private String username;
    private String author;
    private String message;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewPostBinding = ActivityViewPostBinding.inflate(getLayoutInflater());
        setContentView(activityViewPostBinding.getRoot());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        currentPermission = extras.getInt("permission");
        username = extras.getString("username");
        author = extras.getString("author");
        message = extras.getString("message");
        time = extras.getString("time");

        allocateActivityTitle(author + "'s Post");
    }
}