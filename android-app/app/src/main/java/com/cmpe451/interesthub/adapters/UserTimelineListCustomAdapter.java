package com.cmpe451.interesthub.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.TypeData;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserTimelineListCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> item1;
    private List<String> item2;
    private List<String> item3;
    private List<Content> contentList;
    private Context context;
    private  RecyclerView.ViewHolder[] viewList;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public List<TextView> text = new ArrayList<TextView>();
        public List<TextView> longtext = new ArrayList<TextView>();
        public List<ImageView> image = new ArrayList<ImageView>();
        public List<CalendarView> datetime = new ArrayList<CalendarView>();
        public List<VideoView> video = new ArrayList<VideoView>();
        public List<TextView> number = new ArrayList<TextView>();
        public ViewHolder(View itemView,List<String> list) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            CardView c = (CardView)itemView;
            RelativeLayout r = (RelativeLayout) c.getChildAt(0);
            LinearLayout l = (LinearLayout) r.getChildAt(0);
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
    public UserTimelineListCustomAdapter(Context context, List<String> item1,List<String> item2,List<String> item3) {
            this.item1 = item1;
            this.item2 = item2;
            this.item3 = item3;

            this.context = context;


        }
    public UserTimelineListCustomAdapter(Context context, List<Content> list) {
        this.contentList= list;

        this.context = context;


    }
    public UserTimelineListCustomAdapter(Context context) {

        this.context = context;
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            List<String> list = null;
            ViewHolder rcv = null;

               CardView def = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout_def, null);
               list = contentList.get(viewType).getContentType().getComponents();
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
                rcv = new ViewHolder(def,list);

            return rcv;



        }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //holder.postHeader.setText(itemList.get(position).getHeader());

        if(contentList.get(position).getComponents()!=null || contentList.get(position).getComponents().size()!=0 ){
            int texti=0,longtexti=0,imagei=0,datetimei=0,videoi=0,numberi=0;
            for(int i=0;i<contentList.get(position).getComponents().size();i++){
                Component c = contentList.get(position).getComponents().get(i);
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
            return contentList.size();

        }
}