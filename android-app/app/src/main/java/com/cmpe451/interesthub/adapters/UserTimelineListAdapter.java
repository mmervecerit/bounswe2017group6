package com.cmpe451.interesthub.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.ContentType;

import java.util.List;

public class UserTimelineListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Content> itemList;
    private Context context;
    private  RecyclerView.ViewHolder[] viewList;
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
    public static class ViewHolder2 extends RecyclerView.ViewHolder {

        public TextView postHeader;
        public ImageView postImage;
        public TextView postContent;
        public ViewHolder2(View itemView) {
            super(itemView);

            CardView c = (CardView)itemView;
            RelativeLayout r = (RelativeLayout) c.getChildAt(0);
            LinearLayout l = (LinearLayout) r.getChildAt(0);
            postHeader = (TextView) l.getChildAt(0);
            postContent = (TextView) l.getChildAt(2);
            postImage = (ImageView) l.getChildAt(1);
        }

    }
    public static class ViewHolder3 extends RecyclerView.ViewHolder {

        public TextView postHeader;
        public TextView postContent;
        public ViewHolder3(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            CardView c = (CardView)itemView;
            RelativeLayout r = (RelativeLayout) c.getChildAt(0);
            LinearLayout l = (LinearLayout) r.getChildAt(0);
            postHeader = (TextView) l.getChildAt(0);
            postContent = (TextView) l.getChildAt(1);

        }

    }

    public UserTimelineListAdapter(Context context, List<Content> itemList) {
            this.itemList = itemList;
            this.context = context;
            viewList=new RecyclerView.ViewHolder[itemList.size()];
            for(int i =0 ;i<itemList.size() ; i++){
                ContentType type = itemList.get(i).getContentType();

                for(String s : type.getComponents()){

                }
            }

        }
    public UserTimelineListAdapter(Context context) {

        this.context = context;
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if(viewType==0) {
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, null);
                ViewHolder rcv = new ViewHolder(layoutView);
                return  rcv;
            }
            else if(viewType == 1){
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout2, null);
                TextView t1= (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_text,null);
                TextView t2= (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_text,null);
                CardView def = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout_def, null);
                t2.setTextSize(10);

                LinearLayout l = (LinearLayout) def.findViewById(R.id.content);
                ImageView img = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_image,null);
                img.setAdjustViewBounds(true);
                l.addView(t1);
                l.addView(img);
                l.addView(t2);
                ViewHolder2 rcv = new ViewHolder2(def);

                return rcv;
            }else{
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout2, null);
                TextView t1= (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_text,null);
                TextView t2= (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_component_text,null);
                CardView def = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout_def, null);
                t2.setTextSize(10);
                LinearLayout l = (LinearLayout) def.findViewById(R.id.content);
                l.addView(t1);
                l.addView(t2);
                ViewHolder3 rcv = new ViewHolder3(def);
                return rcv;

            }

        }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 3;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //holder.postHeader.setText(itemList.get(position).getHeader());

            if(holder.getItemViewType()==0) {

                ((ViewHolder)holder).postImage.setImageResource(R.drawable.indir);
                ((ViewHolder)holder).postContent.setText("PostContent " + position);
                //TODO

                ((ViewHolder)holder).postHeader.setText("Post Header " + position);
            }
            else if(holder.getItemViewType()==1){
                ((ViewHolder2)holder).postContent.setText("PostContent without image. Second type of default post" + position);
                //TODO

                ((ViewHolder2)holder).postHeader.setText("Post Header without image" + position);
                ((ViewHolder2)holder).postImage.setImageResource(R.drawable.placeholder);
            }
            else{
                ((ViewHolder3)holder).postContent.setText("PostContent without image. Third type of default post" + position);
                //TODO

                ((ViewHolder3)holder).postHeader.setText("Post Header without image" + position);
            }
        }

        @Override
        public int getItemCount() {
            return this.itemList==null ? 5 : itemList.size();

        }
}