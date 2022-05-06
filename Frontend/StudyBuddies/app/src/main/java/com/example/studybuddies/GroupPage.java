package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;
import com.example.studybuddies.databinding.ActivityGroupPageBinding;
import com.example.studybuddies.objects.Member;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class represents the home page for any given group.
 * @author Andy Bruder
 */
public class GroupPage extends DrawerBaseActivity {

    private TextView welcomeMessage;
    private Button membersButton;
    private Button groupPostsButton;
    private Button groupChatButton;
    private Button deleteGroupButton;

    private ActivityGroupPageBinding activityGroupPageBinding;

    private int userId;
    private int groupID;
    private String groupTitle;
    private boolean isPublic;
    private int memberID;
    private int currentPermission;

    /**
     * Shared preferences stores the information about the
     * user who is currently logged into the app
     */
    private SharedPreferences sharedPreferences;

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

        groupID = extras.getInt("groupId");
        groupTitle = extras.getString("groupTitle");
        isPublic = extras.getBoolean("isPublic");
        userId = sharedPreferences.getInt(LoginScreen.ID_KEY, 0);

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

        deleteGroupButton = findViewById(R.id.delete_group_button);
        Member.getMembers(groupID, new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                for (Object o : a) {
                    Member m = (Member) o;
                    if (m.getGroupId() == groupID && m.getUserId() == userId) {
                        memberID = m.getId();
                        currentPermission = m.getPermission();
                        if (currentPermission >= 2) {
                            deleteGroupButton.setVisibility(View.VISIBLE);
                            deleteGroupButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Member.getMembers(groupID, new OnFinishedArrayList() {
                                        @Override
                                        public void onFinishedArrayList(ArrayList a) {
                                            for (Object o : a) {
                                                Member member = (Member) o;
                                                String deleteMemberURL = Const.GET_MEMBERS + "/" + ((Member) o).getId();
                                                RequestsCentral.deleteJSONObject(deleteMemberURL, new OnSuccessfulObject() {
                                                    @Override
                                                    public void onSuccess(JSONObject response) throws JSONException {
                                                        Log.i("onSuccess", "member successfully deleted");
                                                        String deleteGroupURL = Const.GET_GROUPS + "/" + groupID;
                                                        RequestsCentral.deleteJSONObject(deleteGroupURL, new OnSuccessfulObject() {
                                                            @Override
                                                            public void onSuccess(JSONObject response) throws JSONException {
                                                                Log.i("onSuccess", "group successfully deleted");
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                });

                                            }
                                        }
                                    });
                                }
                            });
                        }
                        break;
                    }
                }
            }
        });
    }
}
