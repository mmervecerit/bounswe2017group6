package com.cmpe451.interesthub.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.UserActivity;
import com.cmpe451.interesthub.models.Dummy;
import com.cmpe451.interesthub.models.Group;

import java.util.List;


public class UserHomeGroupListAdapter extends BaseAdapter {

    Context context;
    List<Group> list;
    private UserActivity userActivity;
    private String[] t1,t2;

    public UserHomeGroupListAdapter(UserActivity userActivity, List<Group> list){
        this.list = list;
        this.userActivity = userActivity;
        this.context= context;

    }




    @Override
    public int getCount() {
        return list.isEmpty() ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = userActivity.getLayoutInflater().inflate(R.layout.user_group_list_layout,null );
        ImageView image= (ImageView) view.findViewById(R.id.imageView_user_group_list);
        TextView text1= (TextView) view.findViewById(R.id.textView_user_group_list);
        TextView text2= (TextView) view.findViewById(R.id.textView2_user_group_list);
        text1.setText(String.valueOf(list.get(i).getId()));
        text2.setText(list.get(i).getName());


        return view;
    }
}
