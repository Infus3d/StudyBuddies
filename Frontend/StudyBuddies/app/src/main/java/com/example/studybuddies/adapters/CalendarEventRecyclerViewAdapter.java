package com.example.studybuddies.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddies.R;

import org.w3c.dom.Text;

public class CalendarEventRecyclerViewAdapter extends RecyclerView.Adapter<CalendarEventRecyclerViewAdapter.ViewHolder>{
    public CalendarEventRecyclerViewAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
