package com.cmpe451.interesthub.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.ContentActivity;
import com.cmpe451.interesthub.models.Comment;
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.Interest;
import com.cmpe451.interesthub.models.TypeData;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SingleContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {

        void onItemClick(int pos);

    }
    private OnItemClickListener listener;


    private Content content;
    private Context context;
    private List<Comment> comments;
    private  RecyclerView.ViewHolder[] viewList;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public List<TextView> text = new ArrayList<TextView>();
        public List<TextView> longtext = new ArrayList<TextView>();
        public List<ImageView> image = new ArrayList<ImageView>();
        public List<CalendarView> datetime = new ArrayList<CalendarView>();
        public List<VideoView> video = new ArrayList<VideoView>();
        public List<TextView> number = new ArrayList<TextView>();
        public TextView owner;
        public TextView date;
        public ImageView pic;
        public  LinearLayout commentRegion;
        private Button commentButton;
        public ViewHolder(View itemView, List<String> list, final int pos) {
            super(itemView);

            CardView c = (CardView)itemView;
            LinearLayout l = c.findViewById(R.id.content);
             commentRegion = c.findViewById(R.id.comment_region);
            owner = (TextView) c.findViewById(R.id.post_owner);
            date = (TextView) c.findViewById(R.id.post_date);
            pic = (ImageView) c.findViewById(R.id.post_owner_img);
            commentButton = c.findViewById(R.id.post_comment_button);


           for(int i = 0 ; i<list.size();i++){
               String s = list.get(i);

               if(s.equals("text")||s.equals("title")){
                   text.add((TextView) l.getChildAt(i));
               }
               else if(s.equals("longtext")){
                   longtext.add((TextView) l.getChildAt(i));

               }
               else if(s.equals("title")){
                   text.add((TextView) l.getChildAt(i));

               }
               else if(s.equals("image")){
                   image.add((ImageView) l.getChildAt(i));

               }else if(s.equals("video")){
                   //video.add((VideoView) l.getChildAt(i));
                   image.add((ImageView) l.getChildAt(i));
               }
               else if(s.equals("number")){
                   number.add((TextView) l.getChildAt(i));

               }
               else if (s.equals("datetime")){
                   datetime.add((CalendarView) l.getChildAt(i));

               }
           }
        }

    }

    //with listener
    public SingleContentAdapter(Context context, Content content, OnItemClickListener listener) {
        this.content= content;
        this.listener = listener;

        this.context = context;


    }
    public SingleContentAdapter(Context context, Content content,List<Comment> comments) {
        this.content= content;
        this.comments=comments;
        this.context = context;


    }
    //without listener
    public SingleContentAdapter(Context context, Content content) {
        this.content= content;

        this.context = context;


    }
    public SingleContentAdapter(Context context) {

        this.context = context;
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            List<String> list = null;
            ViewHolder rcv = null;

               CardView def = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout_def, null);
              list =  content.getContentType().getComponents();
               for (String s : list) {
                   LinearLayout l = (LinearLayout) def.findViewById(R.id.content);
                   if (s.equals("text")) {
                       l.addView((TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_text, null));
                   } else if (s.equals("title")) {
                       l.addView((TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_text, null));

                   } else if (s.equals("longtext")) {
                       l.addView((TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_longtext, null));

                   } else if (s.equals("image") || (s.equals("video") ) ) {
                       ImageView img = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_image, null);
                       img.setAdjustViewBounds(true);
                       l.addView(img);
                   }/*else if (s.equals("video") ) {
                       VideoView vid = (VideoView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_video, null);
                       l.addView(vid);
                   }*/
                   else if (s.equals("number")) {
                       l.addView((TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_number, null));

                   }
                   else if(s.equals("datetime")){
                       l.addView((CalendarView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_datetime, null));

                   }
               }
                rcv = new ViewHolder(def,list,viewType);

            return rcv;



        }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //holder.postHeader.setText(itemList.get(position).getHeader());

        //sets click listener for each card view in order to open content activity;
        if(listener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);
                }
            });
        }

        //disable comment region if there is no comment
        if(comments==null||comments.isEmpty()) ((ViewHolder)holder).commentRegion.setVisibility(View.GONE);
        //else set comments
        else{
            ((TextView) ((ViewHolder)holder).commentRegion.getChildAt(1)).setText(comments.get(0).getText());
        }
        if(content.getComponents()!=null ||content.getComponents().size()!=0 ){
            ((ViewHolder)holder).owner.setText(content.getOwner().getUsername()+" > " + content.getGroupName());
            long postDate = content.getCreatedDate().getTime();
            long now = Calendar.getInstance().getTimeInMillis();
            long different = now-postDate;
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;
            ((ViewHolder)holder).date.setText(elapsedHours+" hours ago");

            int texti=0,longtexti=0,imagei=0,datetimei=0,videoi=0,numberi=0;
            for(int i=0;i<content.getComponents().size();i++){
                Component c = content.getComponents().get(i);
                TypeData data = c.getType_data();

                if (c.getComponent_type().equals("text")) {
                    ((ViewHolder)holder).text.get(texti).setText(data.getData());
                    texti++;
                } else if (c.getComponent_type().equals("title")) {
                    ((ViewHolder)holder).text.get(texti).setText(data.getData());
                    texti++;
                } else if (c.getComponent_type().equals("longtext")) {
                    ((ViewHolder)holder).longtext.get(longtexti).setText(data.getData());
                    longtexti++;
                }
                else if (c.getComponent_type().equals("number")) {
                    ((ViewHolder)holder).number.get(numberi).setText(data.getData());
                    numberi++;
                } else if (c.getComponent_type().equals("image") ) {
                    Picasso.with(context)
                            .load(data.getData())
                            .resize(200,200).into(((ViewHolder)holder).image.get(imagei));
                    imagei++;
                }else if (c.getComponent_type().equals("video")) {
                    /*
                    Log.d("VIDEO",data.getData());
                    VideoView mVideoView = ((ViewHolder)holder).video.get(videoi);
                    mVideoView.setMediaController(new MediaController(context));
                   // mVideoView.setVideoURI();
                    mVideoView.requestFocus();
                    mVideoView.start();


                     videoi++;
                     */
                    ((ViewHolder)holder).image.get(imagei).setImageResource(R.drawable.placeholder);
                    imagei++;
                } else if(c.getComponent_type().equals("datetime")){

                }
            }


        }

    }

        @Override
        public int getItemCount() {
            return 1;

        }
}