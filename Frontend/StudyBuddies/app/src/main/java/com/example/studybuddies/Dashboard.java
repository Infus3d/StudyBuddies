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
import com.example.studybuddies.utils.Const;
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

public class Dashboard extends AppCompatActivity {

    private String TAG = Dashboard.class.getSimpleName();
    private String tag_json_obj = "jobj_req";

    private TextView welcomeUser;

    private Button profileButton;
    private Button settingsButton;
    private Button dashboardButton;
    private Button groupsButton;
    private Button scheduleButton;

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
    SharedPreferences sharedPreferences;

    private int layoutCounter;
    private int textViewCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getGroups();

        welcomeUser = findViewById(R.id.welcome_user);

        profileButton = findViewById(R.id.profile);
        settingsButton = findViewById(R.id.settings);
        dashboardButton = findViewById(R.id.dashboard);
        groupsButton = findViewById(R.id.dashboard);
        scheduleButton = findViewById(R.id.schedule);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(ID_KEY, 0);
        username_s = sharedPreferences.getString(USERNAME_KEY, null);
        email_s = sharedPreferences.getString(EMAIL_KEY, null);
        password_s = sharedPreferences.getString(PASSWORD_KEY, null);
        location_s = sharedPreferences.getString(LOCATION_KEY, null);

        welcomeUser.setText("WELCOME " + username_s.toUpperCase(Locale.ROOT));

        /**
         * Uncomment when profile page is implemented
         *
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(i);
            }
        });
         */

        /**
         *
         * Uncomment when settings page is implemented
         *
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsPage.class);
                startActivity(i);
            }
        });
         */

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGroups();
            }
        });

        /**
         * Uncomment when public groups page is implemented
         *
        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PublicGroups.class);
                startActivity(i);
            }
        });
         */

        /**
         * Uncomment when schedule page is implemented
         *
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SchedulePage.class);
                startActivity(i);
            }
        });
         */

    }

    public void showGroups(JSONArray groups) throws JSONException {

        LinearLayout container = findViewById(R.id.scrollerLinearLayout);
        container.removeAllViews();

        layoutCounter = 0;
        textViewCounter = 0;

        for (int i=0; i<groups.length(); i++) {

            JSONObject currentGroup = null;

            try {
                currentGroup = groups.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();

            }
            addGroupToLayout(container, currentGroup);

        }
    }

    public void addGroupToLayout(LinearLayout container, JSONObject obj) throws JSONException {

        TextView groupID = new TextView(Dashboard.this);
        groupID.setText("ID: " + obj.getInt("id"));
        groupID.setTextColor(Color.BLACK);
        groupID.setId(++textViewCounter);

        TextView groupTitle = new TextView(Dashboard.this);
        groupTitle.setText("Title: " + obj.getString("title"));
        groupTitle.setTextColor(Color.BLACK);
        groupTitle.setId(++textViewCounter);

        TextView isPublic = new TextView(Dashboard.this);
        isPublic.setText("isPublic: " + obj.getString("isPublic"));
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

        tempGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GroupPage.class);

                try {
                    i.putExtra("groupID", obj.getInt("id"));
                    i.putExtra("groupTitle", obj.getString("title"));
                    i.putExtra("isPublic", obj.getString("isPublic"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(i);
            }
        });

        container.addView(tempGroup);
    }

    public void getGroups() {

        RequestsCentral.getJSONArray(Const.GET_GROUPS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) {
                try {
                    showGroups(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * THIS IS TO BE IMPLEMENTED ONCE THE MEMBERS TABLE IS UP AND RUNNING
     * NOT YET COMPLETE, BUT HOPEFULLY A GOOD START FOR WHEN THAT IS UP
     *
    public void findUsersGroups() {

        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) {
                searchMembersForUser(response);
            }
        });

    }

    public JSONArray searchMembersForUser(JSONArray members) {

        JSONArray userGroups = null;

        for (int i = 0; i < members.length(); i++) {

            try {
                JSONObject member = members.getJSONObject(i);
                // make sure to check that the user id is called userID when members is implemented
                int CurrentMemberID = member.getInt("userID");
                if (CurrentMemberID == id) {
                    int groupID = member.getInt("id");
                    RequestsCentral.getJSONObject(Const.GET_GROUPS + "/" + groupID, new OnSuccessfulObject() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            userGroups.put(response.toString());
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return userGroups;

    }
     */




}