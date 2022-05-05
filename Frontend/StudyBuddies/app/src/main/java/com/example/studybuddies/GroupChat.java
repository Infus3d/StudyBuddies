package com.example.studybuddies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddies.databinding.ActivityGroupChatBinding;
import com.example.studybuddies.objects.ChatMessage;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;

import org.java_websocket.client.WebSocketClient;

public class GroupChat extends DrawerBaseActivity {

    private SharedPreferences sharedPreferences;
    private int id;
    private String username_s;
    private String email_s;
    private String password_s;
    private String location_s;

    private WebSocketClient client;

    private ActivityGroupChatBinding activityGroupChatBinding;
    private String groupName;
    private int groupId;

    private LinearLayout messageDisplay;
    private EditText enterMessage;
    private Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGroupChatBinding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(activityGroupChatBinding.getRoot());

        Intent incoming = getIntent();
        Bundle extras = incoming.getExtras();
        groupName = extras.getString("title");
        groupId = extras.getInt("groupId");

        sharedPreferences = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(LoginScreen.ID_KEY, 0);
        username_s = sharedPreferences.getString(LoginScreen.USERNAME_KEY, null);
        email_s = sharedPreferences.getString(LoginScreen.EMAIL_KEY, null);
        password_s = sharedPreferences.getString(LoginScreen.PASSWORD_KEY, null);
        location_s = sharedPreferences.getString(LoginScreen.LOCATION_KEY, null);

        allocateActivityTitle(groupName +"'s Group Chat");

        messageDisplay = findViewById(R.id.message_display);
        enterMessage = findViewById(R.id.enter_message_field);
        sendMessage = findViewById(R.id.send_message_button);

        ChatMessage in = new ChatMessage(4, getTime(), "Omar Muhammetkulyyev", groupId, "hey, how are you?");
        ChatMessage out = new ChatMessage(id, getTime(), username_s, groupId, "i'm good, how are you?");

        ArrayList<ChatMessage> messages = new ArrayList<ChatMessage>();
        messages.add(in);
        messages.add(out);

        //openConnection();

        displayExistingMessages(messages);

    }

    public void getExistingMessages(){

    }

    private void displayExistingMessages(ArrayList<ChatMessage> messages) {
        for (ChatMessage message : messages) {
            displayMessage(messageDisplay, message);
        }
    }

    public String getTime() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
    }

    private void displayMessage(LinearLayout messageHolder, ChatMessage message) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        Boolean outgoing = message.getUserId() == id;
        if (outgoing) {
            addMessage(outgoing, layout, message);
            addProfile(layout, message);
        } else {
            addProfile(layout, message);
            addMessage(outgoing, layout, message);
        }
        messageHolder.addView(layout);
    }

    /**
     * This method will attempt to open the websocket connection
     */
    private void openConnection() {
        Draft[] drafts = {new Draft_6455()};

        String w = "ws://coms-309-011.class.las.iastate.edu:8080/websocket/" + sharedPreferences.getInt("id", 0);

        try {
            Log.d("Socket:", "Trying socket");
            client = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    String s = enterMessage.getText().toString();
                    enterMessage.setText(s + "\nServer:" + message);
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }

            };
        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        client.connect();

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    client.send(sendMessage.getText().toString());
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        });

    }

    public void addMessage(boolean outgoing, LinearLayout layout, ChatMessage message) {
        TextView messageView = new TextView(this);
        if (outgoing) {
            messageView.setBackgroundResource(R.drawable.outgoing_message);
            messageView.setGravity(View.FOCUS_RIGHT);
        } else {
            messageView.setBackgroundResource(R.drawable.incoming_message);
        }
        messageView.setText(message.getMessage());
        messageView.setTextColor(Color.BLACK);
        layout.addView(messageView);
    }

    private void addProfile(LinearLayout layout, ChatMessage message) {
        String usernameDisplay;
        TextView profileView = new TextView(this);
        profileView.setBackgroundResource(R.drawable.profile_holder);
        if (message.getAuthor().length() > 8) {
            usernameDisplay = message.getAuthor().substring(0, 8) + "...";
        } else {
            usernameDisplay = message.getAuthor();
        }
        profileView.setText(usernameDisplay);
        profileView.setTextColor(Color.BLACK);
        profileView.setHeight(50);
        layout.addView(profileView);
    }

}