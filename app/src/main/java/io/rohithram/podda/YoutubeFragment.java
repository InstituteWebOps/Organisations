package io.rohithram.podda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.rohithram.podda.Adapters.VideoItem;

/**
 * Created by rohithram on 13/7/17.
 */

public class YoutubeFragment extends Fragment {

    ArrayList<VideoItem> response1;
    static VideoAdapter vadapter;
    String channelID;
    ViewPager viewPager;
    RecyclerView recyclerView;


    public YoutubeFragment() {
        //Log.i("DDDDDD",channelID);

        // Required empty public constructor
    }

    public void setResponse(ArrayList<VideoItem> response, String channelID, ViewPager viewPager) {
        this.channelID = channelID;
        Log.i("DDDDDD", channelID);
        this.response1 = response;
        this.viewPager = viewPager;
        // Log.i("DFEFFX", String.valueOf(response1.get(4).channelTitle));
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
        recyclerView = (RecyclerView) view.findViewById(R.id.videorv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        vadapter = new VideoAdapter(response1, context);
        recyclerView.setAdapter(vadapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vadapter = new VideoAdapter(response1,getContext());
        //Log.i("Xsdsds", String.valueOf(V.videoList.size()));

        recyclerView.setAdapter(vadapter);
        vadapter.notifyDataSetChanged();
    }
}
