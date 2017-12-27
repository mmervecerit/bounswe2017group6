package com.cmpe451.interesthub.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.support.v4.app.FragmentTransaction;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.activities.baseActivities.BaseActivity;
import com.cmpe451.interesthub.adapters.UserFragmentsAdapter;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.fragments.SearchFragment;
import com.cmpe451.interesthub.models.User;

public class UserActivity extends BaseActivity {

    private TabLayout tabLayout;
    private UserFragmentsAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private FrameLayout search;
    SearchFragment searchFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_layout);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        InterestHub hub = (InterestHub)getApplication();

        User user = hub.getSessionController().getUser();
        search = (FrameLayout) findViewById(R.id.search_frame);
        searchFragment = new SearchFragment();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.search_frame, searchFragment);
        ft.commit();
        search.setVisibility(View.GONE);


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
        final TabLayout.Tab recomm = tabLayout.newTab();
        final TabLayout.Tab profile = tabLayout.newTab();

        home.setText("Home");
        profile.setText("Profile");
        group.setText("Groups");
        recomm.setText("Recommendations");

        tabLayout.addTab(home,0);
        tabLayout.addTab(group,1);
        tabLayout.addTab(recomm,2);
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

    }@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked","SERACH");
                tabLayout.setVisibility(View.GONE);

                viewPager.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("clicked","SERACHclosed");
                tabLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);

                search.setVisibility(View.GONE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFragment.changeAdapter(newText);
                return false;
            }
        });

        return true;
    }



}
