package com.example.studybuddies;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.studybuddies.adapters.CalendarEventRecyclerViewAdapter;
import com.example.studybuddies.databinding.ActivityUserScheduleBinding;
import com.example.studybuddies.objects.CalendarEvent;
import com.example.studybuddies.objects.Group;
import com.example.studybuddies.objects.Member;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

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
    private SharedPreferences sharedPreferences;
    private int user_ID;
    private ArrayList<Member> userMemberships;
    private String selectedCalendarDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserScheduleBinding = ActivityUserScheduleBinding.inflate(getLayoutInflater());
        setContentView(activityUserScheduleBinding.getRoot());
        allocateActivityTitle("Schedule");

        sharedPreferences = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);
        user_ID = sharedPreferences.getInt(LoginScreen.ID_KEY, 0);

        calendarView = (CalendarView) findViewById(R.id.personalCalendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                refreshRecView(getStringDate(i, i1+1, i2));
                selectedCalendarDate = getStringDate(i, i1+1, i2);
            }
        });

        calendarEventRecView = findViewById(R.id.calendarEventRecyclerView);
        adapter = new CalendarEventRecyclerViewAdapter(this);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        selectedCalendarDate = sdf.format(new Date(calendarView.getDate()));
        Member.getMemberships(user_ID, new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                userMemberships = (ArrayList<Member>) a;
                refreshRecView(sdf.format(new Date(calendarView.getDate())));
            }
        });
        calendarEventRecView.setAdapter(adapter);
        calendarEventRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Button addNewEventButton = (Button) findViewById(R.id.addNewEventButton);
        addNewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.layout_personal_event_dialog);
                dialog.show();

                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                Button enterButton = (Button) dialog.findViewById(R.id.enterButton);
                Button selectTimeButton = (Button) dialog.findViewById(R.id.selectTimeButton);
                TextView title = (TextView) dialog.findViewById(R.id.newPersonalEventTitleTextView);
                EditText eventMessage = (EditText) dialog.findViewById(R.id.eventMessageEditText);

                selectTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int hour = 0, minute = 0;
                        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), android.app.AlertDialog.THEME_HOLO_DARK, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String newTime = String.format(Locale.getDefault(), "%02d:%02d", i, i1);
                                selectTimeButton.setText(newTime);
                            }
                        }, hour, minute, true);
                        timePickerDialog.show();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                enterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = Const.CREATE_NEW_PERSONAL_EVENT;
                        JSONObject jsonObject = new JSONObject();
                        try {
                            if(eventMessage.getText().toString().length() == 0){
                                Toast.makeText(view.getContext(), "Please fill in the message for the event", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(selectTimeButton.getText().toString().length() != 5){
                                Toast.makeText(view.getContext(), "Please pick a time for the event", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            jsonObject.put("userId", user_ID);
                            jsonObject.put("message", eventMessage.getText());
                            jsonObject.put("time", selectedCalendarDate.substring(0, 10) + " " + selectTimeButton.getText());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RequestsCentral.postJSONObject(url, jsonObject, new OnSuccessfulObject() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                Toast.makeText(view.getContext(), "Successfully updated the event", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    }
                });
            }
        });
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
                    if(jsonObject.getInt("userId") == user_ID && jsonObject.getString("time").substring(0, 10).equals(dateToMatch))
                        calendarEvents.add(new CalendarEvent(jsonObject, true));
                }
                RequestsCentral.getJSONArray(Const.GET_GROUP_EVENTS, new OnSuccessfulArray() {
                    @Override
                    public void onSuccess(JSONArray response2) throws JSONException {
                        for(int j=0; j<response2.length(); j++){
                            JSONObject jsonObject = response2.getJSONObject(j);
                            boolean found = false;
                            Member membership = null;
                            for(int k=0; k<userMemberships.size(); k++){
                                if(userMemberships.get(k).getGroupId() == jsonObject.getInt("eventGroupId")){
                                    found = true;
                                    membership = userMemberships.get(k);
                                    break;
                                }
                            }
                            if(found && jsonObject.getString("time").substring(0, 10).equals(dateToMatch)) {
                                boolean okToEditAndDelete = false;
                                if(membership.getPermission() == 2 || user_ID == jsonObject.getJSONObject("membersDetail").getInt("userId"))
                                    okToEditAndDelete = true;
                                calendarEvents.add(new CalendarEvent(jsonObject, okToEditAndDelete));
                            }
                        }
                        Collections.sort(calendarEvents);
                        adapter.setCalendarEvents(calendarEvents);
                    }
                });
            }
        });
    }
}