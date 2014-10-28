package com.jacmobile.sensorpanellite.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigator;

import javax.inject.Inject;

/**
 * Created by alex on 10/12/14.
 */
public class SensorActivity extends ABaseActivity implements Navigator
{
    @Inject
    TimerController timerController;

    private static final String SENSOR_FRAGMENT = "com.jacmobile.sensorpanellite.sensorfragment";
    private static final String SENSOR_LIST_FRAGMENT = "com.jacmobile.sensorpanellite.sensorlistfragment";
    private static final String TIMER_ACTIVITY = "com.jacmobile.sensorpanellite.timeractivity";

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
    }

    @Override
    public void onTransition(int... which)
    {
        if (which.length < 2) {
            this.newSensorFragment(which[0]);
        } else {
//            startActivity(new Intent(this, TimerActivity.class));
        }
    }

    private void newSensorFragment(int sensor)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, SensorFragment.newInstance(sensor), SENSOR_FRAGMENT);
        transaction.addToBackStack(null).commitAllowingStateLoss();
    }
}