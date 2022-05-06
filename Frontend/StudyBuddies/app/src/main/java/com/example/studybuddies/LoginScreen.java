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

import com.example.studybuddies.objects.User;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class represents the page to log into the app if a user already has an account.
 * @author Andy Bruder
 */
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

    /**
     * Shared preferences stores the information about the
     * user who is currently logged into the app
     */
    SharedPreferences sharedPreferences;

    /**
     * On the creation of this view, this method initializes the UI elements present.
     * Populates the existing users to check the credentials entered by user.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        username = findViewById(R.id.enter_user_name);
        password = findViewById(R.id.enter_password);
        errorMessage = findViewById(R.id.login_error);
        loginB = findViewById(R.id.login_button);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User.getUsers(new OnFinishedArrayList() {
                    @Override
                    public void onFinishedArrayList(ArrayList a) {
                        boolean matchFound = false;
                        for (Object o : a) {
                            User currentUser = (User) o;
                            if (currentUser.getUsername().equals(username.getText().toString()) && currentUser.getPassword().equals(password.getText().toString())) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putInt(ID_KEY, currentUser.getId());
                                editor.putString(USERNAME_KEY, currentUser.getUsername());
                                editor.putString(EMAIL_KEY, currentUser.getEmail());
                                editor.putString(PASSWORD_KEY, currentUser.getPassword());
                                editor.putString(LOCATION_KEY, currentUser.getLocation());

                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(intent);
                                finish();
                                matchFound = true;
                            }
                        }
                        if(matchFound == false)
                            errorMessage.setText("Invalid username or password. Try again");
                    }
                });
            }
            });
    }
}
