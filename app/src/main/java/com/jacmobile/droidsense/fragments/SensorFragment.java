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
import android.widget.ImageView;
import android.widget.TextView;

import com.jacmobile.droidsense.R;
import com.jacmobile.droidsense.interfaces.Navigatable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by alex on 10/19/14.
 */
public class SensorFragment extends ABaseFragment implements SensorEventListener
{
    private static final String SENSOR = "sensor";

    @Inject Picasso picasso;
    @Inject SensorManager sensorManager;
    @Inject LayoutInflater layoutInflater;
    @Inject ArrayList<Navigatable> sensorData;

    private Navigatable mSensor;

    public static SensorFragment newInstance(int sensor)
    {
        Bundle args = new Bundle();
        args.putInt(SENSOR, sensor);
        SensorFragment result = new SensorFragment();
        result.setArguments(args);
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);

        this.mSensor = this.sensorData.get(getArguments().getInt(SENSOR));

        ((TextView) view.findViewById(R.id.tv_sensor_title)).setText(this.mSensor.getName());
        ((TextView) view.findViewById(R.id.tv_sensor_sub_title)).setText(this.mSensor.getSensor().getVendor());
        ((TextView) view.findViewById(R.id.tv_sensor_descript)).setText(this.mSensor.getSensor().getName());
        this.picasso.load(
                this.mSensor.getIconUrl())
                .placeholder(R.drawable.gear)
                .into(((ImageView) view.findViewById(R.id.iv_sensor_icon)));
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
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
//
//    public ArrayList<Sensor> getSensors()
//    {
//        return sensors;
//    }
//
//    public Sensor getSensor(int which)
//    {
//        return sensors.get(which);
//    }
//
//    public int getCount()
//    {
//        return sensors.size();
//    }
}
