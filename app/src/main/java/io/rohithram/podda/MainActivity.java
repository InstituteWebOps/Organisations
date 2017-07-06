package io.rohithram.podda;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookSdk;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements VideoFragment.OnFragmentInteractionListener{

    TextView tv_post_des;
    ArrayList<Posts> postList;
    AccessToken key;
    public ImageView iv_content;
    public RecyclerView rv_list;
    public PostApapter adapter;
    public ProgressBar progressBar;
    Context context;
    VideoView vid_post;
    static FrameLayout layout_MainMenu;


    @Override
    protected void onCreate(Bundle onRetainNonConfigurationChanges) {
        super.onCreate(onRetainNonConfigurationChanges);
        setContentView(R.layout.posts_activity);



        layout_MainMenu = (FrameLayout) findViewById( R.id.mainview);
        layout_MainMenu.getForeground().setAlpha( 0);


        String apptoken = getString(R.string.Apptoken);
        final String pageid;
        String appid = getString(R.string.facebook_app_id);
        String logo_url;
        String Pagename;


        Log.i("fsfsa", "Good bye");



        Intent i = getIntent();
        pageid = i.getStringExtra("pageid");
        Pagename = i.getStringExtra("pagename");
        logo_url = i.getStringExtra("logo_url");


// Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        android.support.v4.app.FragmentManager fragmentManager = null;
        VideoFragment fragment = null;
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (onRetainNonConfigurationChanges != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            fragment = new VideoFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments

            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentManager = getSupportFragmentManager();
        }


        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setItemAnimator(new DefaultItemAnimator());

        tv_post_des = (TextView) findViewById(R.id.tv_post_des);
        iv_content = (ImageView) findViewById(R.id.iv_content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        postList = new ArrayList<>();
        adapter = new PostApapter(MainActivity.this, postList, key, Pagename, logo_url, fragmentManager, fragment,layout_MainMenu);
        rv_list.setAdapter(adapter);

        /* make the API call */

        key = new AccessToken(apptoken, appid, getString(R.string.userid), null, null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE, null, null);

        GraphGetRequest request = new GraphGetRequest();
        try {
            request.dorequest(context, key, pageid + "/posts", null, progressBar, adapter, postList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public static void dim(){
        layout_MainMenu.getForeground().setAlpha(160);
    }
    public static void normal(){
        layout_MainMenu.getForeground().setAlpha(0);
    }
}




