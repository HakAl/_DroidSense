package com.jacmobile.droidsense.fragments;

import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by alex on 10/19/14.
 */
public class SensorFragment extends ABaseFragment implements SensorEventListener
{
    @Inject SensorManager sensorManager;

    private ArrayList<Sensor> sensors = null;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (sensors == null) {
            sensors = new ArrayList<Sensor>(this.sensorManager.getSensorList(Sensor.TYPE_ALL));
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }

    public ArrayList<Sensor> getSensors()
    {
        return sensors;
    }

    public Sensor getSensor(int which)
    {
        return sensors.get(which);
    }

    public int getCount()
    {
        return sensors.size();
    }
}
