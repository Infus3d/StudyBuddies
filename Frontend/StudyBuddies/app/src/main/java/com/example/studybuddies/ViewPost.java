package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studybuddies.databinding.ActivityViewPostBinding;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONObject;

public class ViewPost extends DrawerBaseActivity {

    ActivityViewPostBinding activityViewPostBinding;

    private int currentPermission;
    private String username;
    private String author;
    private String message;
    private String time;
    private int announcementID;

    private TextView title;
    private TextView messageView;
    private Button deleteButton;

    private String deleteURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewPostBinding = ActivityViewPostBinding.inflate(getLayoutInflater());
        setContentView(activityViewPostBinding.getRoot());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        announcementID = extras.getInt("id");
        currentPermission = extras.getInt("permission");
        username = extras.getString("username");
        author = extras.getString("author");
        message = extras.getString("message");
        time = extras.getString("time");

        deleteURL = Const.GET_ANNOUNCEMENTS + "/" + announcementID;

        allocateActivityTitle(author + "'s Post");

        title = findViewById(R.id.view_post_title);
        messageView = findViewById(R.id.view_post_message);
        deleteButton = findViewById(R.id.delete_post_button);

        if (currentPermission >= 2 || author.equals(username)) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RequestsCentral.deleteJSONObject(deleteURL, new OnSuccessfulObject() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            finish();
                        }
                    });

                }
            });
        }

        title.setText("From " + author + " at " + time);
        messageView.setText(message);
    }
}