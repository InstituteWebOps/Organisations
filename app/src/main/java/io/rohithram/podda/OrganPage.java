package io.rohithram.podda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.google.android.youtube.player.YouTubeBaseActivity;

import io.rohithram.podda.Adapters.VideoitemList;


public class OrganPage extends YouTubeBaseActivity {

    public static RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    public static VideoAdapter videoAdapter;
    public static ProgressBar progressBar;
    public static String CHANNELID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_fragment);
        Intent intent = getIntent();
        CHANNELID = intent.getStringExtra(DeveloperKey.EXTRA_MESSAGE); //we get the channelID from MainActivity, and this used in
                                                                       //VideoitemList to fetch data from internet
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
      //  progressBar.setVisibility(View.GONE); //By default this is "gone"  ,when AsyncTask is started it is made visilble and later it's gone

        /*recyclerView = (RecyclerView) findViewById(R.id.videorv);
        layoutManager = new LinearLayoutManager(this);

        //videoAdapter = new VideoAdapter(VideoitemList.getVideoList(OrganPage.this),OrganPage.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(videoAdapter);*/
    }
}
