package com.jacmobile.sensorpanellite.util;

import android.hardware.Sensor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by alex on 10/19/14.
 */
public class SensorsAdapter extends BaseAdapter {
    private ArrayList<Sensor> sensors;

    public SensorsAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return this.sensors.size();
    }

    @Override
    public Object getItem(int position) {
        return this.sensors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}