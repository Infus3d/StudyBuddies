package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private JSONArray users;

    private int id;
    private String username_s;
    private String email_s;
    private String password_s;
    private String location_s;
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

        RequestsCentral.getJSONArray(Const.GET_USERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) {
                users = response;
            }
        });

        sharedPreferences = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(LoginScreen.ID_KEY, 0);
        username_s = sharedPreferences.getString(LoginScreen.USERNAME_KEY, null);
        email_s = sharedPreferences.getString(LoginScreen.EMAIL_KEY, null);
        password_s = sharedPreferences.getString(LoginScreen.PASSWORD_KEY, null);
        location_s = sharedPreferences.getString(LoginScreen.LOCATION_KEY, null);

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


                RequestsCentral.postJSONObject(Const.CREATE_NEW_USER, jsonObject, new OnSuccessfulObject() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        errorDisplay.setText("success");
                    }
                });

                int newUserid = -1;

                try {
                    newUserid = users.getJSONObject(users.length() - 1).getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt(LoginScreen.ID_KEY, newUserid);
                editor.putString(LoginScreen.USERNAME_KEY, username_s);
                editor.putString(LoginScreen.EMAIL_KEY, email_s);
                editor.putString(LoginScreen.PASSWORD_KEY, password_s);
                editor.putString(LoginScreen.LOCATION_KEY, location_s);

                editor.apply();

                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();


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

    /**
     * This method takes the input from a user to determine if their inputs are in a  valid format and unique from other users
     * @return Returns boolean value to indicate if a user is valid
     * @throws JSONException
     */
    public boolean validNewUser() throws JSONException {

        if (!password1.getText().toString().equals(password2.getText().toString())) {
            errorDisplay.setText("passwords don't match");
            return false;
        }

        if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".")) {
            errorDisplay.setText("invalid email address");
            return false;
        }

        for (int i = 0; i < users.length(); i++) {
            JSONObject obj = users.getJSONObject(i);
            String e = (String) obj.get("email");
            String u = (String) obj.get("username");
            if (e.equals(email.getText().toString())) {
                errorDisplay.setText("email already in use");
                return false;
            }
            if (u.equals(username.getText().toString())) {
                errorDisplay.setText("username already in use");
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (id != 0 && email_s != null && username_s != null && password_s != null && location_s != null){
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
        }
    }

}
