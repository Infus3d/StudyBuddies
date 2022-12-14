package com.example.studybuddies;

import static android.graphics.BlendMode.COLOR;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.studybuddies.app.AppController;
import com.example.studybuddies.databinding.ActivityDashboardBinding;
import com.example.studybuddies.databinding.ActivityPublicGroupsBinding;
import com.example.studybuddies.objects.Group;
import com.example.studybuddies.objects.User;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class represents the user dashboard page.
 * @author Andy Bruder
 */
public class Dashboard extends DrawerBaseActivity {

    private ActivityDashboardBinding activityDashboardBinding;

    private String TAG = Dashboard.class.getSimpleName();
    private String tag_json_obj = "jobj_req";

    private TextView welcomeUser;

    private Button createGroup;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String ID_KEY = "id_key";
    public static final String USERNAME_KEY = "username_key";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    public static final String LOCATION_KEY = "location_key";

    private int id;
    private String username_s;
    private String email_s;
    private String password_s;
    private String location_s;

    private User currentUser;

    /**
     * Shared preferences stores the information about the
     * user who is currently logged into the app
     */
    SharedPreferences sharedPreferences;

    private int layoutCounter;
    private int textViewCounter;

    /**
     * On the creation of the view, this method initializes all UI elements
     * present on this activity and populates the user's groups
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(ID_KEY, 0);
        username_s = sharedPreferences.getString(USERNAME_KEY, null);
        email_s = sharedPreferences.getString(EMAIL_KEY, null);
        password_s = sharedPreferences.getString(PASSWORD_KEY, null);
        location_s = sharedPreferences.getString(LOCATION_KEY, null);


        currentUser = new User(id,username_s,email_s,password_s,location_s);
        findUsersGroups(currentUser);

        allocateActivityTitle(username_s + "'s Dashboard");

        createGroup = findViewById(R.id.create_group);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateGroup.class));
            }
        });

    }

    /**
     * Adds a all groups to the layout
     * @param groups List of groups that the current user is a member of
     * @throws JSONException
     */
    public void showGroups(ArrayList<Group> groups) {

        LinearLayout container = findViewById(R.id.scrollerLinearLayout);
        container.removeAllViews();

        layoutCounter = 0;
        textViewCounter = 0;

        for (Group g : groups) {
            addGroupToLayout(container, g);
        }
    }

    /**
     * Adds a group to the layout to display them on the dashboard
     * @param container Layout to hold all of the groups on a dashboard
     * @param group Group to add to the layout
     * @throws JSONException
     */
    public void addGroupToLayout(LinearLayout container, Group group) {

        TextView groupID = new TextView(Dashboard.this);
        groupID.setText("ID: " + group.getId());
        groupID.setTextColor(Color.BLACK);
        groupID.setId(++textViewCounter);

        TextView groupTitle = new TextView(Dashboard.this);
        groupTitle.setText("Title: " + group.getTitle());
        groupTitle.setTextColor(Color.BLACK);
        groupTitle.setId(++textViewCounter);

        TextView isPublic = new TextView(Dashboard.this);
        isPublic.setText("isPublic: " + group.isPublic());
        isPublic.setTextColor(Color.BLACK);
        isPublic.setId(++textViewCounter);

        LinearLayout tempGroup = new LinearLayout(Dashboard.this);
        tempGroup.setId(++layoutCounter);
        tempGroup.setOrientation(LinearLayout.VERTICAL);
        tempGroup.setHorizontalGravity(LinearLayout.HORIZONTAL);

        tempGroup.addView(groupID);
        tempGroup.addView(groupTitle);
        tempGroup.addView(isPublic);
        tempGroup.setClickable(true);
        tempGroup.setBackgroundColor(Color.LTGRAY);

        tempGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GroupPage.class);

                i.putExtra("groupId", group.getId());
                i.putExtra("groupTitle", group.getTitle());
                i.putExtra("isPublic", group.isPublic());

                startActivity(i);
            }
        });

        container.addView(tempGroup);
    }

    /**
     * Finds the groups for the user currently logged in
     * @param user
     */
    public void findUsersGroups(User user) {

        Group.getGroups(user, new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                ArrayList<Group> groups = (ArrayList<Group>) a;
                showGroups(groups);
            }
        });

    }

}