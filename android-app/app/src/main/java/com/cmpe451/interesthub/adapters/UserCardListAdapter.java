package com.cmpe451.interesthub.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserCardListAdapter extends RecyclerView.Adapter<UserCardListAdapter.ViewHolder> {
    public interface OnItemClickListener {

        void onItemClick(int pos);

    }
    private OnItemClickListener listener;

    private List<User> itemList;
    private Context context;
    private View.OnClickListener mClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {



        public TextView userName;
        public ImageView userIcon;
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
            userName = (TextView) itemView.findViewById(R.id.username);
            userIcon = (ImageView) itemView.findViewById(R.id.userIcon);
        }
    }

    public UserCardListAdapter(Context context, List<User> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout_card, null);
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.userName.setText(itemList.get(position).getUsername());
         if(itemList.get(position).getProfile()==null || itemList.get(position).getProfile().getPhoto()==null )
            holder.userIcon.setImageResource(R.drawable.prosmall);
        else{
            Log.d("IMAGE","image load "+itemList.get(position).getProfile().getPhoto());
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
            builder.build().load(itemList.get(position).getProfile().getPhoto()).into(holder.userIcon);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();

    }
}