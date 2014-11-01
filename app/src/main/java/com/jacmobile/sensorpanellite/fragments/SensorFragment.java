package com.jacmobile.sensorpanellite.fragments;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplot.util.Redrawer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.activities.SensorController;
import com.jacmobile.sensorpanellite.interfaces.ContentView;
import com.jacmobile.sensorpanellite.interfaces.Navigable;
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
    private static final String WHICH_SENSOR = "sensor";

    private Navigable mSensor;

    private Redrawer drawer;
    private TextView txtTimer;
    private ImageView imgTimerBtn;
    private SimpleXYSeries xSeries = null;
    private SimpleXYSeries ySeries = null;
    private SimpleXYSeries zSeries = null;

    private int scale;
    public int[] range;
    private boolean paused = false;

    @Inject Picasso picasso;
    @Inject ContentView contentView;
    @Inject SensorManager sensorManager;
    @Inject ArrayList<Navigable> sensorData;
    @Inject SensorController sensorController;

    /**
     * @param sensor the sensor to represent
     * @return new SensorFragment for the chosen sensor
     */
    public static SensorFragment newInstance(int sensor)
    {
        Bundle args = new Bundle();
        args.putInt(WHICH_SENSOR, sensor);
        SensorFragment result = new SensorFragment();
        result.setArguments(args);
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.mSensor = this.sensorData.get(getArguments().getInt(WHICH_SENSOR));
        this.sensorManager.registerListener(this, this.mSensor.getSensor(), SensorManager.SENSOR_DELAY_UI);
        this.sensorController.onResumeSensorFeed(this, mSensor);
        return this.getSensorView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.mSensor = this.sensorData.get(getArguments().getInt(WHICH_SENSOR));
        this.sensorManager.registerListener(this, this.mSensor.getSensor(), SensorManager.SENSOR_DELAY_UI);
        sensorController.restartTimer();
        this.drawer.start();
        this.setTimerButtonImage();
    }

    @Override
    public void onPause()
    {
        paused = true;
        this.sensorController.onPauseSensorFeed();
        this.sensorManager.unregisterListener(this, this.mSensor.getSensor());
        this.mSensor = null;
        this.drawer.pause();
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.drawer.finish();
    }

    private View getSensorView()
    {
        this.range = getRange();
        this.setScale();
        ViewGroup view = contentView.getPlot(getActivity());
        XYPlot sensorPlot = (XYPlot) view.findViewById(R.id.sensor_plot);
        sensorPlot.setRangeBoundaries(range[0], range[1], BoundaryMode.FIXED);
        sensorPlot.setRangeLabel(this.mSensor.getUnitLabel());
        sensorPlot.setDomainBoundaries(0, SensorController.HISTORY_SIZE, BoundaryMode.FIXED);
        sensorPlot.setTitle(this.mSensor.getName());
        this.setSeries(sensorPlot);
        drawer = new Redrawer(sensorPlot, SensorController.HISTORY_SIZE, false);
        this.setSensorCard(view);
        return view;
    }

    private void setSeries(XYPlot sensorPlot)
    {
        if (sensorController.isSingleSeries()) {
            xSeries = new SimpleXYSeries("X");
            xSeries.useImplicitXVals();
            sensorPlot.addSeries(xSeries, new LineAndPointFormatter(Color.RED, null, null, null));
        } else {
            xSeries = new SimpleXYSeries("X");
            ySeries = new SimpleXYSeries("Y");
            zSeries = new SimpleXYSeries("Z");
            xSeries.useImplicitXVals();
            ySeries.useImplicitXVals();
            zSeries.useImplicitXVals();
            sensorPlot.addSeries(xSeries, new LineAndPointFormatter(Color.RED, null, null, null));
            sensorPlot.addSeries(ySeries, new LineAndPointFormatter(Color.CYAN, null, null, null));
            sensorPlot.addSeries(zSeries, new LineAndPointFormatter(Color.YELLOW, null, null, null));
        }
    }

    private void setSensorCard(View parent)
    {
        this.txtTimer = ((TextView) parent.findViewById(R.id.tv_sensor_timer));
        ((TextView) parent.findViewById(R.id.tv_sensor_title)).setText(this.mSensor.getName());
        ((TextView) parent.findViewById(R.id.tv_sensor_sub_title)).setText(this.mSensor.getSensor().getVendor());
        ((TextView) parent.findViewById(R.id.tv_sensor_descript)).setText(this.mSensor.getSensor().getName());
        this.imgTimerBtn = (ImageView) parent.findViewById(R.id.iv_sensor_switch);
        this.imgTimerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setTimerButton(v);
            }
        });
        this.picasso.load(
                this.mSensor.getIconUrl())
                .placeholder(R.drawable.gear)
                .into(((ImageView) parent.findViewById(R.id.iv_sensor_icon)));
    }

    private void setTimerButtonImage() {
        this.picasso.load(
                paused ?
                        R.drawable.ic_action_ic_media_play
                        : R.drawable.ic__pause).into(this.imgTimerBtn);
    }

    private void setTimerButton(View view)
    {
        if (paused) {
            paused = false;
            sensorController.onResumeSensorFeed(this, this.mSensor);
            sensorController.restartTimer();
        } else {
            paused = true;
            sensorController.onPauseSensorFeed();
        }
        setTimerButtonImage();
    }


    public void updateSeries(float... data)
    {
        if (sensorController.isSingleSeries()) {
            xSeries.setTitle(String.valueOf((float) Math.round(scale * data[0]) / scale));
            if (xSeries.size() > sensorController.HISTORY_SIZE) {
                xSeries.removeFirst();
            }
            xSeries.addLast(null, data[0]);
        } else {
            if (scale == 1) {
                xSeries.setTitle(String.valueOf(Math.round(scale * data[0]) / scale));
                ySeries.setTitle(String.valueOf(Math.round(scale * data[1]) / scale));
                zSeries.setTitle(String.valueOf(Math.round(scale * data[2]) / scale));
            } else {
                xSeries.setTitle(String.valueOf((float) Math.round(scale * data[0]) / scale));
                ySeries.setTitle(String.valueOf((float) Math.round(scale * data[1]) / scale));
                zSeries.setTitle(String.valueOf((float) Math.round(scale * data[2]) / scale));
            }
            if (zSeries.size() > sensorController.HISTORY_SIZE) {
                zSeries.removeFirst();
                ySeries.removeFirst();
                xSeries.removeFirst();
            }
            xSeries.addLast(null, data[0]);
            ySeries.addLast(null, data[1]);
            zSeries.addLast(null, data[2]);
        }
    }

    private void setScale()
    {
        if (this.range[1] >= 99) {
            this.scale = 1;
            return;
        } else if (this.range[1] >= 20) {
            this.scale = 10;
            return;
        } else {
            this.scale = 100;
        }
    }

    private int[] getRange()
    {
        return new int[]{
                Integer.valueOf(mSensor.getSensorRange()[0]),
                Integer.valueOf(mSensor.getSensorRange()[1])};
    }

    public void setElapsedNanos(long elapsedNanos)
    {
        txtTimer.setText(String.format("%.2f", elapsedNanos / 1000000000d));
    }

    @Override
    public synchronized void onSensorChanged(SensorEvent event)
    {
        sensorController.useFilteredData(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}