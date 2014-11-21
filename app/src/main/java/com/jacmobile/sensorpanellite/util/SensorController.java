package com.jacmobile.sensorpanellite.util;

import android.hardware.SensorEvent;
import android.os.Handler;

import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class SensorController {

    private static final float ALPHA = .9f;
    public static final int HISTORY_SIZE = 100;
    public static final float STANDARD_GRAVITY = 9.80665f;

    private float currentX, currentY, currentZ;

    @Inject Handler handler;

    private Navigable mSensor;
    private SensorFragment fragment;

    private long timerStartNanos;
    private boolean timerRunning;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (fragment == null || !timerRunning) return;
            fragment.setElapsedNanos(System.nanoTime() - timerStartNanos);
            fragment.updateSeries(currentX, currentY, currentZ);
            handler.postDelayed(this, 33);
        }
    };

    public void onResumeSensorFeed(SensorFragment fragment, Navigable sensor) {
        this.mSensor = sensor;
        this.fragment = fragment;
        handler.post(runnable);
    }

    public void onPauseSensorFeed() {
        this.fragment = null;
        handler.removeCallbacks(runnable);
    }

    public void restartTimer() {
        if (!timerRunning) {
            timerRunning = true;
            handler.post(runnable);
        }
        timerStartNanos = System.nanoTime();
    }

    public void useFilteredData(SensorEvent event) {
        if (isSingleSeries()) {
            setSingleSeriesData(event);
            return;
        } else if (isMagnetometer()) {
            setMagnetometerData(event);
            return;
        } else if (isGravity()) {
            setGravityData(event);
            return;
        } else {
            currentX = ALPHA * currentX + (1 - ALPHA) * event.values[0];
            currentY = ALPHA * currentY + (1 - ALPHA) * event.values[1];
            currentZ = ALPHA * currentZ + (1 - ALPHA) * event.values[2];
        }
    }

    private void setSingleSeriesData(SensorEvent event) {
        currentX = ALPHA * currentX + (1 - ALPHA) * event.values[0];
        Math.abs(currentX);
    }

    private void setMagnetometerData(SensorEvent event) {
        currentX = Math.abs(ALPHA * currentX + (1 - ALPHA) * event.values[0]);
        currentY = Math.abs(ALPHA * currentX + (1 - ALPHA) * event.values[1]);
        currentZ = Math.abs(ALPHA * currentX + (1 - ALPHA) * event.values[2]);
    }

    private void setGravityData(SensorEvent event) {
        double a = Math.round(Math.sqrt(Math.pow(event.values[0], 2)
                + Math.pow(event.values[1], 2)
                + Math.pow(event.values[2], 2)));
        currentX = (float) (Math.abs((float) (a - STANDARD_GRAVITY)) / 9.81);
    }

    private boolean isMagnetometer() {
        return this.mSensor.getName().equals("Magnetometer");
    }

    private boolean isLight() {
        return this.mSensor.getName().equals("Light");
    }

    private boolean isProximity() {
        return this.mSensor.getName().equals("Proximity");
    }

    private boolean isGravity() {
        return this.mSensor.getName().equals("Gravity");
    }

    private boolean isPressure() {
        return this.mSensor.getName().equals("Pressure");
    }

    private boolean isHumidity() {
        return this.mSensor.getName().equals("Humidity");
    }

    private boolean isDeviceTemperature() {
        return this.mSensor.getName().equals("Device Temperature");
    }

    private boolean isAmbientTemperature() {
        return this.mSensor.getName().equals("Ambient Temperature");
    }

    public boolean isSingleSeries() {
        return (isLight() || isProximity() || isGravity() || isAmbientTemperature() || isDeviceTemperature() || isHumidity() || isPressure());
    }
}