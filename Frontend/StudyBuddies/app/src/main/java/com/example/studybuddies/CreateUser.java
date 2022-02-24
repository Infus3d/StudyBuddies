package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.studybuddies.app.AppController;
import com.example.studybuddies.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateUser extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    TextView response_txt;
    Button button;
    private String TAG = GroupCreation.class.getSimpleName();
    private String tag_json_obj = "jobj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        response_txt = findViewById(R.id.response);
        button = findViewById(R.id.button_first);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName_s = firstName.getText().toString();
                String lastName_s = lastName.getText().toString();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("firstName", firstName_s);
                    jsonObject.put("lastName", lastName_s);
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
                            Const.POST_URL_JSON_OBJECT,
                            j,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d(TAG, response.toString());
                                    response_txt.setText(response.toString());
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
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
                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

                };

}