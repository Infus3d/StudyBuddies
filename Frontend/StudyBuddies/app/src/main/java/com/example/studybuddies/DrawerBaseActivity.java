package com.example.studybuddies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

/**
 * This class is serves as the base extendable activity that helps enables access to the menu
 * of the application from any activity that extends it.
 * @author Omar Muhammetkulyyev
 */
public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    /**
     * Method to set the current content view of the activity to a given parameter view
     * @param view Given view to replace the current content view
     */
    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.content_layout_toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Method that dictates what actions to take upon selection of the items from the menu.
     * When an item is selected, the associated activity with that item is initialized with a new Intent()
     * @param item selected Item from the menu
     * @return true if the action is taken, false otherwise
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.nav_publicGroups:
                startActivity(new Intent(this, PublicGroups.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_schedule:
                startActivity(new Intent(this, UserSchedule.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_dashboard:
                startActivity(new Intent(this, Dashboard.class));
                overridePendingTransition(0,0);
                break;

                /*
            case R.id.nav_settings:
                startActivity((new Intent(this, SettingsPage.class)));
                overridePendingTransition(0,0);
                break;*/


            case R.id.nav_profile:
                startActivity((new Intent(this, ProfilePage.class)));
                overridePendingTransition(0,0);
                break;

            case R.id.nav_logout:
                getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE).edit().clear().apply();
                startActivity(new Intent(this, CreateUser.class));
                break;

        }
        return false;
    }

    /**
     * Helper method that assigns the title of the page in the action bar at the top
     * @param titleString title of the page
     */
    protected void allocateActivityTitle(String titleString){
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(titleString);
    }
}