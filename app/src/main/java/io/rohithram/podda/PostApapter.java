package io.rohithram.podda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.widget.LikeView;
import com.stfalcon.multiimageview.MultiImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import static android.R.attr.homeLayout;
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
    PopupWindow reactionpopup,multipopup;
    View layout,layout1;



    public PostApapter(Context context, ArrayList<Posts> postList, AccessToken key, String pagename, String logo_url, FragmentManager fragmentManager, VideoFragment fragment, FrameLayout layout_MainMenu, ProgressDialog pd, PopupWindow reactions_popup, View view, PopupWindow multipopup, View layout1) {
        this.context = context;
        this.Postlist = postList;
        this.key = key;
        this.pagename = pagename;
        this.logo_url = logo_url;
        this.fm = fragmentManager;
        this.fragment = fragment;
        this.mainlayout = layout_MainMenu;
        this.pd = pd;
        this.reactionpopup = reactions_popup;
        this.layout = view;
        this.multipopup = multipopup;
        this.layout1 = layout1;



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
                .placeholder(R.drawable.loading)
                .error(null)
                .crossFade(500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_org);

        holder.fblike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.fblike.setObjectIdAndType(
                        Postlist.get(holder.getAdapterPosition()).id,
                        LikeView.ObjectType.OPEN_GRAPH);
                Log.i("XDEf","successfully liked");


                new GraphRequest(
                        key,
                        "/"+Postlist.get(holder.getAdapterPosition()).id+"/likes",
                        null,
                        HttpMethod.POST,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                Log.i("SUCCEESS","successfully liked");
                            }
                        }
                ).executeAsync();

            }
        });


        if(Postlist.get(holder.getAdapterPosition()).type!=null && Postlist.get(holder.getAdapterPosition()).type.equalsIgnoreCase("photo") || Postlist.get(holder.getAdapterPosition()).type.equalsIgnoreCase("video") ) {

            if(Postlist.get(holder.getAdapterPosition()).sub_imgurls!=null && Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()==0){
            Glide.with(context).
                    load(Postlist.get(holder.getAdapterPosition()).img_url)
                    .placeholder(R.color.Imageback)
                    .crossFade(500)
                    .into(holder.iv_content);
                            }

            if(Postlist.get(holder.getAdapterPosition()).sub_imgurls!=null && Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()!=0) {

                if(Postlist.get(holder.getAdapterPosition()).sub_imgurls.size() >= 2){

                    holder.iv_content.setVisibility(View.INVISIBLE);
                    holder.iv_content.getLayoutParams().height = 0;
                    holder.iv_content.getLayoutParams().width = 0;

                    if(Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()>=3){

                    Glide.with(context).
                                load(Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(0))
                                .placeholder(R.drawable.loading)
                                .crossFade(1000)
                                .centerCrop()
                                .into(holder.iv_imag11);
                    Glide.with(context).
                            load(Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(1))
                            .placeholder(R.drawable.loading)
                            .crossFade(1000)
                            .into(holder.iv_image12);

                    Glide.with(context).
                            load(Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(2))
                            .placeholder(R.drawable.loading)
                            .crossFade(1000)
                            .into(holder.iv_image13);

                        holder.rv_gridimages.setVisibility(View.VISIBLE);

                    }

                    if(Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()==2) {

                        Glide.with(context).
                                load(Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(0))
                                .placeholder(R.drawable.loading)
                                .crossFade(500)
                                .centerCrop()
                                .into(holder.iv_image21);
                        Glide.with(context).
                                load(Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(1))
                                .placeholder(R.drawable.loading)
                                .crossFade(500)
                                .centerCrop()
                                .into(holder.iv_image22);

                        holder.rv_gridimages2.setVisibility(View.VISIBLE);


                    }

                    if(Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()==3){
                        holder.tv_nofimages.setVisibility(View.INVISIBLE);

                    }
                    else if(Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()==2){
                        holder.tv_nofimages.setVisibility(View.INVISIBLE);
                        holder.iv_image12.getLayoutParams().height = 280;

                    }
                    else if(Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()>3) {

                        holder.tv_nofimages.setText(String.valueOf(Postlist.get(holder.getAdapterPosition()).sub_imgurls.size() - 3) + "+");
                    }

                    }
                    holder.rv_gridimages2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            try{

                                    final int[] p = {0};
                                    multipopup = new PopupWindow(layout1,RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT,true);

                                    final ImageView iv_popupimage;
                                    ImageButton ibt_close,ibt_fwd,ibt_back;

                                    iv_popupimage = (ImageView)layout1.findViewById(R.id.iv_popupimage);

                                    ibt_close = (ImageButton)layout1.findViewById(R.id.ibt_close);
                                    ibt_fwd = (ImageButton)layout1.findViewById(R.id.ibt_forward);
                                    ibt_back = (ImageButton)layout1.findViewById(R.id.ibt_backward);

                                    Glide.with(context).
                                            load(Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(p[0]))
                                            .placeholder(R.color.Imageback)
                                            .crossFade(500)
                                            .into(iv_popupimage);

                                    multipopup.setTouchable(true);
                                    multipopup.setFocusable(true);
                                    multipopup.setBackgroundDrawable(new ColorDrawable(
                                            android.graphics.Color.TRANSPARENT));
                                    multipopup.setOutsideTouchable(false);



                                    new Handler().postDelayed(new Runnable(){
                                        public void run() {
                                            multipopup.showAtLocation(v,Gravity.CENTER,0,0);
                                            PostActivity.dim();
                                        }

                                    }, 200L);


                                    ibt_back.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if(p[0]==0){
                                                Toast.makeText(context, "Move Forward!", Toast.LENGTH_SHORT).show();

                                            }
                                            if(p[0]!=0){
                                                p[0] = p[0]-1;
                                                setImage(iv_popupimage,Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(p[0]));
                                            }

                                        }
                                    });


                                    ibt_fwd.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(p[0]==Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()-1){
                                                Toast.makeText(context, "That's All Buddy!", Toast.LENGTH_SHORT).show();

                                            }
                                            if(p[0]!=Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()-1){
                                                p[0] = p[0] +1;
                                                setImage(iv_popupimage,Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(p[0]));}

                                        }
                                    });

                                    ibt_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            multipopup.dismiss();
                                            PostActivity.normal();

                                        }
                                    });

                                    multipopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
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



                    holder.rv_gridimages.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(final View v) {

                            try{

                                final int[] p = {0};
                                multipopup = new PopupWindow(layout1,RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT,true);

                                final ImageView iv_popupimage;
                                ImageButton ibt_close,ibt_fwd,ibt_back;

                                iv_popupimage = (ImageView)layout1.findViewById(R.id.iv_popupimage);

                                ibt_fwd = (ImageButton)layout1.findViewById(R.id.ibt_forward);
                                ibt_close = (ImageButton)layout1.findViewById(R.id.ibt_close);
                                ibt_back = (ImageButton)layout1.findViewById(R.id.ibt_backward);

                                Glide.with(context).
                                        load(Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(p[0]))
                                        .placeholder(R.color.Imageback)
                                        .crossFade(500)
                                        .into(iv_popupimage);

                                multipopup.setTouchable(true);
                                multipopup.setFocusable(true);
                                multipopup.setElevation(32);
                                multipopup.setBackgroundDrawable(new ColorDrawable(
                                        android.graphics.Color.TRANSPARENT));
                                multipopup.setOutsideTouchable(false);



                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    multipopup.showAtLocation(v,Gravity.CENTER,0,0);
                                    PostActivity.dim();
                                }

                            }, 200L);


                                ibt_back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if(p[0]==0){
                                            Toast.makeText(context, "Move Forward!", Toast.LENGTH_SHORT).show();

                                        }
                                        if(p[0]!=0){
                                        p[0] = p[0]-1;
                                        setImage(iv_popupimage,Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(p[0]));
                                    }

                                    }
                                });


                                ibt_fwd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if(p[0]==Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()-1 ){
                                            Toast.makeText(context, "That's All Buddy", Toast.LENGTH_SHORT).show();
                                        }
                                        if(p[0]!=Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()-1){
                                            p[0] = p[0] +1;
                                            if(p[0]==Postlist.get(holder.getAdapterPosition()).sub_imgurls.size()-2){

                                            }
                                            setImage(iv_popupimage,Postlist.get(holder.getAdapterPosition()).sub_imgurls.get(p[0]));
                                        }

                                    }
                                });

                                ibt_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        multipopup.dismiss();
                                        PostActivity.normal();

                                    }
                                });

                                multipopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
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




            }

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
                holder.iv_content.getLayoutParams().height=0;
                holder.iv_content.getLayoutParams().width = 0;
            }

        holder.iv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder imagezoom = new AlertDialog.Builder(context,R.style.custom_alert_dialog);
                LayoutInflater layout = LayoutInflater.from(context);
                final View view = layout.inflate(R.layout.imagezoom, null);
                ImageView image = (ImageView)view.findViewById(R.id.iv_imagezoom);

                Glide.with(context)
                        .load(Postlist.get(holder.getAdapterPosition()).img_url)
                        .placeholder(R.color.Imageback)
                        .error(null)
                        .crossFade(1000)
                        .into(image);
                imagezoom.setView(view);
                imagezoom.show();

            }
        });



        if(Postlist.get(holder.getAdapterPosition()).message!=null){
        holder.tv_post_des.setText(Postlist.get(holder.getAdapterPosition()).message);}
        else {
            holder.tv_post_des.setVisibility(View.INVISIBLE);
            holder.tv_post_des.getLayoutParams().width = 0;
            holder.tv_post_des.getLayoutParams().height = 0;
        }


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
                                reactionpopup.showAsDropDown(v,-20,-250);
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

                       holder.iv_content.setClickable(false);

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


    private Void  setImage(ImageView image,String url){
        Glide.with(context).
                load(url)
                .placeholder(R.color.Imageback)
                .fitCenter()
                .crossFade(500)
                .into(image);
        return null;
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
        public TextView tv_likes,tv_time,tv_nofimages;
        public LinearLayout lin_emojis;
        ImageView iv_11,iv_21,iv_31,iv_41,iv_51,iv_61,iv_imag11,iv_image12,iv_image13,iv_image21,iv_image22;
        TextView tv_likecount,tv_hahacount,tv_lovecount,tv_wowcount,tv_angrycount,tv_sadcount;
        RelativeLayout rv_gridimages,rv_gridimages2;



        public ViewHolder(View itemView,View layout) {
            super(itemView);

            tv_post_des = (TextView) itemView.findViewById(R.id.tv_post_des);
            tv_org = (TextView) itemView.findViewById(R.id.tv_org);

            cv_popup = (CardView)itemView.findViewById(R.id.cv_popup);
            cv_post = (CardView) itemView.findViewById(R.id.cv_post);
            iv_org = (ImageView) itemView.findViewById(R.id.iv_org_profilepic);
            iv_content = (ImageView) itemView.findViewById(R.id.iv_content);
            fblike = (LikeView) itemView.findViewById(R.id.fb_like);


            tv_likes = (TextView) itemView.findViewById(R.id.tv_likes);
            tv_time = (TextView)itemView.findViewById(R.id.tv_time);
            iv_videocover=(ImageView)itemView.findViewById(R.id.iv_videocover);
            fl_images = (FrameLayout)itemView.findViewById(R.id.fl_images);
            lin_emojis = (LinearLayout)itemView.findViewById(R.id.lin_emojis);

            rv_gridimages = (RelativeLayout)itemView.findViewById(R.id.rv_gridimages3);
            rv_gridimages2 = (RelativeLayout)itemView.findViewById(R.id.rv_gridimages2);
            tv_nofimages = (TextView)itemView.findViewById(R.id.tv_nofimages);
            iv_imag11 = (ImageView)itemView.findViewById(R.id.iv_image11);
            iv_image12 = (ImageView)itemView.findViewById(R.id.iv_image12);
            iv_image13 = (ImageView)itemView.findViewById(R.id.iv_image13);
            iv_image21 = (ImageView)itemView.findViewById(R.id.iv_image21);
            iv_image22 = (ImageView)itemView.findViewById(R.id.iv_image22);



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

