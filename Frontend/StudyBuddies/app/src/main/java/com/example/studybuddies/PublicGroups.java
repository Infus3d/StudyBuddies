package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.studybuddies.databinding.ActivityPublicGroupsBinding;

public class PublicGroups extends DrawerBaseActivity {
    ActivityPublicGroupsBinding activityPublicGroupsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPublicGroupsBinding = ActivityPublicGroupsBinding.inflate(getLayoutInflater());
        setContentView(activityPublicGroupsBinding.getRoot());
        allocateActivityTitle("Public Groups");

        SharedPreferences sharedpref = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);
        String name = sharedpref.getString("name", "");
        String email = sharedpref.getString("email", "");
        Integer id = sharedpref.getInt("id", -1);

        TextView message = (TextView) findViewById(R.id.PublicGroups_textview);
        message.setText(message.getText().toString() + "\n" + "name " + name + "\n" + "email " + email + "\n" + "id " + id);
    }
}