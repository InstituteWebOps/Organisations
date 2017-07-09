package io.rohithram.podda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import static android.R.attr.id;
import static android.R.attr.layout_below;
import static android.R.attr.targetActivity;
import static android.R.attr.theme;
import static android.R.attr.type;

/**
 * Created by rohithram on 23/6/17.
 */

public class PostApapter extends RecyclerView.Adapter <PostApapter.ViewHolder>    {

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
    PopupWindow reactionpopup;
    View layout;



    public PostApapter(Context context, ArrayList<Posts> postList, AccessToken key, String pagename, String logo_url, FragmentManager fragmentManager, VideoFragment fragment, FrameLayout layout_MainMenu, ProgressDialog pd, PopupWindow reactionpopup, View layout) {
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
        this.reactionpopup = reactionpopup;
        this.layout = layout;



    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pods_activity, parent, false);
        return new ViewHolder(itemView,layout);
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

        holder.tv_post_des.setText(Postlist.get(holder.getAdapterPosition()).message);


        String udata=String.valueOf("Reactions: "+Postlist.get(holder.getAdapterPosition()).count);
        final SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        holder.tv_likes.setText(content);


        hideview(holder.iv_2,Postlist.get(holder.getAdapterPosition()).haha_count);
        hideview(holder.iv_3,Postlist.get(holder.getAdapterPosition()).love_count);
        hideview(holder.iv_5,Postlist.get(holder.getAdapterPosition()).sad_count);
        hideview(holder.iv_4,Postlist.get(holder.getAdapterPosition()).wow_count);
        hideview(holder.iv_1,Postlist.get(holder.getAdapterPosition()).like_count);
        hideview(holder.iv_6,Postlist.get(holder.getAdapterPosition()).angry_count);

