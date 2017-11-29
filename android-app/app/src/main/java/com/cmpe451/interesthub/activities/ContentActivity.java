package com.cmpe451.interesthub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;
import com.cmpe451.interesthub.adapters.SingleContentAdapter;
import com.cmpe451.interesthub.models.Comment;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.UpDown;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentActivity extends BaseActivity {


    private RecyclerView recyclerView;
    private  InterestHub hub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        hub = (InterestHub) getApplication();
        recyclerView  = (RecyclerView) findViewById(R.id.single_content_recycler);

       final SingleContentAdapter.OnItemClickListener listener = new SingleContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long pos) {
                Log.d("position", String.valueOf(pos));
                Intent intent = new Intent(getBaseContext(), OtherUserActivity.class);
                startActivity(intent);
            }
        };



        final Content content = hub.getTempContent();

        final List<Comment> comments = new ArrayList<>();
        final List<UpDown> votes = new ArrayList<>();
        hub.getApiService().getGroupComments(content.getId()).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.body() == null || response.body().isEmpty())
                    setAdapter(content,null,listener);
                else{
                    for(Comment c : response.body()) comments.add(c);
                    setAdapter(content,comments,listener);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
    }

    public void setAdapter(Content content, List<Comment> comments,SingleContentAdapter.OnItemClickListener listener){
        final LinearLayoutManager ll = new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.VERTICAL);


        SingleContentAdapter adapter;
        if(comments==null)
            adapter = new SingleContentAdapter(getBaseContext(),content,hub,listener);
        else
            adapter = new SingleContentAdapter(getBaseContext(),content,comments,hub,listener);
        recyclerView.setLayoutManager(ll);

        recyclerView.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
