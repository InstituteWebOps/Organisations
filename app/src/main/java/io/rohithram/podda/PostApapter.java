package io.rohithram.podda;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.widget.LikeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohithram on 23/6/17.
 */

public class PostApapter extends RecyclerView.Adapter <PostApapter.ViewHolder> {
    Context context;
    List<Posts> Postlist;
    AccessToken key;

    public PostApapter(Context context, ArrayList<Posts> postList, AccessToken key) {
        this.context = context;
        this.Postlist = postList;
        this.key = key;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pods_activity, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.i("Xscvf","Hello eneteefe");

        holder.tv_post_des.setText(Postlist.get(holder.getAdapterPosition()).message);



        ImageLoader imageLoader = ImageUtil.getImageLoader(this.context);
        imageLoader.displayImage(Postlist.get(holder.getAdapterPosition()).img_url,holder.iv_content);

        final String id = Postlist.get(holder.getAdapterPosition()).id;

        holder.tv_likes.setText(String.valueOf(Postlist.get(holder.getAdapterPosition()).count));


    }

    @Override
    public int getItemCount() {
        return Postlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_post_des,tv_org;
        public ImageView iv_content;
        public ImageView iv_org;
        public LikeView fblike;
        public TextView tv_likes;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_post_des=(TextView)itemView.findViewById(R.id.tv_post_des);
            tv_org = (TextView)itemView.findViewById(R.id.tv_org);
            iv_org = (ImageView)itemView.findViewById(R.id.iv_pod);
            iv_content = (ImageView)itemView.findViewById(R.id.iv_content);
            fblike = (LikeView)itemView.findViewById(R.id.fb_like);
            fblike.setLikeViewStyle(LikeView.Style.STANDARD);
            fblike.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
            tv_likes = (TextView)itemView.findViewById(R.id.tv_likes);


        }
    }
}

