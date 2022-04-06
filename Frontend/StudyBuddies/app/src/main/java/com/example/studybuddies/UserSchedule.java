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
    }
}