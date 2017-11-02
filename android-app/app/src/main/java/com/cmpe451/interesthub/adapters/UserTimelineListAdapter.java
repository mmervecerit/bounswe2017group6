package com.cmpe451.interesthub.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Content;

import java.util.List;

public class UserTimelineListAdapter extends RecyclerView.Adapter<UserTimelineListAdapter.ViewHolder> {

    private List<Content> itemList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView postHeader;
        public ImageView postImage;
        public TextView postContent;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            postHeader = (TextView) itemView.findViewById(R.id.post_header);
            postImage = (ImageView) itemView.findViewById(R.id.groupIcon);
            postContent = (TextView) itemView.findViewById(R.id.post_content);
        }
    }
        public UserTimelineListAdapter(Context context, List<Content> itemList) {
            this.itemList = itemList;
            this.context = context;
        }
    public UserTimelineListAdapter(Context context) {

        this.context = context;
    }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, null);
            ViewHolder rcv = new ViewHolder(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //holder.postHeader.setText(itemList.get(position).getHeader());
            holder.postImage.setImageResource(R.drawable.placeholder);
            holder.postContent.setText("PostContent "+position);
            //TODO

            holder.postHeader.setText("Post Header "+position);
        }

        @Override
        public int getItemCount() {
            return this.itemList==null ? 5 : itemList.size();

        }
}