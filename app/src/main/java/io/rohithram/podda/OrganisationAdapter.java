package io.rohithram.podda;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by rohithram on 2/7/17.
 */

public class OrganisationAdapter extends RecyclerView.Adapter <OrganisationAdapter.ViewHolder> {

    List<OrganisationObject> OrgList;
    Context context;



    public OrganisationAdapter(Context context,List<OrganisationObject> OrgList) {
        this.context = context;
        this.OrgList = OrgList;

    }


    @Override
    public OrganisationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.organ_content, parent, false);
        return new OrganisationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrganisationAdapter.ViewHolder holder, int position) {
        holder.tv_org_name.setText(OrgList.get(holder.getAdapterPosition()).org_name);

        ImageLoader imageLoader = ImageUtil.getImageLoader(this.context);
        imageLoader.displayImage(OrgList.get(holder.getAdapterPosition()).logo_url,holder.iv_org_logo);

        holder.cv_org_item.setFocusable(true);
        holder.cv_org_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (OrgList.get(holder.getAdapterPosition()).org_identity){
                    case 1: break;
                    case 2:break;
                    case 3:break;
                    case 4:break;
                    case 5:break;
                    case 6:break;
                    case 7:break;
                    case 8:break;
                    case 9:break;
                    case 10:break;
                    case 11:break;
                    case 12:break;
                    case 13:break;
                    case 14:break;
                    case 15:break;
                    case 16:break;


                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return OrgList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_org_logo;
        public TextView tv_org_name;
        public CardView cv_org_item;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_org_logo = (ImageView)itemView.findViewById(R.id.iv_org_logo);
            tv_org_name = (TextView)itemView.findViewById(R.id.tv_org_name);
            cv_org_item = (CardView)itemView.findViewById(R.id.cv_org_name);


        }
    }
}
