package com.example.studybuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.studybuddies.databinding.ActivityPublicGroupsBinding;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * This class represents a Public Group Page where users can search for groups open to the public.
 * The activity extends DrawerBaseActivity in order to get access to the menu.
 * @author Omar Muhammetkulyyev
 */
public class PublicGroups extends DrawerBaseActivity {
    ActivityPublicGroupsBinding activityPublicGroupsBinding;

    private int linearLayoutCounter, textViewCounter, buttonCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPublicGroupsBinding = ActivityPublicGroupsBinding.inflate(getLayoutInflater());
        setContentView(activityPublicGroupsBinding.getRoot());
        allocateActivityTitle("Public Groups");

//        EditText editText = findViewById(R.id.editTextSearch);
//        TextView textView = findViewById(R.id.textViewResponse);

//        RelativeLayout relativeLayout = new RelativeLayout(this);
//        LinearLayout groupContainer = new LinearLayout(this);
//        LinearLayout innerLinear = new LinearLayout(this);
//
//        Button visitButton = new Button(this);
//        Button joinButton = new Button(this);
//        visitButton.setText("Visit");
//        joinButton.setText("Join");
//
//        TextView groupName = new TextView(this);
//        groupName.setText("Group Name");
//        groupName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        groupName.setMinimumWidth(30);
//        innerLinear.setOrientation(LinearLayout.VERTICAL);
//        innerLinear.addView(groupName);
//
//        groupContainer.setOrientation(LinearLayout.VERTICAL);
//        groupContainer.addView(innerLinear);
//        groupContainer.addView(visitButton);
//        groupContainer.addView(joinButton);
//        groupContainer.setVisibility(View.VISIBLE);
//
//        LinearLayout outerContainer = findViewById(R.id.scrollViewLinearContainer);
//        outerContainer.addView(groupContainer);

        RequestsCentral.getJSONArray(Const.GET_GROUPS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                showAllGroups(response);
            }
        });

        Spinner spinnerSortBy = findViewById(R.id.spinnerSortBy);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sortBy_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSortBy.setAdapter(adapter);

        SearchView groupSearch = findViewById(R.id.searchViewGroupSearch);
        groupSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RequestsCentral.getJSONArray(Const.GET_GROUPS, new OnSuccessfulArray() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        ArrayList<JSONObject> alist = new ArrayList<JSONObject>();
                        for(int i=0; i<response.length(); i++){
                            JSONObject cur = response.getJSONObject(i);
                            if(cur.getString("title").length() >= newText.length() && cur.getString("title").substring(0, newText.length()).equals(newText))
                                alist.add(cur);
                        }
                        showAllGroups(alist);
                    }
                });
                return false;
            }
        });
    }

    private void showAllGroups(ArrayList<JSONObject> alist) throws JSONException {
        LinearLayout container = findViewById(R.id.scrollViewLinearContainer);
        container.removeAllViews();

        linearLayoutCounter = textViewCounter = buttonCounter = 0;
        for(int i=alist.size()-1; i>=0; i--){
            addGroup(container, alist.get(i));
        }
    }
    private void showAllGroups(JSONArray jsonArray) throws JSONException {
        LinearLayout container = findViewById(R.id.scrollViewLinearContainer);
        container.removeAllViews();

        linearLayoutCounter = textViewCounter = buttonCounter = 0;
        for(int i=jsonArray.length()-1; i>=0; i--){
            addGroup(container, jsonArray.getJSONObject(i));
        }
    }

    private void addGroup(LinearLayout contianer, JSONObject obj) throws JSONException {
        LinearLayout groupContainer = new LinearLayout(this);

        Button visitButton = new Button(this);
        Button joinButton = new Button(this);
        visitButton.setText("Visit");
        joinButton.setText("Join");

        visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GroupPage.class); //change to GroupPage.class
                try {
                    intent.putExtra("groupId", obj.getString("id"));
                    intent.putExtra("groupTitle", obj.getString("title"));
                    intent.putExtra("isPublic", obj.getString("isPublic"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        TextView groupName = new TextView(this);
        groupName.setText(obj.getString("title"));
        groupName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        groupContainer.setOrientation(LinearLayout.VERTICAL);
        groupContainer.addView(groupName);
        groupContainer.addView(visitButton);
        groupContainer.addView(joinButton);
        groupContainer.setVisibility(View.VISIBLE);

        LinearLayout outerContainer = findViewById(R.id.scrollViewLinearContainer);
        outerContainer.addView(groupContainer);

    }
}