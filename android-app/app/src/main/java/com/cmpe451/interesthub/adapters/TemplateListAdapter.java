package com.cmpe451.interesthub.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.cmpe451.interesthub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eren on 16.11.2017.
 */

public class TemplateListAdapter extends RecyclerView.Adapter<TemplateListAdapter.ViewHolder>{

    public interface OnItemClickListener {

        void onItemClick(int pos);

    }


    List<String> list;
    private final OnItemClickListener listener;
    public TemplateListAdapter(List<String> list,OnItemClickListener listener) {
        this.list = list;
        this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout2, null);

        ViewHolder rcv =  new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position == list.size())
            holder.bind("NEW TEMPLATE",position,true,listener);
        else
            holder.bind(list.get(position),position,false,listener);
    }

    @Override
    public int getItemCount() {
        return list.size() +1 ;
    }

    @Override
    public int getItemViewType(int position){
        if(position ==list.size())
            return 1;
        else return 0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            text = (TextView) itemView.findViewById(R.id.post_header);

        }

        public void bind(final String s, final int pos,boolean last, final OnItemClickListener listener) {
            if(last){
                text.setTextColor(Color.RED);
            }

            text.setText(s);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(pos);
                }

            });
        }
    }
}
