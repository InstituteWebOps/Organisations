package io.rohithram.podda;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import io.rohithram.podda.Adapters.VideoItem;
import io.rohithram.podda.Adapters.VideoitemList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Srikanth on 6/23/2017.
 */


public class VideoAsyncTask extends AsyncTask<String,String,String> {

    private VideoItem vi = null;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        VideoitemList.videoList.clear();  // when ArrayList is filled with data ,it is made sure to be completely empty
        //OrganPage.progressBar.setVisibility(View.VISIBLE);
        //OrganPage.recyclerView.setVisibility(View.INVISIBLE);

    }




    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://www.googleapis.com/youtube/v3/search?key="+DeveloperKey.DEVELOPER_KEY+"&channelId="+params[0]+"&part=snippet,id&order=date&maxResults=50");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream stream =urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }
            JSONObject videoIDJson = new JSONObject(buffer.toString());

            JSONArray items = videoIDJson.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                JSONObject item = items.getJSONObject(i);
                JSONObject id = item.getJSONObject("id");


                String kind = id.getString("kind");
                if(kind.equals("youtube#playlist")){  // If want to add playlists ,then there some code to be added here.

                    continue;
                }
                else {
                    JSONObject snippet = item.getJSONObject("snippet");
                    String title= snippet.getString("title");
                    String videoId = id.getString("videoId");
                    vi = new VideoItem();
                    vi.videoId = videoId;
                    vi.videoTitle = title;
                    if(!VideoitemList.videoList.contains(vi)) VideoitemList.videoList.add(vi);

                }
            }
            /*The no of results per jsonObject is hardcoded to 50(it's the maximum we can get)
              so there is a "nextPagetoken" included if there are more results, this function(fetchdatafromNextPage()) takes care of them of them*/
            if(videoIDJson.has("nextPageToken")) {
                String nextpage = videoIDJson.getString("nextPageToken");
                fetchDatafromNextPage(nextpage);
            }

            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        OrganPage.recyclerView.setVisibility(View.VISIBLE);
        OrganPage.progressBar.setVisibility(View.GONE);
        OrganPage.videoAdapter.notifyDataSetChanged();

    }

    private void fetchDatafromNextPage(String next) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://www.googleapis.com/youtube/v3/search?key=AIzaSyAU0d027wNVn0IQGbLjxQ21AWH906poSKM&channelId=UCSsS3HSbI7fCu0pGq0XFgxw&part=snippet,id&order=date&maxResults=50&pageToken="+next);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream stream =urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }
            JSONObject videoIDJson = new JSONObject(buffer.toString());



            JSONArray items = videoIDJson.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                JSONObject item = items.getJSONObject(i);
                JSONObject id = item.getJSONObject("id");
                String kind = id.getString("kind");
                if(kind.equals("youtube#playlist")){
                                                        // If want to add playlists ,then there some code to be added here.

                    continue;
                }
                else {
                    JSONObject snippet = item.getJSONObject("snippet");
                    String title= snippet.getString("title");
                    String videoId = id.getString("videoId");

                    vi = new VideoItem();
                    vi.videoId = videoId;
                    vi.videoTitle = title;
                    if(!VideoitemList.videoList.contains(vi)) VideoitemList.videoList.add(vi);
                }
            }
            if(videoIDJson.has("nextPageToken")) {
                String nextpage = videoIDJson.getString("nextPageToken");
                fetchDatafromNextPage(nextpage);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}






