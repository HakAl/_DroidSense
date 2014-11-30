package com.jacmobile.sensorpanellite.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplot.util.Redrawer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;
import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.interfaces.ContentView;
import com.jacmobile.sensorpanellite.interfaces.Navigable;
import com.jacmobile.sensorpanellite.interfaces.Navigator;
import com.jacmobile.sensorpanellite.util.SensorController;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by alex on 10/19/14.
 */
public class SensorFragment extends ABaseFragment implements SensorEventListener
{
    private static final String WHICH_SENSOR = "sensor";

    private Navigator navigator;
    private Navigable mSensor;

    private Redrawer drawer;
    private TextView txtTimer;
    private SwitchCompat switchTimer;
    private SimpleXYSeries xSeries = null;
    private SimpleXYSeries ySeries = null;
    private SimpleXYSeries zSeries = null;
    private XYPlot sensorPlot;
    private ImageView ivSensor;
    private Bitmap sensorBitmap;

    private int scale;
    private int[] range;
    private boolean isSingleSeries = false;

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
//        this.sensorManager.registerListener(this, this.mSensor.getSensor(), SensorManager.SENSOR_DELAY_UI);
        this.sensorController.onResumeSensorFeed(this, mSensor);
        this.navigator.setNavigationTitle(mSensor.getName());
        return this.getSensorView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.mSensor = this.sensorData.get(getArguments().getInt(WHICH_SENSOR));
        this.sensorManager.registerListener(this, this.mSensor.getSensor(), SensorManager.SENSOR_DELAY_UI);
        this.sensorController.restartTimer();
        this.drawer.start();
    }

    @Override
    public void onPause()
    {
        this.switchTimer.setChecked(false);
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

    @Override
    public void onDetach()
    {
        super.onDetach();
        this.navigator = null;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            this.navigator = (Navigator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Navigator");
        }
    }

    private View getSensorView()
    {
        this.range = getRange();
        this.setScale();
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        ViewGroup view = (ViewGroup) layoutInflater.inflate(R.layout.fragment_sensor, contentView.get(getActivity()), false);
        this.sensorPlot = (XYPlot) view.findViewById(R.id.sensor_plot);
        sensorPlot.setDomainStepMode(XYStepMode.INCREMENT_BY_VAL);
        sensorPlot.setDomainStepValue(10 / 1);
        sensorPlot.setTicksPerRangeLabel(3);
        // Number format
        sensorPlot.setDomainValueFormat(new DecimalFormat("#"));
        sensorPlot.setRangeValueFormat(new DecimalFormat("#"));
        sensorPlot.setRangeBoundaries(range[0], range[1], BoundaryMode.FIXED);
        sensorPlot.setRangeLabel(this.mSensor.getUnitLabel());
        sensorPlot.setDomainBoundaries(0, SensorController.HISTORY_SIZE, BoundaryMode.FIXED);
        this.setSeries(sensorPlot);
        drawer = new Redrawer(sensorPlot, SensorController.HISTORY_SIZE, false);
        this.setSensorCard(view);
        getPallette();
        return view;
    }


    private void setSeries(XYPlot sensorPlot)
    {
        if (sensorController.isSingleSeries()) {
            xSeries = new SimpleXYSeries("X");
            xSeries.useImplicitXVals();
            isSingleSeries = true;
        } else {
            xSeries = new SimpleXYSeries("X");
            ySeries = new SimpleXYSeries("Y");
            zSeries = new SimpleXYSeries("Z");
            xSeries.useImplicitXVals();
            ySeries.useImplicitXVals();
            zSeries.useImplicitXVals();
        }
    }

    private void getPallette()
    {
        Palette.generateAsync(sensorBitmap, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                setColor(palette);
            }
        });
    }

    private void setColor(Palette palette)
    {
        if (isSingleSeries) {
            sensorPlot.addSeries(xSeries, new LineAndPointFormatter(palette.getVibrantColor(Color.RED), null, null, null));
        } else {
            sensorPlot.addSeries(xSeries, new LineAndPointFormatter(palette.getVibrantColor(Color.RED), null, null, null));
            sensorPlot.addSeries(ySeries, new LineAndPointFormatter(palette.getLightVibrantColor(Color.CYAN), null, null, null));
            sensorPlot.addSeries(zSeries, new LineAndPointFormatter(palette.getDarkVibrantColor(Color.YELLOW), null, null, null));
        }
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            sensorBitmap = bitmap;
            ivSensor.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable)
        {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable)
        {

        }
    };

    private void setSensorCard(View parent)
    {
        this.txtTimer = ((TextView) parent.findViewById(R.id.tv_sensor_timer));
        ((TextView) parent.findViewById(R.id.tv_sensor_title)).setText(this.mSensor.getName());
        ((TextView) parent.findViewById(R.id.tv_sensor_sub_title)).setText(this.mSensor.getSensor().getVendor());
        ((TextView) parent.findViewById(R.id.tv_sensor_descript)).setText(this.mSensor.getSensor().getName());
        this.switchTimer = (SwitchCompat) parent.findViewById(R.id.switch_sensor_timer);
        this.switchTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                setTimer(isChecked);
            }
        });

        this.ivSensor = ((ImageView) parent.findViewById(R.id.iv_sensor_icon));

        this.picasso.load(
                this.mSensor.getIconUrl())
                .placeholder(R.drawable.ic_launcher)
                .into(target);
    }

    private void setTimer(boolean isChecked)
    {
        if (isChecked) {
            sensorController.onResumeSensorFeed(this, this.mSensor);
            sensorController.restartTimer();
        } else {
            sensorController.onPauseSensorFeed();
        }
    }

    public void updateSeries(float... data)
    {
        if (sensorController.isSingleSeries()) {
            xSeries.setTitle(String.format("%.2f", data[0]));
            if (xSeries.size() > sensorController.HISTORY_SIZE) {
                xSeries.removeFirst();
            }
            xSeries.addLast(null, data[0]);
        } else {
            if (scale == 1) {
                xSeries.setTitle(String.format("%.0f", data[0]));
                ySeries.setTitle(String.format("%.0f", data[1]));
                zSeries.setTitle(String.format("%.0f", data[2]));
            } else {
                xSeries.setTitle(String.format("%.0f",  data[0]));
                ySeries.setTitle(String.format("%.0f", data[1]));
                zSeries.setTitle(String.format("%.0f", data[2]));
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
        txtTimer.setText(getTimeString(elapsedNanos));
    }

    private String getTimeString(long nanos)
    {
        long second = TimeUnit.NANOSECONDS.toSeconds(nanos);
        long minute = TimeUnit.NANOSECONDS.toMinutes(nanos);
        return String.format("%02d:%02d", minute, second);
    }

    @Override
    public synchronized void onSensorChanged(SensorEvent event)
    {
        sensorController.useFilteredData(event);
    }

    private static final String[] accuracyList = {
                "High", "Low", "Medium", "No Contact", "Unreliable"
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        switch (accuracy) {
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                sensorPlot.setTitle("Accuracy: " + accuracyList[0]);

                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                sensorPlot.setTitle("Accuracy: " + accuracyList[1]);

                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                sensorPlot.setTitle("Accuracy: " + accuracyList[2]);

                break;
            case SensorManager.SENSOR_STATUS_NO_CONTACT:
                sensorPlot.setTitle("Accuracy: " + accuracyList[3]);

                break;
            case SensorManager.SENSOR_STATUS_UNRELIABLE:
                sensorPlot.setTitle("Accuracy: " + accuracyList[4]);

                break;
            default:

                break;
        }
    }
}