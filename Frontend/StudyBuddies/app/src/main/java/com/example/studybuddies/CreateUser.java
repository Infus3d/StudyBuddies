package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.studybuddies.app.AppController;
import com.example.studybuddies.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateUser extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password1;
    EditText password2;
    TextView errorDisplay;
    Button createButton;
    TextView showUsers;
    private String TAG = GroupCreation.class.getSimpleName();
    private String tag_json_obj = "jobj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email_address);
        password1 = findViewById(R.id.password_one);
        password2 = findViewById(R.id.password_two);
        errorDisplay = findViewById(R.id.error_display);
        showUsers = findViewById(R.id.users_display);
        createButton = findViewById(R.id.create_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!password1.getText().toString().equals(password2.getText().toString())){
                    errorDisplay.setText("passwords don't match");
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("firstName", firstName.getText().toString());
                    jsonObject.put("lastName", lastName.getText().toString());
                    jsonObject.put("email", email.getText().toString());
                    jsonObject.put("password", password1.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                makeJsonObjReq(jsonObject);
            }
        });

    }
    private void makeJsonObjReq(JSONObject j) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                Const.MOCK_POST,
                j,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        showUsers.setText(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if(response != null && response.data != null){
                    String errorString = new String(response.data);
                    Log.d("log error", errorString);
                }
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
                params.put("firstName", "first");
                params.put("lastName", "last");

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    };

    public void makeJSONArrayRequest() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Const.GET_URL_JSON_OBJECT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                showUsers.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if(response != null && response.data != null){
                    String errorString = new String(response.data);
                    Log.d("log error", errorString);
                }
            }
        }){

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
                params.put("firstName", "first");
                params.put("lastName", "last");

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonArrayRequest, tag_json_obj);

    }

}
