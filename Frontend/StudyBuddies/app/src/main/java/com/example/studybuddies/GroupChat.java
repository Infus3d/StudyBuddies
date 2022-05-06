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

/**
 * This class represents the group chat activity for the app. It connects to the
 * websocket at a url specific to the group and the user and displays messages accordingly
 * @author Andy Bruder
 */
public class GroupChat extends DrawerBaseActivity {

    /**
     * Stores the shared preferences (user details)
     */
    private SharedPreferences sharedPreferences;
    /**
     * The username of the current user
     */
    private String username_s;
    /**
     * The counters for messages and text views to assign ids to the new message views
     */
    private int messageCounter, textViewCounter;
    /**
     * The websocekt client used to connect to the server
     */
    private WebSocketClient client;
    private ActivityGroupChatBinding activityGroupChatBinding;
    /**
     * The name of the current group
     */
    private String groupName;
    /**
     * The id of the current group
     */
    private int groupId;
    /**
     * The container for the message views
     */
    private LinearLayout messageDisplay;
    /**
     * The enter message box at the bottom
     */
    private EditText enterMessage;
    /**
     * Button used to send the message to the chat
     */
    private Button sendMessage;

    /**
     * Populates the necessary fields, initializes views, and opens the websocket
     * connection which populates the chat with previous messages
     * @param savedInstanceState
     */
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
        username_s = sharedPreferences.getString(LoginScreen.USERNAME_KEY, null);

        allocateActivityTitle(groupName +"'s Group Chat");

        messageDisplay = findViewById(R.id.message_display);
        enterMessage = findViewById(R.id.enter_message_field);
        sendMessage = findViewById(R.id.send_message_button);

        messageCounter = 0; textViewCounter = 0;

        openConnection();

    }

    /**
     * Displays all previous messages that were sent in this group chat
     * @param messages the list of messages to be displayed
     */
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

    /**
     * This method adds an incoming message to the view (one sent by other users)
     * @param message the message to be displayed
     */
    private void createIncomingMessage(ChatMessage message) {
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

    /**
     * This method adds an outgoing message to the view (one sent by the current user)
     * @param message the message to be displayed
     */
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

    /**
     * When the back button is pressed, ensures
     * websocket is closed before activity is left
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        client.close();
        this.finish();
    }

}