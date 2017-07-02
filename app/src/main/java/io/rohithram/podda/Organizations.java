package io.rohithram.podda;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Organizations extends AppCompatActivity {

    TextView tv_org_name;
    List<OrganisationObject> orgsList;
    OrganisationObject organisation;


    public ImageView iv_org_logo;
    public RecyclerView rv_org_list;
    public OrganisationAdapter adapter;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organisation);

        rv_org_list = (RecyclerView) findViewById(R.id.rv_org_list);
        rv_org_list.setItemAnimator(new DefaultItemAnimator());
        rv_org_list.setLayoutManager(new LinearLayoutManager(this));

        tv_org_name = (TextView) findViewById(R.id.tv_post_des);
        iv_org_logo = (ImageView) findViewById(R.id.iv_content);

        orgsList = new ArrayList<OrganisationObject>();



        initOrganisations(orgsList,organisation);
        adapter = new OrganisationAdapter(Organizations.this, orgsList);
        rv_org_list.setAdapter(adapter);


    }

    private Void initOrganisations(List<OrganisationObject> orgsList, OrganisationObject organisation){

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/c52.52.651.651/s160x160/530729_392833077475780_911269183_n.jpg?oh=bd6aab5e6e104b2634348b90525e7099&oe=59CF5D5F","Media club","145571852201905",1);
        orgsList.add(organisation);

        organisation = new  OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p100x100/12246719_884309415000092_1198439460298766286_n.png?oh=21cfa3991acdb63a7245f5d47a12df5a&oe=59D1D189","Pod_Da","884294325001601",2);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p320x320/13010840_596900417128108_5316622524519344269_n.jpg?oh=00e285590c99c0d0ca4dbc4e263e4aee&oe=59CEEF36","Fine arts club IITM","191907254294095",3);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p160x160/1796687_758964894121623_306906998_n.jpg?oh=05604beca7ccdaa9195d52a8cfa2dcac&oe=59D96320","CFI IITM","160909410608035",4);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p160x160/13325690_1192678840753567_5282120316932677496_n.jpg?oh=e7c965631de8abc13ccabb7a75d69a31&oe=5A107477","IIT Madras official", "571648656189925",5);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/c36.0.159.159/p160x160/10380891_643222725757775_8572251655887332562_n.jpg?oh=f9fa54367c5a1adda83ea65caa8a0af9&oe=5A120EFE","Saarang","120012338753",6);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p160x160/10492125_675662332488734_2137650625274591896_n.jpg?oh=26b2dae6ce01d4729b5377bcca462444&oe=5A0C4EF1","Oratory Club","533179140070388",7);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent-sin6-2.xx.fbcdn.net/v/t31.0-8/13909376_1229422713735894_5049285609096326983_o.jpg?oh=5bf75e7c179c0fb667c2b51c481c6656&oe=59D7A4F2","SLC IITM","1016635365014631",8);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p100x100/155990_173670326109472_994519463_n.jpg?oh=9c60a9b66675ac49473d656ebd808abb&oe=59D9D860","IIT Madras sports","147226105420561",9);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/c27.0.160.160/p160x160/12651280_1207393169288924_6482309451863971655_n.jpg?oh=d5ae104f14be6aa13fb24a3cf2fad283&oe=59DBDCC1","NSS IITM","114501385244780",10);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p100x100/17630173_1367359299976446_2508338379356337084_n.jpg?oh=637ada066f6ffc58f10c930e6b128ec1&oe=59C3F49D","Shaastra","105321269513595",11);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p160x160/1926934_755450974487272_7856319898186352969_n.png?oh=3b280d13c80204e1f3441bd962ae8c38&oe=59D34EB8","IITM TV","353542971344743",12);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p320x320/10402517_781762928568087_6204322848510610816_n.png?oh=e7c7db33f174058b5f8252a62d87c28d&oe=59DBDDE8","IAR","498186993592350",13);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/c22.22.276.276/p320x320/1005069_478420812250697_314656219_n.png?oh=236c4525e1b8b75239a52a6a1f4a10e3&oe=5A072E2C","MITR","401363316623114",14);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p160x160/13692609_660255010795090_4690149022822602562_n.jpg?oh=e7ac1c42e2820ecea1ad43b86b880499&oe=59D424CB","Saathi","660252517462006",15);
        orgsList.add(organisation);

        organisation = new OrganisationObject("https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/p160x160/10678764_548402891961076_62779937181075368_n.png?oh=52f1d8d6de891a8856b4d163f25698ce&oe=5A0E93D9","EML","174720872662615",16);
        orgsList.add(organisation);

        return null;

    }
}
