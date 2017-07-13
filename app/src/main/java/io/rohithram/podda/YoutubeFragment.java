package io.rohithram.podda;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.rohithram.podda.Adapters.VideoItem;
import io.rohithram.podda.Adapters.VideoitemList;

/**
 * Created by rohithram on 13/7/17.
 */

public class YoutubeFragment extends Fragment{

    ArrayList <VideoItem> response;
    static VideoAdapter vadapter;
    String channelID;


    public YoutubeFragment() {
        // Required empty public constructor
    }

    public void setResponse(ArrayList<VideoItem> response,String channelID) {
        this.channelID = channelID;
        this.response = response;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.youtube_fragment
                , container, false);
        PostActivity obj = new PostActivity();
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.videorv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        vadapter = new VideoAdapter(response,context);
        recyclerView.setAdapter(vadapter);
        Log.i("Xsdsds",String.valueOf(VideoitemList.videoList));
        return view;
    }
}
