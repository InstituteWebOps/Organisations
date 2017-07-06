package io.rohithram.podda;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by rohithram on 28/6/17.
 */

public class GraphGetRequest  {

    public Void dorequest(final AccessToken key, String url, Bundle params,final PostApapter adapter, final ArrayList<Posts> Postlist, final ProgressDialog pd)  {
        final GraphRequest request = new GraphRequest(
                key,
                url,
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            final JSONObject jsonresponse = new JSONObject(String.valueOf(response.getJSONObject()));
                            JSONArray postsjson = jsonresponse.getJSONArray("data");
                            for (int i = 0; i < postsjson.length(); i++) {
                                JSONObject postjs = postsjson.getJSONObject(i);
                                if (postjs.has("message")) {
                                    final Posts post = new Posts(postjs.getString("message"), postjs.getString("id"));
                                    post.created_time = postjs.getString("created_time");
                                    new GraphRequest(key,
                                            post.id + "/?fields=type,source,full_picture,attachments,reactions.summary(true){total_count}",
                                            null,
                                            HttpMethod.GET,
                                            new GraphRequest.Callback() {
                                                @Override
                                                public void onCompleted(GraphResponse response) {
                                                    try {
                                                        JSONObject jsonresponse2 = new JSONObject(String.valueOf(response.getJSONObject()));
                                                        post.type = jsonresponse2.getString("type");
                                                        if(jsonresponse2.has("full_picture")){
                                                            post.img_url = jsonresponse2.getString("full_picture");
                                                        }
                                                        if(jsonresponse2.getString("type").equals("video")){
                                                                post.vid_url = jsonresponse2.getString("source");
                                                            }
                                                        /*if(jsonresponse2.has("attachments")){
                                                        JSONObject attachments = jsonresponse2.getJSONObject("attachments");
                                                        JSONArray attach_data = attachments.getJSONArray("data");
                                                        if (attach_data.length() > 0) {
                                                            JSONObject attach_dataJSONObject = attach_data.getJSONObject(0);
                                                            if (attach_dataJSONObject.has("media")) {
                                                                JSONObject mediajson = attach_dataJSONObject.getJSONObject("media");
                                                                if (mediajson.has("image")) {
                                                                    JSONObject imagejson = mediajson.getJSONObject("image");
                                                                    post.img_url = imagejson.getString("src");
                                                                    Log.i("Tsfff",String.valueOf(post.img_url));

                                                                }
                                                            }
                                                        }}*/
                                                        JSONObject likesjson = jsonresponse2.getJSONObject("reactions");
                                                        if (likesjson.has("summary")) {
                                                            JSONObject likes = likesjson.getJSONObject("summary");
                                                            post.count = likes.getInt("total_count");
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }finally {
                                                        pd.dismiss();
                                                        Postlist.add(post);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }).executeAsync();

                                }
                            }
                        }catch (JSONException e) {
                                e.printStackTrace();
                        }
                    }
                });
        request.executeAsync();
    return null;
    }

}
