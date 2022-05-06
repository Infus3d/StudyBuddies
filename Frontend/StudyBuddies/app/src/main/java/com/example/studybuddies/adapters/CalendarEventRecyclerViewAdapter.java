package com.example.studybuddies.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddies.R;
import com.example.studybuddies.UserSchedule;
import com.example.studybuddies.objects.CalendarEvent;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class CalendarEventRecyclerViewAdapter extends RecyclerView.Adapter<CalendarEventRecyclerViewAdapter.ViewHolder>{
    private ArrayList<CalendarEvent> calendarEvents = new ArrayList<>();
    private Context context;
    public CalendarEventRecyclerViewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendarevent_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.eventSrc.setText(calendarEvents.get(position).getEventSource());
        holder.eventDescription.setText(calendarEvents.get(position).getMessage());
        holder.eventDescription.setMovementMethod(new ScrollingMovementMethod());
        holder.eventTime.setText(calendarEvents.get(position).getTime());

        holder.editButton.setVisibility(calendarEvents.get(position).isEditAndDelete() ? View.VISIBLE : View.INVISIBLE);
        holder.deleteButton.setVisibility(calendarEvents.get(position).isEditAndDelete() ? View.VISIBLE : View.INVISIBLE);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_personal_event_dialog);
                dialog.show();

                CalendarEvent temp = calendarEvents.get(holder.getAdapterPosition());
                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                Button enterButton = (Button) dialog.findViewById(R.id.enterButton);
                Button selectTimeButton = (Button) dialog.findViewById(R.id.selectTimeButton);
                TextView title = (TextView) dialog.findViewById(R.id.newPersonalEventTitleTextView);
                EditText eventMessage = (EditText) dialog.findViewById(R.id.eventMessageEditText);

                title.setText("Edit current event");
                selectTimeButton.setText(temp.getTime().substring(11));
                eventMessage.setText(temp.getMessage());

                selectTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int hour = 0, minute = 0;
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_HOLO_DARK, new TimePickerDialog.OnTimeSetListener() {
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
                        String url = (temp.isPersonal() ? Const.GET_PERSONAL_EVENT : Const.GET_GROUP_EVENT) + "/" + temp.getId();
                        JSONObject jsonObject = temp.toJSONObject();
                        try {
                            jsonObject.put("message", eventMessage.getText());
                            jsonObject.put("time", jsonObject.getString("time").substring(0, 11) + selectTimeButton.getText());
                            notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RequestsCentral.putJSONObject(url, jsonObject, new OnSuccessfulObject() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                Toast.makeText(context, "Successfully updated the event", Toast.LENGTH_SHORT).show();
                                try {
                                    calendarEvents.get(holder.getAdapterPosition()).setTime(jsonObject.getString("time"));
                                    calendarEvents.get(holder.getAdapterPosition()).setMessage(jsonObject.getString("message"));
                                    notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                context.startActivity(new Intent(context, UserSchedule.class));
                            }
                        });
                        dialog.dismiss();
                    }
                });
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.ic_baseline_delete_48)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                CalendarEvent temp = calendarEvents.get(holder.getAdapterPosition());
                                if(temp.isPersonal()){
                                    RequestsCentral.deleteJSONObject(Const.GET_PERSONAL_EVENT + "/" + temp.getId(), new OnSuccessfulObject() {
                                        @Override
                                        public void onSuccess(JSONObject response) {
                                            Toast.makeText(context, "Successfully deleted the personal event", Toast.LENGTH_SHORT).show();
                                            calendarEvents.remove(holder.getAdapterPosition());
                                            notifyDataSetChanged();
//                                            context.startActivity(new Intent(context, UserSchedule.class));
                                        }
                                    });
                                }
                                else{
                                    RequestsCentral.deleteJSONObject(Const.GET_GROUP_EVENT + "/" + temp.getId(), new OnSuccessfulObject() {
                                        @Override
                                        public void onSuccess(JSONObject response) {
                                            Toast.makeText(context, "Successfully deleted the" + temp.getMemberDetail().getGroup().getTitle() + " event", Toast.LENGTH_SHORT).show();
                                            calendarEvents.remove(holder.getAdapterPosition());
                                            notifyDataSetChanged();
//                                            context.startActivity(new Intent(context, UserSchedule.class));
                                        }
                                    });
                                }
                                dialog.dismiss();
                            }

                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .create();
                myQuittingDialogBox.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return calendarEvents.size();
    }

    public void setCalendarEvents(ArrayList<CalendarEvent> calendarEvents) {
        this.calendarEvents = calendarEvents;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView eventSrc, eventDescription, eventTime;
        private Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventSrc = (TextView) itemView.findViewById(R.id.eventSrcTextView);
            eventDescription = (TextView) itemView.findViewById(R.id.eventDescriptionTextView);
            eventTime = (TextView) itemView.findViewById(R.id.eventTimeTextView);

            editButton = (Button) itemView.findViewById(R.id.editButton);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);
        }
    }
}
