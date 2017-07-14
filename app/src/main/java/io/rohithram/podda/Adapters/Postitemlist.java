package io.rohithram.podda.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.List;

import io.rohithram.podda.GraphGetRequest;
import io.rohithram.podda.PostActivity;
import io.rohithram.podda.Posts;
import io.rohithram.podda.RequestVolley;

/**
 * Created by rohithram on 14/7/17.
 */

public class Postitemlist {

    public static ArrayList<Posts>  postList  = new ArrayList<Posts>();

    public static ArrayList<Posts> getPostList(AccessToken key, String pageid, ProgressDialog pd, String reaction_url) {
        //fetching data only takes place if httpRequest is false
        if(!PostActivity.fbhttprequest){
        GraphGetRequest request = new GraphGetRequest();
        try {
            request.dorequest(key, pageid + "/posts", null,pd, reaction_url);
            PostActivity.fbhttprequest  = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        }

        return postList;

    }
}
