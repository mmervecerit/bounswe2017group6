package com.cmpe451.interesthub.adapters;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Comment;
import com.cmpe451.interesthub.models.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public UserAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull User[] objects) {
        super(context, resource, objects);
    }

    public UserAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull User[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public UserAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    public UserAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<User> objects) {
        super(context, resource, textViewResourceId, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.user_list_layout, null);
        }

        User p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.user_layout_fullname);
            TextView tt2 = (TextView) v.findViewById(R.id.user_layout_username);
            ImageView img =(ImageView) v.findViewById(R.id.user_layout_pic);

            if (tt1 != null && p.getProfile()!=null) {
                tt1.setText(p.getProfile().getName() + " " + p.getProfile().getLastname());
            }
            else
                tt1.setVisibility(View.GONE);

            if (tt2 != null) {
                tt2.setText(p.getUsername());
            }
           //TODO if(img!=null)

        }

        return v;
    }

}
