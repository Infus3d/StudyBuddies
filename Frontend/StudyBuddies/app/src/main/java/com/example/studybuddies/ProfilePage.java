package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studybuddies.databinding.ActivityProfilePageBinding;
import com.example.studybuddies.objects.User;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ProfilePage extends DrawerBaseActivity {
    private ActivityProfilePageBinding activityProfilePageBinding;

    private EditText username;
    private EditText location, email;
    private EditText currentPassword, newPassword, verifyNewPassword;

    private TextView usernameText, locationText, emailText;

    private Button submitBtn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfilePageBinding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(activityProfilePageBinding.getRoot());
        allocateActivityTitle("Profile");

        usernameText = (TextView) findViewById(R.id.usernameTextView);
        locationText = (TextView) findViewById(R.id.locationTextView);
        emailText = (TextView) findViewById(R.id.emailTextView);

        username = (EditText) findViewById(R.id.usernameEditText);
        location = (EditText) findViewById(R.id.locationEditText);
        email = (EditText) findViewById(R.id.emailEditText);
        currentPassword = (EditText) findViewById(R.id.currentPasswordEditText);
        newPassword = (EditText) findViewById(R.id.newPasswordEditText);
        verifyNewPassword = (EditText) findViewById(R.id.verifyNewPasswordEditText);
        submitBtn = (Button) findViewById(R.id.submitButton);

        sharedPreferences = getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE);

        setViews();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPassword.getText() == null || currentPassword.getText().equals("")) {
                    currentPassword.requestFocus();
                    Toast.makeText(view.getContext(), "Please enter current password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (currentPassword.getText().toString().equals(sharedPreferences.getString(LoginScreen.PASSWORD_KEY, null)) == false) {
                    currentPassword.setText("");
                    currentPassword.requestFocus();
                    Toast.makeText(view.getContext(), "Current Password is incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPassword.getText() != null && (verifyNewPassword.getText() == null || verifyNewPassword.getText().equals(""))) {
                    verifyNewPassword.requestFocus();
                    Toast.makeText(view.getContext(), "New passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                User user = new User(sharedPreferences.getInt(LoginScreen.ID_KEY, 0), sharedPreferences.getString(LoginScreen.USERNAME_KEY, null),
                        sharedPreferences.getString(LoginScreen.EMAIL_KEY, null), sharedPreferences.getString(LoginScreen.PASSWORD_KEY, null),
                        sharedPreferences.getString(LoginScreen.LOCATION_KEY, null));

                if (username.getText() != null) {
                    editor.putString(LoginScreen.USERNAME_KEY, username.getText().toString());
                    user.setUsername(username.getText().toString());
                }
                if(location.getText() != null) {
                    editor.putString(LoginScreen.LOCATION_KEY, location.getText().toString());
                    user.setLocation(location.getText().toString());
                }
                if(email.getText() != null) {
                    editor.putString(LoginScreen.EMAIL_KEY, email.getText().toString());
                    user.setEmail(email.getText().toString());
                }
                if(newPassword.getText() != null) {
                    editor.putString(LoginScreen.PASSWORD_KEY, newPassword.getText().toString());
                    user.setPassword(newPassword.getText().toString());
                }

                JSONObject obj = new JSONObject();
                try {
                    obj.put("id", user.getId());
                    obj.put("username", user.getUsername());
                    obj.put("location", user.getLocation());
                    obj.put("email", user.getEmail());
                    obj.put("password", user.getPassword());

                    RequestsCentral.putJSONObject(Const.GET_USERS + "/" + sharedPreferences.getInt(LoginScreen.ID_KEY, 0), obj, new OnSuccessfulObject() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            editor.apply();
                            setViews();
                            Toast.makeText(view.getContext(), "User information updated successfully", Toast.LENGTH_SHORT);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setViews(){
        usernameText.setText(sharedPreferences.getString(LoginScreen.USERNAME_KEY, null));
        locationText.setText(sharedPreferences.getString(LoginScreen.LOCATION_KEY, null));
        emailText.setText(sharedPreferences.getString(LoginScreen.EMAIL_KEY, null));
    }
}