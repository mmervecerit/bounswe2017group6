package com.cmpe451.interesthub.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;
import com.cmpe451.interesthub.adapters.GroupFragmentsAdapter;
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.Group;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupActivity extends BaseActivity {


    public InterestHub hub ;
    Long groupId;
    String groupName;
    List<Content> contentList;
    RecyclerView contentView;
    private TabLayout tabLayout;
    private GroupFragmentsAdapter viewPagerAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_group);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        if(getIntent().getExtras().getString("groupName")!=null){
            Log.d("Group activity started with: ", (String) getIntent().getExtras().getString("groupName"));
            groupId = getIntent().getExtras().getLong("groupId");
            groupName = getIntent().getExtras().getString("groupName");
            setTitle(groupName);
            Log.d("Group activity started with: ", String.valueOf(groupId));
        }
        tabLayout = (TabLayout) findViewById(R.id.TabLayout_group);
        viewPager = (ViewPager) findViewById(R.id.ViewPager_group);
        viewPagerAdapter = new GroupFragmentsAdapter(getSupportFragmentManager(),groupId,groupName);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final TabLayout.Tab posts = tabLayout.newTab();
        final TabLayout.Tab users = tabLayout.newTab();
        final TabLayout.Tab info = tabLayout.newTab();

        posts.setText("Posts");
        users.setText("Users");
        info.setText("About");

        tabLayout.addTab(posts,0);
        tabLayout.addTab(users,1);
        tabLayout.addTab(info,2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setTabTextColors(Color.WHITE,Color.WHITE);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        hub = (InterestHub) getApplication();
        hub.getApiService().getGroupContents(groupId).enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                contentList = new ArrayList<Content>();
                Log.d("SUCCSEFUL","calling group coıntents");
                if(response.body()==null) return;
                for(Content c : response.body()){
                   contentList.add(c);
                    if(c.getComponents()==null || c.getComponents().size()==0)
                        Log.d("null","null component");

                }
                //setPost();

            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                Log.d("FAIL","failuer when calilng group contents");
            }
        });

    }
    @Override
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
    public void settitle(String name){
        setTitle(name);
    }


}
