package io.rohithram.Organisations;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookSdk;

import java.util.ArrayList;

import io.rohithram.Organisations.Adapters.VideoItem;


public class PostActivity extends AppCompatActivity implements VideoFragment.OnFragmentInteractionListener{

    public  static ArrayList<Posts> postList;
    public  AccessToken key;
    public  FrameLayout layout_MainMenu;
    public PopupWindow reactions_popup,multipopup;
    public CardView  containerLayout;
    public RelativeLayout containerLayout2;
    public String logo_url;
    public String Pagename;
    public android.support.v4.app.FragmentManager fragmentManager;
    public VideoFragment fragment;
    public View layout1,layout;
    public ProgressDialog pd;
    static Boolean  isYoutube;
    public  static String channelID ;
    public static ArrayList<VideoItem> videoList;
    static OrgPagerAdapter pageadapter;
    public static  OrgPagerAdapter pageadapter1;
    static ViewPager viewPager;
    public static TabLayout tabLayout;

    String pageid;
    String appid ;
    String reaction_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.GRAVITY_CENTER);

        String apptoken = getString(R.string.Apptoken);

        appid = getString(R.string.facebook_app_id);
        reaction_url = getString(R.string.reaction_query);

        Intent i = getIntent();
        isYoutube = i.getBooleanExtra("isyoutube", false);
        if (isYoutube) {
            channelID = i.getStringExtra("ChannelId");
        }
        pageid = i.getStringExtra("pageid");
        Pagename = i.getStringExtra("pagename");
        logo_url = i.getStringExtra("logo_url");

        containerLayout = (CardView) findViewById(R.id.cv_popup);
        reactions_popup = new PopupWindow(PostActivity.this);
        reactions_popup.setContentView(containerLayout);
        containerLayout2 = (RelativeLayout) findViewById(R.id.rl_multipopup);
        multipopup = new PopupWindow(PostActivity.this);
        multipopup.setContentView(containerLayout);
        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) PostActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layout = inflater.inflate(R.layout.react_popup, (ViewGroup) findViewById(R.id.cv_popup));
        layout1 = inflater.inflate(R.layout.multimagepopup, (ViewGroup) findViewById(R.id.rl_multipopup));

        pd = new ProgressDialog(PostActivity.this);
        pd.setMessage("Loading " + Pagename);
        pd.setCancelable(false);
        pd.show();

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout


        fragmentManager = null;
        fragment = null;
        Bundle arg = new Bundle();
        arg.putString("intialvalue", "initialisable");

        View layout3 = inflater.inflate(R.layout.fb_fragment, (ViewGroup) findViewById(R.id.mainview));

        if (layout3.findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                    return;
                }
            // Create a new Fragment to be placed in the activity layout
            fragment = new VideoFragment();
            fragment.setArguments(arg);
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentManager = getSupportFragmentManager();
            }

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        postList = new ArrayList<>();
        videoList = new ArrayList<>();

        /* make the API call */

        key = new AccessToken(apptoken, appid, getString(R.string.userid), null, null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE, null, null);
        getdata();
        callviewpager();

    }

    public void getdata(){

        if(isYoutube){
            RequestVolley obj = new RequestVolley();
            obj.request(channelID, PostActivity.this, videoList,isYoutube, viewPager, tabLayout);
        }
        GraphGetRequest request = new GraphGetRequest();
        try {
            request.dorequest(key, pageid + "/posts", null,postList,pd, reaction_url);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public  void callviewpager(){
        if(videoList!=null && postList!=null ){
            if (isYoutube) {
                setupViewPager(viewPager);
                if (tabLayout != null) {
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.setVisibility(View.VISIBLE);
                }
            }
            else if(!isYoutube) {
                tabLayout.setVisibility(View.INVISIBLE);
                tabLayout.getLayoutParams().width = 0;
                tabLayout.getLayoutParams().height = 0;
                setupViewPagerNoYoutube(viewPager);

            }
        }
    }


    public void setupViewPager(final ViewPager mviewPager) {

        pageadapter = new OrgPagerAdapter(getSupportFragmentManager());
        Fbfragment fbfragment = new Fbfragment();
        YoutubeFragment youtubeFragment = new YoutubeFragment();
        fbfragment.setResponse(postList,viewPager);
        youtubeFragment.setResponse(videoList,channelID,viewPager);
        pageadapter.addFragment(fbfragment,"Feed");
        pageadapter.addFragment(youtubeFragment,"Videos");
        mviewPager.setAdapter(pageadapter);
        pageadapter.notifyDataSetChanged();
    }

    public void setupViewPagerNoYoutube(ViewPager mviewPager) {

        pageadapter1 = new OrgPagerAdapter(getSupportFragmentManager());
        Fbfragment fbfragment = new Fbfragment();
        fbfragment.setResponse(postList, viewPager);
        pageadapter1.addFragment(fbfragment,"Feed");
        mviewPager.setAdapter(pageadapter1);
        pageadapter1.notifyDataSetChanged();
    }

    public void dim(){
        //layout_MainMenu.getForeground().setAlpha(160);
    }


    public void normal(){
      //  layout_MainMenu.getForeground().setAlpha(0);
    }
}




