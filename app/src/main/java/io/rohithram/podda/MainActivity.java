package io.rohithram.podda;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.rohithram.podda.Adapters.VideoitemList;


public class MainActivity extends AppCompatActivity {
    public static boolean httpRequest = false; //If this is false Asynctask(in OrganPage) will fetch from internet
    ListView organListview; //List for Organistions names
    public static String[] organisations; // this array will filled with organisation names from string.xml


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        organListview = (ListView)findViewById(R.id.organ_listview);
        /*organisations array is filled*/
        Resources res= getResources();
        organisations = res.getStringArray(R.array.organisations);
        /*A default arrayadapter is being used for listview, so item layout is just a simple TextView (R.layout.organ_item_layout.xml)*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.organ_item_layout,organisations);
        organListview.setAdapter(adapter);
        /*Item clickListener is in this method*/
        organitemClickListener();

    }
    /*When the activity is back from OrganPage, this clears the data in the arraylist and sets httpRequest to "false"
        so that Asynctask can work again(if condition used in VideoitemList.class) next time the OrganPage is opened.*/

    @Override
    protected void onResume() {
        super.onResume();
        VideoitemList.videoList.clear();
        httpRequest = false;
    }

    private void organitemClickListener() {

        organListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,OrganPage.class);
                String channelID="";
                switch(position){
                    case 0 : channelID="UCZgwOAjweG6NZKX5VWXLQLw";break;
                    case 1 : channelID="UCL54SxfvU26y-niXyi1VY9A";break;
                    case 2 : channelID="UCSsS3HSbI7fCu0pGq0XFgxw"; break;
                    case 3 : channelID="UC6GWtMMc4RnjcJAOHbr7XKA";break;
                    case 4 : channelID="UC34XdxxCQkUpAMZbWR6-aqQ";break;
                    case 5 : channelID="UCgY2ugmW-BV2nMRFu-0qPZA";break;
                    default: channelID="" ;

                }
                intent.putExtra(DeveloperKey.EXTRA_MESSAGE,channelID);
                startActivity(intent);
            }
        });
    }

}

/*This app makes use of following URLs
    If we have youtube username and to obtain channelIDs
        https://www.googleapis.com/youtube/v3/channels?part=snippet&forUsername={USERNAME}&key={YOUR_API_KEY}

    If we have youtube channelID and to obtain video and playlist IDS
        https://www.googleapis.com/youtube/v3/search?key=AIzaSyAU0d027wNVn0IQGbLjxQ21AWH906poSKM&channelId=UCSsS3HSbI7fCu0pGq0XFgxw&part=snippet,id&order=date&maxResults=50
            add "pageToken" parameter to get next page in the results

    If we have playlist id and want to obtain video IDs
        https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId={YOUR_CHANNEL_ID}&key={YOUR_API_KEY}
        */