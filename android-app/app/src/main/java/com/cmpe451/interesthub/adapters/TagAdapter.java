package com.cmpe451.interesthub.adapters;


import android.content.Context;
import android.graphics.Color;
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
import com.cmpe451.interesthub.models.wikiDataModels.Search;

import java.util.List;

public class TagAdapter extends ArrayAdapter<Search> {
    int myTagsSize;
    public TagAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public TagAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Search[] objects) {
        super(context, resource, objects);
    }

    public TagAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull Search[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public TagAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Search> objects) {
        super(context, resource, objects);
    }

    public TagAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Search> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public TagAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Search> objects, int myTagsSize) {
        super(context, resource, objects);
        this.myTagsSize = myTagsSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.tag_list_layout, null);
        }

        Search s = getItem(position);

        if (s != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.commentlayour_header);
            if(position<myTagsSize)
                tt1.setBackgroundColor(Color.RED);
            //TextView tt2 = (TextView) v.findViewById(R.id.commentlayour_owner);

            if (tt1 != null) {
                tt1.setText(s.getLabel());
            }
        }

        return v;
    }

}