        holder.lin_emojis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {

                    showview2(holder.tv_hahacount,holder.iv_21);
                    showview2(holder.tv_lovecount,holder.iv_31);
                    showview2(holder.tv_sadcount,holder.iv_51);
                    showview2(holder.tv_wowcount,holder.iv_41);
                    showview2(holder.tv_likecount,holder.iv_11);
                    showview2(holder.tv_angrycount,holder.iv_61);

                    holder.tv_likecount.setText(String.valueOf(Postlist.get(holder.getAdapterPosition()).like_count));
                    holder.tv_hahacount.setText(String.valueOf(Postlist.get(holder.getAdapterPosition()).haha_count));
                    holder.tv_wowcount.setText(String.valueOf(Postlist.get(holder.getAdapterPosition()).wow_count));
                    holder.tv_lovecount.setText(String.valueOf(Postlist.get(holder.getAdapterPosition()).love_count));
                    holder.tv_sadcount.setText(String.valueOf(Postlist.get(holder.getAdapterPosition()).sad_count));
                    holder.tv_angrycount.setText(String.valueOf(Postlist.get(holder.getAdapterPosition()).angry_count));

                    hideview2(holder.tv_hahacount,holder.iv_21,Postlist.get(holder.getAdapterPosition()).haha_count);
                    hideview2(holder.tv_lovecount,holder.iv_31,Postlist.get(holder.getAdapterPosition()).love_count);
                    hideview2(holder.tv_sadcount,holder.iv_51,Postlist.get(holder.getAdapterPosition()).sad_count);
                    hideview2(holder.tv_wowcount,holder.iv_41,Postlist.get(holder.getAdapterPosition()).wow_count);
                    hideview2(holder.tv_likecount,holder.iv_11,Postlist.get(holder.getAdapterPosition()).like_count);
                    hideview2(holder.tv_angrycount,holder.iv_61,Postlist.get(holder.getAdapterPosition()).angry_count);

                    reactionpopup = new PopupWindow(layout,CardView.LayoutParams.WRAP_CONTENT,CardView.LayoutParams.WRAP_CONTENT,true);

                    reactionpopup.setTouchable(true);
                    reactionpopup.setFocusable(true);
                    reactionpopup.setBackgroundDrawable(new BitmapDrawable());
                    reactionpopup.setOutsideTouchable(true);

                    new Handler().postDelayed(new Runnable(){
                            public void run() {
                                reactionpopup.showAsDropDown(v,-20,0);
                                PostActivity.dim();
                            }

                        }, 200L);

                    reactionpopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            PostActivity.normal();
                        }
                    });

                } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });


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

    private Void hideview(View view,int count){
        if(count==0){

            view.getLayoutParams().height=0;
            view.getLayoutParams().width=0;
            view.setVisibility(View.INVISIBLE);
        }

        return null;

    }

    private Void showview2(View view1,View view2){
            view1.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;
            view1.getLayoutParams().width= ViewGroup.LayoutParams.WRAP_CONTENT;
            view1.setVisibility(View.VISIBLE);

            view2.getLayoutParams().height = 42;
            view2.getLayoutParams().width = 42;
            view2.setVisibility(View.VISIBLE);
        return null;

    }


    private Void hideview2(View view1,View view2, int count){
        if(count==0){

            view1.getLayoutParams().height=0;
            view1.getLayoutParams().width=0;
            view1.setVisibility(View.INVISIBLE);

            view2.getLayoutParams().height=0;
            view2.getLayoutParams().width=0;
            view2.setVisibility(View.INVISIBLE);
        }

        return null;

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
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return Postlist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_post_des, tv_org;
        public CardView cv_post,cv_popup;
        public ImageView iv_content;
        public ImageView iv_org,iv_videocover,iv_1,iv_2,iv_3,iv_4,iv_5,iv_6;
        public LikeView fblike;
        public FrameLayout fl_images;
        public TextView tv_likes,tv_time;
        public LinearLayout lin_emojis;
        ImageView iv_11,iv_21,iv_31,iv_41,iv_51,iv_61;
        TextView tv_likecount,tv_hahacount,tv_lovecount,tv_wowcount,tv_angrycount,tv_sadcount;



        public ViewHolder(View itemView,View layout) {
            super(itemView);

            tv_post_des = (TextView) itemView.findViewById(R.id.tv_post_des);
            tv_org = (TextView) itemView.findViewById(R.id.tv_org);

            cv_popup = (CardView)itemView.findViewById(R.id.cv_popup);
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
            lin_emojis = (LinearLayout)itemView.findViewById(R.id.lin_emojis);

            iv_1 = (ImageView)itemView.findViewById(R.id.iv_1);
            iv_2 = (ImageView)itemView.findViewById(R.id.iv_2);
            iv_3 = (ImageView)itemView.findViewById(R.id.iv_3);
            iv_4 = (ImageView)itemView.findViewById(R.id.iv_4);
            iv_5 = (ImageView)itemView.findViewById(R.id.iv_5);
            iv_6 = (ImageView)itemView.findViewById(R.id.iv_6);

            iv_11 = (ImageView)layout.findViewById(R.id.iv_11);
            iv_21 = (ImageView)layout.findViewById(R.id.iv_21);
            iv_31 = (ImageView)layout.findViewById(R.id.iv_31);
            iv_41 = (ImageView)layout.findViewById(R.id.iv_41);
            iv_51 = (ImageView)layout.findViewById(R.id.iv_51);
            iv_61 = (ImageView)layout.findViewById(R.id.iv_61);

            tv_likecount = (TextView)layout.findViewById(R.id.tv_likecount);
            tv_hahacount = (TextView)layout.findViewById(R.id.tv_hahacount);
            tv_lovecount = (TextView)layout.findViewById(R.id.tv_lovecount);
            tv_wowcount = (TextView)layout.findViewById(R.id.tv_wowcount);
            tv_sadcount = (TextView)layout.findViewById(R.id.tv_sadcount);
            tv_angrycount = (TextView)layout.findViewById(R.id.tv_angrycount);


        }

    }
}

