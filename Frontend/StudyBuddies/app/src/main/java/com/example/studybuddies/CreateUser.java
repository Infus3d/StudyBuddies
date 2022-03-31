package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.studybuddies.app.AppController;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateUser extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText password1;
    private EditText password2;
    private EditText location;
    private TextView errorDisplay;
    private Button createButton;
    private Button loginButton;
    private RequestsCentral requestsCentral;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String ID_KEY = "id_key";
    public static final String USERNAME_KEY = "username_key";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    public static final String LOCATION_KEY = "location_key";

    int id;
    String username_s;
    String email_s;
    String password_s;
    String location_s;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        username = findViewById(R.id.user_name);
        email = findViewById(R.id.email_address);
        password1 = findViewById(R.id.password_one);
        password2 = findViewById(R.id.password_two);
        location = findViewById(R.id.location);
        errorDisplay = findViewById(R.id.error_display);
        createButton = findViewById(R.id.create_button);
        loginButton = findViewById(R.id.login);
        requestsCentral = new RequestsCentral();

        requestsCentral.getJSONArray(Const.GET_URL_JSON_OBJECT);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(ID_KEY, 0);
        username_s = sharedPreferences.getString(USERNAME_KEY, null);
        email_s = sharedPreferences.getString(EMAIL_KEY, null);
        password_s = sharedPreferences.getString(PASSWORD_KEY, null);
        location_s = sharedPreferences.getString(LOCATION_KEY, null);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // this block calls valid new user to see if it meets the criteria to create a new user
                try {
                    if (validNewUser() == false) {
                        return;
                    }
                } catch (JSONException e) {
                    errorDisplay.setText("new user error");
                }

                // creates the json object to send in the request to create the new user
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", username.getText().toString());
                    jsonObject.put("email", email.getText().toString());
                    jsonObject.put("password", password1.getText().toString());
                    jsonObject.put("location", location.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // clears request central and sends the json object
                requestsCentral = new RequestsCentral();
                requestsCentral.postJSONObject(Const.MOCK_POST, jsonObject);

                // waits to allow requests to be sent and received
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // clears request central and gets the updated users list
                requestsCentral = new RequestsCentral();
                requestsCentral.getJSONArray(Const.GET_URL_JSON_OBJECT);

                // gets the new user json object including the id
                try {
                    JSONObject newUser = requestsCentral.getJsonArrayResponse().getJSONObject(requestsCentral.getArrayResponseLength() - 1);

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putInt(ID_KEY, newUser.getInt("id"));
                    editor.putString(USERNAME_KEY, newUser.getString("username"));
                    editor.putString(EMAIL_KEY, newUser.getString("email"));
                    editor.putString(PASSWORD_KEY, newUser.getString("password"));
                    editor.putString(LOCATION_KEY, newUser.getString("location"));

                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);

            }
        });

    }

    public boolean validNewUser() throws JSONException {

        if (!password1.getText().toString().equals(password2.getText().toString())) {
            errorDisplay.setText("passwords don't match");
            return false;
        }

        if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".")) {
            errorDisplay.setText("invalid email address");
            return false;
        }

        for (int i = 0; i < requestsCentral.getArrayResponseLength(); i++) {
            JSONObject obj = requestsCentral.getJsonArrayResponse().getJSONObject(i);
            //String e = (String) obj.get("email");
            String u = (String) obj.get("firstName");
            //if (e.equals(email.getText().toString())) {
            //    errorDisplay.setText("email already in use");
            //    return false;
            //}
            if (u.equals(username.getText().toString())) {
                errorDisplay.setText("username already in use");
                return false;
            }
        }
        return true;
    }

    /** to be changed when Dashboard is created
    @Override
    protected void onStart() {
        super.onStart();
        if (id != 0 && email_s != null && username_s != null && password_s != null && location_s != null){
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
        }
    }
     */

}
