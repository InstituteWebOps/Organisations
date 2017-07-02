package io.rohithram.podda;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.nostra13.universalimageloader.core.ImageLoader;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    TextView tv_post_des,tv_org;
    ImageView iv_org_profilepic;
    ArrayList<Posts> postList;
    AccessToken key;
    public ImageView iv_content;
    public RecyclerView rv_list;
    public PostApapter adapter;
    public ProgressBar progressBar;
    Context context;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_activity);
        String apptoken = getString(R.string.Apptoken);
        String pageid;
        String appid = getString(R.string.facebook_app_id);
        String logo_url;
        String Pagename;

        Intent i = getIntent();
        pageid = i.getStringExtra("pageid");
        Pagename = i.getStringExtra("pagename");
        logo_url = i.getStringExtra("logo_url");



        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setLayoutManager(new LinearLayoutManager(this));

        tv_post_des = (TextView) findViewById(R.id.tv_post_des);
        iv_content = (ImageView) findViewById(R.id.iv_content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tv_org = (TextView) findViewById(R.id.tv_org);
        iv_org_profilepic = (ImageView) findViewById(R.id.iv_org_profilepic);

        tv_org.setText(Pagename);

        ImageLoader imageLoader = ImageUtil.getImageLoader(this.context);
        imageLoader.displayImage(logo_url,iv_org_profilepic);


        FacebookSdk.sdkInitialize(this.getApplicationContext());

        postList = new ArrayList<>();
        adapter = new PostApapter(MainActivity.this,postList, key);
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

}




