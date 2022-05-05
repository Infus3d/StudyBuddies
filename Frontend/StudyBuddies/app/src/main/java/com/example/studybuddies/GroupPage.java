package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;
import com.example.studybuddies.databinding.ActivityGroupPageBinding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class represents the home page for any given group.
 * @author Andy Bruder
 */
public class GroupPage extends DrawerBaseActivity {

    private TextView welcomeMessage;
    private Button membersButton;
    private Button groupPostsButton;
    private Button groupChatButton;

    private ActivityGroupPageBinding activityGroupPageBinding;

    private int groupID;
    private String groupTitle;
    private boolean isPublic;

    /**
     * Shared preferences stores the information about the
     * user who is currently logged into the app
     */
    SharedPreferences sharedPreferences;


    /**
     * On creation of this view, this method initializes all of
     * the UI elements present, grabs information from the Intent
     * object sent by the previous screen, and sets up the page accordingly
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGroupPageBinding = ActivityGroupPageBinding.inflate(getLayoutInflater());
        setContentView(activityGroupPageBinding.getRoot());

        sharedPreferences = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);

        Intent groupIntent = getIntent();

        Bundle extras = groupIntent.getExtras();

        groupID = extras.getInt("groupID");
        groupTitle = extras.getString("groupTitle");
        isPublic = extras.getBoolean("isPublic");

        allocateActivityTitle(groupTitle);

        welcomeMessage = findViewById(R.id.welcome_group);
        welcomeMessage.setText("Welcome to " + groupTitle);

        membersButton = findViewById(R.id.members_button);
        membersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GroupMembers.class);
                intent.putExtra("groupId", groupID);
                startActivity(intent);
            }
        });

        groupPostsButton = findViewById(R.id.posts_button);
        groupPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GroupPosts.class);
                intent.putExtra("groupId", groupID);
                intent.putExtra("title", groupTitle);
                startActivity(intent);
            }
        });

        groupChatButton = findViewById(R.id.group_chat_button);
        groupChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GroupChat.class);
                intent.putExtra("groupId", groupID);
                intent.putExtra("title", groupTitle);
                startActivity(intent);
            }
        });
    }
}
