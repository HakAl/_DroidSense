package com.jacmobile.droidsense.config;

/**
 * Created by alex on 10/12/14.
 */
public class ImageUrls
{
    private static final String PREFIX = "http://jacmobile.com/ds_images/";

    public static final String DROIDSENSE = "http://jacmobile.com/images/droidsense.png";

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
            ACCELEROMETER, AMBIENT_TEMP, DEVICE_TEMP, GRAVITY,
            GYRO, HUMID, LIGHT, LINEAR, MAGNETIC, ORIENTATION, PRESSURE,
            PROXIMITY, ROTATION, TOUCH
    };
    public static String[] getImageUrls()
    {
        return IMAGE_URLS;
    }
}