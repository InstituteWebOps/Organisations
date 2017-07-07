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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.nostra13.universalimageloader.core.ImageLoader;

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

        for(int i=0;i<PagesList.length;i++){
            GraphRequest request = new GraphRequest(
                    key,
                    PagesList[i] + "?fields=picture.type(large),name,about",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            OrganisationObject org = null;
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
                                orgsList.add(org);
                                adapter = new OrganisationAdapter(Organizations.this, orgsList);
                                rv_org_list.setAdapter(adapter);
                            }
                        }
                    });
            request.executeAsync();
        }
    }
}
