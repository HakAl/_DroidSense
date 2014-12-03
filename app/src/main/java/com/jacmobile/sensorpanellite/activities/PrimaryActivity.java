package com.jacmobile.sensorpanellite.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.fragments.PaletteExample;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.fragments.SensorProfileFragment;
import com.jacmobile.sensorpanellite.fragments.SystemInfoFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigator;
import com.jacmobile.sensorpanellite.util.ActionBarManager;

/**
 * Manages navigation.
 * <p/>
 * Created by alex on 10/12/14.
 */
public class PrimaryActivity extends ABaseActivity implements Navigator
{
    private static final String FRAGMENT_SENSOR_LIST = "com.jacmobile.sensorpanellite.sensorlistfragment";
    private static final String FRAGMENT_SENSOR = "com.jacmobile.sensorpanellite.sensorfragment";
    private static final String FRAGMENT_SENSOR_PROFILE = "com.jacmobile.sensorpanellite.sensorprofilefragment";
    private static final String FRAGMENT_SYSTEM_INFO = "com.jacmobile.sensorpanellite.systeminfofragment";

    private int currentPosition;
    private boolean firstLoad = true;
    private boolean isChild = false;
    private ActionBarManager actionBarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_content_view);
        actionBarManager = new ActionBarManager((Toolbar) this.findViewById(R.id.sensor_toolbar));
        actionBarManager.onCreate();
        if (savedInstanceState == null) {
            this.newSensortList();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarManager.onPostCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        actionBarManager.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed()
    {
        if (actionBarManager.isDrawerOpen()) {
            actionBarManager.onStop();
        } else {
            if (isChild) {
                isChild = false;
                this.newSensortList();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        actionBarManager.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        actionBarManager = null;
    }

    @Override
    public void navListClick(int position)
    {
        if (currentPosition == position) {
            return;
        } else {
            currentPosition = position;
            switch (position) {
                case 0:
                    newSensortList();
                    break;
                case 1:
                    newSensorProfile();
                    break;
                case 2:
                    newSystemInfo();
                    break;
                case 3:
//                newPalette();
                    break;
                default:
                    break;
            }
        }
        this.firstLoad = false;
    }

    @Override
    public void onTransition(final int... which)
    {
        if (which.length < 2) {
            newSensorFragment(which[0]);
        } else {
            newSystemInfo();
        }
    }

    @Override
    public void setNavigationTitle(String title)
    {
        actionBarManager.setActionBarTitle(title);
    }

    private void newSensortList()
    {
        if (firstLoad) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, SensorListFragment.newInstance(), FRAGMENT_SENSOR_LIST)
                    .commit();
            return;
        }
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, SensorListFragment.newInstance(), FRAGMENT_SENSOR_LIST)
                .commit();
    }

    private void newSensorProfile()
    {
        isChild = true;
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, SensorProfileFragment.newInstance(), FRAGMENT_SENSOR_PROFILE)
                .commit();
    }

    private void newSystemInfo()
    {
        isChild = true;
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, SystemInfoFragment.newInstance(), FRAGMENT_SYSTEM_INFO)
                .commit();
    }

    private void newSensorFragment(int sensor)
    {
        isChild = true;
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, SensorFragment.newInstance(sensor), FRAGMENT_SENSOR)
                .commit();
    }


    private void newPalette()
    {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, PaletteExample.newInstance(), FRAGMENT_SENSOR_LIST)
                .commit();
    }
}