package com.jacmobile.droidsense.fragments;

import android.graphics.Color;
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

import com.androidplot.util.Redrawer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.jacmobile.droidsense.R;
import com.jacmobile.droidsense.interfaces.ContentView;
import com.jacmobile.droidsense.interfaces.Navigable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * Created by alex on 10/19/14.
 */
public class SensorFragment extends ABaseFragment implements SensorEventListener
{
    private static final String SENSOR = "sensor";
    private static final float ALPHA = .9f;

    private Navigable mSensor;
    private static final int HISTORY_SIZE = 100;
    private float currentX, currentY, currentZ;

    private Timer timer;
    private Redrawer drawer;
    private Runnable timerRunnable;
    private SimpleXYSeries xSeries = null;
    private SimpleXYSeries ySeries = null;
    private SimpleXYSeries zSeries = null;

    @Inject Picasso picasso;
    @Inject ContentView contentView;
    @Inject SensorManager sensorManager;
    @Inject ArrayList<Navigable> sensorData;

    /**
     * @param sensor the sensor to represent
     * @return new SensorFragment for the chosen sensor
     */
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
        ViewGroup view = contentView.getPlot(getActivity());
        this.mSensor = this.sensorData.get(getArguments().getInt(SENSOR));
        XYPlot sensorPlot = (XYPlot) view.findViewById(R.id.sensor_plot);
        sensorPlot.setRangeBoundaries(-20, 20, BoundaryMode.FIXED);
        sensorPlot.setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);
        sensorPlot.setTitle(this.mSensor.getName());
        xSeries = new SimpleXYSeries("X");
        ySeries = new SimpleXYSeries("Y");
        zSeries = new SimpleXYSeries("Z");
        xSeries.useImplicitXVals();
        ySeries.useImplicitXVals();
        zSeries.useImplicitXVals();
        sensorPlot.addSeries(xSeries, new LineAndPointFormatter(Color.RED, null, null, null));
        sensorPlot.addSeries(ySeries, new LineAndPointFormatter(Color.CYAN, null, null, null));
        sensorPlot.addSeries(zSeries, new LineAndPointFormatter(Color.YELLOW, null, null, null));
        drawer = new Redrawer(sensorPlot, HISTORY_SIZE, false);
        this.setSensorCard(view);
        return view;
    }

    private void setSensorCard(View parent)
    {
        ((TextView) parent.findViewById(R.id.tv_sensor_title)).setText(this.mSensor.getName());
        ((TextView) parent.findViewById(R.id.tv_sensor_sub_title)).setText(this.mSensor.getSensor().getVendor());
        ((TextView) parent.findViewById(R.id.tv_sensor_descript)).setText(this.mSensor.getSensor().getName());
        this.picasso.load(
                this.mSensor.getIconUrl())
                .placeholder(R.drawable.gear)
                .into(((ImageView) parent.findViewById(R.id.iv_sensor_icon)));
    }

    private void setTimer()
    {
        this.timer = new Timer("timer");
        this.timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                updateGUI();
            }
        }, 0, 167);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.sensorManager.registerListener(this, this.mSensor.getSensor(), SensorManager.SENSOR_DELAY_UI);
        this.drawer.start();
        this.timerRunnable =  new Runnable()
        {
            public void run()
            {
                xSeries.setTitle(String.valueOf((float) Math.round(100 * currentX) / 100));
                ySeries.setTitle(String.valueOf((float) Math.round(100 * currentY) / 100));
                zSeries.setTitle(String.valueOf((float) Math.round(100 * currentZ) / 100));
            }
        };
        this.setTimer();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.sensorManager.unregisterListener(this, this.mSensor.getSensor());
        this.drawer.pause();
        this.timer.cancel();
        this.timerRunnable = null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.drawer.finish();
    }

    private void updateGUI()
    {
        getActivity().runOnUiThread(this.timerRunnable);
    }

    @Override
    public synchronized void onSensorChanged(SensorEvent event)
    {
        currentX = ALPHA * currentX + (1 - ALPHA) * event.values[0];
        currentY = ALPHA * currentY + (1 - ALPHA) * event.values[1];
        currentZ = ALPHA * currentZ + (1 - ALPHA) * event.values[2];
        if (zSeries.size() > HISTORY_SIZE) {
            zSeries.removeFirst();
            ySeries.removeFirst();
            xSeries.removeFirst();
        }
        xSeries.addLast(null, currentX);
        ySeries.addLast(null, currentY);
        zSeries.addLast(null, currentZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}