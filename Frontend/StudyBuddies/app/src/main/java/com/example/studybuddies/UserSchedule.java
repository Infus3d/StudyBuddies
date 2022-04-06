package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.studybuddies.databinding.ActivityUserScheduleBinding;

public class UserSchedule extends DrawerBaseActivity {
    ActivityUserScheduleBinding activityUserScheduleBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserScheduleBinding = ActivityUserScheduleBinding.inflate(getLayoutInflater());
        setContentView(activityUserScheduleBinding.getRoot());
        allocateActivityTitle("Schedule");
        SharedPreferences sharedpref = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();

        editor.putString("name", "Omar");
        editor.putString("email", "omar99@iastate.edu");
        editor.putInt("id", 69);
        editor.apply();

        Button btn = (Button) findViewById(R.id.submitButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context ctx = view.getContext();

                startActivity(new Intent(view.getContext(), LoginScreen.class));
            }
        });
    }
}