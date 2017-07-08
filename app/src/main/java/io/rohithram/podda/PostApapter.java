package io.rohithram.podda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.share.widget.LikeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by rohithram on 23/6/17.
 */

public class PostApapter extends RecyclerView.Adapter <PostApapter.ViewHolder>   {

    Context context;
    List<Posts> Postlist;
    AccessToken key;
    String pagename;
    String logo_url;
    android.support.v4.app.FragmentManager fm;
    VideoFragment fragment;
    FrameLayout mainlayout;
    ProgressDialog pd;
    ImageLoader imageLoader ;


    public PostApapter(Context context, ArrayList<Posts> postList, AccessToken key, String pagename, String logo_url, FragmentManager fragmentManager, VideoFragment fragment, FrameLayout layout_MainMenu, ProgressDialog pd) {
        this.context = context;
        this.Postlist = postList;
        this.key = key;
        this.pagename = pagename;
        this.logo_url = logo_url;
        this.fm = fragmentManager;
        this.fragment = fragment;
        this.mainlayout = layout_MainMenu;
        this.pd = pd;
        imageLoader = ImageUtil.getImageLoader(this.context);

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pods_activity, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final String type = Postlist.get(holder.getAdapterPosition()).type;
        final String  s = "video";
        final String s1 = "youtube.com";
        String strDate = Postlist.get(holder.getAdapterPosition()).created_time ;
        String datetime = GetLocalDateStringFromUTCString(strDate);

        holder.tv_org.setText(pagename);
        holder.tv_time.setText(datetime);
        Glide.with(context)
                .load(logo_url)
                .placeholder(R.drawable.loading_icon)
                .error(null)
                .crossFade(500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_org);


        if(Postlist.get(holder.getAdapterPosition()).type.equalsIgnoreCase("photo") || Postlist.get(holder.getAdapterPosition()).type.equalsIgnoreCase("video") ) {
            Glide.with(context)
                    .load(Postlist.get(holder.getAdapterPosition()).img_url)
                    .error(null)
                    .crossFade(1000)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_content);
            if (Postlist.get(holder.getAdapterPosition()).type.equalsIgnoreCase("video") && !Postlist.get(holder.getAdapterPosition()).type.equalsIgnoreCase("status")) {
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_play_circle_outline_black_24dp)
                        .error(null)
                        .override(64,64)
                        .into(holder.iv_videocover);
            } else {
                Glide.clear(holder.iv_videocover);
                holder.iv_videocover.setImageDrawable(null);
            }
        }
        else {
                // make sure Glide doesn't load anything into this view until told otherwise
                Glide.clear(holder.iv_content);
                holder.iv_content.setImageDrawable(null);
            }




        //imageLoader.displayImage(logo_url,holder.iv_org);
        holder.tv_post_des.setText(Postlist.get(holder.getAdapterPosition()).message);

        //imageLoader.displayImage(Postlist.get(holder.getAdapterPosition()).img_url,holder.iv_content);


        holder.tv_likes.setText(String.valueOf(Postlist.get(holder.getAdapterPosition()).count));



       if( type!=null && type.equalsIgnoreCase(s)) {
           holder.iv_videocover.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       String type1 = Postlist.get(holder.getAdapterPosition()).type;
                       if(type1!=null && type1.equalsIgnoreCase(s)) {
                           if (!Postlist.get(holder.getAdapterPosition()).vid_url.toLowerCase().contains(s1.toLowerCase())) {

                               Bundle args = new Bundle();
                               args.putString("video_url", Postlist.get(holder.getAdapterPosition()).vid_url);
                               fragment.getArguments().putAll(args);
                               android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                               transaction.replace(R.id.fragment_container, fragment);
                               transaction.addToBackStack(null);
                               transaction.commit();

                           } else {
                               context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Postlist.get(holder.getAdapterPosition()).vid_url)));

                           }
                       }
                   }
               });
           }
    }


    public String GetLocalDateStringFromUTCString(String utcLongDateTime) {
        SimpleDateFormat fb_dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZZ",Locale.getDefault());

        SimpleDateFormat my_dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());


        String localDateString = null;

        long when = 0;
        try {
            when = fb_dateFormat.parse(utcLongDateTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        localDateString = my_dateFormat.format(new Date(when + TimeZone.getDefault().getRawOffset() + (TimeZone.getDefault().inDaylightTime(new Date()) ? TimeZone.getDefault().getDSTSavings() : 0)));
        return localDateString;
    }

    @Override
    public int getItemCount() {
        return Postlist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_post_des, tv_org;
        public CardView cv_post;
        public ImageView iv_content;
        public ImageView iv_org,iv_videocover;
        public LikeView fblike;
        public FrameLayout fl_images;
        public TextView tv_likes,tv_time;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_post_des = (TextView) itemView.findViewById(R.id.tv_post_des);
            tv_org = (TextView) itemView.findViewById(R.id.tv_org);
            cv_post = (CardView) itemView.findViewById(R.id.cv_post);
            iv_org = (ImageView) itemView.findViewById(R.id.iv_org_profilepic);
            iv_content = (ImageView) itemView.findViewById(R.id.iv_content);
            fblike = (LikeView) itemView.findViewById(R.id.fb_like);
            fblike.setLikeViewStyle(LikeView.Style.STANDARD);
            fblike.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
            tv_likes = (TextView) itemView.findViewById(R.id.tv_likes);
            tv_time = (TextView)itemView.findViewById(R.id.tv_time);
            iv_videocover=(ImageView)itemView.findViewById(R.id.iv_videocover);
            fl_images = (FrameLayout)itemView.findViewById(R.id.fl_images);

        }

    }
}

