package io.rohithram.podda;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by rohithram on 13/7/17.
 */

public class Fbfragment extends Fragment  {

    public PostActivity pactivity;
    public ArrayList<Posts> postList;
    public PopupWindow reactions_popup,multipopup;
    public CardView  containerLayout;
    public RelativeLayout containerLayout2;
    public PostApapter adapter;
    ViewPager viewPager;
    RecyclerView recyclerView;
    View layout_MainMenu;
    //public android.support.v4.app.FragmentManager fragmentManager;
    //public VideoFragment fragment;



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

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout

       // layout_MainMenu = (FrameLayout)view.findViewById( R.id.mainview);
        //layout_MainMenu.getForeground().setAlpha(0);

       /* fragmentManager = null;
        fragment = null;
        Bundle arg = new Bundle();
        arg.putString("intialvalue", "initialisable");

        if (view.findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            fragment = new VideoFragment();
            fragment.setArguments(arg);
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentManager = getChildFragmentManager();
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fb_fragment
                , container, false);

        pactivity = (PostActivity) getActivity();

        layout_MainMenu = (FrameLayout) view;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout_MainMenu.getForeground().setAlpha(0);
        }

        //layout_MainMenu = (FrameLayout)view.findViewById( R.id.mainview);
        //layout_MainMenu.getForeground().setAlpha(0);

        /*fragmentManager = null;
        fragment = null;
        Bundle arg = new Bundle();
        arg.putString("intialvalue", "initialisable");

        if (view.findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return null;
            }
            // Create a new Fragment to be placed in the activity layout
            fragment = new VideoFragment();
            fragment.setArguments(arg);
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentManager = getChildFragmentManager();
        }*/

        View v1 = view.findViewById(R.id.rl_fb);
        recyclerView = (RecyclerView)v1.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new PostApapter(getActivity(),postList,pactivity.key,pactivity.Pagename,pactivity.logo_url,pactivity.fragmentManager,pactivity.fragment,pactivity.layout_MainMenu,pactivity.pd,pactivity.reactions_popup,pactivity.layout,pactivity.multipopup,pactivity.layout1);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter =  new PostApapter(getActivity(),postList,pactivity.key,pactivity.Pagename,pactivity.logo_url,pactivity.fragmentManager,pactivity.fragment,pactivity.layout_MainMenu,pactivity.pd,pactivity.reactions_popup,pactivity.layout,pactivity.multipopup,pactivity.layout1);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }





}
