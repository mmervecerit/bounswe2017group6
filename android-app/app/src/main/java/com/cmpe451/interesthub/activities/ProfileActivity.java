package com.cmpe451.interesthub.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;
import com.cmpe451.interesthub.adapters.UserProfileTabsAdapter;
import com.cmpe451.interesthub.models.Interest;
import com.cmpe451.interesthub.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {

    private long userId;
    private User currentUser;
    InterestHub hub ;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private boolean isfollowing;
    private UserProfileTabsAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if(getIntent().getExtras().getLong("userId")!=0){
            userId = getIntent().getExtras().getLong("userId");

        }
        final Button button = (Button) findViewById(R.id.followbutton);
        button.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isfollowing){


                }
                else{


                }
            }
        });
        hub = (InterestHub) getApplication();
        hub.getApiService().getSpesificUser("http://34.209.230.231:8000/users/"+userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response!=null && response.body()!=null){
                    currentUser = response.body();
                    fillUser();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        hub.getApiService().getFollowings().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response!=null&& response.body()!=null){
                    for(User u : response.body()){

                        if(u.getId()==userId){
                            button.setText("Unfollow");
                            button.setVisibility(View.VISIBLE);
                            isfollowing=true;
                            return;
                        }
                    }
                    isfollowing=false;
                    button.setText("Follow");
                    button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });



        tabLayout = (TabLayout) findViewById(R.id.p_TabLayoutProfile);
        viewPager = (ViewPager) findViewById(R.id.p_ViewPagerProfile);
        viewPagerAdapter = new UserProfileTabsAdapter(getSupportFragmentManager(),userId);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.removeAllTabs();
        final TabLayout.Tab home = tabLayout.newTab();
        final TabLayout.Tab followers = tabLayout.newTab();
        final TabLayout.Tab following = tabLayout.newTab();


        home.setText("Posts");
        followers.setText("Followers");
        following.setText("Following");

        tabLayout.addTab(home,0);
        tabLayout.addTab(followers,1);
        tabLayout.addTab(following,2);

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
        tabLayout.setTabTextColors(Color.BLACK,Color.BLACK);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }
    public void fillUser(){
        TextView userEmail = (TextView) findViewById(R.id.p_user_email);
        TextView userDesc = (TextView) findViewById(R.id.p_user_desc);
        TextView userInterests = (TextView) findViewById(R.id.p_user_interests);
        ImageView profileImg = (ImageView) findViewById(R.id.p_profile_image);


        if(currentUser.getEmail()!=null) userEmail.setText(currentUser.getEmail());
        if(currentUser.getProfile()!=null) {
            if(currentUser.getProfile().getName()!=null) setTitle(currentUser.getProfile().getName());
            if(currentUser.getProfile().getAbout()!=null) userDesc.setText(currentUser.getProfile().getAbout());
            if(currentUser.getProfile().getInterests()!=null){
                String interest="";
                for(Interest i : currentUser.getProfile().getInterests()) interest+=i.getLabel()+", ";
                if(interest.length()>2) interest = interest.substring(0,interest.length()-1);
                userInterests.setText(interest);
            }
            if(currentUser.getProfile().getPhoto()!=null){
                String img =currentUser.getProfile().getPhoto();
                Picasso.with(getBaseContext()).load(img).resize(200, 200).into(profileImg);
            }
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(getBaseContext(),UserActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
