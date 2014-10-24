package com.jacmobile.droidsense.config;

/**
 * Created by alex on 10/22/14.
 */
public class SensorPlotConfig
{
    private String[] domain;
    private String[] range;
    private String[] lineLabels;
    private String[] units;


    public String[] getDomain()
    {
        return domain;
    }

    public void setDomain(String[] domain)
    {
        this.domain = domain;
    }

    public String[] getRange()
    {
        return range;
    }

    public void setRange(String[] range)
    {
        this.range = range;
    }

    public String[] getLineLabels()
    {
        return lineLabels;
    }

    public void setLineLabels(String[] lineLabels)
    {
        this.lineLabels = lineLabels;
    }

    public String[] getUnits()
    {
        return units;
    }

    public void setUnits(String[] units)
    {
        this.units = units;
    }
}
