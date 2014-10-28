package com.jacmobile.sensorpanellite.activities;


import android.os.Handler;

import com.jacmobile.sensorpanellite.fragments.SensorFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class TimerController
{
    @Inject
    Handler handler;

    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (fragment == null || !timerRunning) return;
            fragment.setElapsedNanos(System.nanoTime() - timerStartNanos);
            handler.postDelayed(this, 16);
        }
    };

    private SensorFragment fragment;
    private long timerStartNanos;
    private boolean timerRunning;

    public void onResume(SensorFragment fragment)
    {
        this.fragment = fragment;
        handler.post(runnable);
    }

    public void onPause(SensorFragment fragment)
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
}