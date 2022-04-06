package com.example.studybuddies;

import static android.graphics.BlendMode.COLOR;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    private String TAG = Dashboard.class.getSimpleName();
    private String tag_json_obj = "jobj_req";

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
        for (int i=groups.length()-1; i>=0; i--) {
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

}