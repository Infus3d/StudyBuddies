package com.example.group_creation_experiment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.group_creation_experiment.app.AppController;
import com.example.group_creation_experiment.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GroupCreationMain extends AppCompatActivity {
    private String TAG = GroupCreationMain.class.getSimpleName();

    Button createGroupsButton;
    Button showGroupsButton;
    TextView textView;

    private int layoutCounter, textViewCounter;

    private String tag_json_obj = "jobj_req";
    private String tag_json_array = "jarray_req";
    private String[] colors = {"#FF3F86A6", "#FF5253B8", "#FF4372CF"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creation_main);

        showGroupsButton = findViewById(R.id.buttonShowGroups);
        createGroupsButton = findViewById(R.id.buttonCreateGroup);
        textView = findViewById(R.id.displaygroup);

        showGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                makeJsonObjPostReq();
                makeJsonArrayReq();
            }
        });

    }

    private void makeJsonObjPostReq(JSONObject jsonObj) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                Const.URL_JSON_OBJECT_USERS_POST, jsonObj,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        textView.setText(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage().toString());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("question", "COMS 309 Group");
                params.put("answer", "Omar M");

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    public void makeJsonArrayReq(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Const.URL_JSON_ARRAY_USERS_GET,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        textView.setText(response.toString());
                        try {
                            showAllGroups(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonArrayRequest,
                tag_json_array);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    public void showAllGroups(JSONArray response) throws JSONException {
        LinearLayout container = findViewById(R.id.scrollGroupsLinearLayout);
        container.removeAllViews(); layoutCounter = 0; textViewCounter = 0;
        for(int i=0; i<response.length(); i++){
            JSONObject cur = response.getJSONObject(i);
            addGroupToLayout(container, cur, i%3);
        }
    }

    public void addGroupToLayout(LinearLayout container, JSONObject obj, int colorIdx) throws JSONException {
        TextView groupID = new TextView(GroupCreationMain.this);
        groupID.setText("ID: " + obj.getString("id"));
        groupID.setId(++textViewCounter);

        TextView groupName = new TextView(GroupCreationMain.this);
        groupName.setText("Name: " + obj.getString("firstName"));
        groupName.setId(++textViewCounter);

        TextView groupOwner = new TextView(GroupCreationMain.this);
        groupOwner.setText("Owner: " + obj.getString("lastName"));
        groupOwner.setId(++textViewCounter);

        LinearLayout tempGroup = new LinearLayout(GroupCreationMain.this);
        tempGroup.setId(++layoutCounter);
        tempGroup.setOrientation(LinearLayout.VERTICAL);
        tempGroup.setHorizontalGravity(LinearLayout.HORIZONTAL);
        tempGroup.setBackgroundColor(Color.parseColor(colors[colorIdx]));

        tempGroup.addView(groupID);
        tempGroup.addView(groupName);
        tempGroup.addView(groupOwner);

        container.addView(tempGroup);
    }
}