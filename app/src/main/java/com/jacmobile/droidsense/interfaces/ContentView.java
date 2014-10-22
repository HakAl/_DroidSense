package com.jacmobile.droidsense.interfaces;

/**
 * Created by alex on 10/21/14.
 */

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;
import com.jacmobile.droidsense.R;

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
     * An {@link com.jacmobile.droidsense.interfaces.ContentView} which returns the normal activity content view.
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
            SimpleXYSeries xSeries = new SimpleXYSeries("X");
            xSeries.useImplicitXVals();
            SimpleXYSeries ySeries = new SimpleXYSeries("Y");
            ySeries.useImplicitXVals();
            SimpleXYSeries zSeries = new SimpleXYSeries("Z");
            zSeries.useImplicitXVals();
            // Set domain & range
            xyPlot.setDomainBoundaries(0, 100, BoundaryMode.FIXED);
            xyPlot.setRangeBoundaries(-20, 20, BoundaryMode.FIXED);
            // Incorporate x,y,z series
            xyPlot.addSeries(xSeries, new LineAndPointFormatter(Color.RED, null, null, null));
            xyPlot.addSeries(ySeries, new LineAndPointFormatter(Color.CYAN, null, null, null));
            xyPlot.addSeries(zSeries, new LineAndPointFormatter(Color.YELLOW, null, null, null));
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