package io.rohithram.podda;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.share.widget.LikeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohithram on 23/6/17.
 */

public class PostApapter extends RecyclerView.Adapter <PostApapter.ViewHolder> {
    Context context;
    List<Posts> Postlist;
    AccessToken key;
    String pagename;
    String logo_url;

    public PostApapter(Context context, ArrayList<Posts> postList, AccessToken key, String pagename, String logo_url) {
        this.context = context;
        this.Postlist = postList;
        this.key = key;
        this.pagename = pagename;
        this.logo_url = logo_url;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pods_activity, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_org.setText(pagename);

        ImageLoader imageLoader = ImageUtil.getImageLoader(this.context);
        imageLoader.displayImage(logo_url,holder.iv_org);

        holder.tv_post_des.setText(Postlist.get(holder.getAdapterPosition()).message);

        ImageLoader imageLoader1 = ImageUtil.getImageLoader(this.context);
        imageLoader1.displayImage(Postlist.get(holder.getAdapterPosition()).img_url,holder.iv_content);

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
            iv_org = (ImageView)itemView.findViewById(R.id.iv_org_profilepic);
            iv_content = (ImageView)itemView.findViewById(R.id.iv_content);
            fblike = (LikeView)itemView.findViewById(R.id.fb_like);
            fblike.setLikeViewStyle(LikeView.Style.STANDARD);
            fblike.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
            tv_likes = (TextView)itemView.findViewById(R.id.tv_likes);


        }
    }
}

