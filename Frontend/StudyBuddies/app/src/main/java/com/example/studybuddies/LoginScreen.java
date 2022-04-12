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

import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginB;
    private TextView errorMessage;
    private RequestsCentral usersGetter;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String ID_KEY = "id_key";
    public static final String USERNAME_KEY = "username_key";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    public static final String LOCATION_KEY = "location_key";

    private JSONArray users;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        username = findViewById(R.id.enter_user_name);
        password = findViewById(R.id.enter_password);
        errorMessage = findViewById(R.id.login_error);
        loginB = findViewById(R.id.login_button);
        RequestsCentral.getJSONArray(Const.GET_USERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) {
                users = response;
            }
        });



        loginB.setOnClickListener(new View.OnClickListener() {
            /**
             * Takes the input present in the fields and attempts to validate the user with these credentials
             * @param view
             */
            @Override
            public void onClick(View view) {

                for (int i = 0; i < users.length(); i++) {

                    JSONObject currentUser = null;
                    try {
                        currentUser = users.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (currentUser.getString("username").equals(username.getText().toString()) && currentUser.getString("password").equals(password.getText().toString()) ){

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt(ID_KEY, currentUser.getInt("id"));
                            editor.putString(USERNAME_KEY, currentUser.getString("username"));
                            editor.putString(EMAIL_KEY, currentUser.getString("email"));
                            editor.putString(PASSWORD_KEY, currentUser.getString("password"));
                            editor.putString(LOCATION_KEY, currentUser.getString("location"));

                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(intent);
                            finish();

                            Log.i(LoginScreen.class.getSimpleName(), "success");
                            return;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                errorMessage.setText("Invalid username or password. Try again");

            }
        });

    }
}