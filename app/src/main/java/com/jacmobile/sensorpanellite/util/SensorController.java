package com.jacmobile.sensorpanellite.util;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Handler;

import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class SensorController
{
    private static final float ALPHA = .9f;
    public static final int HISTORY_SIZE = 100;
    public static final float STANDARD_GRAVITY = 9.80665f;

    private float currentX, currentY, currentZ;

    @Inject Handler handler;

    private Navigable mSensor;
    private SensorFragment fragment;

    private long timerStartNanos;
    private boolean timerRunning = true;

    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (fragment == null || !timerRunning) return;
            fragment.setElapsedNanos(System.nanoTime() - timerStartNanos);
            fragment.updateSeries(currentX, currentY, currentZ);
            handler.postDelayed(this, 50);
        }
    };

    public void onResumeSensorFeed(SensorFragment fragment, Navigable sensor)
    {
        this.mSensor = sensor;
        this.fragment = fragment;
        timerRunning = false;
        handler.post(runnable);
    }

    public void onPauseSensorFeed()
    {
        this.fragment = null;
        handler.removeCallbacks(runnable);
    }

    public void restartTimer()
    {
        if (!timerRunning) {
            timerRunning = true;
            handler.post(runnable);
        }
        timerStartNanos = System.nanoTime();
    }

    public void useFilteredData(SensorEvent event)
    {
        switch (mSensor.getSensor().getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                setMagnetometerData(event);
                break;
            case Sensor.TYPE_LIGHT:
                setSingleSeriesData(event);
                break;
            case Sensor.TYPE_PRESSURE:
                setSingleSeriesData(event);
                break;
            case Sensor.TYPE_TEMPERATURE:
                setSingleSeriesData(event);
                break;
            case Sensor.TYPE_PROXIMITY:
                setSingleSeriesData(event);
                break;
            case Sensor.TYPE_GRAVITY:
                setGravityData(event);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                setSingleSeriesData(event);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                setSingleSeriesData(event);
                break;
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_ORIENTATION:
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_ROTATION_VECTOR:
                currentX = ALPHA * currentX + (1 - ALPHA) * event.values[0];
                currentY = ALPHA * currentY + (1 - ALPHA) * event.values[1];
                currentZ = ALPHA * currentZ + (1 - ALPHA) * event.values[2];
                break;
        }
    }

    private void setSingleSeriesData(SensorEvent event)
    {
        currentX = ALPHA * currentX + (1 - ALPHA) * event.values[0];
        currentX = currentX > 0 ? currentX : currentX * -1;
//        Math.abs(currentX);
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
        currentX = ((float) (a - STANDARD_GRAVITY) / 9.81f);
        currentX = Math.abs(currentX);
    }

    public boolean isSingleSeries()
    {
        int sensorType = mSensor.getSensor().getType();
        return (sensorType == Sensor.TYPE_LIGHT ||
                sensorType == Sensor.TYPE_PROXIMITY ||
                sensorType == Sensor.TYPE_GRAVITY||
                sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE ||
                sensorType == Sensor.TYPE_TEMPERATURE||
                sensorType == Sensor.TYPE_RELATIVE_HUMIDITY||
                sensorType == Sensor.TYPE_PRESSURE);
    }
}