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
        return  ((PrimaryActivity) this.toolbar.getContext());
    }

    private void initDrawer()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        getActivity().setSupportActionBar(toolbar);
        getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.actionBar = layoutInflater.inflate(R.layout.action_bar, toolbar, false);
        toolbar.addView(actionBar);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.parseColor("#66000000"));
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawer.setDrawerListener(actionBarDrawerToggle);
        ((TextView) getActivity().findViewById(R.id.tv_drawer_header)).setText(getDrawerTitle());
        final ListView mDrawerList = (ListView) getActivity().findViewById(R.id.list_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.drawer_list_item, tempDrawer));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                drawer.closeDrawers();
                getActivity().onDrawerClick(position);
            }
        });
        ((SwitchCompat) getActivity().findViewById(R.id.drawer_autoupload)).setChecked(adsOn);
        getActivity().findViewById(R.id.drawer_autoupload).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAds();
            }
        });
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
        }
        else {
            Toast.makeText(getActivity(), "Ads On", Toast.LENGTH_LONG).show();
            adsOn = true;
        }
    }
}