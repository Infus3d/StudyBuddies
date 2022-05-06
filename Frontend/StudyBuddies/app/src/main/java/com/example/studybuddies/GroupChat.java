package com.example.studybuddies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
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
import java.util.List;
import java.util.Locale;

import org.java_websocket.client.WebSocketClient;

public class GroupChat extends DrawerBaseActivity {

    private SharedPreferences sharedPreferences;
    private int id;
    private String username_s;
    private String email_s;
    private String password_s;
    private String location_s;

    private int messageCounter, textViewCounter;

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

        messageCounter = 0; textViewCounter = 0;

        openConnection();

    }

    private void displayExistingMessages(ArrayList<ChatMessage> messages) {
        for (ChatMessage message : messages) {
            boolean outgoing = message.getAuthor().equals(username_s);
            if (outgoing) {
                createOutgoingMessage(message);
            } else {
                createIncomingMessage(message);
            }
            messageCounter++;
        }
    }

    public String getTime() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
    }

    public void createIncomingMessage(ChatMessage message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ScrollView scrollView = findViewById(R.id.message_scroller);
                View messageRow = getLayoutInflater().inflate(R.layout.incoming_messge_row, messageDisplay, false);
                messageRow.setId(messageCounter);
                messageDisplay.addView(messageRow);
                TextView profile = findViewById(R.id.profile_circle);
                profile.setId(textViewCounter++);
                if (message.getAuthor().length() > 8) {
                    String author = message.getAuthor().substring(0,8) + "...";
                    profile.setText(author);
                } else {
                    profile.setText(message.getAuthor());
                }
                TextView messageBox = findViewById(R.id.message_box);
                messageBox.setId(textViewCounter++);
                messageBox.setText(message.getMessage());
                scrollView.fullScroll(View. FOCUS_DOWN);
            }
        });
    }

    private void createOutgoingMessage(ChatMessage message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ScrollView scrollView = findViewById(R.id.message_scroller);
                View messageRow = getLayoutInflater().inflate(R.layout.outgoing_message_row, messageDisplay, false);
                messageRow.setId(messageCounter);
                messageDisplay.addView(messageRow);
                TextView profile = findViewById(R.id.profile_circle);
                profile.setId(textViewCounter++);
                if (message.getAuthor().length() > 8) {
                    String author = message.getAuthor().substring(0,8) + "...";
                    profile.setText(author);
                } else {
                    profile.setText(message.getAuthor());
                }
                TextView messageBox = findViewById(R.id.message_box);
                messageBox.setId(textViewCounter++);
                messageBox.setText(message.getMessage());
                messageBox.setGravity(View.FOCUS_RIGHT);
                scrollView.fullScroll(View. FOCUS_DOWN);
            }
        });
    }

    /**
     * This method will attempt to open the websocket connection
     */
    private void openConnection() {
        Draft[] drafts = {new Draft_6455()};

        String w = "ws://coms-309-011.class.las.iastate.edu:8080/chat/" + groupId + "/" + username_s;

        try {
            Log.d("Socket:", "Trying socket");
            client = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    if (message.contains("initmess")) {
                        Log.d(String.valueOf(getApplicationContext()), "getting initial message");

                        ArrayList<ChatMessage> messages = new ArrayList<ChatMessage>();
                        String [] rawMessages = message.split("\n");
                        for (String s : rawMessages) {
                            messages.add(new ChatMessage(s, groupId));
                        }
                        displayExistingMessages(messages);
                    } else {
                        Log.d("", "run() returned: " + message);
                            String[] messageParts = message.split(" : ");

                            ChatMessage mess = new ChatMessage(messageParts[1], messageParts[0]);
                            boolean outgoing = mess.getAuthor().equals(username_s);
                            if (outgoing) {
                                createOutgoingMessage(mess);
                            } else {
                                createIncomingMessage(mess);
                            }
                    }
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
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ChatMessage outgoing = new ChatMessage(enterMessage.getText().toString(), username_s);
                    client.send(outgoing.getMessage());
                    enterMessage.setText("");
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage());
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        client.close();
        this.finish();
    }

}