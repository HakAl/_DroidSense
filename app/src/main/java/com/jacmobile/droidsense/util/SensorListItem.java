package com.jacmobile.droidsense.util;

import android.hardware.Sensor;

import com.jacmobile.droidsense.interfaces.Navigable;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListItem implements Navigable
{
    private String url;
    private String name;
    private Sensor sensor;
    private String unitLabel;
    private String[] sensorRange;

    @Override
    public String[] getSensorRange()
    {
        return sensorRange;
    }

    public void setSensorRange(String[] sensorRange)
    {
        this.sensorRange = sensorRange;
    }

    public String getUnitLabel()
    {
        return unitLabel;
    }

    public void setUnitLabel(String unitLabel)
    {
        this.unitLabel = unitLabel;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSensor(Sensor sensor)
    {
        this.sensor = sensor;
    }

    public void setIconUrl(String url)
    {
        this.url = url;
    }

    @Override
    public Sensor getSensor()
    {
        return sensor;
    }

    @Override
    public String getIconUrl()
    {
        return url;
    }
}
