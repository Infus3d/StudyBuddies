package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studybuddies.databinding.ActivityCreateGroupBinding;
import com.example.studybuddies.databinding.ActivityGroupPageBinding;

public class GroupPage extends DrawerBaseActivity {

    private TextView welcomeMessage;
    private Button backButton;
    TextView headerText;

    ActivityGroupPageBinding activityGroupPageBinding;

    private int groupID;
    private String groupTitle;
    private String isPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGroupPageBinding = ActivityGroupPageBinding.inflate(getLayoutInflater());
        setContentView(activityGroupPageBinding.getRoot());

        Intent groupIntent = getIntent();

        Bundle extras = groupIntent.getExtras();

        groupID = extras.getInt("groupID");
        groupTitle = extras.getString("groupTitle");
        isPublic = extras.getString("isPublic");

        allocateActivityTitle(groupTitle);

        welcomeMessage = findViewById(R.id.welcome_group);
        welcomeMessage.setText("Welcome to " + groupTitle);


    }
}
