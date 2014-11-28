package com.jacmobile.sensorpanellite.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigable;

/**
 * Created by alex on 11/24/14.
 */
@Singleton
public class OmniController implements SensorEventListener
{
    @Inject Handler handler;
    @Inject SensorManager sensorManager;

    private SensorListAdapter adapter;
    private SensorListFragment fragment;
    private boolean isRunning;

    private float[][] dataMap;

    private Runnable omniRunnable = new Runnable() {
        @Override
        public void run() {
            if (fragment == null || !isRunning) return;
            adapter.updateData(dataMap);
            handler.postDelayed(this, 100);
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

    private ArrayList<Sensor> getSensors()
    {
        return new ArrayList<>(sensorManager.getSensorList(Sensor.TYPE_ALL));
    }

    private void registerSensorListeners()
    {
        ArrayList<Sensor> sensors = getSensors();
        for (int i = 0; i < sensors.size(); i++) {
            if (i <= 13) {
                sensorManager.registerListener(this, sensors.get(i), Sensor.REPORTING_MODE_ON_CHANGE);
            } else {
                break;
            }
        }
    }

    private void unRegisterSensorListeners()
    {
        //todo : limit
        for (Sensor sensor : getSensors()) {
            sensorManager.unregisterListener(this, sensor);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        this.dataMap[event.sensor.getType()] = event.values;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}