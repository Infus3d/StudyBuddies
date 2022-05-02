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

import com.example.studybuddies.objects.User;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class represents the page to create a user.
 * @author Andy Bruder
 */
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

    /**
     * Shared preferences stores the information about the
     * user who is currently logged into the app
     */
    SharedPreferences sharedPreferences;

    /**
     * On creation of this view, this method initializes all
     * UI elements present. It then populates the users field
     * with all existing users to be used to check credentials
     * @param savedInstanceState
     */
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

        sharedPreferences = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(LoginScreen.ID_KEY, 0);
        username_s = sharedPreferences.getString(LoginScreen.USERNAME_KEY, null);
        email_s = sharedPreferences.getString(LoginScreen.EMAIL_KEY, null);
        password_s = sharedPreferences.getString(LoginScreen.PASSWORD_KEY, null);
        location_s = sharedPreferences.getString(LoginScreen.LOCATION_KEY, null);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User(username.getText().toString(), email.getText().toString(), password1.getText().toString(), location.getText().toString());

                // this block calls valid new user to see if it meets the criteria to create a new user
                try {
                    validNewUser(user);
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

    public void createUser(User user) {
        RequestsCentral.postJSONObject(Const.CREATE_NEW_USER, user.toJSONForServer(), new OnSuccessfulObject() {
            @Override
            public void onSuccess(JSONObject response) {
                int newUserid = -1;

                try {
                    newUserid = response.getInt("id");

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putInt(LoginScreen.ID_KEY, newUserid);
                    editor.putString(LoginScreen.USERNAME_KEY, response.getString("username"));
                    editor.putString(LoginScreen.EMAIL_KEY, response.getString("email"));
                    editor.putString(LoginScreen.PASSWORD_KEY, response.getString("password"));
                    editor.putString(LoginScreen.LOCATION_KEY, response.getString("location"));

                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * This method takes the input from a user to determine if their inputs are in a  valid format and unique from other users
     * @return Returns boolean value to indicate if a user is valid
     * @throws JSONException
     */
    public void validNewUser(User user) throws JSONException {

        if (!password1.getText().toString().equals(password2.getText().toString())) {
            errorDisplay.setText("passwords don't match");
            return;
        }

        if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".")) {
            errorDisplay.setText("invalid email address");
            return;
        }

        User.getUsers(new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                ArrayList<User> users = (ArrayList<User>) a;
                for (User u : users) {
                    String e = u.getEmail();
                    String name = u.getUsername();
                    if (e.equals(user.getEmail())) {
                        errorDisplay.setText("email already in use");
                        return;
                    }
                    if (name.equals(user.getUsername())) {
                        errorDisplay.setText("username already in use");
                        return;
                    }
                }
                createUser(user);
            }
        });

    }

    /**
     * This method checks if the sharedPreferences is already populated, indicating
     * a user has already been logged in on this device. If the sharedPreferences is
     * not null, this method sends the user straight to the dashboard.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (id != 0 && email_s != null && username_s != null && password_s != null && location_s != null){
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
        }
    }

}
