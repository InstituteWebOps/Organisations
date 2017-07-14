package io.rohithram.podda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import io.rohithram.podda.Adapters.Postitemlist;

import static io.rohithram.podda.R.string.pageid;
import static io.rohithram.podda.R.string.reaction_query;

/**
 * Created by rohithram on 13/7/17.
 */

public class Fbfragment extends Fragment {

    public PostActivity pactivity;
    public ArrayList<Posts> postList;
    public  FrameLayout layout_MainMenu;
    public PopupWindow reactions_popup,multipopup;
    public CardView  containerLayout;
    public RelativeLayout containerLayout2;
    public PostApapter adapter;
    ViewPager viewPager;
    RecyclerView recyclerView;


    public Fbfragment() {
        // Required empty public constructor
    }

    public void setResponse(ArrayList<Posts> postList, ViewPager viewPager) {
        this.postList = postList;
        this.viewPager = viewPager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fb_fragment
                , container, false);

        pactivity = (PostActivity) getActivity();

        layout_MainMenu = (FrameLayout)view.findViewById( R.id.mainview);
        layout_MainMenu.getForeground().setAlpha(0);

        Context context = view.getContext();

        View v1 = view.findViewById(R.id.rl_fb);
        recyclerView = (RecyclerView)v1.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //Log.i("NFBbb",postList.get(4).message);

        adapter = new PostApapter(getActivity(),postList,pactivity.key,pactivity.Pagename,pactivity.logo_url,pactivity.fragmentManager,pactivity.fragment,layout_MainMenu,pactivity.pd,pactivity.reactions_popup,pactivity.layout,pactivity.multipopup,pactivity.layout1);
        recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter =  new PostApapter(getActivity(), Postitemlist.postList,pactivity.key,pactivity.Pagename,pactivity.logo_url,pactivity.fragmentManager,pactivity.fragment,layout_MainMenu,pactivity.pd,pactivity.reactions_popup,pactivity.layout,pactivity.multipopup,pactivity.layout1);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
