package io.rohithram.podda;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookSdk;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    TextView tv_post_des;
    ArrayList<Posts> postList;
    AccessToken key;
    public ImageView iv_content;
    public RecyclerView rv_list;
    public PostApapter adapter;
    public ProgressBar progressBar;
    Context context;

    PopupWindow popUpWindow;
    CardView containerLayout;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_activity);

        containerLayout = (CardView)findViewById(R.id.cv_popup);
        popUpWindow = new PopupWindow(MainActivity.this);
        popUpWindow.setContentView(containerLayout);

        String apptoken = getString(R.string.Apptoken);
        final String pageid;
        String appid = getString(R.string.facebook_app_id);
        String logo_url;
        String Pagename;

        Log.i("fsfsa","Good bye");

        Intent i = getIntent();
        pageid = i.getStringExtra("pageid");
        Pagename = i.getStringExtra("pagename");
        logo_url = i.getStringExtra("logo_url");




        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setItemAnimator(new DefaultItemAnimator());

        tv_post_des = (TextView) findViewById(R.id.tv_post_des);
        iv_content = (ImageView) findViewById(R.id.iv_content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        postList = new ArrayList<>();
        adapter = new PostApapter(MainActivity.this,postList, key,Pagename,logo_url);
        rv_list.setAdapter(adapter);

        /* make the API call */

        key = new AccessToken(apptoken, appid, getString(R.string.userid), null, null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE, null, null);

        GraphGetRequest request = new GraphGetRequest();
        try {
            request.dorequest(context, key, pageid + "/posts", null, progressBar,adapter,postList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initiatePopupWindow(final View v, final String video_url) {

        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity

            popUpWindow.setTouchable(true);
            popUpWindow.setFocusable(true);

            LayoutInflater inflater = (LayoutInflater)MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.cv_popup));

            final Button bt_dismiss_pop,bt_pop_play;
            final VideoView vid_post;

            bt_dismiss_pop = (Button)layout.findViewById(R.id.bt_pop_dismiss);
            vid_post = (VideoView)layout.findViewById(R.id.vid_post);
            bt_pop_play = (Button)layout.findViewById(R.id.bt_pop_play);

            popUpWindow = new PopupWindow(layout,CardView.LayoutParams.WRAP_CONTENT,CardView.LayoutParams.WRAP_CONTENT,true);
            // display the popup in the center
            new Handler().postDelayed(new Runnable(){

                public void run() {
                    popUpWindow.showAtLocation( v, Gravity.CENTER, 0, 0);
                }

            }, 200L);

            bt_dismiss_pop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUpWindow.dismiss();


                }
            });
            bt_pop_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(video_url);
                    vid_post.setVideoURI(uri);
                    vid_post.start();


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}




