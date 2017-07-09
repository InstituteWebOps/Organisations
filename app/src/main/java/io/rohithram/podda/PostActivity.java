package io.rohithram.podda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.toolbox.ImageLoader;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookSdk;

import java.util.ArrayList;



public class PostActivity extends AppCompatActivity implements VideoFragment.OnFragmentInteractionListener {

    TextView tv_post_des;
    ArrayList<Posts> postList;
    AccessToken key;
    public ImageView iv_content;
    public RecyclerView rv_list;
    public PostApapter adapter;
    public ProgressBar progressBar;
    Context context;
    static FrameLayout layout_MainMenu;
    PopupWindow reactions_popup;
    CardView  containerLayout;


    @Override
    protected void onCreate(Bundle onRetainNonConfigurationChanges) {
        super.onCreate(onRetainNonConfigurationChanges);
        setContentView(R.layout.posts_activity);

        layout_MainMenu = (FrameLayout) findViewById( R.id.mainview);
        layout_MainMenu.getForeground().setAlpha( 0);

        containerLayout = (CardView) findViewById(R.id.cv_popup);

        reactions_popup = new PopupWindow(PostActivity.this);

        reactions_popup.setContentView(containerLayout);

        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater)PostActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.react_popup,(ViewGroup)findViewById(R.id.cv_popup));

        String apptoken = getString(R.string.Apptoken);
        final String pageid;
        String appid = getString(R.string.facebook_app_id);
        String logo_url;
        String Pagename;
        String reaction_url = getString(R.string.reaction_query);

        Intent i = getIntent();
        pageid = i.getStringExtra("pageid");
        Pagename = i.getStringExtra("pagename");
        logo_url = i.getStringExtra("logo_url");

        final ProgressDialog pd = new ProgressDialog(PostActivity.this);
        pd.setMessage("Loading " + Pagename);
        pd.show();

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        android.support.v4.app.FragmentManager fragmentManager = null;
        VideoFragment fragment = null;
        Bundle arg = new Bundle();
        arg.putString("intialvalue","initialisable");

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (onRetainNonConfigurationChanges != null) {
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

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setItemAnimator(new DefaultItemAnimator());

        tv_post_des = (TextView) findViewById(R.id.tv_post_des);
        iv_content = (ImageView) findViewById(R.id.iv_content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        FacebookSdk.sdkInitialize(this.getApplicationContext());


        postList = new ArrayList<>();

        adapter = new PostApapter(PostActivity.this,postList, key, Pagename, logo_url, fragmentManager, fragment,layout_MainMenu,pd,reactions_popup,layout);
        rv_list.setAdapter(adapter);

        /* make the API call */

        key = new AccessToken(apptoken, appid, getString(R.string.userid), null, null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE, null, null);

        GraphGetRequest request = new GraphGetRequest();
        try {
            request.dorequest(key, pageid + "/posts", null,adapter, postList,pd,reaction_url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void dim(){
        layout_MainMenu.getForeground().setAlpha(160);
    }

    public static void normal(){
        layout_MainMenu.getForeground().setAlpha(0);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}




