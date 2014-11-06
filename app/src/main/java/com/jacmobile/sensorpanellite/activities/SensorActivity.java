package com.jacmobile.sensorpanellite.activities;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigator;

/**
 * Created by alex on 10/12/14.
 */
public class SensorActivity extends ABaseActivity implements Navigator
{
    private static final String SENSOR_FRAGMENT = "com.jacmobile.sensorpanellite.sensorfragment";
    private static final String SENSOR_LIST_FRAGMENT = "com.jacmobile.sensorpanellite.sensorlistfragment";

    private Toolbar mToolbar;
    private boolean isChild = false;
    private boolean adsOn = false;
    private String[] tempDrawer = { "THERE", "ARE", "NO", "ADVERTISEMENTS"};

    @Override
    public void onBackPressed()
    {
        if (isChild) {
            isChild = false;
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, SensorListFragment.newInstance())
                    .commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, SensorListFragment.newInstance())
                    .commit();
        }
        LayoutInflater inflater = LayoutInflater.from(this);

        this.mToolbar = (Toolbar) findViewById(R.id.sensor_toolbar);

        mToolbar.addView(inflater.inflate(R.layout.action_bar, mToolbar, false));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(Color.parseColor("#66000000"));

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.accept, R.string.action_settings);


        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        final ListView mDrawerList = (ListView) findViewById(R.id.list_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, tempDrawer));
        ((Switch) findViewById(R.id.drawer_autoupload)).setChecked(adsOn);
        findViewById(R.id.drawer_autoupload).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                adsOn = !adsOn;
                ((Switch) v).setChecked(adsOn);
                if (adsOn) {
                    mDrawerList.setVisibility(View.VISIBLE);
                } else {
                    mDrawerList.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    protected void setActionBarIcon(int iconRes)
    {
        this.mToolbar.setNavigationIcon(iconRes);
    }

    @Override
    public void onTransition(int... which)
    {
        if (which.length < 2) {
            this.newSensorFragment(which[0]);
            isChild = true;

        } else {
//            startActivity(new Intent(this, Activity.class));
        }
    }

    private void newSensorFragment(int sensor)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, SensorFragment.newInstance(sensor), SENSOR_FRAGMENT);
        transaction.addToBackStack(null).commitAllowingStateLoss();
    }
}