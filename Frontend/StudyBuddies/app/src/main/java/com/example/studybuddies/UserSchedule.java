package com.example.studybuddies;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CalendarView;

import com.example.studybuddies.adapters.CalendarEventRecyclerViewAdapter;
import com.example.studybuddies.databinding.ActivityUserScheduleBinding;
import com.example.studybuddies.objects.CalendarEvent;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * This class represents a User Schedule Page where users can view their personal schedule of activities.
 * Extends DrawerBaseActivity in order to get access to the menu.
 * @author Omar Muhammetkulyyev
 */
public class UserSchedule extends DrawerBaseActivity {
    ActivityUserScheduleBinding activityUserScheduleBinding;

    private RecyclerView calendarEventRecView;
    private CalendarEventRecyclerViewAdapter adapter;
    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserScheduleBinding = ActivityUserScheduleBinding.inflate(getLayoutInflater());
        setContentView(activityUserScheduleBinding.getRoot());
        allocateActivityTitle("Schedule");

        calendarView = (CalendarView) findViewById(R.id.personalCalendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                refreshRecView(getStringDate(i, i1+1, i2));
            }
        });

        calendarEventRecView = findViewById(R.id.calendarEventRecyclerView);
        adapter = new CalendarEventRecyclerViewAdapter();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        refreshRecView(sdf.format(new Date(calendarView.getDate())));

        calendarEventRecView.setAdapter(adapter);
        calendarEventRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
    private String getStringDate(int year, int month, int day){
        String sDay = (day < 10 ? "0" : "") + day;
        String sMonth = (month < 10 ? "0" : "") + month;
        String sYear = (year < 10 ? "0" : "") + year;
        return sMonth + "/" + sDay + "/" + sYear;
    }
    private void refreshRecView(String dateToMatch){
        ArrayList<CalendarEvent> calendarEvents = new ArrayList<>();
//        calendarEvents.add(new CalendarEvent(0, 0, "Message one", "5/4/2022 10:00", "Personal Event"));
//        calendarEvents.add(new CalendarEvent(1, 0, "Message two", "5/4/2022 10:00", "Personal Event"));
//        calendarEvents.add(new CalendarEvent(2, 0, "Message three", "5/4/2022 10:00", "Personal Event"));
//        calendarEvents.add(new CalendarEvent(3, 0, "Message four", "5/4/2022 10:00", "Personal Event"));
//        calendarEvents.add(new CalendarEvent(4, 0, "Lorem ipsum dolor sit amet. Ut magni dolorum est magnam eaque eum possimus Quis ad fugiat dolor ut recusandae animi. Aut saepe reprehenderit eos Quis fugit cum nostrum neque. Est repellat odio in velit delectus sed animi corporis aut temporibus tempore!\n" +
//                "\n" +
//                "Est pariatur quia At itaque tempora et optio debitis est tempora voluptates sed saepe itaque nam consectetur velit. 33 fugit obcaecati et tenetur galisum ut esse suscipit et voluptas recusandae id internos numquam cum odit veniam. Recusandae fugit et dolorem totam et enim placeat est labore quod et ullam voluptatibus. Non velit tenetur qui consequuntur recusandae qui totam rerum et amet galisum aut nostrum autem ut iure amet et placeat nobis.", "5/4/2022 10:00", "Personal Event"));

        RequestsCentral.getJSONArray(Const.GET_PERSONAL_EVENTS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for(int i=0; i<response.length(); i++){
                    JSONObject jsonObject = response.getJSONObject(i);
                    if(jsonObject.getString("time").substring(0, 10).equals(dateToMatch))
                        calendarEvents.add(new CalendarEvent(jsonObject));
                }
                RequestsCentral.getJSONArray(Const.GET_GROUP_EVENTS, new OnSuccessfulArray() {
                    @Override
                    public void onSuccess(JSONArray response2) throws JSONException {
                        for(int j=0; j<response2.length(); j++){
                            JSONObject jsonObject = response2.getJSONObject(j);
                            if(jsonObject.getString("time").substring(0, 10).equals(dateToMatch))
                                calendarEvents.add(new CalendarEvent(jsonObject));
                        }
                        Collections.sort(calendarEvents);
                        adapter.setCalendarEvents(calendarEvents);
                    }
                });
            }
        });
    }
}