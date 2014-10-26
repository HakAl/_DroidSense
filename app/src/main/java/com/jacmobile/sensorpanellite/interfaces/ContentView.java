package com.jacmobile.sensorpanellite.interfaces;

/**
 * Created by alex on 10/21/14.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;
import com.jacmobile.sensorpanellite.R;

import java.text.DecimalFormat;

/**
 * An indirection which allows controlling the root container used for each activity.
 */
public interface ContentView
{
    /**
     * The root {@link android.view.ViewGroup} into which the activity should place its contents.
     */
    ViewGroup get(Activity activity);

    ViewGroup getPlot(Activity activity);

    /**
     * An {@link ContentView} which returns the normal activity content view.
     */
    ContentView DEFAULT = new ContentView()
    {
        @Override
        public ViewGroup get(Activity activity)
        {
            return (ViewGroup) activity.findViewById(android.R.id.content);
        }

        @Override
        public ViewGroup getPlot(Activity activity)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            ViewGroup view = (ViewGroup) layoutInflater.inflate(R.layout.fragment_sensor, get(activity), false);
            XYPlot xyPlot = (XYPlot) view.findViewById(R.id.sensor_plot);
            // Set drawing speed
            xyPlot.setDomainStepMode(XYStepMode.INCREMENT_BY_VAL);
            xyPlot.setDomainStepValue(10 / 1);
            xyPlot.setTicksPerRangeLabel(3);
            // Number format
            xyPlot.setDomainValueFormat(new DecimalFormat("#"));
            xyPlot.setRangeValueFormat(new DecimalFormat("#"));
            return view;
        }
    };
}