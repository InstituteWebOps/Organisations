package io.rohithram.Organisations;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import io.rohithram.Organisations.Adapters.VideoItem;


import java.util.ArrayList;

/**
 * Created by Srikanth on 6/22/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private ArrayList<VideoItem> videoList;
    Context context;

    //public static ArrayList<Integer> playlistno = new ArrayList<>();  //Required if we want to display playlist thumbnails also
    YouTubeThumbnailLoader youTubeThumbnailLoader;

    public VideoAdapter(ArrayList<VideoItem> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;

    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.videoitem, parent, false);
        return new VideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.VideoViewHolder holder, final int position) {

        holder.videoTitle.setText(videoList.get(holder.getAdapterPosition()).videoTitle);

        holder.tv_channel.setText(videoList.get(holder.getAdapterPosition()).channelTitle);

        String url = "https://img.youtube.com/vi/";
        String url_2 = "/0.jpg";

        Glide.with(context).
                load(url+videoList.get(position).videoId+url_2)
                .placeholder(R.drawable.loading)
                .crossFade(500)
                .centerCrop()
                .into(holder.thumbnailView);

        //This onClickListner for thumnailview alone however onClickListner for whole cardview is also included

        holder.thumbnailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;

                //********Required for playlist thumbnails
                /*if(playlistno.contains(position))
                    intent = YouTubeIntents.createPlayPlaylistIntent(context,videoList.get(position).videoId);
                else*/

                intent = YouTubeIntents.createPlayVideoIntentWithOptions(context,videoList.get(holder.getAdapterPosition()).videoId,true,false);
                context.startActivity(intent);

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                //********Required for playlist thumbnails
                /*if(playlistno.contains(position))
                    intent = YouTubeIntents.createPlayPlaylistIntent(context,videoList.get(position).videoId);
                else*/
                intent = YouTubeIntents.createPlayVideoIntentWithOptions(context,videoList.get(holder.getAdapterPosition()).videoId,true,false);
                context.startActivity(intent);
            }
        });

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
        return videoList.size() ;
    }


    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        public YouTubeThumbnailView thumbnailView;
        public TextView videoTitle;
        public CardView cardView;
        public TextView tv_channel;
        public VideoViewHolder(View itemView) {
            super(itemView);

            thumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.thumbnailview);
            cardView = (CardView) itemView.findViewById(R.id.video_cardview);
            videoTitle = (TextView) itemView.findViewById(R.id.video_title);
            tv_channel = (TextView)itemView.findViewById(R.id.tv_channel);
        }
    }
}
