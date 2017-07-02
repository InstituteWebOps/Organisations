package io.rohithram.podda;

/**
 * Created by rohithram on 2/7/17.
 */

public class Orgs {
    String logo_url;
    String org_name;
    int org_identity;

    public Void Orgs(String logo_url,String org_name,int org_identity){
        this.logo_url = logo_url;
        this.org_name = org_name;
        this.org_identity = org_identity;
        return null;
    }

}
