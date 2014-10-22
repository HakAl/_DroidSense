package com.jacmobile.droidsense.fragments;

import android.app.Fragment;
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
import com.androidplot.xy.XYStepMode;
import com.jacmobile.droidsense.R;
import com.jacmobile.droidsense.interfaces.ContentView;
import com.jacmobile.droidsense.interfaces.Navigatable;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by alex on 10/19/14.
 */
public class SensorFragment extends ABaseFragment implements SensorEventListener
{
    private static final String SENSOR = "sensor";

    @Inject
    Picasso picasso;
    @Inject
    SensorManager sensorManager;
    @Inject
    LayoutInflater layoutInflater;
    @Inject
    ArrayList<Navigatable> sensorData;
    @Inject
    ContentView contentView;

//    , String sensorName, String rangeLabel
//    xyPlot.setRangeLabel(rangeLabel);
//    xyPlot.setTitle(sensorName);

    /*
     * UI widgets, and corresponding data
     */
    private TextView tvX, tvY, tvZ;
    private float currentX, currentY, currentZ;
    /*
     * SensorManager and sensor
     */
    private SensorManager jSensorManager = null;
    private Sensor jAccelerometer = null;
    /*
     * Graphing fields
     */
//    private XYPlot accelPlot = null;
    private SimpleXYSeries xSeries = null;
    private SimpleXYSeries ySeries = null;
    private SimpleXYSeries zSeries = null;
    private Redrawer drawer;
    private static final int HISTORY_SIZE = 100;

    private Navigatable mSensor;

    public static SensorFragment newInstance(int sensor)
    {
        Bundle args = new Bundle();
        args.putInt(SENSOR, sensor);
        SensorFragment result = new SensorFragment();
        result.setArguments(args);
        return result;
    }


    private void setSensor()
    {
        this.sensorManager.registerListener(this, this.mSensor.getSensor(), SensorManager.SENSOR_DELAY_UI);
    }

//    private XYPlot getGraph(ViewGroup containerView, String sensorName, String rangeLabel)
//    {
//        // Init Plot and x,y,z series
//        XYPlot xyPlot = (XYPlot) layoutInflater.inflate(R.layout.sensor_plot, containerView, false);
//        xyPlot.setTitle(sensorName);
//        xSeries = new SimpleXYSeries("X");
//        xSeries.useImplicitXVals();
//        ySeries = new SimpleXYSeries("Y");
//        ySeries.useImplicitXVals();
//        zSeries = new SimpleXYSeries("Z");
//        zSeries.useImplicitXVals();
//        // Set domain & range
//        xyPlot.setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);
//        xyPlot.setRangeBoundaries(-20, 20, BoundaryMode.FIXED);
//        xyPlot.setRangeLabel(rangeLabel);
//        // Incorporate x,y,z series
//        xyPlot.addSeries(xSeries, new LineAndPointFormatter(Color.RED, null, null, null));
//        xyPlot.addSeries(ySeries, new LineAndPointFormatter(Color.CYAN, null, null, null));
//        xyPlot.addSeries(zSeries, new LineAndPointFormatter(Color.YELLOW, null, null, null));
//        // Set drawing speed
//        xyPlot.setDomainStepMode(XYStepMode.INCREMENT_BY_VAL);
//        xyPlot.setDomainStepValue(10 / 1);
//        xyPlot.setTicksPerRangeLabel(3);
//        // Number format
//        xyPlot.setDomainValueFormat(new DecimalFormat("#"));
//        xyPlot.setRangeValueFormat(new DecimalFormat("#"));
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//        View view = inflater.inflate(R.layout.fragment_sensor, container, false);


        ViewGroup view = contentView.getPlot(getActivity());


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

    private float alpha = .9f;

    @Override
    public synchronized void onSensorChanged(SensorEvent event)
    {
        // get rid the oldest sample in history:
        if (zSeries.size() > HISTORY_SIZE) {
            zSeries.removeFirst();
            ySeries.removeFirst();
            xSeries.removeFirst();
        }
        // add the latest history sample:
        xSeries.addLast(null, event.values[0]);
        ySeries.addLast(null, event.values[1]);
        zSeries.addLast(null, event.values[2]);
        // Widget data
        currentX = alpha * currentX + (1 - alpha) * event.values[0];
        currentY = alpha * currentY + (1 - alpha) * event.values[1];
        currentZ = alpha * currentZ + (1 - alpha) * event.values[2];
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
