package io.rohithram.podda;

import java.util.Date;

/**
 * Created by rohithram on 23/6/17.
 */

public class Posts {
    String message ;
    String id;
    public  String img_url;
    public int count;
    public String vid_url;
    public String type;
    public String created_time;

    public Posts(String message,String id){
        this.message = message;
        this.id = id;
    }
    public  Void setImg_url(String img_url){
        this.img_url = img_url;
        return null;
    }
}
