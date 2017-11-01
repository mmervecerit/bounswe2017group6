package com.cmpe451.interesthub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Group;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final InterestHub hub = (InterestHub) getApplication();
        final EditText groupName;
        final EditText groupDes;
        final EditText groupTags;
        final EditText groupIcon;

        groupName = (EditText)findViewById(R.id.group_name);
        groupDes = (EditText)findViewById(R.id.group_desc);
        groupIcon = (EditText)findViewById(R.id.group_icon);
        Button createButton = (Button)findViewById(R.id.group_create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(groupName.getText().toString().equals("") )
                {
                    Toast toast = Toast.makeText(GroupCreation.this, "Enter Group Name", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }if(groupDes.getText().toString().equals("") )
                {

                    Toast toast = Toast.makeText(GroupCreation.this, "Enter Group Description", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
               hub.getApiService().addGroup(groupName.getText().toString(),
                       groupDes.getText().toString(),groupIcon.getText().toString()).enqueue(new Callback<Group>() {
                   @Override
                   public void onResponse(Call<Group> call, Response<Group> response) {
                       Toast toast = Toast.makeText(GroupCreation.this, groupName.getText() + " is created!", Toast.LENGTH_SHORT);
                       toast.show();
                       Intent intent= new Intent(GroupCreation.this, UserActivity.class);
                       startActivity(intent);

                   }

                   @Override
                   public void onFailure(Call<Group> call, Throwable t) {
                       Toast toast = Toast.makeText(GroupCreation.this, "An error occured!", Toast.LENGTH_SHORT);
                       toast.show();
                   }
               });
            }
        });
    }

}
