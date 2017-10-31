package com.cmpe451.interesthub.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;
import com.cmpe451.interesthub.adapters.UserFragmentsAdapter;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.User;

public class UserActivity extends BaseActivity {

    private TabLayout tabLayout;
    private UserFragmentsAdapter viewPagerAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_layout);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        InterestHub hub = (InterestHub)getApplication();

        User user = hub.getSessionController().getUser();
        if(user != null) {
            for (Group g : user.getGroupList()) {
                Log.d("USER ACTIVITY SSESSION CHECK ", g.getName());
            }
        }

        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        viewPager = (ViewPager) findViewById(R.id.ViewPager);
        viewPagerAdapter = new UserFragmentsAdapter(getSupportFragmentManager());
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

        final TabLayout.Tab home = tabLayout.newTab();
        final TabLayout.Tab group = tabLayout.newTab();
        final TabLayout.Tab events = tabLayout.newTab();
        final TabLayout.Tab profile = tabLayout.newTab();

        home.setText("Home");
        profile.setText("Profile");
        group.setText("Groups");
        events.setText("Events");

        tabLayout.addTab(home,0);
        tabLayout.addTab(group,1);
        tabLayout.addTab(events,2);
        tabLayout.addTab(profile,3);

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

    }


}
