package io.rohithram.podda;

/**
 * Created by rohithram on 2/7/17.
 */

public class OrganisationObject {

    String logo_url;
    String org_name;
    String pageid;
    String org_about;
    Boolean isYoutube = false ;
    String channelID = " ";

    public  OrganisationObject(String logo_url,String org_name,String pageid,String org_about){
        this.logo_url = logo_url;
        this.org_name = org_name;
        this.pageid = pageid;
        this.org_about = org_about;

    }

}
