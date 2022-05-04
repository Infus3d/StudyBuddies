package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.studybuddies.adapters.CalendarEventRecyclerViewAdapter;
import com.example.studybuddies.databinding.ActivityUserScheduleBinding;
import com.example.studybuddies.objects.CalendarEvent;

import java.util.ArrayList;

/**
 * This class represents a User Schedule Page where users can view their personal schedule of activities.
 * Extends DrawerBaseActivity in order to get access to the menu.
 * @author Omar Muhammetkulyyev
 */
public class UserSchedule extends DrawerBaseActivity {
    ActivityUserScheduleBinding activityUserScheduleBinding;

    private RecyclerView calendarEventRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserScheduleBinding = ActivityUserScheduleBinding.inflate(getLayoutInflater());
        setContentView(activityUserScheduleBinding.getRoot());
        allocateActivityTitle("Schedule");

        calendarEventRecView = findViewById(R.id.calendarEventRecyclerView);
        ArrayList<CalendarEvent> calendarEvents = new ArrayList<>();
        calendarEvents.add(new CalendarEvent(0, 0, "Message one", "5/4/2022 10:00", "Personal Event"));
        calendarEvents.add(new CalendarEvent(1, 0, "Message two", "5/4/2022 10:00", "Personal Event"));
        calendarEvents.add(new CalendarEvent(2, 0, "Message three", "5/4/2022 10:00", "Personal Event"));
        calendarEvents.add(new CalendarEvent(3, 0, "Message four", "5/4/2022 10:00", "Personal Event"));
        calendarEvents.add(new CalendarEvent(4, 0, "Lorem ipsum dolor sit amet. Ut magni dolorum est magnam eaque eum possimus Quis ad fugiat dolor ut recusandae animi. Aut saepe reprehenderit eos Quis fugit cum nostrum neque. Est repellat odio in velit delectus sed animi corporis aut temporibus tempore!\n" +
                "\n" +
                "Est pariatur quia At itaque tempora et optio debitis est tempora voluptates sed saepe itaque nam consectetur velit. 33 fugit obcaecati et tenetur galisum ut esse suscipit et voluptas recusandae id internos numquam cum odit veniam. Recusandae fugit et dolorem totam et enim placeat est labore quod et ullam voluptatibus. Non velit tenetur qui consequuntur recusandae qui totam rerum et amet galisum aut nostrum autem ut iure amet et placeat nobis.", "5/4/2022 10:00", "Personal Event"));

        CalendarEventRecyclerViewAdapter adapter = new CalendarEventRecyclerViewAdapter();
        adapter.setCalendarEvents(calendarEvents);

        calendarEventRecView.setAdapter(adapter);
        calendarEventRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}