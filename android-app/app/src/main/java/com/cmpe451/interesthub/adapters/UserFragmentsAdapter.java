package com.cmpe451.interesthub.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cmpe451.interesthub.fragments.UserRecommendations;
import com.cmpe451.interesthub.fragments.UserGroups;
import com.cmpe451.interesthub.fragments.UserProfile;
import com.cmpe451.interesthub.fragments.UserTimelineFragment;

/**
 * Created by eren on 19.10.2017.
 */

public class UserFragmentsAdapter extends FragmentStatePagerAdapter {


    public UserFragmentsAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new UserTimelineFragment();
        else if (position==1)
            return new UserGroups();
        else if (position==2)
            return new UserRecommendations();
        else return new UserProfile();

    }

    @Override
    public int getCount() {
        return 4;
    }
}