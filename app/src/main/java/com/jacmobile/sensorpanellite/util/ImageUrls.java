package com.jacmobile.sensorpanellite.util;

/**
 * Created by alex on 10/12/14.
 */
public class ImageUrls
{
    private static final String PREFIX = "http://jacmobile.com/ds_images/";

    public static final String ACCELEROMETER = PREFIX + "accel.png";
    public static final String AMBIENT_TEMP = PREFIX + "ambtemp.png";
    public static final String DEVICE_TEMP = PREFIX + "devicetemp.png";
    public static final String GRAVITY = PREFIX + "gravity.png";
    public static final String GYRO = PREFIX + "gyro.png";
    public static final String HUMID = PREFIX + "humid.png";
    public static final String LIGHT = PREFIX + "light.png";
    public static final String LINEAR = PREFIX + "lineaccel.png";
    public static final String MAGNETIC = PREFIX + "magnet.png";
    public static final String ORIENTATION = PREFIX + "orient.png";
    public static final String PRESSURE = PREFIX + "pressure.png";
    public static final String PROXIMITY = PREFIX + "prox.png";
    public static final String ROTATION = PREFIX + "rotation.png";
    public static final String TOUCH = PREFIX + "touch.png";

    private static final String[] IMAGE_URLS = {
            TOUCH, ACCELEROMETER, MAGNETIC, ORIENTATION, GYRO,
            LIGHT,PRESSURE, DEVICE_TEMP, PROXIMITY, GRAVITY,
            LINEAR, ROTATION, HUMID, AMBIENT_TEMP
    };
    public static String[] getImageUrls()
    {
        return IMAGE_URLS;
    }
}