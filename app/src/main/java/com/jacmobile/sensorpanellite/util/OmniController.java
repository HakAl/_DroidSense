package com.jacmobile.sensorpanellite.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jacmobile.sensorpanellite.activities.ABaseActivity;
import com.jacmobile.sensorpanellite.app.DaggerApplication;

import java.util.ArrayList;
import java.util.logging.Handler;

import javax.inject.Inject;

/**
 * Created by alex on 11/24/14.
 */
public class OmniController implements SensorEventListener
{
    @Inject Handler handler;
    @Inject SensorManager sensorManager;

    private ListView listView;
    private android.widget.HeaderViewListAdapter adapter;

    public OmniController(ListView list)
    {
        ((DaggerApplication)list.getContext().getApplicationContext()).inject(this);
        listView = list;
        adapter = (android.widget.HeaderViewListAdapter) listView.getAdapter();
    }

    private Context getActivityContext()
    {
        return this.listView.getContext();
    }

    public void onResume()
    {
        registerSensorListeners();
    }

    public void onPause()
    {
        unRegisterSensorListeners();
    }

    public void onStart()
    {

    }

    public void onStop()
    {

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
        switch (event.sensor.getType()) {
            case 1 :

                break;
            case 2 :
                break;
            case 3 :
                break;
            case 4 :
                break;
            case 5 :
                break;
            case 6 :
                break;
            case 7 :
                break;
            case 8 :
                break;
            case 9 :
                break;
            case 10 :
                break;
            case 11 :
                break;
            case 12 :
                break;
            case 13 :
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
