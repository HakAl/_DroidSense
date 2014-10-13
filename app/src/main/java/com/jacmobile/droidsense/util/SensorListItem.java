package com.jacmobile.droidsense.util;

import com.jacmobile.droidsense.interfaces.Navigatable;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListItem implements Navigatable
{
    private String name;
    private String iconUrl;
    private String description;

    @Override
    public String getIconUrl()
    {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
