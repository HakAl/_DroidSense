package com.jacmobile.droidsense.util;

import android.hardware.Sensor;

import com.jacmobile.droidsense.interfaces.Navigatable;

import java.util.HashMap;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListItem implements Navigatable
{
    private String url;
    private String name;
    private Sensor sensor;
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
