package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GroupPage extends AppCompatActivity {

    private TextView welcomeMessage;

    private int groupID;
    private String groupTitle;
    private String isPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);

        Intent groupIntent = getIntent();

        Bundle extras = groupIntent.getExtras();

        groupID = extras.getInt("groupID");
        groupTitle = extras.getString("groupTitle");
        isPublic = extras.getString("isPublic");

        welcomeMessage = findViewById(R.id.welcome_group);
        welcomeMessage.setText("Welcome to " + groupTitle);

    }
}
