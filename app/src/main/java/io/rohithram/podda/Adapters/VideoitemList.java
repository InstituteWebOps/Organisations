package io.rohithram.podda.Adapters;



import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;

import io.rohithram.podda.MainActivity;
import io.rohithram.podda.OrganPage;
import io.rohithram.podda.RequestVolley;
import io.rohithram.podda.VideoAsyncTask;

import java.util.ArrayList;

/**
 * Created by Srikanth on 6/22/2017.
 */

public class VideoitemList  {

    public static ArrayList<VideoItem> videoList = new ArrayList<VideoItem>();

    public static ArrayList<VideoItem> getVideoList(String channelID, Context ctx, Boolean isYoutube, ViewPager viewPager, TabLayout tabLayout) {
        //fetching data only takes place if httpRequest is false
        Log.i("Xfffff",channelID);
        RequestVolley obj = new RequestVolley();
        obj.request(channelID,ctx,isYoutube,viewPager,tabLayout);
        return videoList;

    }

}
