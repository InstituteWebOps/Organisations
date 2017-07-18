package io.rohithram.Organisations;

import java.util.ArrayList;

/**
 * Created by rohithram on 23/6/17.
 */

public class Posts {
    String message;
    String id;
    public String img_url;
    public int count;
    public int like_count, haha_count, sad_count, angry_count, love_count, wow_count;
    public String vid_url;
    public String type;
    public String created_time;
    public ArrayList<String> sub_imgurls = new ArrayList<String>();
    public Posts(String id ) {
        this.id = id;
    }
}
