package com.example.studybuddies.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddies.LoginScreen;
import com.example.studybuddies.R;
import com.example.studybuddies.objects.Member;
import com.example.studybuddies.utils.Const;
import com.example.studybuddies.utils.OnFinishedArrayList;
import com.example.studybuddies.utils.OnSuccessfulArray;
import com.example.studybuddies.utils.OnSuccessfulObject;
import com.example.studybuddies.utils.RequestsCentral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupMembersRecyclerViewAdapter extends RecyclerView.Adapter<GroupMembersRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "GroupMembersRecyclerVie";
    private ArrayList<Member> groupMembers = new ArrayList<>();
    private Context context;

    private int userID, groupID;
    private Member userMembership;

    public GroupMembersRecyclerViewAdapter(Context context, int groupID) {
        this.context = context;
        this.groupID = groupID;
        this.userID = context.getSharedPreferences(LoginScreen.SHARED_PREFS, Context.MODE_PRIVATE).getInt(LoginScreen.ID_KEY, 0);
        RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                for(int i=0; i<response.length(); i++){
                    JSONObject jsonObject = response.getJSONObject(i);
                    if(jsonObject.getInt("groupId") == groupID && jsonObject.getInt("userId") == userID){
                        userMembership = new Member(jsonObject);
                        refreshRecView();
                        break;
                    }
                }
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupmembers_list_item, parent, false);
        GroupMembersRecyclerViewAdapter.ViewHolder holder = new GroupMembersRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Member curMember = groupMembers.get(holder.getAdapterPosition());
        holder.username.setText(curMember.getUser().getUsername());
        holder.ownership.setText(stringifyPermission(curMember.getPermission()));

        holder.makeOwnerButton.setVisibility(curMember.isEditable() ? View.VISIBLE : View.INVISIBLE);
        holder.kickUserButton.setVisibility(curMember.isEditable() ? View.VISIBLE : View.INVISIBLE);

        holder.kickUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(view.getContext())
                    // set message, title, and icon
                    .setTitle("Kick")
                    .setMessage("Do you want to kick " + curMember.getUser().getUsername() + " ?")
                    .setIcon(R.drawable.ic_baseline_person_remove_48)
                    .setPositiveButton("kick", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            RequestsCentral.deleteJSONObject(Const.GET_MEMBERS + "/" + curMember.getId(), new OnSuccessfulObject() {
                                @Override
                                public void onSuccess(JSONObject response) {
                                    Toast.makeText(context, curMember.getUser().getUsername() + " was kicked from the group", Toast.LENGTH_SHORT).show();
                                    refreshRecView();
                                }
                            });
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            refreshRecView();
                            dialog.dismiss();
                        }
                    })
                    .create();
                myQuittingDialogBox.show();
            }
        });

        holder.makeOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(view.getContext())
                        // set message, title, and icon
                        .setTitle("Pass Ownership")
                        .setMessage("Do you want to pass ownership to " + curMember.getUser().getUsername() + " ?")
                        .setIcon(R.drawable.ic_baseline_owner_panel_settings_48)
                        .setPositiveButton("pass", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                RequestsCentral.getJSONArray(Const.GET_MEMBERS, new OnSuccessfulArray() {
                                    @Override
                                    public void onSuccess(JSONArray response) throws JSONException {
                                        for(int i=0; i<response.length(); i++){
                                            JSONObject jsonObjectI = response.getJSONObject(i);
                                            if(jsonObjectI.getInt("groupId") == groupID && jsonObjectI.getInt("permission") == 2){ //found the owner of the group
                                                jsonObjectI.put("permission", 1); //taking away ownership, making it {1} user
                                                RequestsCentral.putJSONObject(Const.GET_MEMBERS + "/" + jsonObjectI.getInt("id"), jsonObjectI, new OnSuccessfulObject() {
                                                    @Override
                                                    public void onSuccess(JSONObject response) {
                                                        JSONObject jsonObjectJ = curMember.toJSONObject();
                                                        try {
                                                            jsonObjectJ.put("permission", 2); //giving {2} ownership
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        RequestsCentral.putJSONObject(Const.GET_MEMBERS + "/" + curMember.getId(), jsonObjectJ, new OnSuccessfulObject() {
                                                            @Override
                                                            public void onSuccess(JSONObject response) throws JSONException {
                                                                Toast.makeText(context, "Changed ownership from " + jsonObjectI.getJSONObject("usersDetail").getString("username") +
                                                                        " to " + jsonObjectJ.getJSONObject("usersDetail").getString("username"), Toast.LENGTH_SHORT).show();
                                                                refreshRecView();
                                                            }
                                                        });
                                                    }
                                                });
                                                break;
                                            }
                                        }
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                refreshRecView();
                                dialog.dismiss();
                            }
                        })
                        .create();
                myQuittingDialogBox.show();
            }
        });
    }

    private String stringifyPermission(int permission){
        if(permission == 1) return "User";
        if(permission == 2) return "Owner";
        if(permission == 3) return "Admin";
        return "Unrecognized user permission";
    }

    private void refreshRecView(){
        ArrayList<Member> members = new ArrayList<>();
        Member.getMembers(groupID, new OnFinishedArrayList() {
            @Override
            public void onFinishedArrayList(ArrayList a) {
                ArrayList<Member> responseMembers = (ArrayList<Member>) a;
                for(int i=0; i<responseMembers.size(); i++){
                    if(responseMembers.get(i).getUserId() == userID)
                        userMembership = responseMembers.get(i);
                }
                for(int i=0; i<responseMembers.size(); i++){
                    responseMembers.get(i).setEditable((userMembership.getPermission() > 1 && responseMembers.get(i).getUserId() != userID) ? true : false);
                }
                setMembers(responseMembers);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupMembers.size();
    }

    public void setMembers(ArrayList<Member> members) {
        this.groupMembers = members;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView username, ownership;
        private Button makeOwnerButton, kickUserButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatarImgView);
            username = (TextView) itemView.findViewById(R.id.usernameTxtView);
            ownership = (TextView) itemView.findViewById(R.id.permissionTextView);

            makeOwnerButton = (Button) itemView.findViewById(R.id.makeOwnerButton);
            kickUserButton = (Button) itemView.findViewById(R.id.kickUserButton);
        }
    }
}
