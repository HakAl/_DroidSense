package com.jacmobile.sensorpanellite.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

    private boolean isChild = false;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private String[] tempDrawer = { "Device Profile", "More", "Features", "Coming", "Soon"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.sensor_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View actionBar = inflater.inflate(R.layout.action_bar, mToolbar, false);
        mToolbar.addView(actionBar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(Color.parseColor("#66000000"));

//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                activity, transitionView, DetailActivity.EXTRA_IMAGE);
//        ActivityCompat.startActivity(activity, new Intent(activity, DetailActivity.class),
//                options.toBundle());

        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        final ListView mDrawerList = (ListView) findViewById(R.id.list_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, tempDrawer));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0) {
                    startActivity(new Intent(SensorActivity.this, RecyclerActivity.class));
                }
            }
        });
        ((SwitchCompat) findViewById(R.id.drawer_autoupload)).setChecked(true);
        findViewById(R.id.drawer_autoupload).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(SensorActivity.this, "There are no ads.", Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, SensorListFragment.newInstance())
                    .commit();
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

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