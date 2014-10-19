package com.jacmobile.droidsense.util;

/**
 * Created by alex on 10/12/14.
 */
public class DeviceInfo
{

    public static DeviceInfo newInstance()
    {
        return new DeviceInfo();
    }

    /*
     * Model is the end-user-visible name of the product
     */
    public static String getModel()
    {
        return android.os.Build.MODEL;
    }

    /*
     * The name of the overall product
     */
    public static String getProduct()
    {
        return android.os.Build.PRODUCT;
    }

    /*
     * OS (kernel) version
     */
    public static String getOSVersion()
    {
        return System.getProperty("os.version");
    }

    /*
     * Internal value used by underlying source control to represent this build.
     */
    public static String getOSVersionIncremental()
    {
        return android.os.Build.VERSION.INCREMENTAL;
    }

    /*
     * The user-visible SDK version of the framework;
     * its possible values are defined in Build.VERSION_CODES
     */
    public static String getSDKVersion()
    {
        return String.valueOf(android.os.Build.VERSION.SDK_INT);
    }


    private DeviceInfo()
    {
    }
}
