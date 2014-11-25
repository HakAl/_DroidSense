package com.jacmobile.sensorpanellite.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.fragments.SensorProfileFragment;
import com.jacmobile.sensorpanellite.fragments.SystemInfoFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigator;
import com.jacmobile.sensorpanellite.util.ActionBarManager;

/**
 * Created by alex on 10/12/14.
 */
public class PrimaryActivity extends ABaseActivity implements Navigator
{
    private static final String SENSOR_LIST_FRAGMENT = "com.jacmobile.sensorpanellite.sensorlistfragment";
    private static final String SENSOR_FRAGMENT = "com.jacmobile.sensorpanellite.sensorfragment";
    private static final String SENSOR_PROFILE_FRAGMENT = "com.jacmobile.sensorpanellite.sensorprofilefragment";
    private static final String SYSTEM_INFO_FRAGMENT = "com.jacmobile.sensorpanellite.systeminfofragment";
    private static final String OMNI_FRAGMENT = "com.jacmobile.sensorpanellite.omnifragment";

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
    protected void onStop()
    {
        super.onStop();
        actionBarManager.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarManager.onPostCreate();
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

    public void onDrawerClick(int position)
    {
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
//                todo settings();
                break;
            default:
                break;
        }
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

    public void setActionBarTitle(String title)
    {
        actionBarManager.setActionBarTitle(title);
    }

    private void newSensortList()
    {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SensorListFragment.newInstance(), SENSOR_LIST_FRAGMENT)
                .commit();
    }

    private void newSensorProfile()
    {
        isChild = true;
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SensorProfileFragment.newInstance(), SENSOR_PROFILE_FRAGMENT)
                .commit();
        setActionBarTitle(getString(R.string.sensor_profile));
    }

    private void newSystemInfo()
    {
        isChild = true;
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SystemInfoFragment.newInstance(), SYSTEM_INFO_FRAGMENT)
                .commit();
        setActionBarTitle(getString(R.string.system_profile));
    }

    private void newSensorFragment(int sensor)
    {
        isChild = true;
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SensorFragment.newInstance(sensor), SENSOR_FRAGMENT)
                .commit();
    }
}