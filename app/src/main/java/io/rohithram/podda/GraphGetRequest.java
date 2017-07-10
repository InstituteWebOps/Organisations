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


    public Void dorequest(final AccessToken key, String url, Bundle params, final PostApapter adapter, final ArrayList<Posts> Postlist, final ProgressDialog pd, final String reaction_url)  {

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
                                if(postjs.has("id")) {

                                    final Posts post = new Posts(postjs.getString("id"));

                                    if (postjs.has("message")) {
                                        post.message = postjs.getString("message");
                                    }

                                    post.created_time = postjs.getString("created_time");
                                    new GraphRequest(key,
                                            post.id + "/?fields=" + reaction_url + ",type,source,full_picture,attachments,reactions.summary(true){total_count}",
                                            null,
                                            HttpMethod.GET,
                                            new GraphRequest.Callback() {
                                                @Override
                                                public void onCompleted(GraphResponse response) {
                                                    try {
                                                        JSONObject jsonresponse2 = new JSONObject(String.valueOf(response.getJSONObject()));
                                                        post.type = jsonresponse2.getString("type");
                                                        if (!post.type.equalsIgnoreCase("status")) {
                                                            if (jsonresponse2.has("full_picture")) {
                                                                post.img_url = jsonresponse2.getString("full_picture");
                                                            }
                                                            if (jsonresponse2.getString("type").equals("video")) {
                                                                post.vid_url = jsonresponse2.getString("source");
                                                            }
                                                        }
                                                        if(jsonresponse2.has("attachments")){
                                                        JSONObject attachments = jsonresponse2.getJSONObject("attachments");
                                                        JSONArray attach_data = attachments.getJSONArray("data");
                                                        if (attach_data.length() >= 0) {
                                                            JSONObject attachmentsjson = attach_data.getJSONObject(0);
                                                            if (attachmentsjson.has("subattachments")) {
                                                                JSONObject subattachments = attachmentsjson.getJSONObject("subattachments");
                                                                if (subattachments.has("data")) {
                                                                    JSONArray subdata = subattachments.getJSONArray("data");
                                                                    for (int k = 0; k < subdata.length(); k++) {
                                                                        JSONObject jsoni = subdata.getJSONObject(k);
                                                                        if (jsoni.has("media")) {
                                                                            JSONObject mediajson = jsoni.getJSONObject("media");
                                                                            JSONObject imagejson = mediajson.getJSONObject("image");
                                                                            post.sub_imgurls.add(imagejson.getString("src"));
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                        }
                                                        JSONObject Likejson = jsonresponse2.getJSONObject("like");
                                                        if (Likejson.has("summary")) {
                                                            JSONObject like = Likejson.getJSONObject("summary");
                                                            post.like_count = like.getInt("total_count");
                                                        }
                                                        JSONObject hahajson = jsonresponse2.getJSONObject("haha");
                                                        if (hahajson.has("summary")) {
                                                            JSONObject haha = hahajson.getJSONObject("summary");
                                                            post.haha_count = haha.getInt("total_count");
                                                        }

                                                        JSONObject wowjson = jsonresponse2.getJSONObject("wow");
                                                        if (wowjson.has("summary")) {
                                                            JSONObject wow = wowjson.getJSONObject("summary");
                                                            post.wow_count = wow.getInt("total_count");
                                                        }

                                                        JSONObject angryjson = jsonresponse2.getJSONObject("angry");
                                                        if (angryjson.has("summary")) {
                                                            JSONObject angry = angryjson.getJSONObject("summary");
                                                            post.angry_count = angry.getInt("total_count");
                                                        }

                                                        JSONObject sadjson = jsonresponse2.getJSONObject("sad");
                                                        if (sadjson.has("summary")) {
                                                            JSONObject sad = sadjson.getJSONObject("summary");
                                                            post.sad_count = sad.getInt("total_count");
                                                        }

                                                        JSONObject lovejson = jsonresponse2.getJSONObject("love");
                                                        if (lovejson.has("summary")) {
                                                            JSONObject love = lovejson.getJSONObject("summary");
                                                            post.love_count = love.getInt("total_count");
                                                        }

                                                        JSONObject reactionsjson = jsonresponse2.getJSONObject("reactions");
                                                        if (reactionsjson.has("summary")) {
                                                            JSONObject likes = reactionsjson.getJSONObject("summary");
                                                            post.count = likes.getInt("total_count");
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } finally {
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
