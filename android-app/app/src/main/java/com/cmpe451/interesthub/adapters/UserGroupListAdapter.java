package com.cmpe451.interesthub.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private View.OnClickListener mClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView groupDesc;
        public TextView groupName;
        public ImageView groupIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    Bundle b = new Bundle();
                    b.putInt("key", 1); //Your id
                    intent.putExtras(b); //Put your id to your next Intent

                }
            });
            groupName = (TextView) itemView.findViewById(R.id.name);
            groupDesc = (TextView) itemView.findViewById(R.id.desc);
            groupIcon = (ImageView) itemView.findViewById(R.id.groupIcon);
        }
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }
    public UserGroupListAdapter(Context context, List<Group> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_layout, null);
        ViewHolder rcv = new ViewHolder(layoutView);
        rcv.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.groupName.setText(itemList.get(position).getName());
        if(itemList.get(position).getDesc()!=null)
            holder.groupDesc.setText(itemList.get(position).getDesc());
        if(itemList.get(position).getImage()==null || itemList.get(position).getImage().equals("") )
            holder.groupIcon.setImageResource(R.drawable.prosmall);
        else{
            Log.d("IMAGE","image load "+itemList.get(position).getImage());
            /*Picasso.with(context)
                    .load(itemList.get(position).getImage())
                    .resize(200,200).into(holder.groupIcon);
            */
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener()
            {


                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    Log.d("IMAGE",exception.toString());
                }
            });
            builder.build().load(itemList.get(position).getImage()).into(holder.groupIcon);
        }

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();

    }
}