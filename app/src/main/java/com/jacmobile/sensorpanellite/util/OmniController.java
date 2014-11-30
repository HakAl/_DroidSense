package com.jacmobile.sensorpanellite.util;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigable;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alex on 11/24/14.
 */
@Singleton
public class OmniController implements SensorEventListener
{
    @Inject Handler handler;
    @Inject SensorManager sensorManager;

    private float[][] dataMap;
    private boolean isRunning;
    private SensorListAdapter adapter;
    private SensorListFragment fragment;

    private Runnable omniRunnable = new Runnable() {
        @Override
        public void run() {
            if (fragment == null || !isRunning) return;
            adapter.updateData(dataMap);
            handler.postDelayed(this, 333);
        }
    };

    public void restartTimer()
    {
        if (!isRunning) {
            isRunning = true;
            handler.post(omniRunnable);
        }
    }

    public void onResume(SensorListFragment fragment, SensorListAdapter adapter)
    {
        this.adapter = adapter;
        this.fragment = fragment;
        this.dataMap = new float[14][];
        this.registerSensorListeners();
    }

    public void onPause()
    {
        this.unRegisterSensorListeners();
        this.fragment = null;
        this.adapter = null;
        this.isRunning = false;
        handler.removeCallbacks(omniRunnable);
    }

    private void registerSensorListeners()
    {
        ArrayList<Navigable> sensors = adapter.getData();

        for (int i = 0; i < sensors.size(); i++) {
            if (i <= 13) {
                sensorManager.registerListener(this, sensors.get(i).getSensor(), 333);
            } else {
                break;
            }
        }
    }

    private void unRegisterSensorListeners()
    {
        this.sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int position = event.sensor.getType();
        if (position <= 13) {
            this.dataMap[position] = event.values;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}