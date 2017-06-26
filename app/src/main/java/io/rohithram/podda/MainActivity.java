package io.rohithram.podda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TextView tv_post_des;

    ArrayList<Posts> postList;
    AccessToken key;
    long index;
    public ImageView iv_content;
    RecyclerView rv_list;
    PostApapter adapter;
    public ProgressBar progressBar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_activity);
        String apptoken = getString(R.string.Apptoken);
        String pageid = getString(R.string.pageid);
        String appid = getString(R.string.facebook_app_id);


        rv_list = (RecyclerView)findViewById(R.id.rv_list);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setLayoutManager(new LinearLayoutManager(this));


        tv_post_des = (TextView)findViewById(R.id.tv_post_des);
        iv_content = (ImageView)findViewById(R.id.iv_content);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        FacebookSdk.sdkInitialize(this.getApplicationContext());


        postList = new ArrayList<>();



        /* make the API call */
        key = new AccessToken(apptoken,appid,getString(R.string.userid), null, null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE,null, null);

        new GraphRequest(
                key,
                "/"+pageid+"/posts",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        try {

                            JSONObject start = new JSONObject(String.valueOf(response.getJSONObject()));
                            JSONArray postsjson = start.getJSONArray("data");
                            index = postsjson.length();
                            for(int i=0;i<postsjson.length();i++){
                                JSONObject postjs = postsjson.getJSONObject(i);
                                if(postjs.has("message")) {
                                    final Posts post = new Posts(postjs.getString("message"),
                                            postjs.getString("id"));

                                    new GraphRequest(
                                            key,
                                            post.id + "/attachments",
                                            null,
                                            HttpMethod.GET,
                                            new GraphRequest.Callback() {
                                                public void onCompleted(GraphResponse response) {
                                                    try {
                                                        JSONObject resjson = new JSONObject(String.valueOf(response.getJSONObject()));
                                                        JSONArray attach_data = resjson.getJSONArray("data");
                                                        if (attach_data.length() > 0) {
                                                            JSONObject attach_dataJSONObject = attach_data.getJSONObject(0);
                                                            if(attach_dataJSONObject.has("media")){
                                                            JSONObject mediajson = attach_dataJSONObject.getJSONObject("media");
                                                            JSONObject imagejson = mediajson.getJSONObject("image");
                                                            post.img_url = imagejson.getString("src");
                                                        }
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }

                                            }).executeAsync();

                                    GraphRequest request;

                                    /* make the 11API call */
                                    request =
                                            new GraphRequest(
                                                    key,
                                                    post.id + "/reactions?summary=true",
                                                    null,
                                                    HttpMethod.GET,
                                                    new GraphRequest.Callback() {
                                                        public void onCompleted(GraphResponse response) {
                                /* handle the result */
                                                            try {
                                                                JSONObject likesjson = new JSONObject(String.valueOf(response.getJSONObject()));
                                                                if(likesjson.has("summary")) {
                                                                    JSONObject likes = likesjson.getJSONObject("summary");
                                                                    post.count = likes.getInt("total_count");
                                                                }


                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }


                                                        }
                                                    }
                                            );
                                    request.executeAsync();
                                    postList.add(post);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                            progressBar.setVisibility(View.INVISIBLE);
                            adapter = new PostApapter(MainActivity.this,postList,key);
                            rv_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }

                    }

                }

        ).executeAsync();
    }
}


