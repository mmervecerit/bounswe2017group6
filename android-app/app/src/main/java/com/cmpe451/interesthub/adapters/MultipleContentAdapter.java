package com.cmpe451.interesthub.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.TypeData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MultipleContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {

        void onItemClick(int pos);

    }
    private OnItemClickListener listener;


    private List<Content> contentList;
    private Context context;
    private  RecyclerView.ViewHolder[] viewList;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public List<TextView> text = new ArrayList<TextView>();
        public List<TextView> longtext = new ArrayList<TextView>();
        public List<ImageView> image = new ArrayList<ImageView>();
        public List<CalendarView> datetime = new ArrayList<CalendarView>();
        public List<WebView> video = new ArrayList<WebView>();
        public List<TextView> number = new ArrayList<TextView>();
        public TextView owner;
        public TextView date;
        public ImageView pic;
        public List<TextView> headers = new ArrayList<>();
        private Button commentButton;
        public ViewHolder(View itemView, List<String> list, final int pos) {
            super(itemView);

            CardView c = (CardView)itemView;
            LinearLayout l = c.findViewById(R.id.content);
            LinearLayout commentRegion = c.findViewById(R.id.comment_region);
            commentRegion.setVisibility(View.GONE);
            owner = (TextView) c.findViewById(R.id.post_owner);
            date = (TextView) c.findViewById(R.id.post_date);
            pic = (ImageView) c.findViewById(R.id.post_owner_img);
            commentButton = c.findViewById(R.id.post_comment_button);


           for(int i = 1 ; i<list.size()*2;i+=2){
               String s = list.get(i/2);
               headers.add((TextView) l.getChildAt(i-1));
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
                   video.add((WebView) l.getChildAt(i));
                   //image.add((ImageView) l.getChildAt(i));
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
    public MultipleContentAdapter(Context context, List<Content> list, OnItemClickListener listener) {
        this.contentList= list;
        this.listener = listener;

        this.context = context;


    }
    //without listener
    public MultipleContentAdapter(Context context, List<Content> list) {
        this.contentList= list;

        this.context = context;


    }
    public MultipleContentAdapter(Context context) {

        this.context = context;
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            List<String> list = null;
            ViewHolder rcv = null;

               CardView def = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout_def, null);
               list = contentList.get(viewType).getContentType().getComponents();
               for (String s : list) {
                   TextView header = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_header, null);
                   LinearLayout l = (LinearLayout) def.findViewById(R.id.content);
                   l.addView(header);
                   if (s.equals("text")) {
                       l.addView((TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_text, null));
                   } else if (s.equals("title")) {
                       l.addView((TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_text, null));

                   } else if (s.equals("longtext")) {
                       l.addView((TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_longtext, null));

                   } else if (s.equals("image")  ) {
                       ImageView img = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_image, null);
                       img.setAdjustViewBounds(true);
                       l.addView(img);
                   }else if (s.equals("video") ) {
                       WebView vid = (WebView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_video, null);
                       l.addView(vid);
                   }
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
        if(listener!=null){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });}
        if(contentList.get(position).getComponents()!=null || contentList.get(position).getComponents().size()!=0 ){
            ((ViewHolder)holder).owner.setText(contentList.get(position).getOwner().getUsername()+" > " + contentList.get(position).getGroupName());
            long postDate = contentList.get(position).getCreatedDate().getTime();
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
            for(int i=0;i<contentList.get(position).getComponents().size();i++){
                Component c = contentList.get(position).getComponents().get(i);
                TypeData data = c.getType_data();
                ((ViewHolder)holder).headers.get(i).setText(contentList.get(position).getContentType().getComponent_names().get(i)+":");
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

                    int equalsIndex = data.getData().indexOf('=');
                    String hash = data.getData().substring(equalsIndex+1,data.getData().length());
                    String base ="https://www.youtube.com/embed/"+hash+"?fs=1&amp;feature=oembed";
                    String frameVideo = "<html><body>Video From YouTube<br><iframe width=\"320\" height=\"200\" src=\""+base+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                    String html = "<iframe width=\"320\" height=\"200\" src='"+base+"'frameborder=\"0\" allowfullscreen=\"\"></iframe>";

                    WebView mContentWebView = ((ViewHolder)holder).video.get(videoi);

                    mContentWebView.setWebChromeClient(new WebChromeClient());
                    mContentWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
                    mContentWebView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
                    mContentWebView.setWebViewClient(new WebViewClient());
                    mContentWebView.getSettings().setJavaScriptEnabled(true);
                    /*
                   // WebChromeClient client = new WebChromeClient();
                    ((ViewHolder)holder).video.get(videoi).getSettings().setJavaScriptEnabled(true);
                   // ((ViewHolder)holder).video.get(i).getSettings().setPluginsEnabled(true);
                    ((ViewHolder)holder).video.get(videoi).setWebChromeClient(new WebChromeClient() {
                    });

                    ((ViewHolder)holder).video.get(videoi).loadData(frameVideo, "text/html", "utf-8");
*                    */
                    mContentWebView.loadDataWithBaseURL(base, html,
                            "text/html", "UTF-8", null);
                     videoi++;


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