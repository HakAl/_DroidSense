package com.jacmobile.sensorpanellite.util;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
public class ActionBarManager
{

    private boolean adsOn = true;
    private String[] tempDrawer = {"Sensor Feed List", "Sensor Profile", "System Properties", "OMNI"};
    private PrimaryActivity activity;
    private LayoutInflater layoutInflater;

    private DrawerLayout drawer;
    private View actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public ActionBarManager(PrimaryActivity activity)
    {
        this.activity = activity;
    }

    public ActionBarManager(PrimaryActivity activity, String[] list)
    {
        this.activity = activity;
        this.tempDrawer = list;
    }

    private void initDrawer()
    {
        Toolbar mToolbar = (Toolbar) activity.findViewById(R.id.sensor_toolbar);
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.actionBar = layoutInflater.inflate(R.layout.action_bar, mToolbar, false);
        mToolbar.addView(actionBar);
        drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.parseColor("#66000000"));
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(activity,
                drawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawer.setDrawerListener(actionBarDrawerToggle);
        String brand = Build.BRAND.substring(0, 1).toUpperCase() + Build.BRAND.substring(1);
        String title = brand + " " + Build.MODEL;
        ((TextView) activity.findViewById(R.id.tv_drawer_header)).setText(title);
        final ListView mDrawerList = (ListView) activity.findViewById(R.id.list_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(activity, R.layout.drawer_list_item, tempDrawer));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                drawer.closeDrawers();
                activity.onDrawerClick(position);
            }
        });
        ((SwitchCompat) activity.findViewById(R.id.drawer_autoupload)).setChecked(adsOn);
        activity.findViewById(R.id.drawer_autoupload).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAds();
            }
        });
    }

    public void onPostCreate()
    {
        actionBarDrawerToggle.syncState();
    }

    public void onCreate()
    {
        this.initDrawer();
        layoutInflater = LayoutInflater.from(activity);
    }

    public void onStop()
    {
        drawer.closeDrawers();
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
            Toast.makeText(activity, "Ads Off", Toast.LENGTH_SHORT).show();
            adsOn = false;
        }
        else {
            Toast.makeText(activity, "Ads On", Toast.LENGTH_LONG).show();
            adsOn = true;
        }
    }
}