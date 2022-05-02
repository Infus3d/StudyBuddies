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
import com.example.studybuddies.objects.Group;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
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

        Group.getPublicGroups(new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                ArrayList<Group> groups = (ArrayList<Group>) a;
                showAllGroups(a);
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
                Group.getPublicGroups(new OnFinishedArrayList() {
                    @Override
                    public void onFinishedArrayList(ArrayList a) {
                        ArrayList<Group> groups = new ArrayList<Group>();
                        for (Object o : a) {
                            Group current = (Group) o;
                            if (current.getTitle().length() >= newText.length() && current.getTitle().substring(0, newText.length()).equals(newText)) {
                                groups.add(current);
                            }
                        }
                        showAllGroups(groups);
                    }
                });
                return false;
            }
        });
    }

    private void showAllGroups(ArrayList<Group> groups) {
        LinearLayout container = findViewById(R.id.scrollViewLinearContainer);
        container.removeAllViews();

        linearLayoutCounter = textViewCounter = buttonCounter = 0;
        for(Group g : groups){
            addGroup(container, g);
        }
    }

    private void addGroup(LinearLayout contianer, Group group) {
        LinearLayout groupContainer = new LinearLayout(this);

        Button visitButton = new Button(this);
        Button joinButton = new Button(this);
        visitButton.setText("Visit");
        joinButton.setText("Join");

        visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GroupPage.class); //change to GroupPage.class

                intent.putExtra("groupId", group.getId());
                intent.putExtra("groupTitle", group.getTitle());
                intent.putExtra("isPublic", group.isPublic());

                startActivity(intent);
            }
        });

        TextView groupName = new TextView(this);
        groupName.setText(group.getTitle());
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