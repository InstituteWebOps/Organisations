package io.rohithram.podda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by rohithram on 2/7/17.
 */

public class OrganisationAdapter extends RecyclerView.Adapter <OrganisationAdapter.ViewHolder> {

    List<OrganisationObject> OrgList;
    Context context;
    com.android.volley.toolbox.ImageLoader mImageLoader;

    public OrganisationAdapter(Context context, List<OrganisationObject> OrgList, com.android.volley.toolbox.ImageLoader mImageLoader) {
        this.context = context;
        this.OrgList = OrgList;
        this.mImageLoader = mImageLoader;

    }

    @Override
    public OrganisationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.organ_content, parent, false);
        return new OrganisationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrganisationAdapter.ViewHolder holder, int position) {

        holder.tv_org_name.setText(OrgList.get(holder.getAdapterPosition()).org_name);
        String about = OrgList.get(holder.getAdapterPosition()).org_about;
        holder.tv_org_about.setText(about);

        mImageLoader.get(OrgList.get(holder.getAdapterPosition()).logo_url, ImageLoader.getImageListener(holder.iv_org_logo
                ,R.drawable.loading_icon
                ,android.R.drawable.ic_dialog_alert));

        holder.iv_org_logo.setImageUrl(OrgList.get(holder.getAdapterPosition()).logo_url, mImageLoader);

       // ImageLoader imageLoader = ImageUtil.getImageLoader(this.context);
        //imageLoader.displayImage(OrgList.get(holder.getAdapterPosition()).logo_url,holder.iv_org_logo);

        holder.cv_org_item.setFocusable(true);

        holder.cv_org_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context,PostActivity.class);
                i.putExtra("pageid",OrgList.get(holder.getAdapterPosition()).pageid);
                i.putExtra("pagename",OrgList.get(holder.getAdapterPosition()).org_name);
                i.putExtra("logo_url",OrgList.get(holder.getAdapterPosition()).logo_url);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return OrgList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView iv_org_logo;
        public TextView tv_org_name,tv_org_about;
        public CardView cv_org_item;


        public ViewHolder(View itemView) {
            super(itemView);
            iv_org_logo = (NetworkImageView) itemView.findViewById(R.id.iv_org_logo);
            tv_org_name = (TextView)itemView.findViewById(R.id.tv_org_name);
            cv_org_item = (CardView)itemView.findViewById(R.id.cv_org_name);
            tv_org_about =(TextView)itemView.findViewById(R.id.tv_org_about);


        }
    }
}
