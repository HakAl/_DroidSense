package com.jacmobile.sensorpanellite.activities;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigator;

/**
 * Created by alex on 10/12/14.
 */
public class SensorActivity extends ABaseActivity implements Navigator {
    private static final String SENSOR_FRAGMENT = "com.jacmobile.sensorpanellite.sensorfragment";
    private static final String SENSOR_LIST_FRAGMENT = "com.jacmobile.sensorpanellite.sensorlistfragment";

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, SensorListFragment.newInstance())
                    .commit();
        }

        this.mToolbar = (Toolbar) findViewById(R.id.sensor_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final DrawerLayout drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.parseColor("#66000000"));
        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });

        drawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        }, 1500);
    }

    protected void setActionBarIcon(int iconRes) {
        this.mToolbar.setNavigationIcon(iconRes);
    }

    @Override
    public void onTransition(int... which) {
        if (which.length < 2) {
            this.newSensorFragment(which[0]);
        } else {
//            startActivity(new Intent(this, Activity.class));
        }
    }

    private void newSensorFragment(int sensor) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, SensorFragment.newInstance(sensor), SENSOR_FRAGMENT);
        transaction.addToBackStack(null).commitAllowingStateLoss();
    }
}