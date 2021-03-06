package com.jacmobile.sensorpanellite.interfaces;

import android.hardware.Sensor;

/**
 * Created by acorll on 10/12/2014
 */
public interface Navigable
{
    public String getName();

    public Sensor getSensor();

    public String getIconUrl();

    public String getUnitLabel();

    public String[] getSensorRange();
}