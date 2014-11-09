package com.jacmobile.sensorpanellite.util;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
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
import com.jacmobile.sensorpanellite.activities.SensorActivity;

/**
 * Created by alex on 11/9/14.
 */
public class DrawerController
{
    private String[] tempDrawer = {"Sensor Feed List", "Sensor Profile", "More", "Features", "Coming", "Soon"};
    private ActionBarActivity activity;

    private DrawerLayout drawer;
    private View actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public DrawerController(ActionBarActivity activity)
    {
        this.activity = activity;
    }

    public DrawerController(ActionBarActivity activity, String[] list)
    {
        this.activity = activity;
        this.tempDrawer = list;
    }

    private void initDrawer()
    {
        LayoutInflater inflater = LayoutInflater.from(activity);
        Toolbar mToolbar = (Toolbar) activity.findViewById(R.id.sensor_toolbar);
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.actionBar = inflater.inflate(R.layout.action_bar, mToolbar, false);
        mToolbar.addView(actionBar);
        drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.parseColor("#66000000"));
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(activity,
                drawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawer.setDrawerListener(actionBarDrawerToggle);

        final ListView mDrawerList = (ListView) activity.findViewById(R.id.list_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(activity, R.layout.list_item, tempDrawer));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                drawer.closeDrawers();
                ((SensorActivity) activity).onDrawerClick(position);
            }
        });
        ((SwitchCompat) activity.findViewById(R.id.drawer_autoupload)).setChecked(true);
        activity.findViewById(R.id.drawer_autoupload).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(activity, "There are no ads.", Toast.LENGTH_SHORT).show();
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
    }

    public void onStop()
    {
        drawer.closeDrawers();
    }

    public void setActionBarTitle(String title)
    {
        TextView txtTitle = (TextView) this.actionBar.findViewById(R.id.action_bar_title);
        txtTitle.setText(title);
    }
}
