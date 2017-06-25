package io.rohithram.podda;

import android.os.AsyncTask;
import android.util.Log;

import com.facebook.FacebookSdk;

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
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by rohithram on 22/6/17.
 */

public class NetworkHelper {

    public static String fetchData(String requestUrl, String apikey, String pageid){
        URL url = null;
        try {
            url = new URL(requestUrl+pageid+"/posts?access_token"+apikey);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String response = null;

        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }





    private static String makeHttpRequest(URL url) throws IOException{
        String response = "";

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000000);
            urlConnection.setReadTimeout(1500000);
            urlConnection.setDoInput(true);
            urlConnection.setInstanceFollowRedirects( true );
            urlConnection.connect();

                inputStream = urlConnection.getInputStream();
                response = getStringResponse(inputStream);
                Log.i("zXSASADsbbdada",response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }

        return response;
    }

    private static String getStringResponse(InputStream inputStream) throws IOException{

        StringBuilder builder = new StringBuilder();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();

        while (line != null){
            builder.append(line);
            line = bufferedReader.readLine();
        }

        return builder.toString();
    }

  /*  private static List<Matches> extractdata(String response) {
        List<Matches> matchesList = new ArrayList<>();


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray matches = jsonObject.getJSONArray("matches");

            for (int i = 0; i < matches.length(); ++i){
                JSONObject data = matches.getJSONObject(i);
                Log.e("logcat1",data.getString("team-1"));

                Matches match = new Matches(
                        data.getDouble("unique_id"),
                        data.getString("team-1"),
                        data.getString("team-2")
                );

                matchesList.add(match);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return matchesList;
    }*/
}
