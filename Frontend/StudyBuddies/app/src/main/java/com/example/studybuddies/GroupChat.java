package com.example.studybuddies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddies.databinding.ActivityGroupChatBinding;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;

public class GroupChat extends DrawerBaseActivity {

    private SharedPreferences sharedPreferences;

    private WebSocketClient client;

    private ActivityGroupChatBinding activityGroupChatBinding;
    private String groupName;
    private int groupId;

    private RecyclerView messageDisplay;
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

        allocateActivityTitle(groupName +"'s Group Chat");

        messageDisplay = findViewById(R.id.message_display);
        enterMessage = findViewById(R.id.enter_message_field);
        sendMessage = findViewById(R.id.send_message_button);

        openConnection();



    }

    public void getExistingMessages(){

    }

    public void displayExistingMessages() {

    }

    public void displayMessage(String message) {

    }

    /**
     * This method will attempt to open the websocket connection
     */
    public void openConnection() {
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

}