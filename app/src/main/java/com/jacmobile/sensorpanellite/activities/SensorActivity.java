package com.jacmobile.sensorpanellite.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigator;

/**
 * Created by alex on 10/12/14.
 */
public class SensorActivity extends ABaseActivity implements Navigator
{
    private static final String SENSOR_FRAGMENT = "com.jacmobile.droidsense.sensorfragment";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, SensorListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onTransition(int... which)
    {
        this.newSensorFragment(which[0]);
    }

    private void newSensorFragment(int sensor)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, SensorFragment.newInstance(sensor), SENSOR_FRAGMENT);
        transaction.addToBackStack(null).commitAllowingStateLoss();
    }
}