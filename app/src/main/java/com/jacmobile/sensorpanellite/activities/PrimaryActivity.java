package com.jacmobile.sensorpanellite.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.fragments.SensorProfileFragment;
import com.jacmobile.sensorpanellite.fragments.SystemInfoFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigator;
import com.jacmobile.sensorpanellite.util.DrawerController;

/**
 * Created by alex on 10/12/14.
 */
public class PrimaryActivity extends ABaseActivity implements Navigator
{
    private static final String SENSOR_FRAGMENT = "com.jacmobile.sensorpanellite.sensorfragment";
    private static final String SENSOR_PROFILE_FRAGMENT = "com.jacmobile.sensorpanellite.sensorprofilefragment";
    private static final String SYSTEM_INFO_FRAGMENT = "com.jacmobile.sensorpanellite.systeminfofragment";

    private boolean isChild = false;
    private DrawerController drawerController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_content_view);
        drawerController = new DrawerController(PrimaryActivity.this);
        drawerController.onCreate();
        if (savedInstanceState == null) {
            this.newSensortList();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        drawerController.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerController.onPostCreate();
    }

    @Override
    public void onBackPressed()
    {
        if (isChild) {
            isChild = false;
            this.newSensortList();
        } else {
            super.onBackPressed();
        }
    }

    public void onDrawerClick(int position)
    {
        switch (position) {
            case 0:
                this.newSensortList();
                break;
            case 1:
                this.newSensorProfile();
                break;
            case 2:
                this.newSystemInfo();
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

    private void newSensortList()
    {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SensorListFragment.newInstance())
                .commit();
        drawerController.setActionBarTitle(getString(R.string.app_name));
    }

    private void newSensorProfile()
    {
        isChild = true;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, SensorProfileFragment.newInstance(), SENSOR_PROFILE_FRAGMENT);
        transaction.addToBackStack(null).commitAllowingStateLoss();
        drawerController.setActionBarTitle(getString(R.string.sensor_profile));
    }

    private void newSystemInfo()
    {
        isChild = true;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, SystemInfoFragment.newInstance(), SYSTEM_INFO_FRAGMENT);
        transaction.addToBackStack(null).commitAllowingStateLoss();
        drawerController.setActionBarTitle(getString(R.string.sensor_profile));
    }

    private void newSensorFragment(int sensor)
    {
        isChild = true;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, SensorFragment.newInstance(sensor), SENSOR_FRAGMENT);
        transaction.addToBackStack(null).commitAllowingStateLoss();
        String[] sensorTitles = getResources().getStringArray(R.array.sensors_array);
        drawerController.setActionBarTitle(sensorTitles[sensor + 1]);
    }
}