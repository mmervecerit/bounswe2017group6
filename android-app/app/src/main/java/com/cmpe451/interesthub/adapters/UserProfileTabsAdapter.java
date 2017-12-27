package com.cmpe451.interesthub.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.cmpe451.interesthub.fragments.User_Followers_Fragment;
import com.cmpe451.interesthub.fragments.User_Following_Fragment;
import com.cmpe451.interesthub.fragments.User_MyPosts_Fragment;

/**
 * Created by eren on 19.10.2017.
 */

public class UserProfileTabsAdapter extends FragmentStatePagerAdapter {

    private long userid;
    public UserProfileTabsAdapter(FragmentManager fm,long userid){
        super(fm);
        this.userid = userid;
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0) return new User_MyPosts_Fragment(userid);
        else if (position==1) return new User_Followers_Fragment(userid);
        else return new User_Following_Fragment(userid);
    }

    @Override
    public int getCount() {
        return 3;
    }
}