package io.rohithram.podda;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.share.widget.LikeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohithram on 2/7/17.
 */

public class OrganisationAdapter extends RecyclerView.Adapter <OrganisationAdapter.ViewHolder> {

    List<Orgs> Orgs;
    Context context;


    public OrganisationAdapter(Context context,List<Orgs> Orgs) {
        this.context = context;
        this.Orgs = Orgs;

    }


    @Override
    public OrganisationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.organ_content, parent, false);
        return new OrganisationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrganisationAdapter.ViewHolder holder, int position) {
        holder.tv_org_name.setText(Orgs.get(holder.getAdapterPosition()).org_name);

        ImageLoader imageLoader = ImageUtil.getImageLoader(this.context);
        imageLoader.displayImage(Orgs.get(holder.getAdapterPosition()).logo_url,holder.iv_org_logo);

    }

    @Override
    public int getItemCount() {
        return Orgs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_org_logo;
        public TextView tv_org_name;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_org_logo = (ImageView)itemView.findViewById(R.id.iv_org_logo);
            tv_org_name = (TextView)itemView.findViewById(R.id.tv_org_name);


        }
    }
}
