package io.rohithram.podda;

import android.content.Context;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    TextView tv_post_des;

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
        String pageid = getString(R.string.pageid);
        String appid = getString(R.string.facebook_app_id);


        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setLayoutManager(new LinearLayoutManager(this));






        tv_post_des = (TextView) findViewById(R.id.tv_post_des);
        iv_content = (ImageView) findViewById(R.id.iv_content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        postList = new ArrayList<>();
        adapter = new PostApapter(MainActivity.this,postList, key);
        rv_list.setAdapter(adapter);






        /* make the API call */

        key = new AccessToken(apptoken, appid, getString(R.string.userid), null, null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE, null, null);

        GraphGetRequest request = new GraphGetRequest();
        try {
            request.dorequest(context, key, pageid + "/posts", null, progressBar, rv_list,adapter,postList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   }




