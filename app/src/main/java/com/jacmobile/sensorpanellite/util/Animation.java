package com.jacmobile.sensorpanellite.util;

import android.view.View;

public abstract class Animation
{
    public static final int DURATION = 500;

    View view;

    /**
     * This method animates the properties of the view specific to the Animation
     * object.
     */
    public abstract void animate();
}