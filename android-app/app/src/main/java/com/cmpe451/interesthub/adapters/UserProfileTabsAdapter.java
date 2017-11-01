package com.cmpe451.interesthub.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.cmpe451.interesthub.fragments.UserEvents;
import com.cmpe451.interesthub.fragments.UserGroups;
import com.cmpe451.interesthub.fragments.UserProfile;
import com.cmpe451.interesthub.fragments.UserTimelineFragment;

/**
 * Created by eren on 19.10.2017.
 */

public class UserProfileTabsAdapter extends FragmentStatePagerAdapter {


    public UserProfileTabsAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

            return new UserEvents();

    }

    @Override
    public int getCount() {
        return 4;
    }
}