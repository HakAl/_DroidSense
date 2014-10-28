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
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplot.util.Redrawer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.activities.TimerController;
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
    private static final String SENSOR = "sensor";
    private static final float ALPHA = .9f;
    public static final float STANDARD_GRAVITY = 9.80665f;
    private static final int HISTORY_SIZE = 100;

    private Navigable mSensor;
    private int[] range;
    private int scale;
    private float currentX, currentY, currentZ;

    private Timer timer;
    private Redrawer drawer;
    private Runnable timerRunnable;
    private SimpleXYSeries xSeries = null;
    private SimpleXYSeries ySeries = null;
    private SimpleXYSeries zSeries = null;
    private TextView txtTimer;

    @Inject Picasso picasso;
    @Inject ContentView contentView;
    @Inject SensorManager sensorManager;
    @Inject ArrayList<Navigable> sensorData;
    @Inject TimerController timerController;

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
        this.range = getRange();
        this.setScale();
        sensorPlot.setRangeBoundaries(range[0], range[1], BoundaryMode.FIXED);
        sensorPlot.setRangeLabel(this.mSensor.getUnitLabel());
        sensorPlot.setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);
        sensorPlot.setTitle(this.mSensor.getName());
        this.setSeries(sensorPlot);
        drawer = new Redrawer(sensorPlot, HISTORY_SIZE, false);
        this.setSensorCard(view);
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        resumeSensorFeed();
    }

    @Override
    public void onPause()
    {
        this.timerController.onPause(this);
        super.onPause();
        pauseSensorFeed();
    }
    private void resumeSensorFeed()
    {
        this.sensorManager.registerListener(this, this.mSensor.getSensor(), SensorManager.SENSOR_DELAY_UI);
        this.drawer.start();
        setRunnable();
        this.setTimer();
    }

    private void pauseSensorFeed()
    {
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

    private void setSeries(XYPlot sensorPlot)
    {
        if (isSingleSeries()) {
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

    private void setScale()
    {
        Log.wtf("RANGE: ", this.range[1] + "");
        if (this.range[1] >= 99) {
            this.scale = 1;
            return;
        } else if (this.range[1] >= 20) {
            this.scale = 10;
            return;
        } else {
            this.scale = 100;
        }
        Log.wtf("SCALE: ", this.scale + "");
    }

    private int[] getRange()
    {
        return new int[] {
                Integer.valueOf(mSensor.getSensorRange()[0]),
                Integer.valueOf(mSensor.getSensorRange()[1]) };
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.timerController.onResume(this);
        this.timerController.restartTimer();
    }

    private void setSensorCard(View parent)
    {
        ((TextView) parent.findViewById(R.id.tv_sensor_title)).setText(this.mSensor.getName());
        ((TextView) parent.findViewById(R.id.tv_sensor_sub_title)).setText(this.mSensor.getSensor().getVendor());
        ((TextView) parent.findViewById(R.id.tv_sensor_descript)).setText(this.mSensor.getSensor().getName());
        this.txtTimer = ((TextView) parent.findViewById(R.id.tv_sensor_timer));
        parent.findViewById(R.id.iv_sensor_switch).setOnClickListener(new View.OnClickListener()
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


    boolean paused = false;
    private void setTimerButton(View view)
    {
        int resId;
        if (paused) {
            timerController.onResume(this);
            timerController.restartTimer();
            resId = R.drawable.ic__pause;
            paused = false;
            resumeSensorFeed();
        } else {
            timerController.onPause(this);
            resId = R.drawable.ic_action_ic_media_play;
            paused = true;
            pauseSensorFeed();
        }
        this.picasso.load(resId).into((ImageView) view);
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

    private void setRunnable()
    {
        if (isSingleSeries()) {
            this.scale = 100;
            this.timerRunnable =  new Runnable()
            {
                public void run()
                {
                    xSeries.setTitle(String.valueOf((float) Math.round(scale * currentX) / scale));
                }
            };
        } else if (scale == 1) {
            this.timerRunnable =  new Runnable()
            {
                public void run()
                {
                    xSeries.setTitle(String.valueOf(Math.round(scale * currentX) / scale));
                    ySeries.setTitle(String.valueOf(Math.round(scale * currentY) / scale));
                    zSeries.setTitle(String.valueOf(Math.round(scale * currentZ) / scale));
                }
            };
        } else {
            this.timerRunnable =  new Runnable()
            {
                public void run()
                {
                    xSeries.setTitle(String.valueOf((float) Math.round(scale * currentX) / scale));
                    ySeries.setTitle(String.valueOf((float) Math.round(scale * currentY) / scale));
                    zSeries.setTitle(String.valueOf((float) Math.round(scale * currentZ) / scale));
                }
            };
        }
    }

    private void updateGUI()
    {
        getActivity().runOnUiThread(this.timerRunnable);
    }

    @Override
    public synchronized void onSensorChanged(SensorEvent event)
    {
        useFilteredData(event);
    }

    private void useFilteredData(SensorEvent event)
    {
         if (isLight() || isProximity() || isHumidity() || isDeviceTemperature() || isAmbientTemperature() || isPressure()) {
             setSingleSeries(event);
             return;
        } else if (isMagnetometer()) {
             setMagnetometerData(event);
        } else if(isGravity()) {
             setGravityData(event);
             setSingleSeries(event);
             return;
         } else {
            currentX = ALPHA * currentX + (1 - ALPHA) * event.values[0];
            currentY = ALPHA * currentY + (1 - ALPHA) * event.values[1];
            currentZ = ALPHA * currentZ + (1 - ALPHA) * event.values[2];
        }
        if (zSeries.size() > HISTORY_SIZE) {
            zSeries.removeFirst();
            ySeries.removeFirst();
            xSeries.removeFirst();
        }
        xSeries.addLast(null, currentX);
        ySeries.addLast(null, currentY);
        zSeries.addLast(null, currentZ);
    }

    private void setMagnetometerData(SensorEvent event)
    {
        currentX = Math.abs(ALPHA * currentX + (1 - ALPHA) * event.values[0]);
        currentY = Math.abs(ALPHA * currentX + (1 - ALPHA) * event.values[1]);
        currentZ = Math.abs(ALPHA * currentX + (1 - ALPHA) * event.values[2]);
    }

    private void setGravityData(SensorEvent event)
    {
        double a = Math.round(Math.sqrt(Math.pow(event.values[0], 2)
                + Math.pow(event.values[1], 2)
                + Math.pow(event.values[2], 2)));
        currentX = (float) ( Math.abs((float) (a - STANDARD_GRAVITY)) / 9.81 );
    }

    private void setSingleSeries(SensorEvent event)
    {
        currentX = ALPHA * currentX + (1 - ALPHA) * event.values[0];
        if (xSeries.size() > HISTORY_SIZE) {
            xSeries.removeFirst();
        }
        xSeries.addLast(null, currentX);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }

    private boolean isMagnetometer()
    {
        return this.mSensor.getName().equals("Magnetometer");
    }

    private boolean isSingleSeries()
    {
        return (isLight() || isProximity() || isGravity() || isAmbientTemperature() || isDeviceTemperature() || isHumidity() || isPressure());
    }

    private boolean isLight()
    {
        return this.mSensor.getName().equals("Light");
    }

    private boolean isProximity()
    {
        return this.mSensor.getName().equals("Proximity");
    }

    private boolean isGravity()
    {
        return this.mSensor.getName().equals("Gravity");
    }

    private boolean isPressure()
    {
        return this.mSensor.getName().equals("Pressure");
    }

    private boolean isHumidity()
    {
        return this.mSensor.getName().equals("Humidity");
    }

    private boolean isDeviceTemperature()
    {
        return this.mSensor.getName().equals("Device Temperature");
    }

    private boolean isAmbientTemperature()
    {
        return this.mSensor.getName().equals("Ambient Temperature");
    }

    public void setElapsedNanos(long elapsedNanos)
    {
        txtTimer.setText(String.format("%.2f", elapsedNanos / 1000000000d));
    }
}