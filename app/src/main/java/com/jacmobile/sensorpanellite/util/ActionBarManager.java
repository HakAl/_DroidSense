package com.jacmobile.sensorpanellite.util;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.activities.PrimaryActivity;

/**
 * Created by alex on 11/9/14.
 */
public class ActionBarManager implements AdapterView.OnItemClickListener
{
    private boolean adsOn = true;
    private String[] drawerStrings = {"Sensor Feed List", "Sensor Profile", "System Properties"};
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private View actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public ActionBarManager(Toolbar toolbar)
    {
        this.toolbar = toolbar;
    }

    private PrimaryActivity getActivity()
    {
        return ((PrimaryActivity) this.toolbar.getContext());
    }

    private void initDrawer()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        getActivity().setSupportActionBar(toolbar);
        this.actionBar = layoutInflater.inflate(R.layout.action_bar, toolbar, false);
        toolbar.addView(actionBar);
//        // Set an OnMenuItemClickListener to handle menu item clicks
//        toolbar.setOnMenuItemClickListener(
//                new Toolbar.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        // Handle the menu item
//                        return true;
//                    }
//                });
//
//        // Inflate a menu to be displayed in the toolbar
//        toolbar.inflateMenu(R.menu.overflow);

        ViewCompat.setElevation(toolbar, 5f);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.parseColor("#66000000"));
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
//        {
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset)
//            {
//                super.onDrawerSlide(drawerView, slideOffset);
//            }
//
//            /** Called when a drawer has settled in a completely closed state. */
//            @Override
//            public void onDrawerClosed(View view)
//            {
//                super.onDrawerClosed(view);
//                getActivity().getSupportActionBar().setTitle("hello");
//            }
//
//            /** Called when a drawer has settled in a completely open state. */
//            @Override
//            public void onDrawerOpened(View drawerView)
//            {
//                super.onDrawerOpened(drawerView);
//                getActivity().getSupportActionBar().setTitle("hi");
//            }
//        };
        drawer.setDrawerListener(actionBarDrawerToggle);
//        getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getActivity().getSupportActionBar().setHomeButtonEnabled(true);
//        getActivity().getSupportActionBar().setElevation(5);

        actionBarDrawerToggle.syncState();
        ((TextView) getActivity().findViewById(R.id.tv_drawer_header)).setText(getDrawerTitle());
        final ListView mDrawerList = (ListView) getActivity().findViewById(R.id.list_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.drawer_list_item, drawerStrings));
        mDrawerList.setOnItemClickListener(this);
    //        ((Sw`
    }

    private String getDrawerTitle()
    {
        String brand = Build.BRAND.substring(0, 1).toUpperCase() + Build.BRAND.substring(1);
        return brand + " " + Build.MODEL;
    }

    public void onPostCreate()
    {
        actionBarDrawerToggle.syncState();
    }

    public void onCreate()
    {
        this.initDrawer();
    }

    public void onStop()
    {
        drawer.closeDrawers();
    }


    public void onConfigurationChanged(Configuration newConfig)
    { // <---- added
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setActionBarTitle(String title)
    {
        ((TextView) actionBar.findViewById(R.id.action_bar_title)).setText(title);
    }

    public boolean isDrawerOpen()
    {
        return drawer.isDrawerOpen(GravityCompat.START);
    }

    private void setAds()
    {
        if (adsOn) {
            Toast.makeText(getActivity(), "Ads Off", Toast.LENGTH_SHORT).show();
            adsOn = false;
        } else {
            Toast.makeText(getActivity(), "Ads On", Toast.LENGTH_LONG).show();
            adsOn = true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        drawer.closeDrawers();
        getActivity().navListClick(position);
    }
}