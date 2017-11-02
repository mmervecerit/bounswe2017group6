package com.cmpe451.interesthub.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Group;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserGroupListAdapter extends RecyclerView.Adapter<UserGroupListAdapter.ViewHolder> {

    private List<Group> itemList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView groupDesc;
        public TextView groupName;
        public ImageView groupIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            groupName = (TextView) itemView.findViewById(R.id.name);
            groupDesc = (TextView) itemView.findViewById(R.id.desc);
            groupIcon = (ImageView) itemView.findViewById(R.id.groupIcon);
        }
    }
    public UserGroupListAdapter(Context context, List<Group> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_layout, null);
        ViewHolder rcv = new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.groupName.setText(itemList.get(position).getName());
        if(itemList.get(position).getDesc()!=null)
            holder.groupDesc.setText(itemList.get(position).getDesc());
        if(itemList.get(position).getImage()==null)
            holder.groupIcon.setImageResource(R.drawable.prosmall);
        else{

            Picasso.with(context)
                    .load(itemList.get(position).getImage())
                    .resize(200,200).into(holder.groupIcon);

        }

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();

    }
}