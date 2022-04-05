package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.studybuddies.utils.PopulateUserGroups;

import org.json.JSONArray;

public class Dashboard extends AppCompatActivity {

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

    private JSONArray groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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

        groups = PopulateUserGroups.getGroups();

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
                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(i);
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
}