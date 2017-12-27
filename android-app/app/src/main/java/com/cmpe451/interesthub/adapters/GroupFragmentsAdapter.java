package com.cmpe451.interesthub.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cmpe451.interesthub.fragments.GroupInfo;
import com.cmpe451.interesthub.fragments.GroupPosts;
import com.cmpe451.interesthub.fragments.GroupUsers;

/**
 * Created by eren on 19.10.2017.
 */

public class GroupFragmentsAdapter extends FragmentStatePagerAdapter {

    String name;
    long id;
    public GroupFragmentsAdapter(FragmentManager fm,long id,String name){
        super(fm);
        this.id=id;
        this.name=name;
    }


    @Override
    public Fragment getItem(int position) {
            if(  position == 0 ) return GroupPosts.newInstance(name,id);
        else if ( position==1)return GroupUsers.newInstance(name,id);
        else return  GroupInfo.newInstance(name,id);

    }

    @Override
    public int getCount() {
        return 3;
    }
}