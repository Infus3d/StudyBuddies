package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    EditText username;
    EditText email;
    EditText password1;
    EditText password2;
    EditText location;
    TextView errorDisplay;
    Button createButton;
    Button loginButton;
    RequestsCentral requestsCentral;

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

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (validNewUser() == false) {
                        return;
                    }
                } catch (JSONException e) {
                    errorDisplay.setText("new user error");
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", username.getText().toString());
                    jsonObject.put("email", email.getText().toString());
                    jsonObject.put("password", password1.getText().toString());
                    jsonObject.put("location", location.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                requestsCentral = new RequestsCentral();
                requestsCentral.postJSONObject(Const.MOCK_POST, jsonObject);

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

        for (int i = 0; i < requestsCentral.getArrayResponseLength(); i++){
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

}
