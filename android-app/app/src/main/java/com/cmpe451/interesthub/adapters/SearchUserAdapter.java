package com.cmpe451.interesthub.adapters;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.User;

import java.util.List;

public class SearchUserAdapter extends ArrayAdapter<User> {

    public SearchUserAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SearchUserAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull User[] objects) {
        super(context, resource, objects);
    }

    public SearchUserAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull User[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public SearchUserAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    public SearchUserAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<User> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("GROUPS", String.valueOf(position));
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.tag_list_layout, null);
        }

        User s = getItem(position);
        if (s != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.commentlayour_header);

            if (tt1 != null) {
                tt1.setText(s.getUsername());
            }
        }

        return v;
    }

}
