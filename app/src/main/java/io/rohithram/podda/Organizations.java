package io.rohithram.podda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Organizations extends AppCompatActivity {

    TextView tv_org_name;
    List<OrganisationObject> orgsList;
    public ImageView iv_org_logo;
    public RecyclerView rv_org_list;
    public OrganisationAdapter adapter;
    String[] PagesList;
    Context context;
    AccessToken key;
    final String yt_url = "https://www.googleapis.com/youtube/v3/channels?part=id&forUsername=";
    final String yt_url2 ="&key=";
    OrganisationObject org = null;

    @Override
    protected void onCreate(Bundle onRetainNonConfigurationChanges) {
        super.onCreate(onRetainNonConfigurationChanges);
        setContentView(R.layout.organisation);

        String apptoken = getString(R.string.Apptoken);
        String appid = getString(R.string.facebook_app_id);

        rv_org_list = (RecyclerView) findViewById(R.id.rv_org_list);
        rv_org_list.setItemAnimator(new DefaultItemAnimator());
        rv_org_list.setLayoutManager(new LinearLayoutManager(this));

        key = new AccessToken(apptoken, appid, getString(R.string.userid), null, null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE, null, null);

        tv_org_name = (TextView) findViewById(R.id.tv_post_des);
        iv_org_logo = (ImageView) findViewById(R.id.iv_content);

        orgsList = new ArrayList<OrganisationObject>();

        PagesList = getResources().getStringArray(R.array.Listofpagenames);

        final ProgressDialog pd = new ProgressDialog(Organizations.this);
        pd.setMessage("Loading Organisations");
        pd.show();

        String [] indexofyoutube = getResources().getStringArray(R.array.indexOfYoutubeOrg);


        final ArrayList<String> yt_username = new ArrayList<>();

        for(int j=0;j<indexofyoutube.length;j++){
            yt_username.add(indexofyoutube[j].substring(indexofyoutube[j].lastIndexOf("|") + 1));
        }

        for(int i=0;i<PagesList.length;i++){
            final int finalI = i;
            final GraphRequest request = new GraphRequest(
                    key,
                    PagesList[i] + "?fields=picture.type(large),name,about",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            try {
                                final JSONObject jsonresponse = new JSONObject(String.valueOf(response.getJSONObject()));
                                String pic_url = jsonresponse.getJSONObject("picture").getJSONObject("data").getString("url");
                                String name = jsonresponse.getString("name");
                                String about = jsonresponse.getString("about");
                                String id = jsonresponse.getString("id");
                                org = new OrganisationObject(pic_url, name, id, about);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                pd.dismiss();
                                switch (finalI){
                                    case 0: channelIDrequest(yt_username.get(0),org); break;
                                    case 4: channelIDrequest(yt_username.get(1),org);break;
                                    case 5: channelIDrequest(yt_username.get(2),org);break;
                                    case 10:channelIDrequest(yt_username.get(3),org);break;
                                    case 11:channelIDrequest(yt_username.get(4),org);break;
                                    case 15:channelIDrequest(yt_username.get(5),org);break;
                                    default:org.isYoutube = false;
                                            org.channelID = null;
                                            orgsList.add(org);
                                            adapter = new OrganisationAdapter(Organizations.this, orgsList);
                                            rv_org_list.setAdapter(adapter);
                                }
                            }
                        }
                    });
            request.executeAsync();
        }
    }

    private void channelIDrequest(String username, final OrganisationObject org){
        final JsonObjectRequest jsObjRequest1 = new JsonObjectRequest
                (Request.Method.GET,yt_url+username+yt_url2+DeveloperKey.DEVELOPER_KEY, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            JSONObject jsonresponse = new JSONObject(response.toString());
                            JSONArray itemsjson = jsonresponse.getJSONArray("items");
                            JSONObject jsonitem =  itemsjson.getJSONObject(0);
                            org.channelID = jsonitem.getString("id");
                            org.isYoutube = true;
                            orgsList.add(org);
                            adapter = new OrganisationAdapter(Organizations.this, orgsList);
                            rv_org_list.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            orgsList.add(org);
                            adapter = new OrganisationAdapter(Organizations.this, orgsList);
                            rv_org_list.setAdapter(adapter);
                        }

                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });
        MySingleton.getInstance(Organizations
        .this).addToRequestQueue(jsObjRequest1);

    }
}
