package com.example.studybuddies.adapters;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddies.R;
import com.example.studybuddies.objects.CalendarEvent;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CalendarEventRecyclerViewAdapter extends RecyclerView.Adapter<CalendarEventRecyclerViewAdapter.ViewHolder>{
    private ArrayList<CalendarEvent> calendarEvents = new ArrayList<>();

    public CalendarEventRecyclerViewAdapter(){

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
