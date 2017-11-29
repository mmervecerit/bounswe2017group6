package com.cmpe451.interesthub.adapters;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Comment;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {
    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Comment[] objects) {
        super(context, resource, objects);
    }

    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull Comment[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
    }

    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Comment> objects) {
        super(context, resource, textViewResourceId, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.commentlayout, null);
        }

        Comment p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.commentlayour_header);
            TextView tt2 = (TextView) v.findViewById(R.id.commentlayour_owner);

            if (tt1 != null) {
                tt1.setText(p.getText());
            }if (tt2 != null) {
                tt2.setText(p.getOwner().getUsername());
            }
        }

        return v;
    }

}
