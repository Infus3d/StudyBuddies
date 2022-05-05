package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.studybuddies.databinding.ActivityCreateGroupBinding;
import com.example.studybuddies.objects.Group;
import com.example.studybuddies.objects.User;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents the page to create a group.
 * @author Andy Bruder
 */
public class CreateGroup extends DrawerBaseActivity {

    private ActivityCreateGroupBinding activityCreateGroupBinding;

    private EditText groupNameEditText;
    private EditText groupDescriptionEditText;
    private Switch isPublicSwitch;
    private Button createGroupButton;

    private String newGroupName;
    private String newGroupDescription;
    private boolean newIsPublic;
    private String isPublicS;

    private JSONArray currentGroups;

    private int id;
    private String username_s;
    private String email_s;
    private String password_s;
    private String location_s;

    /**
     * Shared preferences stores the information about the
     * user who is currently logged into the app
     */
    SharedPreferences sharedPreferences;

    /**
     * On the creation of this view, this method initializes the UI elements
     * present. Populates the existing groups to obtain the ID of the new group.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateGroupBinding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Create a group");
        setContentView(activityCreateGroupBinding.getRoot());

        sharedPreferences = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(LoginScreen.ID_KEY, 0);
        username_s = sharedPreferences.getString(LoginScreen.USERNAME_KEY, null);
        email_s = sharedPreferences.getString(LoginScreen.EMAIL_KEY, null);
        password_s = sharedPreferences.getString(LoginScreen.PASSWORD_KEY, null);
        location_s = sharedPreferences.getString(LoginScreen.LOCATION_KEY, null);

        groupNameEditText = findViewById(R.id.group_name_edit_text);
        groupDescriptionEditText = findViewById(R.id.group_description);
        isPublicSwitch = findViewById(R.id.is_public_switch);
        createGroupButton = findViewById(R.id.create_group_button);

        /**
         * Sends the information entered by the user to the server to
         * create a group. Then sends information about the group and
         * the user to the server to tie the user to that group
         */
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newGroupName = groupNameEditText.getText().toString();
                newGroupDescription = groupDescriptionEditText.getText().toString();
                newIsPublic = isPublicSwitch.isChecked();

                if (newGroupName != "" && newGroupDescription != ""){

                Group group = new Group(newGroupName, newIsPublic);
                createGroup(group);

                }
            }
        });

    }

    public void createGroup(Group group) {
        RequestsCentral.postJSONObject(Const.CREATE_NEW_GROUP, group.toJSONForServer(), new OnSuccessfulObject() {
            @Override
            public void onSuccess(JSONObject response) {

                try {
                    createMember(response.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void createMember(int newGroupId) throws JSONException {

        JSONObject newMember = new JSONObject();

            newMember.put("userId", id);
            newMember.put("groupId", newGroupId);
            newMember.put("permission", 2); //2 as in Owner of the group, 1 for those who 'join' the group for public page

        RequestsCentral.postJSONObject(Const.CREATE_NEW_MEMBER, newMember, new OnSuccessfulObject() {
            @Override
            public void onSuccess(JSONObject response) {

                Intent i = new Intent(getApplicationContext(), GroupPage.class);

                i.putExtra("groupID", newGroupId);
                i.putExtra("groupTitle", newGroupName);
                i.putExtra("isPublic", newIsPublic);

                startActivity(i);

            }
        });

    }

}