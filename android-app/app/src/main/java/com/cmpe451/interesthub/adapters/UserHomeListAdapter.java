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

import java.util.List;

public class UserHomeListAdapter extends BaseAdapter {

    Context context;
    List<Dummy> list;
    private UserActivity userActivity;

    public UserHomeListAdapter(UserActivity userActivity){
        this.list = list;
        this.userActivity = userActivity;
        this.context= context;

    }




    @Override
    public int getCount() {
        return 10;
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
        text1.setText("Post Header: "+ i);
        text2.setText("Post Content: "+ i);


        return view;
    }
}
