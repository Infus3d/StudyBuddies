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
    EditText password_one;
    EditText password_two;
    Button createButton;
    TextView errorDisplay;
    JSONArray users;
    private String TAG = GroupCreation.class.getSimpleName();
    private String tag_json_obj = "jobj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email_address);
        password_one = findViewById(R.id.password_one);
        password_two = findViewById(R.id.password_two);
        createButton = findViewById(R.id.button_first);
        errorDisplay = findViewById(R.id.error_display);
        getUsers();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName_s = firstName.getText().toString();
                String lastName_s = lastName.getText().toString();
                String email_s = email.getText().toString();
                String password_one_s = password_one.getText().toString();
                String password_two_s = password_two.getText().toString();

                if (validNewUser(email_s, password_one_s, password_two_s) != null) {
                    errorDisplay.setText(validNewUser(email_s, password_one_s, password_two_s));
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("firstName", firstName_s);
                    jsonObject.put("lastName", lastName_s);
                    jsonObject.put("email", email_s);
                    jsonObject.put("password_one", password_one_s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sendUser(jsonObject);
            }
        });

    }
    private void sendUser(JSONObject j) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                Const.MOCK_POST,
                j,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                    }
                    },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if (response != null && response.data != null){
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
                params.put("email", "user@domain.com");
                params.put("password", "pass");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    };

    public void getUsers() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                Const.MOCK_GET,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        users = response;
                        Log.i(TAG, response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.d("log error", errorString);
                        }
                    }
                }
        ) {

            /**
             * Passing some request headers
             */
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
                params.put("email", "user@domain.com");
                params.put("password", "pass");

                return params;
            }
        };
    }

    public String validNewUser(String email, String pass1, String pass2) {

        if (!pass1.equals(pass2)){
            return "passwords do not match";
        }

        for (int i = 0; i < users.length(); i++) {
            try {
                JSONObject obj = users.getJSONObject(i);
                String e = (String) obj.get("email");
                if (email.equals(e)){
                    return "user already exists";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (!email.contains("@") || !email.contains(".")) {
            return "invalid email address";
        }

        return null;
    }

}