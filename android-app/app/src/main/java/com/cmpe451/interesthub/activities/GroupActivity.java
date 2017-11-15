package com.cmpe451.interesthub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.adapters.UserTimelineListAdapter;
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.Group;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupActivity extends AppCompatActivity {


    InterestHub hub ;
    Long groupId;
    String groupName;
    List<Content> contentList;
    RecyclerView contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_group);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),NewContent.class);
                intent.putExtra("groupName",groupName);
                intent.putExtra("groupId",groupId);
                startActivity(intent);
            }
        });

        if(getIntent().getExtras().getString("groupName")!=null){
            Log.d("Group activity started with: ", (String) getIntent().getExtras().getString("groupName"));
            groupId = getIntent().getExtras().getLong("groupId");
            groupName = getIntent().getExtras().getString("groupName");
            setTitle(groupName);
            Log.d("Group activity started with: ", String.valueOf(groupId));
        }
        hub = (InterestHub) getApplication();
        hub.getApiService().getGroupContents(groupId).enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                contentList = new ArrayList<Content>();
                for(Content c : response.body()){
                   contentList.add(c);
                    if(c.getComponents()==null || c.getComponents().size()==0)
                        Log.d("null","null component");
                    for(Component cc : c.getComponents()){
                        Log.d("compomnnet", cc.getLong_text());
                    }
                }
                setPost();

            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {

            }
        });

    }
    public void setPost(){
        final LinearLayoutManager ll = new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.VERTICAL);


        UserTimelineListAdapter adapter = new UserTimelineListAdapter(getApplicationContext(),contentList);

        contentView = (RecyclerView) findViewById(R.id.spesific_group_recycler_view);
        contentView.setLayoutManager(ll);

        contentView.setAdapter(adapter);
    }

}
